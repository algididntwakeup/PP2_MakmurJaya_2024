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

    private final HashMap<String, String[]> pendingRegistrations = new HashMap<>();

    // Simpan data sementara dan kirim OTP
    public String generateAndSendOtp(String email, String nama, String password) {
        // Buat OTP 6 digit
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));

        // Simpan data sementara di HashMap
        pendingRegistrations.put(email, new String[]{nama, password, otp});

        // Kirim OTP melalui email
        try {
            String subject = "Kode OTP Anda";
            String message = "Kode OTP Anda adalah: " + otp + ". Gunakan kode ini untuk verifikasi.";
            EmailUtil.sendEmail(email, subject, message);
            System.out.println("OTP dikirim ke " + email + ": " + otp); // Debug
            return otp;
        } catch (Exception e) {
            System.err.println("Gagal mengirim OTP: " + e.getMessage());
            return null;
        }
    }

    // Verifikasi OTP dan simpan ke database
    public boolean verifyOtpAndRegister(String email, String enteredOtp) {
        if (!pendingRegistrations.containsKey(email)) {
            System.err.println("Email tidak ditemukan di pendingRegistrations.");
            return false;
        }

        // Ambil data dari HashMap
        String[] data = pendingRegistrations.get(email);
        String nama = data[0];
        String password = data[1];
        String otp = data[2];

        // Verifikasi OTP
        if (otp.equals(enteredOtp)) {
            // OTP benar, simpan ke database
            boolean success = registerManagement(nama, email, password);

            if (success) {
                pendingRegistrations.remove(email); // Hapus data dari HashMap setelah berhasil
            }
            return success;
        } else {
            System.err.println("OTP salah.");
            return false;
        }
    }

    // Simpan data ke database
    public boolean registerManagement(String nama, String email, String password) {
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
