package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.RegisterUserList2Binding;
import com.example.taskmanagement.databinding.RegisterUserListBinding;
import com.example.taskmanagement.interfacesPackage.AddInterface;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.model.CreateHP;

import java.util.ArrayList;

public class RegisterUserAdapter2 extends RecyclerView.Adapter<RegisterUserAdapter2.ViewHolder> {

    private Context context;
//    public static ArrayList<RegisterUserModel> list;
    public static ArrayList<CreateHP> list;
    private AddInterface addInterface;



    public RegisterUserAdapter2(Context context, ArrayList<CreateHP> list, AddInterface addInterface) {
        this.context = context;
        this.list = list;
        this.addInterface=addInterface;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        RegisterUserList2Binding binding= DataBindingUtil.inflate(layoutInflater, R.layout.register_user_list2,parent,false);
        return new ViewHolder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CreateHP model=list.get(position);
        holder.binding.setItem(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RegisterUserList2Binding binding;
        public ViewHolder(@NonNull RegisterUserList2Binding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addInterface.addUser(list.get(getAdapterPosition()));
                }
            });


        }
    }
}
