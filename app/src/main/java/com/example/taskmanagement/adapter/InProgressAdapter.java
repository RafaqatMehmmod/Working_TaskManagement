package com.example.taskmanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.InprogressUserListBinding;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.interfacesPackage.WorkerDelete;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateHP;

import java.util.ArrayList;

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.ViewHolder> {

    private Context context;

    public static ArrayList<AddTaskModel> list;
    WorkerDelete workerDelete;




    public InProgressAdapter(Context context, ArrayList<AddTaskModel> list,WorkerDelete workerDelete) {
        this.context = context;
        this.list = list;
        this.workerDelete=workerDelete;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        InprogressUserListBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.inprogress_user_list,parent,false);
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
        private InprogressUserListBinding binding;
        public ViewHolder(@NonNull InprogressUserListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 workerDelete.deleteUser(list.get(getAdapterPosition()),view);
                }
            });
            binding.description.setOnTouchListener(new View.OnTouchListener()
            {

                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event)
                {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            binding.description.setMovementMethod(new ScrollingMovementMethod());

        }
    }
}
