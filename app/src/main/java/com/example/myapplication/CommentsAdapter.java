package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter  extends RecyclerView.Adapter<CommentsAdapter.CustomViewHolder>{
    public List<RetroComments> comments;
    private Context context;

    public CommentsAdapter(Context context, List<RetroComments> comments){
        this.context = context;
        this.comments = comments;
    }
    public void filterList(ArrayList<RetroComments> filterdComments) {
        this.comments = filterdComments;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comments_list_adapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.postId.setText("User ID:  " + comments.get(position).getPostId());
        holder.id.setText("ID:  " +comments.get(position).getId());
        holder.name.setText("Name:  " +comments.get(position).getName());
        holder.email.setText("Email:  " +comments.get(position).getEmail());
        holder.body.setText("Body:  " +comments.get(position).getbody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView postId, id, name, email, body;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            postId = mView.findViewById(R.id.post_id);
            id = mView.findViewById(R.id.id);
            name = mView.findViewById(R.id.name);
            email = mView.findViewById(R.id.email);
            body = mView.findViewById(R.id.body);
        }

    }
}

