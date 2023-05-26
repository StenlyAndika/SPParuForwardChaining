-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 17, 2018 at 08:15 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spparu`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` varchar(5) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`) VALUES
('A001', 'Stenly', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `diagnosa`
--

CREATE TABLE `diagnosa` (
  `idrekam` varchar(12) NOT NULL,
  `idpas` varchar(12) NOT NULL,
  `tgl` date NOT NULL,
  `gejala` varchar(350) NOT NULL,
  `penyakit` varchar(50) NOT NULL,
  `jml_diagnosis` int(3) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `diagnosa`
--

INSERT INTO `diagnosa` (`idrekam`, `idpas`, `tgl`, `gejala`, `penyakit`, `jml_diagnosis`) VALUES
('170818131344', '170818124615', '2018-08-17', '- Pasien Batuk\n- Batuk Lebih Dari 3 Minggu Tanpa Respon Terhadap Obat Batuk\n- Batuk Berdarah\n- Sesak Napas\n- Nafsu Makan Berkurang\n- Berat Badan Menurun\n- Pasien Cepat Lelah\n- Radang Paru Kerap Berulang\n- Suara Parau\n- Ada Rasa Nyeri Di Daerah Dada\n- Ada Rasa Nyeri Di Daerah Bahu Atau Punggung\n- Ada Pembengkakan Di Leher\n- Ada Pembengkakan Di Wajah', 'Kanker Paru', 4),
('170818130930', '170818124615', '2018-08-17', '- Pasien Batuk\n- Batuk Lebih Dari 3 Minggu Tanpa Respon Terhadap Obat Batuk\n- Batuk Berdarah\n- Sesak Napas\n- Nafsu Makan Berkurang\n- Berat Badan Menurun\n- Pasien Cepat Lelah\n- Radang Paru Kerap Berulang\n- Suara Parau\n- Ada Rasa Nyeri Di Daerah Dada\n- Ada Rasa Nyeri Di Daerah Bahu Atau Punggung\n- Ada Pembengkakan Di Leher\n- Ada Pembengkakan Di Wajah', 'Kanker Paru', 3),
('170818130717', '170818124615', '2018-08-17', '- Pasien Batuk\n- Batuk Lebih Dari 3 Minggu Tanpa Respon Terhadap Obat Batuk\n- Nafsu Makan Berkurang\n- Berat Badan Menurun\n- Pasien Cepat Lelah\n- Radang Paru Kerap Berulang\n- Suara Parau\n- Ada Rasa Nyeri Di Daerah Dada\n- Ada Rasa Nyeri Di Daerah Bahu Atau Punggung\n- Ada Pembengkakan Di Leher\n- Ada Pembengkakan Di Wajah\n', 'Kanker Paru', 2),
('170818124637', '170818124615', '2018-08-17', '- Batuk\n- Batuk Lebih Dari 3 Minggu Tanpa Respon Terhadap Obat Batuk\n- Batuk Berdarah\n- Sesak Napas\n- Nafsu Makan Berkurang\n- Berat Badan Menurun\n- Cepat Lelah\n- Radang Paru Kerap Berulang\n- Suara Parau\n- Rasa Nyeri Di Daerah Dada\n- Rasa Nyeri Di Daerah Bahu Atau Punggung\n- Pembengkakan Di Leher\n- Pembengkakan Di Wajah\n', 'Kanker Paru', 1);

-- --------------------------------------------------------

--
-- Table structure for table `gejala`
--

CREATE TABLE `gejala` (
  `kode` varchar(3) NOT NULL,
  `gejala` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gejala`
--

