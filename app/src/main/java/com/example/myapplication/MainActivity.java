package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.OnItemClickListener {
    public RecyclerView recyclerView;
    public MainActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<RetroPosts>> call = service.getAllPosts();
        call.enqueue(new Callback<List<RetroPosts>>() {
            @Override
            public void onResponse(Call<List<RetroPosts>> call, Response<List<RetroPosts>> response) {
                adapter = new MainActivityAdapter(MainActivity.this,response.body());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setClickListener(MainActivity.this);
            }

            @Override
            public void onFailure(Call<List<RetroPosts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        Intent i=new Intent(MainActivity.this,CommentDetailActivity.class);
        i.putExtra("id", adapter.posts.get(position).getId().toString());
        startActivity(i);
    }
}