package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import model.User;
import model.Category;
import model.Trash;
import mapper.CategoryMapper;
import mapper.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

public class CategoriesView extends JFrame {
    private final SqlSessionFactory sqlSessionFactory;
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;
    private JDialog trashDialog;

    public CategoriesView(User user, SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        setTitle("Halaman Kategori");
        setSize(800, 600);
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
                    0, 0, new Color(34, 139, 34),
                    0, getHeight(), new Color(144, 238, 144)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Panel untuk konten
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

        // Label halaman kategori
        JLabel categoryLabel = new JLabel("Daftar Kategori");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(categoryLabel, gbc);

        // Inisialisasi tabel kategori
        String[] columnNames = {"ID Kategori", "Nama Kategori", "Aksi"};
        categoryTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        categoryTable = new JTable(categoryTableModel);
        categoryTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        categoryTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Set lebar kolom
        categoryTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        categoryTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        categoryTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(categoryTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPanel.add(scrollPane, gbc);

        // Tombol navigasi
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Kembali ke Dashboard");
        styleButton(btnBack);
        btnBack.addActionListener(e -> {
            new DashboardView(user, sqlSessionFactory).setVisible(true);
            this.dispose();
        });

        JButton btnTrashDetails = new JButton("Detail Sampah");
        styleButton(btnTrashDetails);
        btnTrashDetails.addActionListener(e -> {
            new TrashDetails(user, sqlSessionFactory).setVisible(true);
            this.dispose();
        });

        buttonPanel.add(btnBack);
        buttonPanel.add(btnTrashDetails);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contentPanel.add(buttonPanel, gbc);

        mainPanel.add(contentPanel, gbc);
        add(mainPanel);
        
        // Load data kategori
        loadCategories();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadCategories() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
            List<Category> categories = categoryMapper.selectAllCategories();
            
            // Bersihkan tabel sebelum menambahkan data baru
            categoryTableModel.setRowCount(0);
            
            // Tambahkan data ke tabel
            for (Category category : categories) {
                Object[] row = new Object[]{
                    category.getId(),
                    category.getNamaKategori(),
                    "Lihat Sampah"
                };
                categoryTableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading categories: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTrashDialog(int categoryId, String categoryName) {
        trashDialog = new JDialog(this, "Daftar Sampah - " + categoryName, true);
        trashDialog.setSize(600, 400);
        trashDialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabel sampah
        String[] trashColumns = {"ID Sampah", "Nama Sampah", "Kategori"};
        DefaultTableModel trashTableModel = new DefaultTableModel(trashColumns, 0);
        JTable trashTable = new JTable(trashTableModel);
        
        // Set lebar kolom
        trashTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        trashTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        trashTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        JScrollPane trashScrollPane = new JScrollPane(trashTable);
        dialogPanel.add(trashScrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Tutup");
        styleButton(closeButton);
        closeButton.addActionListener(e -> trashDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        trashDialog.add(dialogPanel);
        
        // Load data sampah
        loadTrashData(categoryId, trashTableModel);
        
        trashDialog.setVisible(true);
    }

    private void loadTrashData(int categoryId, DefaultTableModel model) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TrashMapper trashMapper = session.getMapper(TrashMapper.class);
            List<Trash> trashList = trashMapper.selectTrashByCategory(categoryId);
            
            model.setRowCount(0);
            
            for (Trash trash : trashList) {
                Object[] row = new Object[]{
                    trash.getId(),
                    trash.getNamaSampah(),
                    trash.getNamaKategori()  // Menggunakan getNamaKategori() instead of getKategoriId()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading trash data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Lihat Sampah");
            return this;
        }
    }

    // Custom Button Editor
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = "Lihat Sampah";
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = categoryTable.getSelectedRow();
                int categoryId = Integer.parseInt(categoryTable.getValueAt(row, 0).toString());
                String categoryName = categoryTable.getValueAt(row, 1).toString();
                showTrashDialog(categoryId, categoryName);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}