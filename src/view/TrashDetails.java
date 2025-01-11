package view;

import mapper.CategoryMapper;
import mapper.TrashMapper;
import model.Category;  // Ganti Kategori menjadi Category
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

        // Membuat tabel untuk menampilkan data
        String[] columnNames = {"Nama Sampah", "Kategori"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Mengambil data dari database
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TrashMapper trashMapper = session.getMapper(TrashMapper.class);  // Ganti nama variabel sesuai standar
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);  // Ganti nama variabel sesuai standar

            List<Category> categories = categoryMapper.selectAllCategories();  // Ganti Kategori menjadi Category
            List<Trash> trashList = trashMapper.selectAllTrash();

            for (Trash trash : trashList) {
                String kategoriName = categories.stream()
                        // .filter(k -> k.getId() == trash.getKategoriId())
                        .map(Category::getNamaKategori)  // Ganti Kategori menjadi Category
                        .findFirst()
                        .orElse("Tidak Diketahui");
                tableModel.addRow(new Object[]{trash.getId(), trash.getNamaSampah(), kategoriName});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
