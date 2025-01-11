package view;

import java.awt.*;
import javax.swing.*;
import model.User;
import controller.UserDocumentController;
import org.apache.ibatis.session.SqlSessionFactory;

public class DashboardView extends JFrame {
    private final SqlSessionFactory sqlSessionFactory;
    private final User user;

    public DashboardView(User user, SqlSessionFactory sqlSessionFactory) {
        this.user = user;
        this.sqlSessionFactory = sqlSessionFactory;
        setTitle("Dashboard Management");
        setSize(600, 400);
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

        // Panel untuk content dengan background semi-transparan
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Selamat datang, " + user.getEmail() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtitle
        JLabel subtitleLabel = new JLabel("E-Waste Management");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        // Create styled buttons
        JButton btnDocument = createStyledButton("Profile User", new Color(34, 139, 34));  // Forest Green
        JButton btnCategories = createStyledButton("Halaman Kategori", new Color(46, 139, 87));    // Sea Green
        JButton btnLogout = createStyledButton("Logout", new Color(178, 34, 34));          // Firebrick

        // Add action listeners
        btnDocument.addActionListener(e -> {
            UserDocumentController controller = new UserDocumentController(sqlSessionFactory, user);
            this.dispose();
        });

        btnCategories.addActionListener(e -> {
            new CategoriesView(user, sqlSessionFactory).setVisible(true);
            this.dispose();
        });

        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose();
        });

        // Add components to content panel
        GridBagConstraints contentGbc = new GridBagConstraints();
        contentGbc.gridx = 0;
        contentGbc.gridy = 0;
        contentGbc.gridwidth = 1;
        contentGbc.insets = new Insets(20, 20, 5, 20);
        contentPanel.add(welcomeLabel, contentGbc);

        contentGbc.gridy = 1;
        contentGbc.insets = new Insets(0, 20, 30, 20);
        contentPanel.add(subtitleLabel, contentGbc);

        // Add buttons
        contentGbc.gridy = 2;
        contentGbc.insets = new Insets(10, 20, 10, 20);
        contentGbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(btnDocument, contentGbc);

        contentGbc.gridy = 3;
        contentPanel.add(btnCategories, contentGbc);

        contentGbc.gridy = 4;
        contentGbc.insets = new Insets(10, 20, 20, 20);
        contentPanel.add(btnLogout, contentGbc);

        // Add content panel to main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(contentPanel, mainGbc);

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
        button.setPreferredSize(new Dimension(200, 45));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}