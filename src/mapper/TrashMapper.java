package mapper;



import model.Trash;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrashMapper {
    @Select("SELECT s.id, s.nama_sampah, s.kategori_id FROM sampah s")
    List<Trash> selectAllTrash();
    
    @Select("SELECT s.id, s.nama_sampah, s.kategori_id FROM sampah s WHERE s.kategori_id = #{kategoriId}")
    List<Trash> selectTrashByCategory(@Param("kategoriId") int kategoriId);
}

