package com.car.view;

import com.carpj.model.Administrator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AdminMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private Administrator currentAdmin;
    
    // Define UI colors
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color BG_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black
    private final Color ADMIN_COLOR = new Color(175, 50, 50); // Darker red for admin

    public AdminMainFrame() {
        this(null);
    }
    
    public AdminMainFrame(Administrator admin) {
        this.currentAdmin = admin;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 管理员界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
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
                
                // Create gradient background with admin theme
                GradientPaint gp = new GradientPaint(0, 0, new Color(250, 240, 240), 0, getHeight(), 
                        new Color(245, 245, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 10, 10, 10));
        setContentPane(contentPane);
        
        // Header panel with admin welcome
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Create custom styled tabbed pane
        tabbedPane = createStyledTabbedPane();
        
        // Add tabs with icons
        ImageIcon userIcon = createIcon("\uD83D\uDC64", 18); // Person emoji
        ImageIcon staffIcon = createIcon("\uD83D\uDC77", 18); // Worker emoji
        ImageIcon orderIcon = createIcon("\uD83D\uDCC3", 18); // Document emoji
        ImageIcon statsIcon = createIcon("\uD83D\uDCCA", 18); // Chart emoji
        ImageIcon settingsIcon = createIcon("\u2699", 18); // Gear emoji
        ImageIcon profileIcon = createIcon("\uD83D\uDC51", 18); // Crown emoji (for admin)
        
        tabbedPane.addTab("用户管理", userIcon, createUserManagementPanel());
        tabbedPane.addTab("维修人员管理", staffIcon, createStaffManagementPanel());
        tabbedPane.addTab("工单管理", orderIcon, createWorkOrderPanel());
        tabbedPane.addTab("数据统计", statsIcon, createStatisticsPanel());
        tabbedPane.addTab("系统设置", settingsIcon, createSystemSettingsPanel());
        tabbedPane.addTab("管理员信息", profileIcon, createProfilePanel());
        
        // Add some padding around the tabbed pane
        JPanel tabbedPaneContainer = new JPanel(new BorderLayout());
        tabbedPaneContainer.setOpaque(false);
        tabbedPaneContainer.setBorder(new EmptyBorder(10, 5, 5, 5));
        tabbedPaneContainer.add(tabbedPane, BorderLayout.CENTER);
        
        contentPane.add(tabbedPaneContainer, BorderLayout.CENTER);
        
        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        JLabel statusLabel = new JLabel("管理员模式 | 系统版本: 1.0");
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        footerPanel.add(statusLabel);
        
        contentPane.add(footerPanel, BorderLayout.SOUTH);
        
        // Set welcome title based on admin info
        if (currentAdmin != null) {
            setTitle("车辆维修管理系统 - 管理员界面 - 欢迎 " + currentAdmin.getName());
        }
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(5, 5, 15, 5));
        
        // System title on left
        JLabel titleLabel = new JLabel("车辆维修管理系统");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(ADMIN_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Admin welcome message on right
        if (currentAdmin != null) {
            JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            userInfoPanel.setOpaque(false);
            
            JLabel welcomeLabel = new JLabel("管理员: " + currentAdmin.getName());
            welcomeLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
            welcomeLabel.setForeground(ADMIN_COLOR);
            
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
                shadow = ADMIN_COLOR;
                darkShadow = ADMIN_COLOR;
                focus = ADMIN_COLOR;
            }
            
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2d.setColor(ADMIN_COLOR);
                    g2d.drawLine(x, y + h, x + w, y + h);
                }
                
                g2d.dispose();
            }
            
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected) {
                    g2d.setColor(new Color(250, 240, 240));
                } else {
                    g2d.setColor(new Color(240, 235, 235));
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
        label.setForeground(ADMIN_COLOR);
        
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        label.paint(g2d);
        g2d.dispose();
        
        return new ImageIcon(image);
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = createStyledPanel("用户管理");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual user management panel
        JLabel notImplementedLabel = new JLabel("用户管理功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        // Add some UI placeholders to show the design
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        JButton addUserBtn = createStyledButton("添加用户", ADMIN_COLOR);
        JButton refreshBtn = createStyledButton("刷新", new Color(100, 100, 100));
        actionPanel.add(addUserBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshBtn);
        
        contentPanel.add(actionPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createStaffManagementPanel() {
        JPanel panel = createStyledPanel("维修人员管理");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual staff management panel
        JLabel notImplementedLabel = new JLabel("维修人员管理功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        // Add some UI placeholders to show the design
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        JButton addStaffBtn = createStyledButton("添加维修人员", ADMIN_COLOR);
        JButton refreshBtn = createStyledButton("刷新", new Color(100, 100, 100));
        actionPanel.add(addStaffBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshBtn);
        
        contentPanel.add(actionPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createWorkOrderPanel() {
        JPanel panel = createStyledPanel("工单管理");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual work order panel
        JLabel notImplementedLabel = new JLabel("工单管理功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = createStyledPanel("数据统计");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual statistics panel
        JLabel notImplementedLabel = new JLabel("数据统计功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createSystemSettingsPanel() {
        JPanel panel = createStyledPanel("系统设置");
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TODO: Replace this with actual system settings panel
        JLabel notImplementedLabel = new JLabel("系统设置功能完善中...", SwingConstants.CENTER);
        notImplementedLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        notImplementedLabel.setForeground(new Color(150, 150, 150));
        contentPanel.add(notImplementedLabel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = createStyledPanel("管理员信息");
        
        if (currentAdmin != null) {
            // Card layout for profile information
            JPanel profileCard = new JPanel();
            profileCard.setOpaque(false);
            profileCard.setLayout(new BorderLayout(15, 15));
            profileCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    new EmptyBorder(25, 25, 25, 25)));
            
            // Admin avatar panel (placeholder)
            JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            avatarPanel.setOpaque(false);
            avatarPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
            
            JLabel avatarLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Draw circle background
                    g2.setColor(ADMIN_COLOR);
                    g2.fillOval(0, 0, 80, 80);
                    
                    // Draw text in circle
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 32));
                    FontMetrics fm = g2.getFontMetrics();
                    String initial = currentAdmin.getName().substring(0, 1).toUpperCase();
                    int textWidth = fm.stringWidth(initial);
                    int textHeight = fm.getHeight();
                    g2.drawString(initial, (80 - textWidth) / 2, ((80 - textHeight) / 2) + fm.getAscent());
                    
                    g2.dispose();
                }
            };
            avatarLabel.setPreferredSize(new Dimension(80, 80));
            
            // Admin name under avatar
            JLabel nameLabel = new JLabel(currentAdmin.getName() + " (系统管理员)", SwingConstants.CENTER);
            nameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            nameLabel.setForeground(TEXT_COLOR);
            
            avatarPanel.add(avatarLabel);
            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.setOpaque(false);
            namePanel.add(nameLabel, BorderLayout.CENTER);
            avatarPanel.add(namePanel);
            
            // Admin details panel
            JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 15, 15));
            detailsPanel.setOpaque(false);
            
            addDetailField(detailsPanel, "用户名:", currentAdmin.getUsername());
            addDetailField(detailsPanel, "姓名:", currentAdmin.getName());
            addDetailField(detailsPanel, "权限级别:", currentAdmin.getAccessLevel() != null ? 
                    currentAdmin.getAccessLevel().toString() : "最高权限");
            addDetailField(detailsPanel, "电话:", currentAdmin.getPhone() != null ? 
                    currentAdmin.getPhone() : "--");
            addDetailField(detailsPanel, "邮箱:", currentAdmin.getEmail() != null ? 
                    currentAdmin.getEmail() : "--");
            
            // Action buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
            
            JButton editButton = createStyledButton("编辑个人信息", ADMIN_COLOR);
            editButton.setPreferredSize(new Dimension(150, 40));
            editButton.addActionListener(e -> {
                // TODO: 实现编辑管理员信息功能
                JOptionPane.showMessageDialog(this, 
                    "编辑管理员信息功能完善中", 
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
            JLabel errorLabel = new JLabel("无法获取管理员信息", SwingConstants.CENTER);
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
            titleLabel.setForeground(ADMIN_COLOR);
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
            new LoginFrame().setVisible(true);
        }
    }
} 