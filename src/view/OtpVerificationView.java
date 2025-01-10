package view;

import java.awt.*;
import javax.swing.*;

public class OtpVerificationView extends JFrame {

    private String email;
    private String correctOtp;

    public OtpVerificationView(String email, String otp) {
        this.email = email;
        this.correctOtp = otp;
        setTitle("Verifikasi OTP");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelOtp = new JLabel("Masukkan OTP:");
        JTextField fieldOtp = new JTextField(20);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(new Color(70, 130, 180));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));

        btnSubmit.addActionListener(e -> {
            String inputOtp = fieldOtp.getText().trim();
            if (inputOtp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "OTP harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (inputOtp.equals(correctOtp)) {
                new ResetPasswordView(email).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "OTP salah!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(labelOtp, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldOtp, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btnSubmit, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OtpVerificationView("example@example.com", "123456").setVisible(true));
    }
}
