package model;

public class Trash {
    private int id;
    private String namaSampah;
    private int kategoriId;
    private String namaKategori;  // Tambahan field untuk nama kategori

    // Getter dan Setter yang sudah ada
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaSampah() {
        return namaSampah;
    }

    public void setNamaSampah(String namaSampah) {
        this.namaSampah = namaSampah;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    // Tambahan getter dan setter untuk namaKategori
    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}