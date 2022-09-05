package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.RegisterUserListBinding;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.RegisterUserModel;

import java.util.ArrayList;

public class RegisterUserAdapter extends RecyclerView.Adapter<RegisterUserAdapter.ViewHolder> {

    private Context context;
//    public static ArrayList<RegisterUserModel> list;
    public static ArrayList<com.example.taskmanagement.model.CreateHP> list;
    private UserDelete userDelete;



    public RegisterUserAdapter(Context context, ArrayList<com.example.taskmanagement.model.CreateHP> list,UserDelete userDelete) {
        this.context = context;
        this.list = list;
        this.userDelete=userDelete;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        RegisterUserListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.register_user_list,parent,false);
        return new ViewHolder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        com.example.taskmanagement.model.CreateHP model=list.get(position);
        holder.binding.setItem(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RegisterUserListBinding binding;
        public ViewHolder(@NonNull RegisterUserListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDelete.deleteUser(list.get(getAdapterPosition()),view,getAdapterPosition());
                }
            });


        }
    }
}