INSERT INTO `gejala` (`kode`, `gejala`) VALUES
('G01', 'Pasien Batuk'),
('G02', 'Batuk Lebih Dari 3 Minggu Tanpa Respon Terhadap Obat Batuk'),
('G03', 'Dahak Bersifat Mukoid (Kental Kehijauan)'),
('G04', 'Dahak Bersifat Purulen (Cair Kekuningan) Dan Bernanah Pada Keadaan Terinfeksi'),
('G05', 'Batuk Berdarah'),
('G06', 'Sesak Napas'),
('G07', 'Sesak Napas Ketika Mengerahkan Tenaga'),
('G08', 'Batuk Muncul Sebelum Atau Bersamaan Dengan Sesak Napas'),
('G09', 'Pasien Demam'),
('G10', 'Menggigil'),
('G11', 'Terjadi Keringat Malam'),
('G12', 'Malaise'),
('G13', 'Nafsu Makan Berkurang'),
('G14', 'Berat Badan Menurun'),
('G15', 'Ketika Bernapas Kadang Terdengar Suara \"Ngik\" Atau Mengi'),
('G16', 'Dada Terasa Penuh'),
('G17', 'Keluhan Menjelang Pagi Atau Malam'),
('G18', 'Asma Nokturnal Terjadi Antara Pukul 04:00 - 06:00 Pagi'),
('G19', 'Batuk Memberat Pada Malam Hari'),
('G20', 'Ada Riwayat Keluarga Asma'),
('G21', 'Pasien Cepat Lelah'),
('G22', 'Radang Paru Kerap Berulang'),
('G23', 'Suara Parau'),
('G24', 'Ada Rasa Nyeri Di Daerah Dada'),
('G25', 'Ada Rasa Nyeri Di Daerah Bahu Atau Punggung'),
('G26', 'Ada Pembengkakan Di Leher'),
('G27', 'Ada Pembengkakan Di Wajah');

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `idpas` varchar(12) NOT NULL,
  `nama` varchar(35) NOT NULL,
  `alamat` varchar(35) NOT NULL,
  `jekel` varchar(1) NOT NULL,
  `umur` int(2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`idpas`, `nama`, `alamat`, `jekel`, `umur`) VALUES
('170818124615', 'Stenly Andika', 'Siulak Mukai', 'L', 21);

-- --------------------------------------------------------

--
-- Table structure for table `penyakit`
--

