package com.example.fables.ui;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fables.R;
import com.example.fables.databinding.ActivityFableDetailsBinding;

public class FableDetailsActivity extends AppCompatActivity {

    private ActivityFableDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFableDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Récupérer les données passées via l'intent
        String title = getIntent().getStringExtra("fable_title");
        String content = getIntent().getStringExtra("fable_content");

        // Afficher les données
        binding.fableTitle.setText(title);
        binding.fableContent.setText(content);
    }
}