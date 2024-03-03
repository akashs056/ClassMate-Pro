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

import com.example.student.Models.FileModel;
import com.example.student.R;
import com.example.student.databinding.SamplePdfsBinding;

import java.util.ArrayList;

public class FIleAdapter extends RecyclerView.Adapter<FIleAdapter.viewHolder> {
    static ArrayList<FileModel> list;
    Context context;

    public FIleAdapter(ArrayList<FileModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_pdfs,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FileModel model=list.get(position);
        holder.binding.name.setText(model.getFilename());
        holder.binding.title.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        SamplePdfsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SamplePdfsBinding.bind(itemView);

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
            FileModel fileModel = list.get(position);
            String filePath = fileModel.getPath();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");

            try {
                itemView.getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(itemView.getContext(), "No app available to open the file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
