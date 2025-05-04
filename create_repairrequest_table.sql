-- RepairRequest table creation script
CREATE TABLE RepairRequest (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    issue_description TEXT NOT NULL,
    request_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'APPROVED', 'ASSIGNED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    preferred_date DATETIME,
    notes TEXT,
    is_urgent BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id) ON DELETE RESTRICT
);

-- Create indexes for better query performance
CREATE INDEX idx_repairrequest_user ON RepairRequest(user_id);
CREATE INDEX idx_repairrequest_vehicle ON RepairRequest(vehicle_id);
CREATE INDEX idx_repairrequest_status ON RepairRequest(status);
CREATE INDEX idx_repairrequest_urgent ON RepairRequest(is_urgent); 