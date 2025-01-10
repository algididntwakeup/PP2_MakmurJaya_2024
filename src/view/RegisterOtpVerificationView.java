package view;

import controller.RegisterController;
import java.awt.*;
import javax.swing.*;

public class RegisterOtpVerificationView extends JFrame {
    private String email;
    private String password;
    private String correctOtp;
    private JFrame previousFrame;
    private RegisterController controller;

    public RegisterOtpVerificationView(String email, String password, String otp, JFrame previousFrame) {
        this.email = email;
        this.password = password;
        this.correctOtp = otp;
        this.previousFrame = previousFrame;
        this.controller = new RegisterController();
        
        setTitle("Verifikasi OTP");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Latar Belakang dengan gradient hijau
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 139, 34),
                    0, getHeight(), new Color(144, 238, 144)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Semi-transparent panel
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        formPanel.setOpaque(false);

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
                    boolean success = controller.registerManagement(email, password);
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

        // Add components to mainPanel using GridBagConstraints
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.insets = new Insets(20, 20, 20, 20);
        
        // Add formPanel to mainPanel
        mainPanel.add(formPanel, mainGbc);

        // Add components to form panel
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