package com.car;

import com.car.view.LoginFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.SwingUtilities;

@SpringBootApplication
@ComponentScan({"com.car", "com.carpj.service", "com.carpj.service.impl", "com.carpj.controller"})
@EntityScan("com.carpj.model")
@EnableJpaRepositories("com.carpj.repository")
public class App {
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        builder.headless(false); // 支持GUI
        ConfigurableApplicationContext context = builder.run(args);
        
        // 在Swing线程中启动GUI界面
        SwingUtilities.invokeLater(() -> {
            // 从Spring上下文中获取登录窗口
            LoginFrame loginFrame = context.getBean(LoginFrame.class);
            loginFrame.setVisible(true);
        });
    }
} 