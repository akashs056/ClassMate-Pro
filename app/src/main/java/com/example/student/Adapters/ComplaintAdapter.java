package com.example.student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Activities.ComplaintReadActivity;
import com.example.student.Models.ComplaintModel;
import com.example.student.R;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.viewHolder> {

    ArrayList<ComplaintModel> list;
    Context context;
    String key;

    public ComplaintAdapter(ArrayList<ComplaintModel> list, Context context, String key) {
        this.list = list;
        this.context = context;
        this.key = key;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_complaint,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ComplaintModel model=list.get(position);
        holder.binding.title.setText(model.getSubject());
        holder.binding.description.setText(model.getDescription());
        holder.binding.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ComplaintReadActivity.class);
                intent.putExtra("title",model.getSubject());
                intent.putExtra("description",model.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class viewHolder extends RecyclerView.ViewHolder{
        com.example.student.databinding.SampleComplaintBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding= com.example.student.databinding.SampleComplaintBinding.bind(itemView);
        }
    }

}
