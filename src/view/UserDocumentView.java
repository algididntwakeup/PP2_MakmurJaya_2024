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
    private JTextField txtFullName, txtAddress, txtPhoneNumber;
    private JComboBox<String> comboGender;
    private JDateChooser dateChooser;
    private JButton btnProfileImage;
    private JTable table;
    private byte[] profileImageData;
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
        formPanel.add(new JLabel("Nama Lengkap:"), gbc);
        gbc.gridx = 1;
        txtFullName = new JTextField(20);
        formPanel.add(txtFullName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAddress = new JTextField(20);
        formPanel.add(txtAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Tanggal Lahir:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        formPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        String[] genderOptions = {"Laki-laki", "Perempuan"};
        comboGender = new JComboBox<>(genderOptions);
        formPanel.add(comboGender, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("No. Telepon:"), gbc);
        gbc.gridx = 1;
        txtPhoneNumber = new JTextField(20);
        formPanel.add(txtPhoneNumber, gbc);

        centerPanel.add(formPanel);

        // Image Preview Panel
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Foto Profil"));

        // Profile Preview
        gbc.gridx = 0;
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
        JButton btnChangePassword = new JButton("Ubah Password");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnChangePassword);

        centerPanel.add(buttonPanel);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        centerPanel.add(scrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add action listeners for image buttons
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
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (Integer) table.getValueAt(selectedRow, 0);
                controller.deleteUserDocument(id);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            }
        });

        btnClear.addActionListener(e -> clearForm());

        btnChangePassword.addActionListener(e -> {
            new ChangePasswordView(user, controller.getSqlSessionFactory()).setVisible(true);
        });

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

        // Update table columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("No. Telepon");
        table.setModel(model);
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
                profileImageData = imageData;
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
            Object idObj = table.getValueAt(selectedRow, 0);
            int id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
            
            txtFullName.setText((String) table.getValueAt(selectedRow, 1));
            txtAddress.setText((String) table.getValueAt(selectedRow, 2));
            
            // Parse and set birth date
            String birthDateStr = (String) table.getValueAt(selectedRow, 3);
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date birthDate = sdf.parse(birthDateStr);
                    dateChooser.setDate(birthDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Set gender and phone number
            comboGender.setSelectedItem(table.getValueAt(selectedRow, 4));
            txtPhoneNumber.setText((String) table.getValueAt(selectedRow, 5));

            // Get document data from database
            UserDocument doc = controller.getUserDocumentById(id);
            if (doc != null) {
                profileImageData = doc.getProfileImage();
                displayImagePreview(profileImageData, lblProfilePreview);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error menampilkan data: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtFullName.setText("");
        txtAddress.setText("");
        dateChooser.setDate(null);
        comboGender.setSelectedIndex(0);
        txtPhoneNumber.setText("");
        lblProfilePreview.setIcon(null);
        profileImageData = null;
    }

    private UserDocument getSelectedDocument() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (Integer) table.getValueAt(selectedRow, 0);
            try (SqlSession session = controller.getSqlSessionFactory().openSession()) {
                UserDocumentMapper mapper = session.getMapper(UserDocumentMapper.class);
                return mapper.selectById(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private UserDocument getFormData() {
        UserDocument doc = new UserDocument();
        doc.setFullName(txtFullName.getText());
        doc.setAddress(txtAddress.getText());
        doc.setBirthDate(dateChooser.getDate());
        doc.setGender((String) comboGender.getSelectedItem());
        doc.setPhoneNumber(txtPhoneNumber.getText());
        doc.setProfileImage(profileImageData);
        return doc;
    }

    private boolean validateInput(UserDocument doc) {
        if (doc.getFullName().isEmpty() || 
            doc.getAddress().isEmpty() ||
            doc.getBirthDate() == null ||
            doc.getGender() == null ||
            doc.getPhoneNumber().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return false;
        }
        return true;
    }

    public void refreshTable(List<UserDocument> documents) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (UserDocument doc : documents) {
            model.addRow(new Object[]{
                doc.getId(),
                doc.getFullName(),
                doc.getAddress(),
                doc.getBirthDate() != null ? sdf.format(doc.getBirthDate()) : "",
                doc.getGender(),
                doc.getPhoneNumber()
            });
        }
    }

    public void displayUserData(UserDocument doc) {
        if (doc != null) {
            txtFullName.setText(doc.getFullName());
            txtAddress.setText(doc.getAddress());
            dateChooser.setDate(doc.getBirthDate());
            comboGender.setSelectedItem(doc.getGender());
            txtPhoneNumber.setText(doc.getPhoneNumber());
            
            // Display profile image if exists
            if (doc.getProfileImage() != null) {
                profileImageData = doc.getProfileImage();
                displayImagePreview(profileImageData, lblProfilePreview);
            }
        }
    }
}