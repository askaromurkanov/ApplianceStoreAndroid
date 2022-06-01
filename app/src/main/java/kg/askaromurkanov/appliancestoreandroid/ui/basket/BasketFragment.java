package kg.askaromurkanov.appliancestoreandroid.ui.basket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentBasketBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentHomeBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.home.DiscountAdapter;
import kg.askaromurkanov.appliancestoreandroid.ui.home.HitAdapter;
import kg.askaromurkanov.appliancestoreandroid.ui.orderMaking.OrderMakingFragment;
import kg.askaromurkanov.appliancestoreandroid.ui.productCard.ProductCardFragment;


public class BasketFragment extends Fragment {

    private RecyclerView recyclerViewBasket;
    private BasketAdapter basketAdapter;

    private AppDatabase appDatabase;
    private ProductDao productDao;
    private BasketDao basketDao;
    private UserDao userDao;

    private BasketViewModel basketViewModel;

    private List<Product> products;


    private FragmentBasketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BasketViewModel basketViewModel =
                new ViewModelProvider(this).get(BasketViewModel.class);


        binding = FragmentBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        onClick();


        return root;
    }

    void init(){
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = appDatabase.productDao();
        basketDao = appDatabase.basketDao();
//        basketDao.cleanTable();


//        List<Integer> productsIds = basketDao.getProducts(UserService.getId());
//        int id = productDao.getById(1).getId();
//        Log.d("Listff", String.valueOf(id));

        basketViewModel = new ViewModelProvider(this).get(BasketViewModel.class);
        basketViewModel.setAppDatabase(appDatabase);
        basketAdapter = new BasketAdapter();


        basketViewModel.getProductsInBasket().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> pd) {
                basketAdapter.setList(pd);
                double price = 0;
                for (Product product : pd){
                    price+=product.getPrice();
                }

                String number = String.valueOf(pd.size());
                String strPrice = String.valueOf(price);
                binding.number.setText(number);
                binding.productPriceSum.setText(strPrice);
            }
        });



        recyclerViewBasket = binding.recyclerBasketProducts;

        recyclerViewBasket.setAdapter(basketAdapter);

    }

    private void onClick(){
        binding.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new OrderMakingFragment();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, myFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}