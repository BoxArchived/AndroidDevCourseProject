package dev.boxz.kingofkaraoke;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> users;
    private User currentUser;

    public RankAdapter(ArrayList<User> users, User currentUser){
        this.users=users;
        this.currentUser=currentUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
        RecyclerView.ViewHolder viewHolder=new RecyclerView.ViewHolder(view){};
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView score=holder.itemView.findViewById(R.id.scoreRank);
        TextView username=holder.itemView.findViewById(R.id.usernameRank);
        TextView email=holder.itemView.findViewById(R.id.emailRank);
        score.setText(users.get(position).getScore()+"");
        username.setText(users.get(position).getUsername());
        email.setText(users.get(position).getEmail());
        if (users.get(position).getEmail().equals(currentUser.getEmail())){
            score.setTextSize(20);
            username.setTextSize(20);
            email.setTextSize(20);
        }
        Log.d("BOX",users.get(position).getUsername());
        Log.d("BOX",users.size()+"");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}