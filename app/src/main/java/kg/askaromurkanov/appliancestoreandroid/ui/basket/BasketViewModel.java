package kg.askaromurkanov.appliancestoreandroid.ui.basket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.Basket;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;

public class BasketViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Basket>> baskets;
    private MutableLiveData<ArrayList<Product>> products;
    private AppDatabase appDatabase;
    private BasketDao basketDao;
    private ProductDao productDao;

    public BasketViewModel() {
        baskets = new MutableLiveData<>();
        products = new MutableLiveData<>();
    }


    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }

    private void init(){
        basketDao = appDatabase.basketDao();
        productDao = appDatabase.productDao();
    }

    public LiveData<ArrayList<Product>> getProductsInBasket(){
        init();
        ArrayList<Product> products1 = new ArrayList<>();
        List<Basket> baskets = basketDao.getBasket(UserService.getId());

        for (Basket basket : baskets){
            products1.add(productDao.getById(basket.getProductId()));
        }

        products.setValue(products1);

        return products;
    }
}