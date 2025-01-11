package view;

import controller.LoginController;
import java.awt.*;
import javax.swing.*;
import model.User;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login Management");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Latar Belakang dengan gradient hijau
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 139, 34), // Forest Green
                    0, getHeight(), new Color(144, 238, 144) // Light Green
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Panel untuk login form dengan background semi-transparan
        JPanel loginPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        loginPanel.setOpaque(false);

        // Title Panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        
        // Login Title with custom font and shadow effect
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        
        // Subtitle "Management"
        JLabel subtitleLabel = new JLabel("E-Waste Management");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        subtitleLabel.setForeground(Color.BLACK);

        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.gridx = 0;
        titleGbc.gridy = 0;
        titleGbc.insets = new Insets(0, 0, 5, 0);
        titlePanel.add(titleLabel, titleGbc);

        titleGbc.gridy = 1;
        titleGbc.insets = new Insets(0, 0, 20, 0);
        titlePanel.add(subtitleLabel, titleGbc);

        // Input Fields with rounded borders and icons
        JTextField fieldEmail = new JTextField(20);
        JPasswordField fieldPassword = new JPasswordField(20);
        
        // Styling input fields
        fieldEmail.setPreferredSize(new Dimension(250, 35));
        fieldPassword.setPreferredSize(new Dimension(250, 35));
        
        // Custom border for input fields
        fieldEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        fieldPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Labels
        JLabel labelEmail = new JLabel("Email");
        JLabel labelPassword = new JLabel("Password");
        labelEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelEmail.setForeground(new Color(50, 50, 50));
        labelPassword.setForeground(new Color(50, 50, 50));

        // Buttons with hover effect
        JButton btnLogin = createStyledButton("Login", new Color(34, 139, 34));  // Forest Green
        JButton btnRegister = createStyledButton("Daftar", new Color(46, 139, 87));  // Sea Green
        JButton btnForgotPassword = createStyledButton("Lupa Password?", new Color(178, 34, 34));  // Firebrick

        // Add components to login panel
        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.gridx = 0;
        loginGbc.gridy = 0;
        loginGbc.gridwidth = 2;
        loginGbc.insets = new Insets(20, 20, 30, 20);
        loginPanel.add(titlePanel, loginGbc);

        // Email field
        loginGbc.gridy = 1;
        loginGbc.gridwidth = 2;
        loginGbc.anchor = GridBagConstraints.WEST;
        loginGbc.insets = new Insets(5, 20, 5, 20);
        loginPanel.add(labelEmail, loginGbc);

        loginGbc.gridy = 2;
        loginGbc.fill = GridBagConstraints.HORIZONTAL;
        loginGbc.gridwidth = 2;
        loginPanel.add(fieldEmail, loginGbc);

        // Password field
        loginGbc.gridy = 3;
        loginGbc.fill = GridBagConstraints.NONE;
        loginGbc.gridwidth = 2;
        loginPanel.add(labelPassword, loginGbc);

        loginGbc.gridy = 4;
        loginGbc.fill = GridBagConstraints.HORIZONTAL;
        loginGbc.gridwidth = 2;
        loginPanel.add(fieldPassword, loginGbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        loginGbc.gridy = 5;
        loginGbc.gridwidth = 2;
        loginGbc.insets = new Insets(20, 20, 10, 20);
        loginPanel.add(buttonPanel, loginGbc);

        loginGbc.gridy = 6;
        loginGbc.insets = new Insets(0, 20, 20, 20);
        loginPanel.add(btnForgotPassword, loginGbc);

        // Add login panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(loginPanel, gbc);

        // Action Listeners
        btnLogin.addActionListener(e -> {
            String email = fieldEmail.getText().trim();
            String password = new String(fieldPassword.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                showErrorMessage("Email dan password harus diisi!");
                return;
            }

            LoginController controller = new LoginController();
            User user = controller.loginManagement(email, password);

            if (user != null) {
                showSuccessMessage("Login berhasil!");
                new DashboardView(user, controller.getSqlSessionFactory()).setVisible(true);
                this.dispose();
            } else {
                showErrorMessage("Login gagal! Email atau password salah.");
            }
        });

        btnRegister.addActionListener(e -> {
            new RegisterView().setVisible(true);
            this.dispose();
        });

        btnForgotPassword.addActionListener(e -> {
            new ForgotPasswordView().setVisible(true);
            this.dispose();
        });

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
