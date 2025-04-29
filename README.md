#PBO_Tugas_1

#Program Investasi Sederhana Berbasis Command Line

Hai! Selamat datang pada proyek kecil kami. Proyek program investasi sederhana ini kami buat dengan tujuan, agar mampu mengimplementasikan konsep pemrograman berorientasi objek pada suatu
studi kasus menggunakan bahasa pemrograman Java, serta sebagai syarat untuk menuntaskan Tugas I dari mata kuliah pemrograman berorientasi obyek.

Program Investasi ini dirancang untuk dua jenis user dengan akses yang berbeda, yaitu:
	1. Admin diberikan akses untuk menambahkan saham, mengubah harga saham, dan menambahkan produk SBN
	2. Customer diberikan akses untuk melakukan pembelian dan penjualan saham, pembelian SBN, simulasi SBN, dan melihat portofolio investasi.

Program Investasi ini juga dilengkapi dengan input validasi pada class Input untuk memastikan pengguna memberikan input yang sesuai dengan yang dibutuhkan oleh program.

Di bawah ini adalah deskripsi program, penjelasan dari bagaimana program akan dijalankan, dan UML dari program ini. Untuk deskripsi kode lebih lengkap dapat dilihat pada kode program yang sudah dicommit sebelumnya (sudah disertakan beberapa comment untuk membantu dalam mengerti cara kerja program). Selamat menyimak :>

#Identitas Kontributor
	Nama : I Made Sandika Wijaya NIM : 2405551082 Matkul : PBO (E)
	Nama : I Gusti Bagus Eri Widura NIM : 2405551054 Matkul : PBO (E)

#UML

!(https://github.com/baguseri054/ToolTID/blob/main/timart%20(2).png?raw=true)

#Bagaimana Program Dijalankan

Program Investasi ini dijalankan dengan menerapkan konsep Object Oriented Programming yaitu:
	Penggunaan Class dan Oject
	Penggunaan Flow Control seperti While loop, for each loop, do while loop, if-else, dan switch case
	Array list
	Inheritance
	Enscapsulasi

#Penggunaan Program
Di bawah ini merupakan tata cara penggunaan program investasi beserta hasil screenshot pada command line.
#Login Admin dan Customer
g.tampilan awal
g.login admin
g.password
g.login Customer
g.password
g.pesan selamat datang

Setelah program dijalankan, user akan diminta untuk memasukan username dan password mereka. Jika valid, program akan menentukan apakah user termasuk Admin atau Customer. Jika benar maka program akan mengeluarkan pesan selamat datang seperti gambar di atas. Jika salah program akan meminta user untuk menginput ulang usename dan password yang valid.

#Menu Administrator
![menu admin]()
Setelah melakukan login sebagai administrator, maka user akan disambut dengan menu dan dapat memilih intruksi tertera. Admin akan diminta untuk memilih opsi di atas dengan menginputkan angka untuk melanjutkan program.

#Menu Customer
![menu customer]()
Setelah melakukan login sebagai customer, maka user akan disambut dengan menu Customer berisikan instruksi seperti gambar di atas. Customer akan diminta untuk memilih opsi di atas dengan menginputkan angka untuk melanjutkan program.

#Admin - Menu Saham
![Menu Saham]()
setelah Admin memasukkan opsi 1, maka Admin akan disambut dengan menu saham. Pada menu saham Admin dapat menambahkan saham, mengubah saham dan Kembali ke menu Administrator dengan memasukkan opsi terkait.


