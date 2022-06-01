package kg.askaromurkanov.appliancestoreandroid.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentBasketBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentOrdersBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.basket.BasketAdapter;
import kg.askaromurkanov.appliancestoreandroid.ui.basket.BasketViewModel;


public class OrdersFragment extends Fragment {

    private RecyclerView recyclerViewBasket;
    private OrdersAdapter ordersAdapter;

    private AppDatabase appDatabase;
    private ProductDao productDao;
    private OrderDao orderDao;
    private UserDao userDao;
    private OrdersViewModel ordersViewModel;
    private List<Product> products;
    private FragmentOrdersBinding binding;

    public OrdersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();

        return root;
    }

    void init(){

        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = appDatabase.productDao();
        orderDao = appDatabase.orderDao();


        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        ordersViewModel.setAppDatabase(appDatabase);
        ordersAdapter = new OrdersAdapter();

        ordersViewModel.getOrderedProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> pd) {
                ordersAdapter.setList(pd);
            }
        });



        recyclerViewBasket = binding.recyclerOrderProducts;

        recyclerViewBasket.setAdapter(ordersAdapter);
    }
}