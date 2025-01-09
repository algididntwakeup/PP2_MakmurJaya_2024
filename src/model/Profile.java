package model;

public class Profile {

    private int id;
    private int userId;
    private String foto;
    private String nama;
    private String jenisKelamin;
    private String alamat;
    private String tanggalLahir;

    public Profile(int id, int userId, String nama, String foto, String alamat, String tanggalLahir, String noRekening, String fotokk, String fotoktp) {
        this.id = id;
        this.userId = userId;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.foto = foto;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
   
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
     public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

   
}
