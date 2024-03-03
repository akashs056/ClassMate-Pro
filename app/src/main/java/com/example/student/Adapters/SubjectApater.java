package com.example.student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Activities.SubjectActivity;
import com.example.student.Models.SubjectModel;
import com.example.student.R;
import com.example.student.databinding.SampleSubjectsBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class SubjectApater extends RecyclerView.Adapter<SubjectApater.viewHolder> {

    ArrayList<SubjectModel> list;
    Context context;
    String key;

    public SubjectApater(ArrayList<SubjectModel> list, Context context, String key) {
        this.list = list;
        this.context = context;
        this.key = key;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_subjects,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SubjectModel model=list.get(position);
        holder.binding.subjectName.setText(model.getSubjectName());
        holder.binding.facultyName.setText(model.getFacultyName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SubjectActivity.class);
                intent.putExtra("subjectName",model.getSubjectName());
                intent.putExtra("key",key);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class  viewHolder extends RecyclerView.ViewHolder{
        SampleSubjectsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleSubjectsBinding.bind(itemView);
        }
    }
}
