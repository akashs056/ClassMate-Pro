package com.example.student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.student.Activities.FacultyDetails;
import com.example.student.Activities.Feedback;
import com.example.student.Models.FacultyModel;
import com.example.student.R;
import com.example.student.databinding.SampleFacultiesBinding;

import java.util.ArrayList;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.viewHolder> {

    ArrayList<FacultyModel> list;
    Context context;

    public FacultyAdapter(ArrayList<FacultyModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_faculties,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FacultyModel model=list.get(position);
        Glide.with(context).load(model.getProfileImage()).placeholder(R.drawable.user).into(holder.binding.profileImage);
        holder.binding.facultyName.setText(model.getFullName());

        holder.binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Feedback.class);
                intent.putExtra("name",model.getFullName());
                intent.putExtra("image",model.getProfileImage());
                intent.putExtra("facultyUid",model.getFacultyUid());
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FacultyDetails.class);
                intent.putExtra("college",model.getCollegeName());
                intent.putExtra("name",model.getFullName());
                intent.putExtra("email",model.getEmail());
                intent.putExtra("phone",model.getPhone());
                intent.putExtra("age",model.getAge());
                intent.putExtra("gender",model.getGender());
                intent.putExtra("image",model.getProfileImage());
                intent.putExtra("facultyUid",model.getFacultyUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class viewHolder extends RecyclerView.ViewHolder{
        SampleFacultiesBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleFacultiesBinding.bind(itemView);
        }
    }
}
