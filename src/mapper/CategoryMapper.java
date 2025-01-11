package mapper;

import model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM kategori")
    List<Category> selectAllCategories();
}
