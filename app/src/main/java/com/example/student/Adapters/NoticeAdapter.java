package com.example.student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Activities.OpenNoticeActivity;
import com.example.student.Models.NoticeModel;
import com.example.student.R;
import com.example.student.databinding.SampleNoticeBinding;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.viewHolder>{

    ArrayList<NoticeModel> list;
    Context context;

    public NoticeAdapter(ArrayList<NoticeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notice,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NoticeModel model=list.get(position);
        holder.binding.title.setText(model.getNoticeTitle());
        holder.binding.description.setText(model.getNoticeDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OpenNoticeActivity.class);
                intent.putExtra("title",model.getNoticeTitle());
                intent.putExtra("description",model.getNoticeDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class viewHolder extends RecyclerView.ViewHolder{
        SampleNoticeBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleNoticeBinding.bind(itemView);
        }
    }
}
