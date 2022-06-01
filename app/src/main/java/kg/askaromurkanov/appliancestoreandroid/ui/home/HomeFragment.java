package kg.askaromurkanov.appliancestoreandroid.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.App;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.OrderDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Order;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewDiscount;
    private RecyclerView recyclerViewHit;
    private RecyclerView recyclerViewNew;

    private DiscountAdapter discountAdapter;
    private HitAdapter hitAdapter;
    private NewAdapter newAdapter;



    private AppDatabase appDatabase;
    private ProductDao productDao;

    private FragmentHomeBinding binding;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
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

        discountAdapter.filterdList(filteredList);
        hitAdapter.filterdList(filteredList);
        newAdapter.filterdList(filteredList);
    }

    void init(){

       insertProducts();
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        productDao = appDatabase.productDao();
//        productDao.cleanTable();


        recyclerViewDiscount = binding.recyclerDiscountProducts;
        recyclerViewHit = binding.recyclerHitProducts;
        recyclerViewNew = binding.recyclerNewProducts;

        discountAdapter = new DiscountAdapter();
        discountAdapter.setList(productDao.getProductsWithDiscount());

        hitAdapter = new HitAdapter();
        hitAdapter.setList(productDao.getHitProducts());

        newAdapter = new NewAdapter();
        newAdapter.setList(productDao.getNewProducts());


        recyclerViewDiscount.setAdapter(discountAdapter);
        recyclerViewHit.setAdapter(hitAdapter);
        recyclerViewNew.setAdapter(newAdapter);

    }

    void insertProducts(){
        appDatabase = App.getAppDatabase(getContext());

        productDao = appDatabase.productDao();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String add_date = formatter.format(date);
        String add_date2 = "2022-05-30";

        Product product1 = new Product("Чайник","W-K18465G", "ARG", "Кухонная техника", 27,
                10, 5, R.drawable.tefal,
                "Чайник ARG W-K18465G произведен из экологического и термически стойкого стекла. " +
                        "Основание из нержавеющей стали и встроенная подсветка дополняют необычное решение для чайника, " +
                        "а подставка с центральным контактом способствует его вращению на 360°.", 4.7, add_date, 5);

        Product product2 = new Product("Стиральная машина","F2V5HS2S", "LG", "Для дома", 320,
                7, 5, R.drawable.washing_machine,
                "В память стиральной машины заложена база данных из 20 000 возможных сочетаний тканей. Анализ " +
                        "этих данных позволяет подбирать свой алгоритм стирки для каждого типа белья. Как результат - высокое " +
                        "качество стирки и на 18% меньше повреждений ткани.", 4.8, add_date2, 4);

        Product product3 = new Product("Пылесос","VT-1833PR", "VITEK", "Для домом", 400,
                5, 5, R.drawable.vacuum_cleaner,
                "Пылесос оснащён фильтром, эффективно улавливающим мельчайшую пыль, бактерии, аллергены. Семиступенчатая" +
                        " система фильтрации не позволяет пыли, собранной прибором, попадать в воздух.", 4.5, add_date, 2);

        Product product4 = new Product("СВЧ-печь", "MG-2071D","ARG", "Кухонная техника", 85,
                5, 5, R.drawable.microwave,
                "ARG MG-2071D не только впишется в современный интерьер вашей кухни, но и подчеркнет ваш собственный " +
                        "вкус и стиль в выборе бытовой техники. Управление в данной модели осуществляется при помощи сенсора. " +
                        "Для приготовления особо вкусных блюд производитель советует воспользоваться функцией гриля, мощность " +
                        "которого достигает 900 Вт.", 4.8, add_date, 7);

        Product product5 = new Product("Холодильник", "ХМ-4024-000","ATLANT", "Кухонная техника", 370,
                0, 5, R.drawable.refregerator,
                "Холодильник подойдет для любой кухни\n" +
                        "Невысокий, но довольно вместительный холодильник АТЛАНТ ХМ-4024-000 с продуманной организацией внутреннего" +
                        " пространства лучшим образом подойдет для использования на любой кухне. Отличную негромкую работу ему" +
                        " обеспечивает надежный компрессор.", 4.9, add_date, 5);

        Product product6 = new Product("Посудомоечная машина", "ZWM536WH","HANSA", "Кухонная техника", 247,
                0, 5, R.drawable.machine,
                "Модель предназначена для загрузки 6 комплектов посуды и расходует 6.5 л воды. Благодаря размерам машинки 43.8x55x50" +
                        " см вы сможете установить ее в качестве встраиваемой техники, что гарантирует экономию пространства. " +
                        "Устройство с поддержкой таймера отсрочки сигнала использует конденсационную бережную сушку.", 4.8, add_date, 2);

        productDao.insert(product1);
        productDao.insert(product2);
        productDao.insert(product3);
        productDao.insert(product4);
        productDao.insert(product5);
        productDao.insert(product6);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}