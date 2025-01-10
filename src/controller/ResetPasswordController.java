/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import mapper.UserMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.PasswordHasher;

/**
 *
 * @author lio
 */
public class ResetPasswordController {
     public boolean resetPasswordByEmail(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password baru
            String hashedPassword = PasswordHasher.hashPassword(password);

            // Update password di database berdasarkan email
            userMapper.updatePasswordByEmail(email, hashedPassword);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory();  // Pastikan ini sesuai dengan cara Anda mendapatkan SqlSessionFactory
    }
    
}
