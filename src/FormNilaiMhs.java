import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormNilaiMhs extends JFrame {
    private JTextField txtNim;
    private JTextField txtNama;
    private JTextField txtUts;
    private JTextField txtUas;
    private JTextField txtTugas;
    private JTextField txtNilaiAkhir;
    private JTextField txtNilaiHuruf;
    private JTextField txtPredikat;
    
    private JButton btnProses;
    private JButton btnSimpan;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnTambahLain;
    
    private JTable tblMhs;
    private DefaultTableModel tableModel;
    
    private List<Mhs> mhsList = new ArrayList<>();
    
    private int selectedId = -1;

    public FormNilaiMhs() {
        setTitle("Aplikasi Penilaian Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));
        setContentPane(mainPanel);
        
        JLabel lblHeader = new JLabel("Form Penilaian Mahasiswa", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(new Color(44, 62, 80));
        mainPanel.add(lblHeader, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        contentPanel.setOpaque(false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);
        contentPanel.add(leftPanel);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        formPanel.setBackground(Color.WHITE);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;
        
        txtNim = createStyledTextField();
        txtNama = createStyledTextField();
        txtUts = createStyledTextField();
        txtUas = createStyledTextField();
        txtTugas = createStyledTextField();
        
        txtNilaiAkhir = createStyledTextField();
        txtNilaiAkhir.setEditable(false);
        txtNilaiAkhir.setBackground(new Color(240, 242, 245));
        
        txtNilaiHuruf = createStyledTextField();
        txtNilaiHuruf.setEditable(false);
        txtNilaiHuruf.setBackground(new Color(240, 242, 245));
        
        txtPredikat = createStyledTextField();
        txtPredikat.setEditable(false);
        txtPredikat.setBackground(new Color(240, 242, 245));
        
        addFormRow(formPanel, "NIM:", txtNim, gbc, 0);
        addFormRow(formPanel, "Nama Mahasiswa:", txtNama, gbc, 1);
        addFormRow(formPanel, "Nilai UTS (35%):", txtUts, gbc, 2);
        addFormRow(formPanel, "Nilai UAS (35%):", txtUas, gbc, 3);
        addFormRow(formPanel, "Nilai Tugas (30%):", txtTugas, gbc, 4);
        addFormRow(formPanel, "Nilai Akhir:", txtNilaiAkhir, gbc, 5);
        addFormRow(formPanel, "Nilai Huruf:", txtNilaiHuruf, gbc, 6);
        addFormRow(formPanel, "Predikat:", txtPredikat, gbc, 7);
        
        JPanel btnPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        btnPanel.setOpaque(false);
        
        btnProses = createStyledButton("Proses", new Color(52, 152, 219));
        btnSimpan = createStyledButton("Simpan", new Color(46, 204, 113));
        btnUpdate = createStyledButton("Update", new Color(241, 196, 15));
        btnHapus = createStyledButton("Hapus", new Color(231, 76, 60));
        btnTambahLain = createStyledButton("Tambah Lain", new Color(149, 165, 166));
        
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        
        btnPanel.add(btnProses);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnTambahLain);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnHapus);
        
        leftPanel.add(btnPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setOpaque(false);
        contentPanel.add(rightPanel);
        
        String[] columns = {"ID", "NIM", "Nama", "UTS", "UAS", "Tugas", "Akhir", "Huruf", "Predikat"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblMhs = new JTable(tableModel);
        tblMhs.setRowHeight(25);
        tblMhs.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblMhs.getTableHeader().setBackground(new Color(230, 235, 240));
        tblMhs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblMhs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tblMhs.getColumnModel().getColumn(0).setMinWidth(0);
        tblMhs.getColumnModel().getColumn(0).setMaxWidth(0);
        tblMhs.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tblMhs);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        btnProses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesHitungNilai();
            }
        });
        
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });
        
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });
        
        btnTambahLain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kosongkanTextField();
                btnSimpan.setEnabled(true);
                btnUpdate.setEnabled(false);
                btnHapus.setEnabled(false);
                tblMhs.clearSelection();
                selectedId = -1;
            }
        });
        
        tblMhs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tblMhs.getSelectedRow();
                if (index != -1) {
                    terpilih(index);
                }
            }
        });
        
        tampilkanDiTabel();
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 205, 210), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return textField;
    }
    
    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void addFormRow(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(50, 65, 80));
        
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(textField, gbc);
    }
    
    public void kosongkanTextField() {
        txtNim.setText("");
        txtNama.setText("");
        txtUts.setText("");
        txtUas.setText("");
        txtTugas.setText("");
        txtNilaiAkhir.setText("");
        txtNilaiHuruf.setText("");
        txtPredikat.setText("");
    }
    
    public void prosesHitungNilai() {
        try {
            if (txtNim.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() || 
                txtUts.getText().trim().isEmpty() || txtUas.getText().trim().isEmpty() || 
                txtTugas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field input wajib diisi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double uts = Double.parseDouble(txtUts.getText().trim());
            double uas = Double.parseDouble(txtUas.getText().trim());
            double tugas = Double.parseDouble(txtTugas.getText().trim());
            
            if (uts < 0 || uts > 100 || uas < 0 || uas > 100 || tugas < 0 || tugas > 100) {
                JOptionPane.showMessageDialog(this, "Nilai UTS, UAS, dan Tugas harus berada di rentang 0 - 100!", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Mhs mhs = new Mhs(txtNim.getText().trim(), txtNama.getText().trim(), uts, uas, tugas);
            
            txtNilaiAkhir.setText(String.format("%.2f", mhs.nilaiAkhir()));
            txtNilaiHuruf.setText(mhs.getNilHuruf());
            txtPredikat.setText(mhs.getPredikat());
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nilai UTS, UAS, dan Tugas harus berupa angka numerik!", "Format Salah", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Mhs> getMhsList() {
        List<Mhs> list = new ArrayList<>();
        String query = "SELECT * FROM mhs ORDER BY id_mhs DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Mhs mhs = new Mhs(
                    rs.getInt("id_mhs"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getDouble("nilai_uts"),
                    rs.getDouble("nilai_uas"),
                    rs.getDouble("nilai_tugas")
                );
                list.add(mhs);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data dari database: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return list;
    }
    
    public void kosongkanTabel() {
        tableModel.setRowCount(0);
    }
    
    public void tampilkanDiTabel() {
        kosongkanTabel();
        mhsList = getMhsList();
        
        for (Mhs mhs : mhsList) {
            Object[] row = {
                mhs.getIdMhs(),
                mhs.getNim(),
                mhs.getNama(),
                mhs.uts(),
                mhs.uas(),
                mhs.tugas(),
                String.format("%.2f", mhs.nilaiAkhir()),
                mhs.getNilHuruf(),
                mhs.getPredikat()
            };
            tableModel.addRow(row);
        }
    }
    
    public void terpilih(int index) {
        if (index >= 0 && index < mhsList.size()) {
            Mhs mhs = mhsList.get(index);
            selectedId = mhs.getIdMhs();
            
            txtNim.setText(mhs.getNim());
            txtNama.setText(mhs.getNama());
            txtUts.setText(String.valueOf(mhs.uts()));
            txtUas.setText(String.valueOf(mhs.uas()));
            txtTugas.setText(String.valueOf(mhs.tugas()));
            
            txtNilaiAkhir.setText(String.format("%.2f", mhs.nilaiAkhir()));
            txtNilaiHuruf.setText(mhs.getNilHuruf());
            txtPredikat.setText(mhs.getPredikat());
            
            btnSimpan.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnHapus.setEnabled(true);
        }
    }
    
    private void simpanData() {
        prosesHitungNilai();
        if (txtNilaiAkhir.getText().isEmpty()) return;
        
        String query = "INSERT INTO mhs (nim, nama, nilai_uts, nilai_uas, nilai_tugas, nilai_akhir, nilai_huruf, predikat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                       
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            double uts = Double.parseDouble(txtUts.getText().trim());
            double uas = Double.parseDouble(txtUas.getText().trim());
            double tugas = Double.parseDouble(txtTugas.getText().trim());
            Mhs mhs = new Mhs(txtNim.getText().trim(), txtNama.getText().trim(), uts, uas, tugas);
            
            pstmt.setString(1, mhs.getNim());
            pstmt.setString(2, mhs.getNama());
            pstmt.setDouble(3, mhs.uts());
            pstmt.setDouble(4, mhs.uas());
            pstmt.setDouble(5, mhs.tugas());
            pstmt.setDouble(6, mhs.nilaiAkhir());
            pstmt.setString(7, mhs.getNilHuruf());
            pstmt.setString(8, mhs.getPredikat());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            kosongkanTextField();
            tampilkanDiTabel();
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "NIM sudah terdaftar! Harap gunakan NIM yang unik.", "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        prosesHitungNilai();
        if (txtNilaiAkhir.getText().isEmpty()) return;
        
        String query = "UPDATE mhs SET nim = ?, nama = ?, nilai_uts = ?, nilai_uas = ?, nilai_tugas = ?, nilai_akhir = ?, nilai_huruf = ?, predikat = ? WHERE id_mhs = ?";
                       
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            double uts = Double.parseDouble(txtUts.getText().trim());
            double uas = Double.parseDouble(txtUas.getText().trim());
            double tugas = Double.parseDouble(txtTugas.getText().trim());
            Mhs mhs = new Mhs(txtNim.getText().trim(), txtNama.getText().trim(), uts, uas, tugas);
            
            pstmt.setString(1, mhs.getNim());
            pstmt.setString(2, mhs.getNama());
            pstmt.setDouble(3, mhs.uts());
            pstmt.setDouble(4, mhs.uas());
            pstmt.setDouble(5, mhs.tugas());
            pstmt.setDouble(6, mhs.nilaiAkhir());
            pstmt.setString(7, mhs.getNilHuruf());
            pstmt.setString(8, mhs.getPredikat());
            pstmt.setInt(9, selectedId);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            kosongkanTextField();
            btnSimpan.setEnabled(true);
            btnUpdate.setEnabled(false);
            btnHapus.setEnabled(false);
            selectedId = -1;
            
            tampilkanDiTabel();
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "NIM sudah terdaftar pada mahasiswa lain!", "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data mahasiswa terpilih?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM mhs WHERE id_mhs = ?";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setInt(1, selectedId);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                kosongkanTextField();
                btnSimpan.setEnabled(true);
                btnUpdate.setEnabled(false);
                btnHapus.setEnabled(false);
                selectedId = -1;
                
                tampilkanDiTabel();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
