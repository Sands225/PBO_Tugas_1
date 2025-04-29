# PBO_Tugas_1
# Program Investasi Sederhana Berbasis Command Line
___
Hai! Selamat datang pada Program Sederhana kami.
<div style="text-align: justify">
Proyek program investasi sederhana ini kami buat dengan tujuan, agar mampu mengimplementasikan konsep pemrograman berorientasi objek pada suatu studi kasus menggunakan bahasa pemrograman Java, serta sebagai syarat untuk menuntaskan Tugas I dari mata kuliah Pemrograman Berorientasi Obyek.
</div>

Program Investasi ini dirancang untuk dua jenis user dengan akses yang berbeda, yaitu:
1. Admin diberikan akses untuk menambahkan saham, mengubah harga saham, dan menambahkan produk SBN<br>
2. Customer diberikan akses untuk melakukan pembelian dan penjualan saham, pembelian SBN, simulasi SBN, dan melihat portofolio investasi<br>

<div style="text-align: justify">

Program Investasi ini juga dilengkapi dengan input validasi pada class Input untuk memastikan pengguna memberikan input yang sesuai dengan yang dibutuhkan oleh program.


Selanjutnya, berikut merupakan deskripsi program, penjelasan bagaimana program akan dijalankan, dan UML dari program Investasi Sederhana.
</div>

# Identitas Kontributor
___
1. Nama : I Made Sandika Wijaya NIM : 2405551082 Matkul : PBO (E)
2. Nama : I Gusti Bagus Eri Widura NIM : 2405551054 Matkul : PBO (E)

# UML
___
![UML Program Investasi Sederhana](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/%5BSaved%5D%20UML_PBO_Tugas_1.jpg?raw=true)
___
# Bagaimana Program Dijalankan
<div style="text-align: justify">
Program Investasi ini dijalankan dengan menerapkan konsep Object Oriented Programming yaitu:
	Penggunaan Class dan Oject
	Penggunaan Flow Control seperti While loop, for each loop, do while loop, if-else, dan switch case
	Array list
	Inheritance
	Enscapsulasi
</div>

# Penggunaan Program
<div style="text-align: justify">Di bawah ini merupakan tata cara penggunaan Program Investasi Sederhana beserta hasil screenshot pada command line.</div>

# Login Admin dan Customer
![Login Admin](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/login.jpeg?raw=true)
![Welcome Admin](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/welcomeAdmin.jpeg?raw=true)
![Login Customer](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/WhatsApp%20Image%202025-04-29%20at%201.59.30%20PM(1).jpeg?raw=true)
![Welcome Customer](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/WhatsApp%20Image%202025-04-29%20at%201.59.30%20PM.jpeg?raw=true)
<div style="text-align: justify">Setelah program dijalankan, user akan diminta untuk memasukan username dan password mereka. Jika valid, program akan menentukan apakah user termasuk Admin atau Customer. Jika benar maka program akan mengeluarkan pesan selamat datang seperti gambar di atas. Jika salah program akan meminta user untuk menginput ulang usename dan password yang valid.</div>


# Menu Administrator
![menu admin](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/AdminMenu.jpeg?raw=true)
<div style="text-align: justify">Setelah melakukan login sebagai administrator, maka user akan disambut dengan menu dan dapat memilih intruksi tertera. Admin akan diminta untuk memilih opsi di atas dengan menginputkan angka untuk melanjutkan program.</div>

# Admin - Menu Saham
![Menu Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/AdminMenuSaham.jpeg?raw=true)
<div style="text-align: justify">Menu ini merupakan pusat pengelolaan data saham oleh administrator. Setelah memilih opsi "Saham" dari menu administrator, admin akan dihadapkan pada beberapa pilihan, seperti menambahkan saham baru, memperbarui harga saham yang sudah ada, dan kembali ke menu sebelumnya. Menu ini berfungsi sebagai akses langsung bagi admin untuk menjaga data saham tetap mutakhir dan relevan, serta memastikan portofolio investasi yang tersedia bagi customer selalu diperbarui secara akurat.</div>

# Admin - Menambahkan Saham
![Menu Admin Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/AdminMenuSaham.jpeg?raw=true)
![Menu Menambahkan Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/menusaham.jpeg?raw=true)
![Saham Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/SahamTersedia.jpeg?raw=true)
![Admin Menambahkan Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/admin-menambahkansaham.jpeg?raw=true)
![Konfirmasi Menambahkan Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/konfirmasitambahsaham.jpeg?raw=true)
![Berhasil Menambahkan Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/berhasiltambahsaha.jpeg?raw=true)

<div style="text-align: justify">
Fitur Menambah Saham memungkinkan administrator untuk menambahkan jenis saham baru ke dalam daftar investasi yang tersedia. Setelah memilih menu "Saham" dan memilih opsi untuk menambahkan saham, admin akan diminta untuk menginputkan data saham seperti nama saham, harga, dan kode saham. Data yang diinput akan divalidasi dan ditambahkan ke dalam sistem jika sudah sesuai. Proses ini memberikan fleksibilitas bagi admin dalam memperkaya portofolio investasi yang bisa diakses oleh customer.
</div>

# Admin - Memperbarui Saham
![Menu Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/menusaham2.jpeg?raw=true)
![Saham Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/SahamTersedia.jpeg?raw=true)
![Mengubah Harga Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/mengubahhargasaham.jpeg?raw=true)
![Konfirmasi Perbarui Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/konfirmasiperubahan.jpeg?raw=true)
![berhasil Perbarui Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/berhasildiperbarui.jpeg?raw=true)

