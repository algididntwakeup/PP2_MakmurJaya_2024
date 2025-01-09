package view;

import controller.KurirController;
import java.awt.*;
import javax.swing.*;

public class ResetPasswordView extends JFrame {

    private KurirController controller;
    private String email;

    public ResetPasswordView(String email) {
        this.email = email;
        controller = new KurirController();
        setTitle("Reset Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelPassword = new JLabel("Password Baru:");
        JPasswordField fieldPassword = new JPasswordField(20);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(new Color(70, 130, 180));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));

        btnSubmit.addActionListener(e -> {
            String password = new String(fieldPassword.getPassword()).trim();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password baru harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                boolean success = controller.resetPasswordByEmail(email, password);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Password berhasil direset!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    new LoginView().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mereset password", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(labelPassword, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btnSubmit, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResetPasswordView("example@example.com").setVisible(true));
    }
}
