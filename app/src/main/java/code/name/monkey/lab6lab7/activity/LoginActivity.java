package code.name.monkey.lab6lab7.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import code.name.monkey.lab6lab7.MainActivity;
import code.name.monkey.lab6lab7.databinding.ActivityLoginBinding;
import code.name.monkey.lab6lab7.model.UserResponse;
import code.name.monkey.lab6lab7.model.UserClient;
import code.name.monkey.lab6lab7.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.password.setText("okokokokok");
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(binding.username.getText().toString(), binding.password.getText().toString());
            }
        });

        binding.register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginViewModel.getLoginResponseMutableLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse.getMessage().isStatus()) {
                    UserClient userClient = UserClient.getInstance();
                    userClient.set_id(userResponse.getData().getUser().getId());
                    userClient.setEmail(userResponse.getData().getUser().getEmail());
                    userClient.setName(userResponse.getData().getUser().getName());
                    userClient.setAccessToken(userResponse.getData().getAccessToken());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                Toast.makeText(LoginActivity.this, userResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}