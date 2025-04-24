-- Vehicle Maintenance Management System Database Schema

-- User table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    address VARCHAR(255),
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Vehicle table
CREATE TABLE Vehicle (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT,
    color VARCHAR(30),
    VIN VARCHAR(50) UNIQUE,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Maintenance Staff table
CREATE TABLE MaintenanceStaff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    hourly_rate DECIMAL(10,2) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    hire_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Administrator table
CREATE TABLE Administrator (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    access_level INT DEFAULT 1
);

-- Work Order table
CREATE TABLE WorkOrder (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    staff_id INT,
    issue_description TEXT NOT NULL,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'assigned', 'in_progress', 'completed', 'cancelled') DEFAULT 'pending',
    priority ENUM('low', 'medium', 'high') DEFAULT 'medium',
    estimated_completion DATETIME,
    actual_completion DATETIME,
    total_labor_cost DECIMAL(10,2) DEFAULT 0.00,
    total_parts_cost DECIMAL(10,2) DEFAULT 0.00,
    total_cost DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id) ON DELETE RESTRICT,
    FOREIGN KEY (staff_id) REFERENCES MaintenanceStaff(staff_id) ON DELETE SET NULL
);

-- Repair Record table
CREATE TABLE RepairRecord (
    repair_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    staff_id INT NOT NULL,
    repair_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    repair_description TEXT NOT NULL,
    labor_hours DECIMAL(5,2) DEFAULT 0.00,
    status ENUM('started', 'in_progress', 'completed') DEFAULT 'started',
    notes TEXT,
    FOREIGN KEY (order_id) REFERENCES WorkOrder(order_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES MaintenanceStaff(staff_id) ON DELETE RESTRICT
);

-- Material table
CREATE TABLE Material (
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    unit_price DECIMAL(10,2) NOT NULL,
    current_stock INT DEFAULT 0,
    min_stock_level INT DEFAULT 5,
    supplier VARCHAR(100)
);

-- Material Usage table
CREATE TABLE MaterialUsage (
    usage_id INT AUTO_INCREMENT PRIMARY KEY,
    repair_id INT NOT NULL,
    material_id INT NOT NULL,
    quantity INT NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    usage_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (repair_id) REFERENCES RepairRecord(repair_id) ON DELETE CASCADE,
    FOREIGN KEY (material_id) REFERENCES Material(material_id) ON DELETE RESTRICT
);

-- Feedback table
CREATE TABLE Feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    feedback_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_urgent BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (order_id) REFERENCES WorkOrder(order_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Payment Record table
CREATE TABLE PaymentRecord (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50) NOT NULL,
    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
    transaction_id VARCHAR(100),
    FOREIGN KEY (order_id) REFERENCES WorkOrder(order_id) ON DELETE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE RESTRICT
);

-- Staff Payment table
CREATE TABLE StaffPayment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_id INT NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    hours_worked DECIMAL(10,2) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'completed') DEFAULT 'pending',
    FOREIGN KEY (staff_id) REFERENCES MaintenanceStaff(staff_id) ON DELETE RESTRICT
);

-- Audit Log table
CREATE TABLE AuditLog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    action_type VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id INT NOT NULL,
    user_type VARCHAR(20) NOT NULL,
    user_id INT NOT NULL,
    action_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    ip_address VARCHAR(50)
);

-- Triggers for maintaining data consistency

-- Update WorkOrder total costs when a new material usage is recorded
DELIMITER //
CREATE TRIGGER update_workorder_costs AFTER INSERT ON MaterialUsage
FOR EACH ROW
BEGIN
    DECLARE work_order_id INT;
    
    -- Get the work order ID from the repair record
    SELECT order_id INTO work_order_id 
    FROM RepairRecord 
    WHERE repair_id = NEW.repair_id;
    
    -- Update the work order's total parts cost and total cost
    UPDATE WorkOrder 
    SET total_parts_cost = (
            SELECT SUM(cost) 
            FROM MaterialUsage mu 
            JOIN RepairRecord rr ON mu.repair_id = rr.repair_id 
            WHERE rr.order_id = work_order_id
        ),
        total_cost = total_labor_cost + (
            SELECT SUM(cost) 
            FROM MaterialUsage mu 
            JOIN RepairRecord rr ON mu.repair_id = rr.repair_id 
            WHERE rr.order_id = work_order_id
        )
    WHERE order_id = work_order_id;
END//
DELIMITER ;

