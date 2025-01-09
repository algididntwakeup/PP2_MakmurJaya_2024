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
            refreshTable();
        });
    }

    public void saveUserDocument(UserDocument doc) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Set waktu pembuatan dan update
            LocalDateTime now = LocalDateTime.now();
            // Konversi LocalDateTime ke Date
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
            e.printStackTrace(); // Untuk debugging
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
        if (doc.getKtpNumber() == null || doc.getKtpNumber().trim().isEmpty()) {
            throw new Exception("Nomor KTP wajib diisi");
        }
        if (doc.getKkNumber() == null || doc.getKkNumber().trim().isEmpty()) {
            throw new Exception("Nomor KK wajib diisi");
        }
        if (doc.getFullName() == null || doc.getFullName().trim().isEmpty()) {
            throw new Exception("Nama Lengkap wajib diisi");
        }
        // Tambahkan validasi lainnya sesuai kebutuhan
    }

    // Method untuk menghapus dokumen
    public void deleteUserDocument(String ktpNumber) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            mapper.delete(ktpNumber);
            session.commit();
            JOptionPane.showMessageDialog(view, "Data berhasil dihapus!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method untuk mengambil semua dokumen
    public List<UserDocument> getAllUserDocuments() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            return mapper.selectAll();
        }
    }

    // Method untuk mengambil dokumen berdasarkan KTP
    public UserDocument getUserDocumentByKtp(String ktpNumber) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
            return mapper.selectByKtp(ktpNumber);
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