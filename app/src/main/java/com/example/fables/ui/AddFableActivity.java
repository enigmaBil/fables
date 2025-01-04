package com.example.fables.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fables.R;
import com.example.fables.data.api.ApiClient;
import com.example.fables.data.api.FableApiService;
import com.example.fables.data.model.Fable;
import com.example.fables.databinding.ActivityAddFableBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFableActivity extends AppCompatActivity {

    private ActivityAddFableBinding binding;
    private EditText editTextTitle, editTextContent, editTextAuthor;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFableBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        editTextTitle = binding.editTextTitle;
        editTextContent = binding.editTextContent;
        editTextAuthor = binding.editTextAuthor;
        btnSubmit = binding.btnSubmit;

        btnSubmit.setOnClickListener(v -> submitFable());
    }
    private void submitFable() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Préparer l'objet Fable
        Fable newFable = new Fable(title, content, author);

        // Effectuer le call API pour ajouter la fable
        addFable(newFable);
    }
    private void addFable(Fable fable) {
        String token = getAccessToken(); // Récupérer le token depuis les SharedPreferences ou autre méthode

        FableApiService apiService = ApiClient.getRetrofitInstance().create(FableApiService.class);

        Call<ResponseBody> call = apiService.addFable("Bearer " + token, fable);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddFableActivity.this, "Fable ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    finish(); // Retour à l'activité précédente
                } else {
                    Toast.makeText(AddFableActivity.this, "Erreur lors de l'ajout de la fable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddFableActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Méthode pour récupérer le token d'accès
    private String getAccessToken() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return prefs.getString("access_token", null);
    }
}