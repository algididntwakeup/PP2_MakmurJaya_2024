package controller;

import model.User;
import model.UserDocument;
import model.UserDocumentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import view.UserDocumentView;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

public class UserDocumentController {
    private UserDocumentView view;
    private final SqlSessionFactory sqlSessionFactory;
    private final User user;

    public UserDocumentController(SqlSessionFactory sqlSessionFactory, User user) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.user = user;
        initializeView();
    }

    private void initializeView() {
        SwingUtilities.invokeLater(() -> {
            this.view = new UserDocumentView(this, user);
            this.view.setVisible(true);
            loadUserData();
            refreshTable();
        });
    }

    private void loadUserData() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            UserDocument userDoc = mapper.selectById(user.getId());
            if (userDoc != null) {
                view.displayUserData(userDoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading user data: " + e.getMessage());
        }
    }

    public void saveUserDocument(UserDocument doc) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Set the user ID from the logged-in user
            doc.setId(user.getId());
            
            // Set waktu pembuatan dan update
            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            doc.setCreatedAt(date);
            doc.setUpdatedAt(date);

            // Validasi field yang wajib diisi
            validasiDokumen(doc);

            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            mapper.insert(doc);
            session.commit();
            JOptionPane.showMessageDialog(view, "Data berhasil disimpan!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUserDocument(UserDocument doc) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Update timestamp dengan konversi yang benar
            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            doc.setUpdatedAt(date);

            // Validasi field yang wajib diisi
            validasiDokumen(doc);

            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            mapper.update(doc);
            session.commit();
            JOptionPane.showMessageDialog(view, "Data berhasil diupdate!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            e.printStackTrace(); // Untuk debugging
        }
    }

    // Method untuk validasi data
    private void validasiDokumen(UserDocument doc) throws Exception {
        if (doc.getFullName() == null || doc.getFullName().trim().isEmpty()) {
            throw new Exception("Nama Lengkap wajib diisi");
        }
        if (doc.getAddress() == null || doc.getAddress().trim().isEmpty()) {
            throw new Exception("Alamat wajib diisi");
        }
        if (doc.getBirthDate() == null) {
            throw new Exception("Tanggal Lahir wajib diisi");
        }
    }

    // Method untuk mengambil semua dokumen
    public List<UserDocument> getAllUserDocuments() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            return mapper.selectAll(user.getId());
        }
    }

    // Method untuk mengambil dokumen berdasarkan ID
    public UserDocument getUserDocumentById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            return mapper.selectById(id);
        }
    }

    public void refreshTable() {
        if (view != null) {
            List<UserDocument> documents = getAllUserDocuments();
            view.refreshTable(documents);
        }
    }

    // Method yang diperbaiki untuk memuat file gambar dengan validasi ukuran
    public byte[] loadImageFile(File file) throws Exception {
        // Validasi ukuran file (maksimal 5MB)
        long ukuranMaksimal = 5 * 1024 * 1024; // 5MB
        if (file.length() > ukuranMaksimal) {
            throw new Exception("Ukuran file terlalu besar. Maksimal 5MB");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}