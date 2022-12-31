package UAS_Pbo;

import java.sql.*;;

public class Harga implements Kendaraan { // KELAS HARGA MERUPAKAN ANAK DARI KELAS KENDARAAN

    public Integer tarifMobil = 3000;
    public Integer tarifMotor = 1500;
    public Integer tarifTambahMobil = 1000;
    public Integer tarifTambahMotor = 500;

    public void tarifMobil() {
        this.tarifMobil = tarifMobil;
    }

    public void tarifMotor() {
        this.tarifMotor = tarifMotor;
    }

    public void tarifTambahMobil() {
        this.tarifTambahMobil = tarifTambahMobil;
    }

    public void tarifTambahMotor() {
        this.tarifTambahMotor = tarifTambahMotor;
    }
}
