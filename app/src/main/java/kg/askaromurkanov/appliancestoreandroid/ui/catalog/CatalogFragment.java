package kg.askaromurkanov.appliancestoreandroid.ui.catalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kg.askaromurkanov.appliancestoreandroid.MainActivity;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Order;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentCatalogBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.home.DiscountAdapter;
import kg.askaromurkanov.appliancestoreandroid.ui.home.HitAdapter;


public class CatalogFragment extends Fragment {

    private RecyclerView recyclerView;

    private CatalogAdapter catalogAdapter;



    private AppDatabase appDatabase;
    private ProductDao productDao;

    private FragmentCatalogBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CatalogViewModel catalogViewModel =
                new ViewModelProvider(this).get(CatalogViewModel.class);

        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return root;
    }
    private void filter(String text){
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product product : productDao.getAll()){
            if(product.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(product);
            }
            else if(product.getModel().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(product);
            }
            else if(product.getFactory().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(product);
            }
            else if(product.getFactory().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(product);
            }
        }
        catalogAdapter.filterdList(filteredList);
    }

    void init(){
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = appDatabase.productDao();
        OrderDao orderDao = appDatabase.orderDao();

        recyclerView = binding.recyclerAllProducts;


        catalogAdapter = new CatalogAdapter();
        catalogAdapter.setList(productDao.getAll());

        recyclerView.setAdapter(catalogAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}