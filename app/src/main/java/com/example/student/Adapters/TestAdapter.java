package com.example.student.Adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Models.TestModel;
import com.example.student.R;
import com.example.student.databinding.SampleTextsBinding;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.viewHolder> {

    static ArrayList<TestModel> list;
    Context context;

    public TestAdapter(ArrayList<TestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_texts,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TestModel model=list.get(position);
        holder.binding.title.setText(model.getTitle());
        holder.binding.subjectName.setText(model.getSubjectName());
        holder.binding.name.setText(model.getFilename());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class viewHolder extends RecyclerView.ViewHolder{
        SampleTextsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleTextsBinding.bind(itemView);

            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        openFile(position);
                    }
                }
            });

        }
        @SuppressLint("QueryPermissionsNeeded")
        public void openFile(int position) {
            TestModel testModel = list.get(position);
            String filePath = testModel.getPath();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");

            try {
                itemView.getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle case where no PDF viewer app is available
                Toast.makeText(itemView.getContext(), "No app available to open the file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
