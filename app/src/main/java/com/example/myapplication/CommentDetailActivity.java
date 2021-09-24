package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDetailActivity extends AppCompatActivity {

    public String idToGet;
    public CommentsAdapter adapter;
    public RecyclerView recyclerView;
    public ArrayList<RetroComments> arrayList;
    public EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        editTextSearch = findViewById(R.id.edit_search);
        recyclerView = findViewById(R.id.recycle_view);
        Intent intent = getIntent();
        idToGet = intent.getStringExtra("id");
        String a = idToGet;
        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<RetroComments>> call = service.getComments(idToGet);
        call.enqueue(new Callback<List<RetroComments>>() {
            @Override
            public void onResponse(Call<List<RetroComments>> call, Response<List<RetroComments>> response) {
                arrayList = getArrayList("commentsList");
                if(arrayList != null){
                    adapter = new CommentsAdapter(CommentDetailActivity.this,  response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommentDetailActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter = new CommentsAdapter(CommentDetailActivity.this,  response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommentDetailActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    saveArrayList((ArrayList<RetroComments>) response.body(), "commentsList");
                    arrayList = getArrayList("commentsList");
                }
            }

            @Override
            public void onFailure(Call<List<RetroComments>> call, Throwable t) {
                Toast.makeText(CommentDetailActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

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
        ArrayList<RetroComments> filtercomments = new ArrayList<>();

        for (RetroComments retrocomments : arrayList) {
            if (retrocomments.getName().toLowerCase().contains(text.toLowerCase())) {
                filtercomments.add(retrocomments);
            }
        }
       adapter.filterList(filtercomments);
    }
    public void saveArrayList(ArrayList<RetroComments> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CommentDetailActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<RetroComments> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CommentDetailActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<RetroComments>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