CREATE TABLE `penyakit` (
  `kode` varchar(3) NOT NULL,
  `penyakit` varchar(50) NOT NULL,
  `info` varchar(500) NOT NULL,
  `solusi` varchar(500) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyakit`
--

INSERT INTO `penyakit` (`kode`, `penyakit`, `info`, `solusi`) VALUES
('P01', 'Tuberkulosis Paru (TBC)', 'Tuberkulosis (TB) Yang Juga Dikenal Dengan Singkatan TBC, \nAdalah Penyakit Menular Paru-Paru Yang Disebabkan Oleh Basil Mycobacterium\nTuberculosis. Penyakit Ini Ditularkan Dari Penderita TB Aktif Yang Batuk Dan Mengeluarkan \nTitik-Titik Kecil Air Liur Dan Terinhalasi Oleh Orang Sehat Yang Tidak Memiliki Kekebalan \nTubuh Terhadap Penyakit Ini', 'Pemberian Anti Biotik\n- Isoniazid\n- Rifampicin\n- Pyrazinamide\n- Ethambutol\n\nEfek Samping\n- Rifampicin Dapat Menurunkan Keefektifan Alat Kontrasepsi \n   Yang Mengandung Hormon\n- Ethambutol Dapat Memengaruhi Kondisi Penglihatan Pengidap\n- Isoniazid Berpotensi Merusak Saraf\nEfek Samping Lainnya \n- Mual, Muntah, Penurunan Nafsu Makan, Sakit Kuning, \n   Urine Yang Berwarna Gelap, Demam, Ruam, \n   Serta Gatal-Gatal Pada Kulit'),
('P02', 'Penyakit Paru Obstruktif Kronis (PPOK)', 'Penyakit Paru Obstruktif Kronis (PPOK) adalah penyakit peradangan paru\r\nyang berkembang dalam jangka waktu panjang. Penyakit ini menghalangi\r\naliran udara dari paru-paru karena terhalang pembengkakan dan dahak\r\nsehingga penderitanya sulit bernapas', '- Pemberian inhaler (Obat Hirup)  seperti gabungan bronkodilator dan kortikosteroid\r\n- Pemberian Obat kapsul/tablet seperti teofilin dan mukolitik\r\n- Fisioterapi atau rehabilitas paru-paru\r\n- Operasi (jika penyakit sudah sangat parah)\r\n	'),
('P03', 'Asma Bronkial', 'Asma Bronkial Adalah Nama Lain Dari Penyakit Asma\nAdalah Jenis Penyakit Jangka Panjang Atau Kronis Pada Saluran Pernapasan\nYang Ditandai Dengan Peradangan Dan Penyempitan Saluran Napas Yang \nMenimbulkan Sesak Atau Sulit Bernapas', 'Pemberian Inhaler (Obat Hirup)\n- Direkomendasikan Untuk Menggunakan Inhaler Kombinasi\n  Seperti Bronkodilator Dan Kortikosteroid\nAda Dua Jenis Inhaler\n- Inhaler Pereda Digunakan Untuk Meringankan Gejala Asma\n  Dengan Cepat Saat Serangan Sedang Berlangsung\n- Inhaler Pencegah Digunakan Untuk Mencegah Dan Mengurangi\n  Jumlah Peradangan Dan Sensitivitas Yang Terjadi Di Dalam\n  Saluran Napas\n\nEfek Samping Inhaler\n- Sakit Kepala, Keram Otot, Dan Sedikit Gemetar (Tremor) Pada Tangan'),
('P04', 'Kanker Paru', 'Kanker Paru-Paru Adalah Suatu Kondisi Dimana Sel-Sel Tumbuh Secara \nTidak Terkendali Didalam Paru-Paru', 'Untuk Kanker Stadium Awal\n- Terapi Fotodinamik\n- Terapi Biologis\n\nUntuk Kanker Yang Telah Menyebar\n- Radioterapi dan/atau Kemoterapi\n\nOperasi Pengangkatan Kanker Paru Seperti\n- Wedge Resection\n- Lebektomi\n- Pneumonektomi'),
('P05', 'Pneumonia', 'Pneumonia Atau Dikenal Juga Dengan Istilah Paru-Paru Basah \nAdalah Infeksi Yang Memicu Inflamasi Pada Kantong-Kantong Udara Di Salah Satu \nAtau Kedua Paru-Paru. Pada Pengidap Pneumonia, Sekumpulan Kantong-Kantong \nUdara Kecil Di Ujung Saluran Pernapasan Dalam Paru-Paru Akan Membengkak \nDan Dipenuhi Cairan.', 'Mengonsumsi Analgesik (Obat Pereda Rasa Sakit) seperti \n- Parasetamol\n- Ibuprofen (Tidak Dianjurkan Jika Pasien Mengidap Alergi Terhadap Aspirin)\n\nBerhenti Merorok Karena Merokok Dapat Memperburuk Pneumonia\nMenghindari Konsumsi Obat Batuk Karena Batuk Berfungsi Untuk Mengeluarkan\nDahak Dari Paru-Paru, Meredakan Batuk Bisa Mengakibatkan Infeksi Yang Lebih Lama');

-- --------------------------------------------------------

--
-- Table structure for table `r1`
--

CREATE TABLE `r1` (
  `kode` varchar(15) NOT NULL,
  `tipe` varchar(2) NOT NULL,
  `pertanyaan` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `r1`
--

INSERT INTO `r1` (`kode`, `tipe`, `pertanyaan`) VALUES
('111111111', 'Q', 'G23'),
('1111111111', 'Q', 'G24'),
('1111111', 'Q', 'G21'),
('11111111', 'Q', 'G22'),
('111111', 'Q', 'G14'),
('1111', 'Q', 'G06'),
('11111', 'Q', 'G13'),
('111', 'Q', 'G05'),
('1', 'Q', 'G01'),
('11', 'Q', 'G02'),
('11111111111', 'Q', 'G25'),
('111111111111', 'Q', 'G26'),
('1111111111111', 'Q', 'G27'),
('11111111111111', 'A', 'P04'),
('1110', 'G', 'G.r2');

-- --------------------------------------------------------

--
-- Table structure for table `r2`
--

CREATE TABLE `r2` (
  `kode` varchar(15) NOT NULL,
  `tipe` varchar(2) NOT NULL,
  `pertanyaan` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `r2`
--

INSERT INTO `r2` (`kode`, `tipe`, `pertanyaan`) VALUES
('10', 'G', 'G.r4'),
('110', 'G', 'G.r3'),
('111111', 'A', 'P02'),
('11111', 'Q', 'G08'),
('1111', 'Q', 'G07'),
('111', 'Q', 'G06'),
('11', 'Q', 'G04'),
('1', 'Q', 'G03');

-- --------------------------------------------------------

--
-- Table structure for table `r3`
--

CREATE TABLE `r3` (
  `kode` varchar(15) NOT NULL,
  `tipe` varchar(2) NOT NULL,
  `pertanyaan` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `r3`
--

INSERT INTO `r3` (`kode`, `tipe`, `pertanyaan`) VALUES
('11111111', 'A', 'P01'),
('1', 'Q', 'G05'),
('1111111', 'Q', 'G14'),
('111111', 'Q', 'G13'),
('11111', 'Q', 'G12'),
('1111', 'Q', 'G11'),
('10', 'G', 'G.r1'),
('111', 'Q', 'G09'),
('11', 'Q', 'G06');

-- --------------------------------------------------------

--
-- Table structure for table `r4`
--

CREATE TABLE `r4` (
  `kode` varchar(15) NOT NULL,
  `tipe` varchar(2) NOT NULL,
  `pertanyaan` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `r4`
--

INSERT INTO `r4` (`kode`, `tipe`, `pertanyaan`) VALUES
('1', 'Q', 'G04'),
('11', 'Q', 'G05'),
('111', 'Q', 'G06'),
('1111', 'Q', 'G09'),
('11111', 'Q', 'G10'),
('111111', 'Q', 'G24'),
('1111111', 'A', 'P05'),
('10', 'G', 'G.r5');

-- --------------------------------------------------------

--
-- Table structure for table `r5`
--

CREATE TABLE `r5` (
  `kode` varchar(15) NOT NULL,
  `tipe` varchar(2) NOT NULL,
  `pertanyaan` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `r5`
--

INSERT INTO `r5` (`kode`, `tipe`, `pertanyaan`) VALUES
('1', 'Q', 'G06'),
('11', 'Q', 'G15'),
('111', 'Q', 'G16'),
('1111', 'Q', 'G17'),
('11111', 'Q', 'G18'),
('111111', 'Q', 'G19'),
('1111111', 'Q', 'G20'),
('11111111', 'A', 'P03'),
('11111110', 'Q', 'G20');

-- --------------------------------------------------------

--
-- Table structure for table `ruleutama`
--

CREATE TABLE `ruleutama` (
  `kode` varchar(3) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ruleutama`
--

INSERT INTO `ruleutama` (`kode`) VALUES
('r1'),
('r2'),
('r3'),
('r4'),
('r5');

-- --------------------------------------------------------

--
-- Table structure for table `templogin`
--

CREATE TABLE `templogin` (
  `id` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `diagnosa`
--
ALTER TABLE `diagnosa`
  ADD PRIMARY KEY (`idrekam`);

--
-- Indexes for table `gejala`
--
ALTER TABLE `gejala`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`idpas`);

--
-- Indexes for table `penyakit`
--
ALTER TABLE `penyakit`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `r1`
--
ALTER TABLE `r1`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `r2`
--
ALTER TABLE `r2`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `r3`
--
ALTER TABLE `r3`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `r4`
--
ALTER TABLE `r4`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `r5`
--
ALTER TABLE `r5`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `ruleutama`
--
ALTER TABLE `ruleutama`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `templogin`
--
ALTER TABLE `templogin`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
