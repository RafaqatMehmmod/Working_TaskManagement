package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.UserTaskForCompleteList2Binding;
import com.example.taskmanagement.databinding.UserTaskForCompleteListBinding;
import com.example.taskmanagement.interfacesPackage.TaskUpdate;
import com.example.taskmanagement.model.AddTaskModel;

import java.util.ArrayList;

public class UserTaskAdapter2 extends RecyclerView.Adapter<UserTaskAdapter2.ViewHolder> {

    private Context context;

    public static ArrayList<AddTaskModel> list;
    TaskUpdate taskUpdate;




    public UserTaskAdapter2(Context context, ArrayList<AddTaskModel> list, TaskUpdate taskUpdate) {
        this.context = context;
        this.list = list;
        this.taskUpdate=taskUpdate;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        UserTaskForCompleteList2Binding binding= DataBindingUtil.inflate(layoutInflater, R.layout.user_task_for_complete_list2,parent,false);
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
        private UserTaskForCompleteList2Binding binding;
        public ViewHolder(@NonNull UserTaskForCompleteList2Binding binding) {
            super(binding.getRoot());
            this.binding=binding;




        }
    }
}
