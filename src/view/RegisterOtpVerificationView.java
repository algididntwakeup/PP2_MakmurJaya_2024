package view;

import controller.ManagementController;
import java.awt.*;
import javax.swing.*;

public class RegisterOtpVerificationView extends JFrame {
    private String nama;
    private String email;
    private String password;
    private String correctOtp;
    private JFrame previousFrame;
    private ManagementController controller;

    public RegisterOtpVerificationView(String nama, String email, String password, String otp, JFrame previousFrame) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.correctOtp = otp;
        this.previousFrame = previousFrame;
        this.controller = new ManagementController();
        
        setTitle("Verifikasi OTP Registrasi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel instructionLabel = new JLabel("Kode OTP telah dikirim ke email Anda");
        JLabel labelOtp = new JLabel("Masukkan OTP:");
        JTextField fieldOtp = new JTextField(20);

        JButton btnSubmit = new JButton("Verifikasi");
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
                try {
                    boolean success = controller.registerManagement(nama, email, password);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Registrasi Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        new LoginView().setVisible(true);
                        this.dispose();
                        previousFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Registrasi Gagal", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                        previousFrame.setVisible(true);
                        this.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    previousFrame.setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "OTP salah!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add instruction label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(instructionLabel, gbc);

        // Add OTP input field
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(labelOtp, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldOtp, gbc);

        // Add submit button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btnSubmit, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Add window listener to show previous frame if this one is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                previousFrame.setVisible(true);
            }
        });
    }
} 