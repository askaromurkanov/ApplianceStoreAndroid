package kg.askaromurkanov.appliancestoreandroid.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.Order;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;

public class OrdersViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Order>> orders;
    private MutableLiveData<ArrayList<Product>> products;
    private ArrayList<Order> OrderArrayList;
    private AppDatabase appDatabase;
    private OrderDao orderDao;
    private ProductDao productDao;

    public OrdersViewModel() {
        orders = new MutableLiveData<>();
        products = new MutableLiveData<>();
    }


    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }

    private void init(){
        orderDao = appDatabase.orderDao();
        productDao = appDatabase.productDao();
    }

    public LiveData<ArrayList<Product>> getOrderedProducts(){
        init();
        ArrayList<Product> products1 = new ArrayList<>();
        List<Order> orders = orderDao.getByUser(UserService.getId());

        for (Order order : orders){
            products1.add(productDao.getById(order.getProductId()));
        }

        products.setValue(products1);

        return products;
    }
}