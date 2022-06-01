package kg.askaromurkanov.appliancestoreandroid.ui.singInUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kg.askaromurkanov.appliancestoreandroid.MainActivity;
import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.App;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.UserService.UserService;
import kg.askaromurkanov.appliancestoreandroid.data.models.User;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentSingInBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.productCard.ProductCardFragment;


public class SingInFragment extends Fragment {
    private FragmentSingInBinding binding;
    private AppDatabase appDatabase;
    private UserDao userDao;
    private UserService userService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static SingInFragment newInstance() {
        return new SingInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSingInBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        init();
        userDao = appDatabase.userDao();
//        Log.d("checkList", String.valueOf(userDao.getAll().get(0).getPassword()));
        clickListener();

        return root;
    }

    void init(){
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userService = new UserService(appDatabase);
    }



    private void clickListener(){
        binding.signInBtn.setOnClickListener(view -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
            else {
                if(userService.checkAuth(email, password)) {
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    clean();

//                    Bundle bundle = new Bundle();
//                    bundle.putInt("userId",userService.getUserId(email, password));
//                    Log.d("checkId", String.valueOf(userService.getUserId(email, password)));
//                    Fragment productCardFragment = new ProductCardFragment();
//                    productCardFragment.setArguments(bundle);

                    UserService.setId(userService.getUserId(email, password));
                    Log.d("idUser", String.valueOf(UserService.getId()));

                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.signUp.setOnClickListener(view -> {
            SingUpFragment singUpFragment = new SingUpFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(this.getId(), singUpFragment)
                    .commit();
            clean();
        });
    }

    private void clean(){
        binding.email.setText(null);
        binding.password.setText(null);
    }
}