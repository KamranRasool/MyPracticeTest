package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.CustomViewHolder>{
    public List<RetroPosts> posts;
    private Context context;
    public OnItemClickListener clickListener;
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
    public MainActivityAdapter(Context context, List<RetroPosts> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_activity_adapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.userId.setText("User ID:  " + posts.get(position).getUserId());
        holder.Id.setText("ID:  " +posts.get(position).getId());
        holder.txtTitle.setText("Title:  " +posts.get(position).getTitle());
        holder.descriptionBody.setText("Body:  " +posts.get(position).getbody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        TextView userId, Id, txtTitle, descriptionBody;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            userId = mView.findViewById(R.id.user_id);
            Id = mView.findViewById(R.id.id);
            txtTitle = mView.findViewById(R.id.title);
            descriptionBody = mView.findViewById(R.id.body);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }

    }
}
