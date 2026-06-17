-- Membuat database pbo jika belum ada
CREATE DATABASE IF NOT EXISTS pbo;
USE pbo;

-- Membuat tabel mhs
CREATE TABLE IF NOT EXISTS mhs (
    id_mhs INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(20) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    nilai_uts DOUBLE NOT NULL,
    nilai_uas DOUBLE NOT NULL,
    nilai_tugas DOUBLE NOT NULL,
    nilai_akhir DOUBLE NOT NULL,
    nilai_huruf VARCHAR(5) NOT NULL,
    predikat VARCHAR(50) NOT NULL
);
