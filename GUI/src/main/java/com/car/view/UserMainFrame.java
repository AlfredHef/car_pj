package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class UserMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private User currentUser;
    private BackendService backendService;
    
    // Define UI colors
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color BG_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black

    // Add new fields for vehicle management
    private DefaultTableModel vehicleTableModel;
    private JTable vehicleTable;
    private JScrollPane vehicleScrollPane;
    private JPanel vehicleEmptyPanel;

    public UserMainFrame() {
        this(null);
    }
    
    public UserMainFrame(User user) {
        this.currentUser = user;
        initializeUI();
    }
    
    public void setBackendService(BackendService backendService) {
        this.backendService = backendService;
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 用户界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        // Set application icon if available
        // setIconImage(new ImageIcon("path/to/icon.png").getImage());
        
        // Main container panel with gradient background
        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, SECONDARY_COLOR, 0, getHeight(), 
                        BG_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 10, 10, 10));
        setContentPane(contentPane);
        
        // Header panel with user welcome
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Create custom styled tabbed pane
        tabbedPane = createStyledTabbedPane();
        
        // Add tabs with icons
        ImageIcon vehicleIcon = createIcon("\uD83D\uDE97", 18); // Car emoji
        ImageIcon requestIcon = createIcon("\uD83D\uDD27", 18); // Wrench emoji
        ImageIcon historyIcon = createIcon("\uD83D\uDCC3", 18); // Document emoji
        ImageIcon profileIcon = createIcon("\uD83D\uDC64", 18); // User emoji
        
        tabbedPane.addTab("车辆信息", vehicleIcon, createVehiclePanel());
        tabbedPane.addTab("维修申请", requestIcon, createRepairRequestPanel());
        tabbedPane.addTab("维修记录", historyIcon, createRepairHistoryPanel());
        tabbedPane.addTab("个人信息", profileIcon, createProfilePanel());
        
        // Add some padding around the tabbed pane
        JPanel tabbedPaneContainer = new JPanel(new BorderLayout());
        tabbedPaneContainer.setOpaque(false);
        tabbedPaneContainer.setBorder(new EmptyBorder(10, 5, 5, 5));
        tabbedPaneContainer.add(tabbedPane, BorderLayout.CENTER);
        
        contentPane.add(tabbedPaneContainer, BorderLayout.CENTER);
        
        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        JLabel statusLabel = new JLabel("连接状态: 已连接");
        statusLabel.setForeground(new Color(60, 60, 60));
        footerPanel.add(statusLabel);
        
        contentPane.add(footerPanel, BorderLayout.SOUTH);
        
        // Set welcome title based on user info
        if (currentUser != null) {
            setTitle("车辆维修管理系统 - 用户界面 - 欢迎 " + currentUser.getName());
        }
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(5, 5, 15, 5));
        
        // System title on left
        JLabel titleLabel = new JLabel("车辆维修管理系统");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // User welcome message on right
        if (currentUser != null) {
            JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            userInfoPanel.setOpaque(false);
            
            JLabel welcomeLabel = new JLabel("欢迎, " + currentUser.getName());
            welcomeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            welcomeLabel.setForeground(TEXT_COLOR);
            
            JButton logoutButton = new JButton("退出登录");
            logoutButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            logoutButton.addActionListener(e -> handleLogout());
            
            userInfoPanel.add(welcomeLabel);
            userInfoPanel.add(Box.createHorizontalStrut(15));
            userInfoPanel.add(logoutButton);
            
            headerPanel.add(userInfoPanel, BorderLayout.EAST);
        }
        
        return headerPanel;
    }
    
    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Custom painting if needed
            }
        };
        
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                lightHighlight = SECONDARY_COLOR;
                shadow = PRIMARY_COLOR;
                darkShadow = PRIMARY_COLOR;
                focus = ACCENT_COLOR;
            }
            
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2d.setColor(ACCENT_COLOR);
                    g2d.drawLine(x, y + h, x + w, y + h);
                }
                
                g2d.dispose();
            }
            
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2d.setColor(SECONDARY_COLOR);
                } else {
                    g2d.setColor(new Color(225, 225, 235));
                }
                
                g2d.fillRect(x, y, w, h);
                g2d.dispose();
            }
        });
        
        tabbedPane.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        tabbedPane.setForeground(TEXT_COLOR);
        tabbedPane.setBackground(Color.WHITE);
        
        return tabbedPane;
    }
    
    private ImageIcon createIcon(String unicode, int size) {
        JLabel label = new JLabel(unicode);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, size));
        label.setForeground(PRIMARY_COLOR);
        
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        label.paint(g2d);
        g2d.dispose();
        
        return new ImageIcon(image);
    }

    private JPanel createVehiclePanel() {
        JPanel panel = createStyledPanel("车辆信息");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create action panel with buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        JButton addVehicleBtn = createStyledButton("添加车辆", ACCENT_COLOR);
        addVehicleBtn.addActionListener(e -> openAddVehicleDialog());
        
        JButton refreshBtn = createStyledButton("刷新", new Color(100, 180, 100));
        refreshBtn.addActionListener(e -> refreshVehiclesList());
        
        actionPanel.add(addVehicleBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshBtn);
        
        contentPanel.add(actionPanel, BorderLayout.NORTH);
        
        // Create table to display vehicles
        String[] columnNames = {"车牌号", "品牌", "型号", "年份", "颜色", "操作"};
        Object[][] data = new Object[0][6]; // Empty data, will be populated dynamically
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the action column is editable
            }
        };
        
        JTable vehiclesTable = new JTable(tableModel);
        vehiclesTable.setFillsViewportHeight(true);
        vehiclesTable.setRowHeight(35);
        vehiclesTable.getTableHeader().setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        vehiclesTable.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        
        // Set custom renderer for the action column
        vehiclesTable.getColumnModel().getColumn(5).setCellRenderer(new TableButtonRenderer());
        vehiclesTable.getColumnModel().getColumn(5).setCellEditor(new TableButtonEditor(new JCheckBox(), this));
        
        JScrollPane scrollPane = new JScrollPane(vehiclesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
        
        // Create empty state panel
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);
        JLabel emptyLabel = new JLabel("暂无车辆信息，点击添加车辆添加您的第一辆车", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        emptyLabel.setForeground(new Color(150, 150, 150));
        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        
        // Initially add the empty panel
        contentPanel.add(emptyPanel, BorderLayout.CENTER);
        
        // Store references for later use
        vehicleTableModel = tableModel;
        vehicleTable = vehiclesTable;
        vehicleScrollPane = scrollPane;
        vehicleEmptyPanel = emptyPanel;
        
        // Load vehicles data
        refreshVehiclesList();
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRepairRequestPanel() {
        JPanel panel = createStyledPanel("维修申请");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create action panel with new request button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        JButton newRequestBtn = createStyledButton("新建申请", ACCENT_COLOR);
        newRequestBtn.addActionListener(e -> openNewRepairRequestDialog());
        actionPanel.add(newRequestBtn);
        
        // Create table to display ongoing repair requests
        String[] columnNames = {"申请编号", "车辆", "提交日期", "状态", "紧急"};
        Object[][] data = new Object[0][5]; // Placeholder data, will be populated dynamically
        
        JTable requestsTable = new JTable(data, columnNames);
        requestsTable.setFillsViewportHeight(true);
        requestsTable.setRowHeight(30);
        requestsTable.getTableHeader().setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        requestsTable.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        
        // Set custom renderer for the "紧急" column
        requestsTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Boolean) {
                    boolean urgent = (Boolean) value;
                    label.setText(urgent ? "是" : "否");
                    label.setForeground(urgent ? Color.RED : TEXT_COLOR);
                }
                return label;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(requestsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
        
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);
        JLabel emptyLabel = new JLabel("暂无维修申请记录", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        emptyLabel.setForeground(new Color(150, 150, 150));
        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        
        // Add components to content panel
        contentPanel.add(actionPanel, BorderLayout.NORTH);
        
        // TODO: Replace this placeholder with data from backend
        // For now, we'll just show the empty state
        contentPanel.add(emptyPanel, BorderLayout.CENTER);
        // When data is available:
        // contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRepairHistoryPanel() {
        JPanel panel = createStyledPanel("维修记录");
        
        // Create content panel with placeholder
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual repair history panel
        JLabel notImplementedLabel = new JLabel("维修记录功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = createStyledPanel("个人信息");
        
        if (currentUser != null) {
            // Card layout for profile information
            JPanel profileCard = new JPanel();
            profileCard.setOpaque(false);
            profileCard.setLayout(new BorderLayout(15, 15));
            profileCard.setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    new EmptyBorder(25, 25, 25, 25)));
            
            // User avatar panel (placeholder)
            JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            avatarPanel.setOpaque(false);
            avatarPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
            
            JLabel avatarLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Draw circle background
                    g2.setColor(PRIMARY_COLOR);
                    g2.fillOval(0, 0, 80, 80);
                    
                    // Draw text in circle
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 32));
                    FontMetrics fm = g2.getFontMetrics();
                    String initial = currentUser.getName().substring(0, 1).toUpperCase();
                    int textWidth = fm.stringWidth(initial);
                    int textHeight = fm.getHeight();
                    g2.drawString(initial, (80 - textWidth) / 2, ((80 - textHeight) / 2) + fm.getAscent());
                    
                    g2.dispose();
                }
            };
            avatarLabel.setPreferredSize(new Dimension(80, 80));
            
            // User name under avatar
            JLabel nameLabel = new JLabel(currentUser.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            nameLabel.setForeground(TEXT_COLOR);
            
            avatarPanel.add(avatarLabel);
            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.setOpaque(false);
            namePanel.add(nameLabel, BorderLayout.CENTER);
            avatarPanel.add(namePanel);
            
            // User details panel
            JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 15, 15));
            detailsPanel.setOpaque(false);
            
            addDetailField(detailsPanel, "用户名:", currentUser.getUsername());
            addDetailField(detailsPanel, "电话:", currentUser.getPhone() != null ? currentUser.getPhone() : "--");
            addDetailField(detailsPanel, "邮箱:", currentUser.getEmail() != null ? currentUser.getEmail() : "--");
            addDetailField(detailsPanel, "地址:", currentUser.getAddress() != null ? currentUser.getAddress() : "--");
            addDetailField(detailsPanel, "注册日期:", currentUser.getRegistrationDate() != null ? 
                    currentUser.getRegistrationDate().toString() : "--");
            
            // Action buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
            
            JButton editButton = createStyledButton("编辑个人信息", ACCENT_COLOR);
            editButton.setPreferredSize(new Dimension(150, 40));
            editButton.addActionListener(e -> {
                // TODO: 实现编辑个人信息功能
                JOptionPane.showMessageDialog(this, 
                    "编辑个人信息功能完善中", 
                    "功能开发中", JOptionPane.INFORMATION_MESSAGE);
            });
            
            JButton changePasswordButton = createStyledButton("修改密码", new Color(100, 100, 100));
            changePasswordButton.setPreferredSize(new Dimension(150, 40));
            changePasswordButton.addActionListener(e -> {
                // TODO: 实现修改密码功能
                JOptionPane.showMessageDialog(this, 
                    "修改密码功能完善中", 
                    "功能开发中", JOptionPane.INFORMATION_MESSAGE);
            });
            
            buttonPanel.add(editButton);
            buttonPanel.add(changePasswordButton);
            
            // Assemble profile card
            profileCard.add(avatarPanel, BorderLayout.NORTH);
            profileCard.add(detailsPanel, BorderLayout.CENTER);
            profileCard.add(buttonPanel, BorderLayout.SOUTH);
            
            // Add the card to a container with some spacing
            JPanel containerPanel = new JPanel(new GridBagLayout());
            containerPanel.setOpaque(false);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            
            containerPanel.add(profileCard, gbc);
            panel.add(containerPanel, BorderLayout.CENTER);
        } else {
            JLabel errorLabel = new JLabel("无法获取用户信息", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            panel.add(errorLabel, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Optional: Add a title to the panel
        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
            titleLabel.setForeground(PRIMARY_COLOR);
            titleLabel.setBorder(new EmptyBorder(0, 15, 10, 15));
            panel.add(titleLabel, BorderLayout.NORTH);
        }
        
        return panel;
    }
    
    private void addDetailField(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        labelComponent.setForeground(new Color(100, 100, 100));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        valueComponent.setForeground(TEXT_COLOR);
        
        panel.add(labelComponent);
        panel.add(valueComponent);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint button background with rounded corners
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));
        
        return button;
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
                "确定要退出登录吗？", "退出登录",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            if (this.backendService != null) {
                loginFrame.setBackendService(this.backendService);
            }
            loginFrame.setVisible(true);
        }
    }
    
    private void openNewRepairRequestDialog() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, 
                "无法获取用户信息，请重新登录", 
                "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            RepairRequestDialog dialog = new RepairRequestDialog(this, currentUser);
            dialog.setBackendService(backendService);
            dialog.setVisible(true);
            
            // TODO: After dialog closes, reload the repair request list to show the new request
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "打开维修申请窗口失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openAddVehicleDialog() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, 
                "无法获取用户信息，请重新登录", 
                "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            VehicleDialog dialog = new VehicleDialog(this, currentUser);
            dialog.setBackendService(backendService);
            dialog.setVisible(true);
            
            // Refresh the vehicles list after dialog closes
            refreshVehiclesList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "打开添加车辆窗口失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openEditVehicleDialog(Vehicle vehicle) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, 
                "无法获取用户信息，请重新登录", 
                "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            VehicleDialog dialog = new VehicleDialog(this, currentUser);
            dialog.setBackendService(backendService);
            dialog.setEditMode(vehicle);
            dialog.setVisible(true);
            
            // Refresh the vehicles list after dialog closes
            refreshVehiclesList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "打开编辑车辆窗口失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshVehiclesList() {
        if (currentUser == null || backendService == null) {
            return;
        }
        
        try {
            List<Vehicle> vehicles = backendService.getUserVehicles(currentUser.getId());
            
            // Clear existing data
            vehicleTableModel.setRowCount(0);
            
            if (vehicles == null || vehicles.isEmpty()) {
                // 检查父容器是否存在并且已经包含scrollPane
                Container parent = vehicleTable.getParent();
                if (parent != null) {
                    // 安全地移除和添加组件
                    if (parent.getParent() instanceof JScrollPane) {
                        // 如果表格在滚动面板中，移除滚动面板
                        Container scrollParent = parent.getParent().getParent();
                        if (scrollParent != null) {
                            scrollParent.remove(vehicleScrollPane);
                            scrollParent.add(vehicleEmptyPanel, BorderLayout.CENTER);
                            scrollParent.revalidate();
                            scrollParent.repaint();
                        }
                    } else {
                        // 直接处理父容器
                        Container contentPane = vehicleScrollPane.getParent();
                        if (contentPane != null) {
                            contentPane.remove(vehicleScrollPane);
                            contentPane.add(vehicleEmptyPanel, BorderLayout.CENTER);
                            contentPane.revalidate();
                            contentPane.repaint();
                        }
                    }
                } else {
                    // 表格还没有添加到UI中，只在模型层面操作
                    // 无需额外操作，初始状态就是显示emptyPanel
                }
            } else {
                // Populate the table with vehicle data
                for (Vehicle vehicle : vehicles) {
                    Object[] row = new Object[6];
                    row[0] = vehicle.getLicensePlate();
                    row[1] = vehicle.getBrand();
                    row[2] = vehicle.getModel();
                    row[3] = vehicle.getYear() != null ? vehicle.getYear().toString() : "--";
                    row[4] = vehicle.getColor() != null ? vehicle.getColor() : "--";
                    row[5] = vehicle; // Store vehicle object for action buttons
                    
                    vehicleTableModel.addRow(row);
                }
                
                // 确保scrollPane已经添加到UI中
                Container parent = vehicleEmptyPanel.getParent();
                if (parent != null) {
                    parent.remove(vehicleEmptyPanel);
                    parent.add(vehicleScrollPane, BorderLayout.CENTER);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "加载车辆信息失败: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteVehicle(Vehicle vehicle) {
        if (vehicle == null || backendService == null) {
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,
                "确定要删除车辆 " + vehicle.getLicensePlate() + " 吗？此操作不可恢复。", 
                "确认删除",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
        if (choice == JOptionPane.YES_OPTION) {
            try {
                boolean success = backendService.deleteVehicle(vehicle.getId());
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "车辆已成功删除", 
                        "删除成功", JOptionPane.INFORMATION_MESSAGE);
                    refreshVehiclesList();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "删除车辆失败，请稍后再试", 
                        "删除失败", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "删除车辆时发生错误: " + e.getMessage(), 
                    "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Inner classes for table button rendering and handling
    
    // Custom renderer for button column
    class TableButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton = new JButton("编辑");
        private JButton deleteButton = new JButton("删除");
        
        public TableButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            setOpaque(false);
            
            editButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            editButton.setPreferredSize(new Dimension(60, 25));
            deleteButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            deleteButton.setPreferredSize(new Dimension(60, 25));
            deleteButton.setForeground(Color.RED);
            
            add(editButton);
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                                                    boolean isSelected, boolean hasFocus, 
                                                    int row, int column) {
            return this;
        }
    }
    
    // Custom editor for button column
    class TableButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private Vehicle currentVehicle;
        private UserMainFrame parent;
        
        public TableButtonEditor(JCheckBox checkBox, UserMainFrame parent) {
            super(checkBox);
            this.parent = parent;
            
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(false);
            
            editButton = new JButton("编辑");
            editButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            editButton.setPreferredSize(new Dimension(60, 25));
            editButton.addActionListener(e -> {
                fireEditingStopped();
                if (currentVehicle != null) {
                    parent.openEditVehicleDialog(currentVehicle);
                }
            });
            
            deleteButton = new JButton("删除");
            deleteButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            deleteButton.setPreferredSize(new Dimension(60, 25));
            deleteButton.setForeground(Color.RED);
            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                if (currentVehicle != null) {
                    parent.deleteVehicle(currentVehicle);
                }
            });
            
            panel.add(editButton);
            panel.add(deleteButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentVehicle = (Vehicle)value;
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return currentVehicle;
        }
    }
} 