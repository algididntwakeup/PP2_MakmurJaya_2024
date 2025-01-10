/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Random;
import util.EmailUtil;

/**
 *
 * @author lio
 */
public class OtpController {
    private static final int OTP_LENGTH = 6;
    
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
        
        
}
