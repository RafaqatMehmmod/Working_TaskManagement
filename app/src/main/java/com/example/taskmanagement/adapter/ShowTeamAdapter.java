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
import com.example.taskmanagement.databinding.ShowTeamListBinding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.interfacesPackage.TeamInterface;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.CreateTeam;

import java.util.ArrayList;

public class ShowTeamAdapter extends RecyclerView.Adapter<ShowTeamAdapter.ViewHolder> {

    private Context context;
    //    public static ArrayList<RegisterUserModel> list;
    public static ArrayList<CreateTeam> list;
    private TeamInterface teamInterface;
    private CardClickInterface card;


    public ShowTeamAdapter(Context context, ArrayList<CreateTeam> list, TeamInterface teamInterface, CardClickInterface card) {
        this.context = context;
        this.list = list;
        this.teamInterface = teamInterface;
        this.card = card;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ShowTeamListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.show_team_list, parent, false);
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
        private ShowTeamListBinding binding;

        public ViewHolder(@NonNull ShowTeamListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    teamInterface.deleteTeam(list.get(getAdapterPosition()), view);
                }
            });


            binding.pdfCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card.click(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
