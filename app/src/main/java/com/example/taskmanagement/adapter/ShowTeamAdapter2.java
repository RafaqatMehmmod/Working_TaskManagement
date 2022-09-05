package com.example.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.ShowTeamList2Binding;
import com.example.taskmanagement.databinding.ShowTeamListBinding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.interfacesPackage.TeamInterface;
import com.example.taskmanagement.model.CreateTeam;

import java.util.ArrayList;

public class ShowTeamAdapter2 extends RecyclerView.Adapter<ShowTeamAdapter2.ViewHolder> {

    private Context context;
    public static ArrayList<CreateTeam> list;
    private CardClickInterface card;


    public ShowTeamAdapter2(Context context, ArrayList<CreateTeam> list, CardClickInterface card) {
        this.context = context;
        this.list = list;
        this.card = card;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ShowTeamList2Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.show_team_list2, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CreateTeam model = list.get(position);
        holder.binding.setItem(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShowTeamList2Binding binding;

        public ViewHolder(@NonNull ShowTeamList2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;



            binding.pdfCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card.click(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