<div style="text-align: justify">
Fitur ini memberikan kemampuan bagi admin untuk memperbarui harga saham yang sudah terdaftar di sistem. Setelah memilih saham yang ingin diperbarui, admin cukup memasukkan harga baru yang diinginkan. Proses validasi input juga diterapkan agar data yang dimasukkan tetap sesuai format. Sistem kemudian menampilkan konfirmasi bahwa perubahan harga telah berhasil dilakukan.
</div>

# Admin - Menu SBN
![Menu Admin SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(31).jpeg?raw=true)
![Menu SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(32).jpeg?raw=true)
![SBN Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(5).jpeg?raw=true)
![Memperbaharui SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(33).jpeg?raw=true)

<div style="text-align: justify">
Pada menu SBN, admin dapat melihat daftar Surat Berharga Negara (SBN) yang tersedia. Menu ini menjadi pusat pengelolaan SBN, memungkinkan admin untuk memperbarui atau menambahkan produk SBN baru. Admin dapat menavigasi ke submenu sesuai dengan kebutuhan pengelolaan.
</div>

# Admin - Menambahkan SBN
![Menambahkan SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(28).jpeg?raw=true)
![Konfirmasi Penambahkan SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(27).jpeg?raw=true)
![Menambahkan SBN Berhasil](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(29).jpeg?raw=true)

<div style="text-align: justify">
Fungsi ini memberikan akses bagi admin untuk menambahkan produk SBN baru. Admin akan diminta untuk mengisi data terkait nama, tingkat bunga, dan jangka waktu. Data yang dimasukkan akan disimpan dan ditampilkan pada daftar SBN yang dapat diakses oleh customer. Konfirmasi keberhasilan akan ditampilkan setelah proses penambahan selesai.
</div>

# Menu Customer
![menu customer](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(24).jpeg?raw=true)

<div style="text-align: justify">
Setelah melakukan login sebagai customer, maka user akan disambut dengan menu Customer berisikan instruksi seperti gambar di atas. Customer akan diminta untuk memilih opsi di atas dengan menginputkan angka untuk melanjutkan program.
</div>

# Customer - Membeli Saham
___

![Menu Customer Jual Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(17).jpeg?raw=true)
![Menu Membeli Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(17).jpeg?raw=true)
![Saham Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(15).jpeg?raw=true)
![Beli Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(16).jpeg?raw=true)
![Konfirmasi Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(13).jpeg?raw=true)
![Membeli Saham Berhasil](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(14).jpeg?raw=true)

<div style="text-align: justify">
Customer dapat membeli saham dari daftar saham yang tersedia. Setelah memilih opsi ini, sistem akan menampilkan daftar saham, kemudian customer dapat memilih saham yang ingin dibeli serta jumlah unit yang diinginkan. Setelah proses konfirmasi, saham yang dibeli akan langsung masuk ke dalam portofolio customer.
</div>

# Customer - Menjual Saham
___

![Menu Menjual Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(22).jpeg?raw=true)
![Saham Dimiliki](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(23).jpeg?raw=true)
![Jual Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(18).jpeg?raw=true)
![Konfirmasi Jual Saham](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(19).jpeg?raw=true)
![Menjual Saham Berhasil](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(20).jpeg?raw=true)

<div style="text-align: justify">
Fitur ini memungkinkan customer untuk menjual saham yang telah dimiliki. Sistem akan menampilkan daftar saham yang dimiliki oleh customer beserta jumlah unitnya. Customer memilih saham yang ingin dijual dan jumlah unit yang akan dilepas. Setelah konfirmasi, sistem akan mengupdate portofolio dan menampilkan informasi penjualan berhasil.
</div>

# Customer - Membeli SBN
___
![Menu Membeli SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(11).jpeg?raw=true)
![SBN Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(10).jpeg?raw=true)
![Membeli SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(12).jpeg?raw=true)

<div style="text-align: justify">
Customer dapat membeli produk Surat Berharga Negara (SBN) yang tersedia dalam sistem. Setelah memilih menu ini, customer akan melihat daftar SBN lengkap dengan tingkat bunga dan tenor. Dengan memasukkan nominal investasi, sistem akan menghitung dan menyimpan pembelian SBN ke dalam portofolio customer.
</div>

# Customer - Simulasi SBN
___
![Menu Simulasi SBN](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(7).jpeg?raw=true)
![SBN Tersedia](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/WhatsApp%20Image%202025-04-29%20at%204.32.06%20PM.jpeg?raw=true)
![Simulasi SBN2](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(6).jpeg?raw=true)
![Hasil Simulasi](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(4).jpeg?raw=true)

<div style="text-align: justify">
Fitur simulasi ini memberikan gambaran kepada customer tentang potensi keuntungan dari investasi SBN. Setelah memilih salah satu produk SBN dan memasukkan jumlah nominal investasi, sistem akan menghitung proyeksi imbal hasil berdasarkan tingkat bunga dan durasi investasi. Hasil simulasi akan ditampilkan sebagai bahan pertimbangan sebelum melakukan investasi riil.
</div>

# Customer - Portofolio
___
![Menu Portofolio](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(3).jpeg?raw=true)
![Lihat Portofolio](https://github.com/BagusEriW/Assets_PBO_Tugas_1/blob/main/1%20(2).jpeg?raw=true)

<div style="text-align: justify">
Fitur ini menyajikan ringkasan seluruh investasi customer, baik dalam bentuk saham maupun SBN. Portofolio akan menampilkan detail nama saham/SBN, jumlah yang dimiliki, serta nilai estimasi saat ini. Fitur ini membantu customer untuk memonitor perkembangan investasi mereka secara komprehensif dan terstruktur.
</div>
