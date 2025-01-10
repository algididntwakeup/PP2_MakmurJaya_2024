package view;

import controller.ManagementController;
import java.awt.*;
import javax.swing.*;
import model.User;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Latar Belakang
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(), new Color(230, 230, 250)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Judul Login
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Label dan Field Email
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField fieldEmail = new JTextField(15);
        fieldEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(labelEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(fieldEmail, gbc);

        // Label dan Field Password
        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JPasswordField fieldPassword = new JPasswordField(15);
        fieldPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(fieldPassword, gbc);

        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton btnLogin = createStyledButton("Login", new Color(0, 120, 215));
        JButton btnRegister = createStyledButton("Daftar", new Color(34, 139, 34));
        JButton btnForgotPassword = createStyledButton("Lupa Password", new Color(255, 69, 0));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        gbc.gridy = 4; // Posisi tombol Lupa Password di bawah buttonPanel
        gbc.gridwidth = 2; // Tombol memakan 2 kolom
        gbc.anchor = GridBagConstraints.CENTER; // Posisi di tengah
        mainPanel.add(btnForgotPassword, gbc);

        // Tambahkan kotak border di seluruh panel utama
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Aksi Tombol
        btnLogin.addActionListener(e -> {
            String email = fieldEmail.getText().trim();
            String password = new String(fieldPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email dan password harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Proses login
            ManagementController controller = new ManagementController();
            User user = controller.loginManagement(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                new DashboardView(user, controller.getSqlSessionFactory()).setVisible(true);  // Tambahkan SqlSessionFactory
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal! Email atau password salah.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> {
            new RegisterView().setVisible(true);
            this.dispose();
        });

        // Tambahkan dalam konstruktor LoginView
        btnForgotPassword.addActionListener(e -> {
            new ForgotPasswordView().setVisible(true);
            this.dispose();
        });


        // Menambahkan mainPanel ke Frame
        add(mainPanel);
    }

    // Method untuk membuat tombol dengan desain modern
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 45));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
