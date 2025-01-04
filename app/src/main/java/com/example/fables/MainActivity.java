package com.example.fables;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fables.adapter.RecyclerAdapter;
import com.example.fables.data.api.ApiClient;
import com.example.fables.data.api.FableApiService;
import com.example.fables.data.model.Fable;
import com.example.fables.data.model.RecyclerFable;
import com.example.fables.databinding.ActivityMainBinding;
import com.example.fables.ui.AddFableActivity;
import com.example.fables.ui.LoginActivity;
import com.example.fables.ui.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    List<RecyclerFable> recyclerFables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //set the recycler view
        recyclerView = binding.rcView;

        // FloatingActionButton (FAB)
        FloatingActionButton fab = binding.fab;

        if (!isUserLoggedIn()) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddFableActivity.class);
                startActivity(intent);
            });

        }

        //fetch data

        FableApiService fableApiService = ApiClient.getRetrofitInstance().create(FableApiService.class);
        fableApiService.getFables().enqueue(new Callback<List<Fable>>() {
            @Override
            public void onResponse(Call<List<Fable>> call, Response<List<Fable>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new RecyclerAdapter(MainActivity.this, response.body());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }else {
                    Log.e("MainActivity", "Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Fable>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed", throwable);
            }
        });

//        // Call the adapter
//        adapter = new RecyclerAdapter(this, recyclerFables);

        //set the adapter on the recycler view
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Vérifier l'état de la connexion à chaque affichage du menu
        checkLoginStatus(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Gérer les clics sur les éléments du menu
        if (item.getItemId() == R.id.menu_item_1) {
            Toast.makeText(this, "Aide cliquée", Toast.LENGTH_SHORT).show();
            return true;
        }else if(item.getItemId() == R.id.menu_login){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            return true;
        }else if(item.getItemId() == R.id.menu_register){
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            return true;
        }else if(item.getItemId() == R.id.menu_logout){
            // Déconnexion
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent logoutIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
            return true;
        }else if (item.getItemId() == R.id.app_bar_search) {
            Toast.makeText(this, "recherche cliquée", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Vérifier si l'utilisateur est connecté
    private boolean isUserLoggedIn() {
        // Par exemple, vérifier si le token d'accès existe
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
        return token != null;
    }

    private void checkLoginStatus(Menu menu) {
        if (menu == null) {
            return; // Pour éviter les erreurs si le menu est nul
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt_token", null);
        System.out.println("--------------------------: "+ token);
        if (token != null) {
            // L'utilisateur est connecté, afficher la déconnexion et masquer connexion et inscription
            MenuItem loginItem = menu.findItem(R.id.menu_login);
            MenuItem registerItem = menu.findItem(R.id.menu_register);
            MenuItem logoutItem = menu.findItem(R.id.menu_logout);

            if (loginItem != null) loginItem.setVisible(false);
            if (registerItem != null) registerItem.setVisible(false);
            if (logoutItem != null) logoutItem.setVisible(true);
        } else {
            // L'utilisateur n'est pas connecté, afficher connexion et inscription, et masquer déconnexion
            MenuItem loginItem = menu.findItem(R.id.menu_login);
            MenuItem registerItem = menu.findItem(R.id.menu_register);
            MenuItem logoutItem = menu.findItem(R.id.menu_logout);

            if (loginItem != null) loginItem.setVisible(true);
            if (registerItem != null) registerItem.setVisible(true);
            if (logoutItem != null) logoutItem.setVisible(false);
        }
    }

}