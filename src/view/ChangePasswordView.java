package view;

import model.User;
import controller.UserController;
import org.apache.ibatis.session.SqlSessionFactory;
import javax.swing.*;
import java.awt.*;

public class ChangePasswordView extends JFrame {
    private final User user;
    private final SqlSessionFactory sqlSessionFactory;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private UserController controller;

    public ChangePasswordView(User user, SqlSessionFactory sqlSessionFactory) {
        this.user = user;
        this.sqlSessionFactory = sqlSessionFactory;
        this.controller = new UserController(sqlSessionFactory);
        initComponents();
    }

    private void initComponents() {
        setTitle("Ubah Password");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with gradient background
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

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Old password
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Password Lama:"), gbc);
        gbc.gridx = 1;
        oldPasswordField = new JPasswordField(20);
        formPanel.add(oldPasswordField, gbc);

        // New password
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password Baru:"), gbc);
        gbc.gridx = 1;
        newPasswordField = new JPasswordField(20);
        formPanel.add(newPasswordField, gbc);

        // Confirm password
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Konfirmasi Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton btnSave = new JButton("Simpan");
        JButton btnCancel = new JButton("Batal");

        btnSave.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Password baru dan konfirmasi tidak cocok!");
                return;
            }

            if (controller.verifyPassword(user.getEmail(), oldPassword)) {
                controller.updatePassword(user.getEmail(), newPassword);
                JOptionPane.showMessageDialog(this, "Password berhasil diubah!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password lama tidak cocok!");
            }
        });

        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add panels to main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 0.8;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.insets = new Insets(20, 20, 10, 20);
        mainPanel.add(formPanel, mainGbc);

        mainGbc.gridy = 1;
        mainGbc.weighty = 0.2;
        mainPanel.add(buttonPanel, mainGbc);

        add(mainPanel);
    }
} 