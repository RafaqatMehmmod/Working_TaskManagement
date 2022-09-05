package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.UserTaskForCompleteListBinding;
import com.example.taskmanagement.interfacesPackage.TaskUpdate;
import com.example.taskmanagement.model.AddTaskModel;

import java.util.ArrayList;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.ViewHolder> {

    private Context context;

    public static ArrayList<AddTaskModel> list;
    TaskUpdate taskUpdate;




    public UserTaskAdapter(Context context, ArrayList<AddTaskModel> list, TaskUpdate taskUpdate) {
        this.context = context;
        this.list = list;
        this.taskUpdate=taskUpdate;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        UserTaskForCompleteListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.user_task_for_complete_list,parent,false);
        return new ViewHolder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AddTaskModel model=list.get(position);
        holder.binding.setItem(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private UserTaskForCompleteListBinding binding;
        public ViewHolder(@NonNull UserTaskForCompleteListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.completeTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskUpdate.UpdateTask(list.get(getAdapterPosition()));

                }
            });


        }
    }
}
