package UAS_Pbo;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;
import java.sql.*;
import java.util.HashMap;

public class App {

    static Connection conn;

    public static void main(String[] args) throws Exception {

        // program pemilihan

        Scanner inputan = new Scanner(System.in);
        String pilihanmenu;

        boolean isLanjutkan = true;

        String url = "jdbc:mysql://localhost:3306/pr_parkiran";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("Class Driver ditemukan!!");

            Transaksi transaksi = new Transaksi();

            while (isLanjutkan) {

                System.out.println("------------------------------------------------");
                System.out.println("        APLIKASI PENGINPUTAN DATA PARKIRAN      ");
                System.out.println("------------------------------------------------");
                String salam = "Selamat Datang , Selamat menggunakan Aplikasi ini";
                System.out.println(salam);

                // String dan Date
                Date tanggalhariini = new Date();
                SimpleDateFormat tanggal = new SimpleDateFormat("E,dd/MM/yyy");
                SimpleDateFormat waktu = new SimpleDateFormat("HH:mm:ss zzzz");
                System.out.println("Tanggal\t" + tanggal.format(tanggalhariini));
                System.out.println("Waktu\t" + waktu.format(tanggalhariini));

                // Hashmap untuk menyimpan nilai dari jenis kendaraan
                HashMap<Integer, String> jeniskendaraan = new HashMap<Integer, String>();
                jeniskendaraan.put(3000, "MOBIL");
                jeniskendaraan.put(1500, "MOTOR");

                System.out.println("\nTARIF PARKIR");
                for (Integer i : jeniskendaraan.keySet()) {
                    System.out.println("Rp." + i + " Perjam untuk " + jeniskendaraan.get(i));

                }
                System.out.println("\n|    Tarif diatas untuk 2 jam Pertama        | ");
                System.err.println("|    Penambahan Tarif Rp. 500 untuk motor    |");
                System.err.println("|    Penambahan Tarif Rp. 1000 untuk mobil   |");
                System.out.println(" ");

                // pilihan menu

                System.out.println("1. Tambah Data Parkir");
                System.out.println("2. Lihat Data Parkir");
                System.out.println("3. Hapus Data Parkir");
                System.out.println("4. Ubah Data Parkir");
                System.out.println("5. Cari Data Parkir");

                System.out.println("Silahkan Pilih (1/2/3/4/5): ");
                pilihanmenu = inputan.next();

                switch (pilihanmenu) {
                    case "1":
                        transaksi.TransaksiParkir();
                        ;
                        break;
                    case "2":
                        transaksi.LihatParkir();
                        ;
                        break;
                    case "3":
                        transaksi.HapusParkir();
                        ;
                        break;
                    case "4":
                        transaksi.UbahParkir();
                        ;
                        break;
                    case "5":
                        transaksi.CariParkir();
                        ;
                        break;
                    default:
                        System.err.println("Input tidak ditemukan! \nSilahkan Input pilihan [1-5]");

                }
                System.out.print("\nApakah Anda ingin melanjutkan [y/t]?");
                pilihanmenu = inputan.next();
                isLanjutkan = pilihanmenu.equalsIgnoreCase("y");

            }
            System.out.println("Program selesai");

        } catch (ClassNotFoundException ex) {
            System.err.println("Driver Error");
            System.exit(0);
        } catch (SQLException e) {
            System.err.println("Tidak Berhasil Terhubung");
        }
    }
}