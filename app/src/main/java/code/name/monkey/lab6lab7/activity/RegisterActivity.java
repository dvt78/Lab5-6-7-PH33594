package code.name.monkey.lab6lab7.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import code.name.monkey.lab6lab7.R;
import code.name.monkey.lab6lab7.databinding.ActivityLoginBinding;
import code.name.monkey.lab6lab7.databinding.ActivityRegisterBinding;
import code.name.monkey.lab6lab7.model.User;
import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.viewmodel.LoginViewModel;
import code.name.monkey.lab6lab7.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.register.setOnClickListener(v -> {
            viewModel.register(new User(binding.name.getText().toString(), binding.email.getText().toString(), binding.username.getText().toString(), binding.password.getText().toString()));
        });

        viewModel.getRegisterResponseMutableLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse.getMessage().isStatus()) {
                    onBackPressed();
                }
                Toast.makeText(RegisterActivity.this, userResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}