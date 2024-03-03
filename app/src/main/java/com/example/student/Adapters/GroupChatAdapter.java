package com.example.student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student.Models.GroupChatModel;
import com.example.student.R;
import com.example.student.databinding.SampleGroupChatBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.viewHolder> {

    ArrayList<GroupChatModel> list;
    Context context;

    public GroupChatAdapter(ArrayList<GroupChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_group_chat,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        GroupChatModel model=list.get(position);
        String time= TimeAgo.using(model.getSentAt());
        holder.binding.sentAt.setText(time);
        holder.binding.message.setText(model.getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        SampleGroupChatBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleGroupChatBinding.bind(itemView);
        }
    }
}
