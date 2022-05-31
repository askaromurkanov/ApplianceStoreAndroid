package kg.askaromurkanov.appliancestoreandroid.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}