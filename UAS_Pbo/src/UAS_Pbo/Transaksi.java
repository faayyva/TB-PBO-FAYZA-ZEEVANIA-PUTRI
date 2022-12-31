package UAS_Pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaksi extends Harga { // anak dari kelas harga
    // deklarasi variabel
    public Integer jamDatang;
    public Integer jamKeluar;
    public Integer jam_berikutnya;
    public Integer totalBayar;
    public Integer noKarcis;
    public String noPlat;
    public Integer lama;
    public String jenisKendaraan;

    // deklarasi scanner
    Scanner input = new Scanner(System.in);

    // deklarasi url databases mysql
    String url = "jdbc:mysql://localhost:3306/pr_parkiran";
    Connection conn;

    // method input data parkiran
    public void TransaksiParkir() throws SQLException {
        System.out.print("Masukkan no plat kendaraan = ");
        noPlat = input.nextLine();
        System.out.print("Masukkan no Karcis = ");
        noKarcis = input.nextInt();
        System.out.print("Masukkan Jenis Kendaraan = ");
        jenisKendaraan = input.next();

        switch (jenisKendaraan) {
            // case 1 untuk tarif mobil
            case "MOBIL":
                System.out.print("Masukkan jam Datang = ");
                jamDatang = input.nextInt();
                System.out.print("Masukkan Jam Keluar = ");
                jamKeluar = input.nextInt();

                // percabangan untuk penentuan biaya parkir
                if (jamKeluar >= jamDatang)
                    lama = jamKeluar - jamDatang;
                else
                    lama = (12 - jamDatang) + jamKeluar;

                if (lama > 2) {
                    jam_berikutnya = (lama - 2) * tarifTambahMobil;
                } else {
                    jam_berikutnya = 0;

                }
                // total bayar parkir mobil
                totalBayar = tarifMobil + jam_berikutnya;

                // penampilan tarif didemo program
                System.out.println("\n--------------------------------------");
                System.out.println(" 2 Jam Pertama = " + tarifMobil);
                System.out.println("Tarif Jam Berikutnya = " + jam_berikutnya);
                System.out.println("Total Bayar = " + totalBayar);
                break;

            // case 2 tarif motor
            case "MOTOR":
                System.out.print("Masukkan Jam datang = ");
                jamDatang = input.nextInt();
                System.out.print("Masukkan jam keluar = ");
                jamKeluar = input.nextInt();

                // percabangan untuk total parkir
                if (jamKeluar >= jamDatang)
                    lama = jamKeluar - jamDatang;
                else
                    lama = (12 - jamDatang) + jamKeluar;

                if (lama > 2) {
                    jam_berikutnya = ((lama - 2) * tarifTambahMotor);
                } else {
                    jam_berikutnya = 0;
                }
                totalBayar = tarifMotor + jam_berikutnya; // biaya total parkir

                // penampilan tarif didemo program
                System.out.println("\n--------------------------------------");
                System.out.println("2 jam pertama " + tarifMotor);
                System.out.println("jam berikutnya = " + jam_berikutnya);
                System.out.println("Total bayar =" + totalBayar);
        }

        // sql untuk menambahkan data ke databases
        String sql = "INSERT INTO data_parkir (noPlat, noKarcis, jenisKendaraan,totalBayar ) VALUES ('" + noPlat + "','"
                + noKarcis + "','" + jenisKendaraan + "','" + totalBayar + "')";
        conn = DriverManager.getConnection(url, "root", "");
        Statement statement = conn.createStatement();
        statement.execute(sql);
        System.out.println("berhasil input data");
    }

    // method untuk melihat dat parkiran
    public void LihatParkir() throws SQLException {
        System.out.println("Data Parkiran\n");

        // sql untuk memanggil dan melihat data yang ada pada databases
        String sql = "SELECT * FROM data_parkir";
        conn = DriverManager.getConnection(url, "root", "");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            System.out.print("\nNo Plat\t         : ");
            System.out.print(result.getString("noPlat"));
            System.out.print("\nNo Karcis\t  : ");
            System.out.print(result.getInt("noKarcis"));
            System.out.print("\nJenis Kendaraan\t  : ");
            System.out.print(result.getString("jenisKendaraan"));
            System.out.print("\nTotal Bayar\t  : ");
            System.out.print(result.getInt("totalBayar"));
            System.out.println("\n--------------------------------------");
        }
    }

    // method untuk menghapus data parkiran
    public void HapusParkir() throws SQLException {

        String text2 = "\n***HAPUS DATA PARKIRAN***";
        System.out.println(text2.toUpperCase());
        Scanner inputan = new Scanner(System.in);

        try {
            LihatParkir();
            System.out.print("Inputkan no Karcis yang akan dihapus : ");
            noKarcis = Integer.parseInt(inputan.nextLine());

            // sql untuk menghapus data yang ada didatabases
            String sql = "DELETE FROM data_parkir WHERE noKarcis = " + noKarcis;
            Statement statement = conn.createStatement();

            if (statement.executeUpdate(sql) > 0) {
                System.out.println("Pengapusan Data Parkir BERHASIL " + noKarcis + ")");

            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan pada menghapusan data");
            System.err.println(e.getMessage());
        }
    }

    // method untuk mengubah data
    public void UbahParkir() throws SQLException {
        System.out.print("***Ubah Data Parkiran***\n");

        try {
            LihatParkir();
            System.out.print("\nMasukkan NoKarcis yang akan diubah : ");
            Integer noKarcis = Integer.parseInt(input.nextLine());

            String sql = "SELECT * FROM data_parkir WHERE noKarcis = " + noKarcis;
            conn = DriverManager.getConnection(url, "root", "");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                System.out.print("No Plat [" + result.getString("noPlat") + "]\t: ");
                String noPlat = input.nextLine();

                // sql untuk mengubah data yang ada pada databases
                sql = "UPDATE data_parkir SET noPlat='" + noPlat + "'WHERE NoKarcis= '" + noKarcis + "'";
                System.out.println(sql);

                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil memperbarui data (noKarcis " + noKarcis + ")");
                }
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam pengubahan data");
            System.err.println(e.getMessage());
        }
    }

    // method untuk mencari data parkiran
    public void CariParkir() throws SQLException {

        String text3 = "\n***CARI DATA PARKIRAN***";
        System.out.println((text3.toUpperCase()));
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan No Karcis : ");
        Integer keyword = Integer.parseInt(input.nextLine());

        // sql untuk mencari data pada databases
        String sql = "SELECT * FROM data_parkir WHERE noKarcis LIKE '%" + keyword + "%'";
        conn = DriverManager.getConnection(url, "root", "");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            System.out.println("--------------------------------------");
            System.out.print("\nNo Plat\t           : ");
            System.out.print(result.getString("noPlat"));
            System.out.print("\nNo Karcis\t       : ");
            System.out.print(result.getInt("noKarcis"));
            System.out.print("\nJenis Kendaraan\t       : ");
            System.out.print(result.getString("jenisKendaraan"));
            System.out.print("\ntotal Bayar\t       : ");
            System.out.print(result.getInt("totalBayar"));
            System.out.println("\n--------------------------------------");

        }

    }
}
