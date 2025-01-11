package view;

import mapper.CategoryMapper;
import mapper.TrashMapper;
import model.Category;
import model.Trash;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrashDetails extends JFrame {
    private final SqlSessionFactory sqlSessionFactory;

    public TrashDetails(User user, SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        setTitle("Detail Sampah");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create back button
        JButton btnBack = new JButton("Kembali");
        btnBack.addActionListener(e -> {
            new CategoriesView(user, sqlSessionFactory).setVisible(true);
            dispose();
        });
        
        // Add back button to top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnBack);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create table
        String[] columnNames = {"Nama Sampah", "Kategori"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Fetch data from database
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TrashMapper trashMapper = session.getMapper(TrashMapper.class);
            List<Trash> trashList = trashMapper.selectAllTrash();

            for (Trash trash : trashList) {
                tableModel.addRow(new Object[]{
                    trash.getNamaSampah(),
                    trash.getNamaKategori()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
