package kg.askaromurkanov.appliancestoreandroid.ui.orderMaking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.Basket;
import kg.askaromurkanov.appliancestoreandroid.data.models.Order;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentOrderMakingBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentProductCardBinding;


public class OrderMakingFragment extends Fragment {

    private FragmentOrderMakingBinding binding;
    private AppDatabase appDatabase;
    private ProductDao productDao;
    private BasketDao basketDao;
    private OrderDao orderDao;


    public OrderMakingFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderMakingBinding.inflate(getLayoutInflater());

        init();
        onClick();
//        return inflater.inflate(R.layout.fragment_order_making, container, false);

        return binding.getRoot();
    }

    void init(){
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productDao = appDatabase.productDao();
        basketDao = appDatabase.basketDao();
        orderDao = appDatabase.orderDao();
    }

    private void onClick(){
        binding.makeOrderBtn.setOnClickListener(view -> {
            String name = binding.name.getText().toString();
            String address = binding.address.getText().toString();
            String extraInfo = binding.extraInfo.getText().toString();

            List<Basket> basket = basketDao.getBasket(UserService.getId());

            for (Basket i : basket){
                orderDao.insert(new Order(name, address, extraInfo, i.getProductId(), i.getUserId(), 1));
            }

            basketDao.cleanTable();
            Toast.makeText(binding.getRoot().getContext(), "Заказ принят! Спасибо", Toast.LENGTH_SHORT).show();

            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(this);
            trans.commit();
            manager.popBackStack();
        });
    }

}