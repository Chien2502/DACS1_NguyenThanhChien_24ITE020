package main;

import controller.LoginController;
import dao.DBConnection;
import view.LoginView;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MainApplication {
    public static void main(String[] args) {
    	// 1. Tải cấu hình từ hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()  // src/main/resources/hibernate.cfg.xml
            .build();

        // 2. Xây metadata từ các entity
        Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClass(model.Room.class)
            .addAnnotatedClass(model.Student.class)
            .addAnnotatedClass(model.Contract.class)
            .addAnnotatedClass(model.User.class)
            .buildMetadata();

        // 3. Tạo SessionFactory
        SessionFactory sessionFactory = metadata.buildSessionFactory();

        // 4. Thử mở session & transaction
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // TODO: Thao tác CRUD mẫu
            session.getTransaction().commit();
        }

        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    	
        try {
            // Khởi tạo giao diện đăng nhập
            LoginView loginView = new LoginView();
            new LoginController(loginView);
        } catch (Exception e) {

        	e.printStackTrace();
            System.err.println("Lỗi khi khởi tạo ứng dụng: " + e.getMessage());
        }
    }
}
