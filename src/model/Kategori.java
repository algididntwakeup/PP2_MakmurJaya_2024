/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lio
 */
public class Kategori {
    private int id;
    private String namaKategori;
    
    public Kategori(int id, String namaKategori) {
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

    public void setNamaKategori(int id) {
        this.namaKategori = namaKategori;
    }
}
