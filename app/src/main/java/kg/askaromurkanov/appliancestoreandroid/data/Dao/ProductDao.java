package kg.askaromurkanov.appliancestoreandroid.data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.models.Product;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    List<Product> getAll();

    @Query("SELECT * FROM Product WHERE id = :id")
    Product getById(long id);

    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM Product")
    public void cleanTable();

    @Query("SELECT * FROM Product ORDER BY sales DESC LIMIT 4")
    List<Product> getHitProducts();

    @Query("SELECT * FROM Product WHERE discount > 0 ORDER BY discount DESC LIMIT 5")
    List<Product> getProductsWithDiscount();
}
