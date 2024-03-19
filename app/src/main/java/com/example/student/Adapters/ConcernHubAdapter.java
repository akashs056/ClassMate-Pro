package com.example.student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Activities.OpenNoticeActivity;
import com.example.student.Models.ComplaintModel;
import com.example.student.Models.NoticeModel;
import com.example.student.R;
import com.example.student.databinding.SampleNoticeBinding;

import java.util.ArrayList;

public class ConcernHubAdapter  extends RecyclerView.Adapter<ConcernHubAdapter.viewHolder>{
    ArrayList<ComplaintModel> list;
    Context context;

    public ConcernHubAdapter(ArrayList<ComplaintModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notice,parent,false);
        return new ConcernHubAdapter.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ComplaintModel model=list.get(position);
        holder.binding.title.setText(model.getSubject());
        holder.binding.description.setText(model.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OpenNoticeActivity.class);
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

    public static  class viewHolder extends RecyclerView.ViewHolder{
        SampleNoticeBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleNoticeBinding.bind(itemView);
        }
    }
}
