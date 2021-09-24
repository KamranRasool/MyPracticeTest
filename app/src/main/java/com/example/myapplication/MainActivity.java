package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.OnItemClickListener {
    public RecyclerView recyclerView;
    public MainActivityAdapter adapter;
    public ArrayList<RetroPosts> arrayList;
    public EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        editTextSearch = findViewById(R.id.edit_search);
        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<RetroPosts>> call = service.getAllPosts();
        call.enqueue(new Callback<List<RetroPosts>>() {
            @Override
            public void onResponse(Call<List<RetroPosts>> call, Response<List<RetroPosts>> response) {
                arrayList = getArrayList("list");
                if(arrayList != null){
                    adapter = new MainActivityAdapter(MainActivity.this, arrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.setClickListener(MainActivity.this);
                }else {
                    adapter = new MainActivityAdapter(MainActivity.this, response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.setClickListener(MainActivity.this);
                    saveArrayList((ArrayList<RetroPosts>) response.body(), "list");
                    arrayList = getArrayList("list");
                }
            }

            @Override
            public void onFailure(Call<List<RetroPosts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<RetroPosts> filterdPost = new ArrayList<>();

        for (RetroPosts retroposts : arrayList) {
            if (retroposts.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filterdPost.add(retroposts);
            }
        }
        adapter.filterList(filterdPost);
    }


    @Override
    public void onClick(View view, int position) {
        Intent i=new Intent(MainActivity.this,CommentDetailActivity.class);
        i.putExtra("id", adapter.posts.get(position).getId().toString());
        startActivity(i);
    }
    public void saveArrayList(ArrayList<RetroPosts> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<RetroPosts> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<RetroPosts>>() {}.getType();
        return gson.fromJson(json, type);
    }
}