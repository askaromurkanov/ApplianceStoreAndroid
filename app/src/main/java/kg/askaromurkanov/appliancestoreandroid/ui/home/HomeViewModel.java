package kg.askaromurkanov.appliancestoreandroid.ui.home;

import android.icu.lang.UProperty;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;

public class HomeViewModel extends ViewModel {
    private AppDatabase appDatabase;
    private MutableLiveData<List<Product>> products;
    private List<Product> productList;

    private ProductDao productDao;


    public HomeViewModel() {
        products = new MutableLiveData<>();
    }


}