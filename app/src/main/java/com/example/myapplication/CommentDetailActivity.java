package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDetailActivity extends AppCompatActivity {

    public String idToGet;
    public CommentsAdapter adapter;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        recyclerView = findViewById(R.id.recycle_view);
        Intent intent = getIntent();
        idToGet = intent.getStringExtra("id");
        String a = idToGet;
        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<RetroComments>> call = service.getComments(idToGet);
        call.enqueue(new Callback<List<RetroComments>>() {
            @Override
            public void onResponse(Call<List<RetroComments>> call, Response<List<RetroComments>> response) {
                adapter = new CommentsAdapter(CommentDetailActivity.this,  response.body());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommentDetailActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RetroComments>> call, Throwable t) {
                Toast.makeText(CommentDetailActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
