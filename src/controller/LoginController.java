/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import mapper.UserMapper;
import model.MyBatisUtil;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.PasswordHasher;

/**
 *
 * @author lio
 */
public class LoginController {
    
    // Login Management
    public User loginManagement(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Cari user berdasarkan email
            User user = userMapper.findByEmail(email);

            if (user != null) {
                // Hash password yang dimasukkan
                String hashedPassword = PasswordHasher.hashPassword(password);

                // // Debug
                // System.out.println("Login - Input Hashed Password: " + hashedPassword);
                // System.out.println("Login - Stored Hashed Password: " + user.getPassword());
                // Perbandingan password hash
                if (hashedPassword.equals(user.getPassword())) {
                    return user;
                } else {
                    System.out.println("Login - Password mismatch!");
                }
            } else {
                System.out.println("Login - User not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory();  // Pastikan ini sesuai dengan cara Anda mendapatkan SqlSessionFactory
    }
}