-- Update WorkOrder labor costs when a repair record is updated
DELIMITER //
CREATE TRIGGER update_labor_costs AFTER UPDATE ON RepairRecord
FOR EACH ROW
BEGIN
    DECLARE hourly_rate DECIMAL(10,2);
    
    -- Get the staff hourly rate
    SELECT hourly_rate INTO hourly_rate 
    FROM MaintenanceStaff 
    WHERE staff_id = NEW.staff_id;
    
    -- Update the work order's total labor cost and total cost if labor hours have changed
    IF NEW.labor_hours != OLD.labor_hours THEN
        UPDATE WorkOrder 
        SET total_labor_cost = (
                SELECT SUM(rr.labor_hours * ms.hourly_rate) 
                FROM RepairRecord rr 
                JOIN MaintenanceStaff ms ON rr.staff_id = ms.staff_id 
                WHERE rr.order_id = NEW.order_id
            ),
            total_cost = (
                SELECT SUM(rr.labor_hours * ms.hourly_rate) 
                FROM RepairRecord rr 
                JOIN MaintenanceStaff ms ON rr.staff_id = ms.staff_id 
                WHERE rr.order_id = NEW.order_id
            ) + total_parts_cost
        WHERE order_id = NEW.order_id;
    END IF;
    
    -- Update the work order status if the repair status has changed to completed
    IF NEW.status = 'completed' AND OLD.status != 'completed' THEN
        -- Check if all repairs for this order are completed
        IF NOT EXISTS (
            SELECT 1 FROM RepairRecord 
            WHERE order_id = NEW.order_id AND status != 'completed'
        ) THEN
            UPDATE WorkOrder 
            SET status = 'completed', actual_completion = CURRENT_TIMESTAMP 
            WHERE order_id = NEW.order_id;
        END IF;
    END IF;
END//
DELIMITER ;

-- Decrement material stock when used in a repair
DELIMITER //
CREATE TRIGGER update_material_stock AFTER INSERT ON MaterialUsage
FOR EACH ROW
BEGIN
    UPDATE Material 
    SET current_stock = current_stock - NEW.quantity 
    WHERE material_id = NEW.material_id;
END//
DELIMITER ;

-- Create views for common queries

-- User vehicle history view
CREATE VIEW UserVehicleHistory AS
SELECT 
    u.user_id, 
    u.name AS user_name, 
    v.vehicle_id, 
    v.license_plate, 
    v.brand, 
    v.model, 
    wo.order_id, 
    wo.creation_date, 
    wo.status, 
    wo.total_cost,
    ms.name AS staff_name
FROM 
    User u
    JOIN Vehicle v ON u.user_id = v.user_id
    JOIN WorkOrder wo ON v.vehicle_id = wo.vehicle_id
    LEFT JOIN MaintenanceStaff ms ON wo.staff_id = ms.staff_id;

-- Repair staff performance view
CREATE VIEW StaffPerformance AS
SELECT 
    ms.staff_id, 
    ms.name, 
    ms.specialty, 
    COUNT(DISTINCT wo.order_id) AS total_orders,
    SUM(rr.labor_hours) AS total_labor_hours,
    SUM(rr.labor_hours * ms.hourly_rate) AS total_earnings,
    AVG(f.rating) AS average_rating
FROM 
    MaintenanceStaff ms
    LEFT JOIN WorkOrder wo ON ms.staff_id = wo.staff_id
    LEFT JOIN RepairRecord rr ON wo.order_id = rr.order_id AND rr.staff_id = ms.staff_id
    LEFT JOIN Feedback f ON wo.order_id = f.order_id
GROUP BY 
    ms.staff_id, ms.name, ms.specialty;

-- Material consumption report view
CREATE VIEW MaterialConsumptionReport AS
SELECT 
    m.material_id, 
    m.name, 
    m.unit_price, 
    SUM(mu.quantity) AS total_used,
    SUM(mu.cost) AS total_cost,
    m.current_stock
FROM 
    Material m
    LEFT JOIN MaterialUsage mu ON m.material_id = mu.material_id
GROUP BY 
    m.material_id, m.name, m.unit_price, m.current_stock;

-- Financial summary view
CREATE VIEW FinancialSummary AS
SELECT 
    YEAR(wo.creation_date) AS year,
    MONTH(wo.creation_date) AS month,
    COUNT(wo.order_id) AS total_orders,
    SUM(wo.total_labor_cost) AS total_labor_revenue,
    SUM(wo.total_parts_cost) AS total_parts_revenue,
    SUM(wo.total_cost) AS total_revenue,
    SUM(sp.amount) AS total_staff_payments,
    SUM(wo.total_cost) - SUM(IFNULL(sp.amount, 0)) AS gross_profit
FROM 
    WorkOrder wo
    LEFT JOIN StaffPayment sp ON YEAR(sp.period_end) = YEAR(wo.creation_date) AND MONTH(sp.period_end) = MONTH(wo.creation_date)
WHERE 
    wo.status = 'completed'
GROUP BY 
    YEAR(wo.creation_date), MONTH(wo.creation_date)
ORDER BY 
    year DESC, month DESC; 