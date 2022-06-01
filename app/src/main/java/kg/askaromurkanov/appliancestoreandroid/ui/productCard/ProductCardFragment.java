package kg.askaromurkanov.appliancestoreandroid.ui.productCard;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.Basket;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.models.User;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentHomeBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentProductCardBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentSingInBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.basket.BasketFragment;
import kg.askaromurkanov.appliancestoreandroid.ui.singInUp.SingUpFragment;


public class ProductCardFragment extends Fragment {

    private FragmentProductCardBinding binding;
    private AppDatabase appDatabase;
    private ProductDao productDao;
    private UserDao userDao;
    private BasketDao basketDao;

    private Product product;
    private User user;

    public static ProductCardFragment newInstance(String param1, String param2) {
        ProductCardFragment fragment = new ProductCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductCardBinding.inflate(getLayoutInflater());

        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productDao = appDatabase.productDao();
        userDao = appDatabase.userDao();
        basketDao = appDatabase.basketDao();


        int userId = UserService.getId();
        user = userDao.getById(userId);

        Log.d("checkUserId", String.valueOf(userId));

        int productId = getArguments().getInt("productId");
        product = productDao.getById(productId);

        binding.productImage.setImageResource(product.getImage());

        String rating = String.valueOf(product.getRating());
        binding.productRating.setText(rating);

        String fullName = product.getName()+" "+product.getFactory()+" "+product.getModel();
        binding.productName.setText(fullName);

        double priceWithDiscount = product.getPrice()-(product.getPrice()*product.getDiscount())/100;
        String price = "$"+priceWithDiscount;
        binding.productPrice.setText(price);

        if(product.getDiscount()>0){
            binding.discountCard.setVisibility(View.VISIBLE);
            String discount = product.getDiscount()+"%";
            binding.productDiscount.setText(discount);
        }

        binding.productDescription.setText(product.getDescription());

        onClick();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void onClick(){
        binding.addToBasketBtn.setOnClickListener(view -> {
            int productId = product.getId();
            int userId = user.getId();


            Basket newBasketItem = new Basket(userId, productId);
            basketDao.insert(newBasketItem);

            binding.addToBasketBtn.setText("Товар в корзине");
            binding.addToBasketBtn.setBackgroundColor(R.color.unchecked);


            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment myFragment = new BasketFragment();

            Toast.makeText(binding.getRoot().getContext(), "Переход в корзину", Toast.LENGTH_SHORT).show();

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, myFragment)
                    .commit();


        });
    }
}