package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.RegisterEmployListBinding;
import com.example.taskmanagement.databinding.RegisterUserListBinding;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.interfacesPackage.UserDelete2;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.RegisterEmployModel;

import java.util.ArrayList;

public class RegisterEmployAdapter extends RecyclerView.Adapter<RegisterEmployAdapter.ViewHolder> {

    private Context context;
//    public static ArrayList<RegisterUserModel> list;
    public static ArrayList<RegisterEmployModel> list;
    private UserDelete2 userDelete;



    public RegisterEmployAdapter(Context context, ArrayList<RegisterEmployModel> list, UserDelete2 userDelete) {
        this.context = context;
        this.list = list;
        this.userDelete=userDelete;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        RegisterEmployListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.register_employ_list,parent,false);
        return new ViewHolder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RegisterEmployModel model=list.get(position);
        holder.binding.setItem(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RegisterEmployListBinding binding;
        public ViewHolder(@NonNull RegisterEmployListBinding binding) {
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
