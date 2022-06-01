package kg.askaromurkanov.appliancestoreandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import kg.askaromurkanov.appliancestoreandroid.databinding.ActivitySingInUpBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.singInUp.SingInFragment;

public class SingInUpActivity extends AppCompatActivity {
    private ActivitySingInUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingInUpBinding.inflate(getLayoutInflater());

        setContentView(R.layout.activity_sing_in_up);

        SingInFragment startFragment = new SingInFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.singInUp, startFragment, "sing in fragment")
                .addToBackStack(null)
                .commit();


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


    }

}