package kg.askaromurkanov.appliancestoreandroid.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Basket;
import kg.askaromurkanov.appliancestoreandroid.data.models.Order;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.models.User;

@Database(entities = {Product.class, User.class, Basket.class, Order.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract UserDao userDao();
    public abstract BasketDao basketDao();
    public abstract OrderDao orderDao();
}