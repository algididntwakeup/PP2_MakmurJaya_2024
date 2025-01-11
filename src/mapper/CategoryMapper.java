package mapper;

import model.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT id, nama_kategori as namaKategori FROM kategori")
    List<Category> selectAllCategories();
}