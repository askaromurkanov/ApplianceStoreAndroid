package kg.askaromurkanov.appliancestoreandroid.ui.singInUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import kg.askaromurkanov.appliancestoreandroid.databinding.FragmentSingUpBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.productCard.ProductCardFragment;


public class SingUpFragment extends Fragment {

    private FragmentSingUpBinding binding;
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

        binding = FragmentSingUpBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        init();
        clickListener();

        return root;
    }

    void init(){
//        appDatabase = App.getAppDatabase(getContext());
        appDatabase = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userService = new UserService(appDatabase);
    }

    private void clickListener() {

        binding.singUpBtn.setOnClickListener(view -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String phonenumber = binding.phonenumber.getText().toString();

            int phone = Integer.parseInt(phonenumber);
            if (email.isEmpty() || phonenumber.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(binding.getRoot().getContext(), "Вы зарегистрированы", Toast.LENGTH_LONG).show();
                User newUser = new User(email, phone, password, "client");


                userService.registerUser(newUser);

                int id = userService.getUserId(email, password);
                UserService.setId(id);
                Log.d("idUser", String.valueOf(UserService.getId()));

                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);

                clean();

                Bundle bundle = new Bundle();
                bundle.putInt("userId",userService.getUserId(email, password));
                Fragment productCardFragment = new ProductCardFragment();
                productCardFragment.setArguments(bundle);
            }

        });


        binding.singIn.setOnClickListener(view -> {
            SingInFragment singInFragment = new SingInFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(this.getId(), singInFragment)
                    .commit();
            clean();
        });
    }

    private void clean(){
        binding.email.setText(null);
        binding.phonenumber.setText(null);
        binding.password.setText(null);
    }
}