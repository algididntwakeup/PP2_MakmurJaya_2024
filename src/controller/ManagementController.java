package controller;

import mapper.UserMapper;
import model.MyBatisUtil;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.PasswordHasher;
import util.EmailUtil;
import java.util.Random;

public class ManagementController {

        private static final int OTP_LENGTH = 6;

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




    // Metode untuk menghasilkan OTP
        public String generateOTP() {
            Random random = new Random();
            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < OTP_LENGTH; i++) {
                otp.append(random.nextInt(10));
            }
            return otp.toString();
        }

        // Metode untuk mengirim OTP ke email
        public boolean sendOtpToEmail(String email, String otp) {
            String subject = "OTP Reset Password";
            String body = "Your OTP for resetting your password is: " + otp;
            try {
                EmailUtil.sendEmail(email, subject, body);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    // Register Management
    public boolean registerManagement(String nama, String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password sebelum ke database
            String hashedPassword = PasswordHasher.hashPassword(password);
            // System.out.println("Register - Hashed Password: " + hashedPassword); // Debug

            User user = new User(0, email,hashedPassword);

            // Insert user ke database
            userMapper.insertUser(user);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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

    // Periksa apakah NIK KTP ada di database
    public boolean checkKTPExists(String ktp) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.checkKTPExists(ktp) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Reset password pengguna
    public boolean resetPassword(String ktp, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password baru
            String hashedPassword = PasswordHasher.hashPassword(password);

            // Update password di database
            userMapper.updatePasswordByKTP(ktp, hashedPassword);
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
