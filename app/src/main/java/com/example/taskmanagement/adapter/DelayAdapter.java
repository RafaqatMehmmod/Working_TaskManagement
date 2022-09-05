package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.DelayUserListBinding;
import com.example.taskmanagement.databinding.InprogressUserListBinding;
import com.example.taskmanagement.interfacesPackage.WorkerDelete;
import com.example.taskmanagement.model.AddTaskModel;

import java.util.ArrayList;

public class DelayAdapter extends RecyclerView.Adapter<DelayAdapter.ViewHolder> {

    private Context context;

    public static ArrayList<AddTaskModel> list;
    WorkerDelete workerDelete;




    public DelayAdapter(Context context, ArrayList<AddTaskModel> list, WorkerDelete workerDelete) {
        this.context = context;
        this.list = list;
        this.workerDelete=workerDelete;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        DelayUserListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.delay_user_list,parent,false);
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
        private DelayUserListBinding binding;
        public ViewHolder(@NonNull DelayUserListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 workerDelete.deleteUser(list.get(getAdapterPosition()),view);
                }
            });


        }
    }
}
