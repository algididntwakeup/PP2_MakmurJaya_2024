package model;

public class Category {
    private int id;
    private String namaKategori;
    
    public Category(int id, String namaKategori) {
        this.id = id;
        this.namaKategori = namaKategori;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNamaKategori() {
        return namaKategori;
    }

    // Perbaiki parameter tipe data
    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
