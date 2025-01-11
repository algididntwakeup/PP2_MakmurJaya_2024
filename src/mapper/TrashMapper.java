package mapper;

import model.Trash;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TrashMapper {
    @Select("SELECT s.id, s.nama_sampah as namaSampah, s.kategori_id as kategoriId, k.nama_kategori as namaKategori " +
           "FROM sampah s " +
           "JOIN kategori k ON s.kategori_id = k.id")
    List<Trash> selectAllTrash();
    
    @Select("SELECT s.id, s.nama_sampah as namaSampah, s.kategori_id as kategoriId, k.nama_kategori as namaKategori " +
           "FROM sampah s " +
           "JOIN kategori k ON s.kategori_id = k.id " +
           "WHERE s.kategori_id = #{kategoriId}")
    List<Trash> selectTrashByCategory(@Param("kategoriId") int kategoriId);
}