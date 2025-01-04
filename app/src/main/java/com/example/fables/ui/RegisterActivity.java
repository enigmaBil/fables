package com.example.fables.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fables.MainActivity;
import com.example.fables.R;
import com.example.fables.data.api.ApiClient;
import com.example.fables.data.api.FableApiService;
import com.example.fables.data.request.RegisterRequest;
import com.example.fables.data.response.LoginResponse;
import com.example.fables.databinding.ActivityRegisterBinding;
import com.example.fables.ui.spalsh.SplashScreenActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EditText etEmail, etPassword, etConfirmPassword, etName, etPhone;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        etName = binding.etName;
        etEmail = binding.etEmail;
        etPhone = binding.etPhone;
        etPassword = binding.etPassword;
        etConfirmPassword = binding.etConfirmPassword;
        btnRegister = binding.btnRegister;
        tvGoToLogin = binding.tvGoToLogin;

        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phoneNumber = etPhone.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String role = "USER";

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }

            // Appeler l'API pour l'inscription
            RegisterRequest request = new RegisterRequest(name, email, phoneNumber, password, role);
            FableApiService apiService = ApiClient.getRetrofitInstance().create(FableApiService.class);
            apiService.register(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(RegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Log.i(request.getName(), "onResponse: ");
                        Toast.makeText(RegisterActivity.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                    Toast.makeText(RegisterActivity.this, "Erreur réseau : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}