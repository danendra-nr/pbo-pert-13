# Aplikasi Penilaian Mahasiswa

Aplikasi Desktop Java berbasis Swing dan MySQL untuk mengelola nilai mahasiswa secara otomatis dengan fungsionalitas CRUD lengkap. Proyek ini dibuat sebagai tugas praktikum Pemrograman Berorientasi Objek (PBO) dengan struktur kode yang telah dioptimasi.

## Fitur Utama

- **GUI Modern**: Tampilan Swing menggunakan layout yang rapi dan System Look & Feel.
- **Perhitungan Otomatis**: Menghitung Nilai Akhir (35% UTS + 35% UAS + 30% Tugas), Nilai Huruf (A-E), dan Predikat secara instan.
- **Koneksi Database**: Menyimpan, membaca, mengubah, dan menghapus data mahasiswa langsung ke MySQL database.
- **Validasi Input**: Mencegah kesalahan input karakter non-numerik pada kolom nilai serta batasan nilai antara 0-100.
- **Keamanan**: Penggunaan `PreparedStatement` untuk menghindari SQL Injection.

## Struktur Project

```text
pbo-pert-13/
├── src/
│   ├── Main.java
│   ├── DBConnection.java
│   ├── Mhs.java
│   └── FormNilaiMhs.java
├── schema.sql
└── README.md
```

## Persyaratan Sistem

- Java Development Kit (JDK) 8 atau yang terbaru.
- MySQL Database Server (misal: via XAMPP).
- Driver MySQL JDBC Connector (`mysql-connector-j` atau `mysql-connector-java`).

## Cara Instalasi & Menjalankan Aplikasi

1. **Setup Database**:
   - Jalankan MySQL server Anda.
   - Buat database `pbo` dan impor tabel `mhs` menggunakan file [schema.sql](schema.sql).

2. **Kompilasi**:
   - Pastikan driver JDBC MySQL berada di classpath Anda.
   - Jalankan perintah kompilasi:
     ```bash
     javac -cp ".;path/to/mysql-connector-java.jar" src/*.java -d bin
     ```

3. **Menjalankan Program**:
   - Jalankan program menggunakan kelas `Main`:
     ```bash
     java -cp "bin;path/to/mysql-connector-java.jar" Main
     ```
