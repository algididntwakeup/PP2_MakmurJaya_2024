package view;

import java.awt.*;
import javax.swing.*;
import model.User;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderView extends JFrame {
    private final SqlSessionFactory sqlSessionFactory;

    public OrderView(User user, SqlSessionFactory sqlSessionFactory) {  // Tambah parameter SqlSessionFactory
        this.sqlSessionFactory = sqlSessionFactory;  // Simpan SqlSessionFactory
        setTitle("Halaman Order");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // label halaman order
        JLabel orderLabel = new JLabel("Daftar Order Anda");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // tombol ke dashboard
        JButton btnBack = new JButton("Kembali ke Dashboard");
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setBackground(new Color(70, 130, 180));
        btnBack.setForeground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(200, 40));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack.addActionListener(e -> {
            new DashboardView(user, sqlSessionFactory).setVisible(true);  // Tambah SqlSessionFactory
            this.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(btnBack);

        mainPanel.add(orderLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}