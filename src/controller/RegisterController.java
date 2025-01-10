package controller;

import java.util.HashMap;
import java.util.Random;
import mapper.UserMapper;
import model.MyBatisUtil;
import model.User;
import org.apache.ibatis.session.SqlSession;
import util.EmailUtil;
import util.PasswordHasher;

public class RegisterController { 
// Register Management
    public boolean registerManagement(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password sebelum ke database
            String hashedPassword = PasswordHasher.hashPassword(password);

            User user = new User(0, email, hashedPassword);

            // Insert user ke database
            userMapper.insertUser(user);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
