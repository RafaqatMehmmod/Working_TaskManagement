package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.InprogressUserListBinding;
import com.example.taskmanagement.databinding.WorkplaceUserListBinding;
import com.example.taskmanagement.interfacesPackage.WorkerDelete;
import com.example.taskmanagement.model.AddTaskModel;

import java.util.ArrayList;

public class WorkPlaceAdapter extends RecyclerView.Adapter<WorkPlaceAdapter.ViewHolder> {

    private Context context;

    public static ArrayList<AddTaskModel> list;





    public WorkPlaceAdapter(Context context, ArrayList<AddTaskModel> list) {
        this.context = context;
        this.list = list;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        WorkplaceUserListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.workplace_user_list,parent,false);
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
        private WorkplaceUserListBinding binding;
        public ViewHolder(@NonNull WorkplaceUserListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;




        }
    }
}
