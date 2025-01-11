package view;

import java.awt.*;
import javax.swing.*;
import model.User;
import org.apache.ibatis.session.SqlSessionFactory;

public class CategoriesView extends JFrame {

    private final SqlSessionFactory sqlSessionFactory;

    public CategoriesView(User user, SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory; // Simpan SqlSessionFactory
        setTitle("Halaman Kategori");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan gradient background
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

        // Panel untuk konten dengan background semi-transparan
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

        // Label halaman order
        JLabel categoryLabel = new JLabel("Daftar Kategori");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(categoryLabel, gbc);

        // Tombol kembali ke dashboard
        JButton btnBack = new JButton("Kembali ke Dashboard");
        
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setBackground(new Color(70, 130, 180));
        btnBack.setForeground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(200, 40));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack.addActionListener(e -> {
            new DashboardView(user, sqlSessionFactory).setVisible(true); // Tambah SqlSessionFactory
            this.dispose();
        });
        
        JButton btnSampah = new JButton("Detail Sampah");
        btnSampah.setFont(new Font("Arial", Font.BOLD, 16));
        btnSampah.setBackground(new Color(70, 130, 180));
        btnSampah.setForeground(Color.WHITE);
        btnSampah.setPreferredSize(new Dimension(200, 40));
        btnSampah.setFocusPainted(false);
        btnSampah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSampah.addActionListener(e -> {
            new TrashDetails(user, sqlSessionFactory).setVisible(true); // Tambah SqlSessionFactory
            this.dispose();
        });
        

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(btnBack, gbc);
        
        gbc.gridx = 3;
        contentPanel.add(btnSampah, gbc);

        // Tambahkan contentPanel ke mainPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(contentPanel, gbc);

        add(mainPanel);
    }
}
