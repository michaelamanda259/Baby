package com.example.baby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> list;

    Adapter(Context context, List<String> list){
        this.layoutInflater = layoutInflater.from(context);
        this.list=list;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String date = list.get(position);
        holder.textTitle.setText(date);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textDate,textTime,textContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.cv_title);
            /*textDate = itemView.findViewById(R.id.cv_date);
            textTime = itemView.findViewById(R.id.cv_time);
            textContent = itemView.findViewById(R.id.cv_content);*/
        }
    }
}
