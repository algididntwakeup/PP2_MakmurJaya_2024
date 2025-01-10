package view;

import controller.UserDocumentController;
import model.User;
import model.UserDocument;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import model.UserDocumentMapper;
import org.apache.ibatis.session.SqlSession;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

public class UserDocumentView extends JFrame {
    private UserDocumentController controller;
    private User user;
    private JTextField txtKtpNumber, txtKkNumber, txtFullName, txtAddress, txtBankAccount, txtEWallet;
    private JDateChooser dateChooser;
    private JButton btnKtpImage, btnKkImage, btnProfileImage;
    private JTable table;
    private byte[] ktpImageData, kkImageData, profileImageData;
    private JLabel lblKtpPreview;
    private JLabel lblKkPreview;
    private JLabel lblProfilePreview;
    private final int PREVIEW_WIDTH = 150;
    private final int PREVIEW_HEIGHT = 200;

    public UserDocumentView(UserDocumentController controller, User user) {
        this.controller = controller;
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Manajemen Dokumen User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Main panel dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel untuk tombol kembali (navbar)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(new Color(245, 245, 245));

        JButton btnBack = new JButton("← Kembali ke Dashboard");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setBackground(new Color(51, 122, 183));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(200, 35));

        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(40, 96, 144));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(51, 122, 183));
            }
        });

        btnBack.addActionListener(e -> {
            new DashboardView(user, controller.getSqlSessionFactory()).setVisible(true);
            this.dispose();
        });

        topPanel.add(btnBack);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel tengah untuk form dan tabel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Data User"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nomor KTP:"), gbc);
        gbc.gridx = 1;
        txtKtpNumber = new JTextField(20);
        formPanel.add(txtKtpNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nomor KK:"), gbc);
        gbc.gridx = 1;
        txtKkNumber = new JTextField(20);
        formPanel.add(txtKkNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Nama Lengkap:"), gbc);
        gbc.gridx = 1;
        txtFullName = new JTextField(20);
        formPanel.add(txtFullName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAddress = new JTextField(20);
        formPanel.add(txtAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Tanggal Lahir:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        formPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Bank Account:"), gbc);
        gbc.gridx = 1;
        txtBankAccount = new JTextField(20);
        formPanel.add(txtBankAccount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("E-Wallet:"), gbc);
        gbc.gridx = 1;
        txtEWallet = new JTextField(20);
        formPanel.add(txtEWallet, gbc);

        centerPanel.add(formPanel);

        // Image Preview Panel
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Preview Dokumen"));

        // KTP Preview
        gbc.gridx = 0;
        gbc.gridy = 0;
        imagePanel.add(new JLabel("KTP:"), gbc);
        gbc.gridy = 1;
        lblKtpPreview = new JLabel();
        lblKtpPreview.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        lblKtpPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(lblKtpPreview, gbc);
        gbc.gridy = 2;
        btnKtpImage = new JButton("Pilih KTP");
        imagePanel.add(btnKtpImage, gbc);

        // KK Preview
        gbc.gridx = 1;
        gbc.gridy = 0;
        imagePanel.add(new JLabel("KK:"), gbc);
        gbc.gridy = 1;
        lblKkPreview = new JLabel();
        lblKkPreview.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        lblKkPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(lblKkPreview, gbc);
        gbc.gridy = 2;
        btnKkImage = new JButton("Pilih KK");
        imagePanel.add(btnKkImage, gbc);

        // Profile Preview
        gbc.gridx = 2;
        gbc.gridy = 0;
        imagePanel.add(new JLabel("Foto Profil:"), gbc);
        gbc.gridy = 1;
        lblProfilePreview = new JLabel();
        lblProfilePreview.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        lblProfilePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(lblProfilePreview, gbc);
        gbc.gridy = 2;
        btnProfileImage = new JButton("Pilih Foto");
        imagePanel.add(btnProfileImage, gbc);

        centerPanel.add(imagePanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Simpan");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        centerPanel.add(buttonPanel);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        centerPanel.add(scrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add action listeners for image buttons
        btnKtpImage.addActionListener(e -> selectImage("KTP", lblKtpPreview));
        btnKkImage.addActionListener(e -> selectImage("KK", lblKkPreview));
        btnProfileImage.addActionListener(e -> selectImage("Profile", lblProfilePreview));

        // Add action listeners for buttons
        btnSave.addActionListener(e -> {
            UserDocument doc = getFormData();
            if (validateInput(doc)) {
                controller.saveUserDocument(doc);
                clearForm();
            }
        });

        btnUpdate.addActionListener(e -> {
            UserDocument doc = getFormData();
            if (validateInput(doc)) {
                controller.updateUserDocument(doc);
                clearForm();
            }
        });

        btnDelete.addActionListener(e -> {
            String ktpNumber = txtKtpNumber.getText();
            if (!ktpNumber.isEmpty()) {
                controller.deleteUserDocument(ktpNumber);
                clearForm();
            }
        });

        btnClear.addActionListener(e -> clearForm());

        // Add table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    displaySelectedData(selectedRow);
                }
            }
        });

        // Add everything to frame
        add(mainPanel);
        pack();
    }

    private void selectImage(String type, JLabel previewLabel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg") ||
                        f.getName().toLowerCase().endsWith(".jpeg") ||
                        f.getName().toLowerCase().endsWith(".png") ||
                        f.isDirectory();
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                byte[] imageData = controller.loadImageFile(selectedFile);
                displayImagePreview(imageData, previewLabel);

                switch (type) {
                    case "KTP":
                        ktpImageData = imageData;
                        break;
                    case "KK":
                        kkImageData = imageData;
                        break;
                    case "Profile":
                        profileImageData = imageData;
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        }
    }

    private void displayImagePreview(byte[] imageData, JLabel previewLabel) {
        if (imageData != null && imageData.length > 0) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImg));
                }
            } catch (Exception ex) {
                previewLabel.setIcon(null);
                previewLabel.setText("Error loading image");
            }
        } else {
            previewLabel.setIcon(null);
            previewLabel.setText("No image");
        }
    }

    private void displaySelectedData(int selectedRow) {
        try {
            // Ambil data dari tabel
            txtKtpNumber.setText((String) table.getValueAt(selectedRow, 0));
            txtKkNumber.setText((String) table.getValueAt(selectedRow, 1));
            txtFullName.setText((String) table.getValueAt(selectedRow, 2));
            txtAddress.setText((String) table.getValueAt(selectedRow, 3));

            // Parse dan set tanggal lahir
            String birthDateStr = (String) table.getValueAt(selectedRow, 4);
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date birthDate = sdf.parse(birthDateStr);
                    dateChooser.setDate(birthDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Set bank account dan e-wallet
            txtBankAccount.setText((String) table.getValueAt(selectedRow, 5));
            txtEWallet.setText((String) table.getValueAt(selectedRow, 6));

            // Ambil data gambar dari database
            UserDocument doc = getSelectedDocument();
            if (doc != null) {
                // Set data gambar
                ktpImageData = doc.getKtpImage();
                kkImageData = doc.getKkImage();
                profileImageData = doc.getProfileImage();

                // Tampilkan preview gambar
                displayImagePreview(ktpImageData, lblKtpPreview);
                displayImagePreview(kkImageData, lblKkPreview);
                displayImagePreview(profileImageData, lblProfilePreview);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error menampilkan data: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtKtpNumber.setText("");
        txtKkNumber.setText("");
        txtFullName.setText("");
        txtAddress.setText("");
        txtBankAccount.setText("");
        txtEWallet.setText("");
        dateChooser.setDate(null);

        lblKtpPreview.setIcon(null);
        lblKkPreview.setIcon(null);
        lblProfilePreview.setIcon(null);

        ktpImageData = null;
        kkImageData = null;
        profileImageData = null;
    }

    private UserDocument getSelectedDocument() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String ktpNumber = (String) table.getValueAt(selectedRow, 0);
            try (SqlSession session = controller.getSqlSessionFactory().openSession()) {
                UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
                return mapper.selectByKtp(ktpNumber);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private UserDocument getFormData() {
        UserDocument doc = new UserDocument();
        doc.setKtpNumber(txtKtpNumber.getText());
        doc.setKkNumber(txtKkNumber.getText());
        doc.setFullName(txtFullName.getText());
        doc.setAddress(txtAddress.getText());
        doc.setBirthDate(dateChooser.getDate());
        doc.setBankAccount(txtBankAccount.getText());
        doc.setEWallet(txtEWallet.getText());
        doc.setKtpImage(ktpImageData);
        doc.setKkImage(kkImageData);
        doc.setProfileImage(profileImageData);
        return doc;
    }

    private boolean validateInput(UserDocument doc) {
        if (doc.getKtpNumber().isEmpty() || doc.getKkNumber().isEmpty() ||
                doc.getFullName().isEmpty() || doc.getAddress().isEmpty() ||
                doc.getBirthDate() == null || doc.getBankAccount().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return false;
        }
        return true;
    }

    public void refreshTable(List<UserDocument> documents) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No. KTP");
        model.addColumn("No. KK");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Tanggal Lahir");
        model.addColumn("No. Rekening");
        model.addColumn("E-Wallet");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (UserDocument doc : documents) {
            model.addRow(new Object[]{
                    doc.getKtpNumber(),
                    doc.getKkNumber(),
                    doc.getFullName(),
                    doc.getAddress(),
                    doc.getBirthDate() != null ? sdf.format(doc.getBirthDate()) : "",
                    doc.getBankAccount(),
                    doc.getEWallet()
            });
        }

        table.setModel(model);
    }
}