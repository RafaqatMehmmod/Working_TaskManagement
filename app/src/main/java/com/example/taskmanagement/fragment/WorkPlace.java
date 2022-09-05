package com.example.taskmanagement.fragment;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.Constant.WORK;
import static com.example.taskmanagement.activities.TaskShowActivity.s_teamName;
import static com.example.taskmanagement.activities.hrdashboard.HRDashboard.after_email;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.NormalUserActivity;
import com.example.taskmanagement.activities.UserTaskListActivity;
import com.example.taskmanagement.activities.UserTaskListActivity2;
import com.example.taskmanagement.adapter.InProgressAdapter;
import com.example.taskmanagement.adapter.ShowTeamAdapter2;
import com.example.taskmanagement.adapter.WorkPlaceAdapter;
import com.example.taskmanagement.databinding.FragmentInProgressBinding;
import com.example.taskmanagement.databinding.FragmentWorkPlaceBinding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateTeam;
import com.example.taskmanagement.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class WorkPlace extends Fragment implements CardClickInterface {

    Firebase_Auth_SDP obj;
    String companyName, teamName, storeTeamName;
    ArrayList<AddTaskModel> list;
    WorkPlaceAdapter adapter;
    FragmentWorkPlaceBinding binding;
    String currentDay, currentMonth, currentYear;
    int i_day, i_month, i_year;
    String currentDate, name;
    public static String storeCompanyName;
    public static String designation;
    public static String workPlace_email;

    ArrayList<CreateTeam> list2;
    ShowTeamAdapter2 adapter2;

    public WorkPlace() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        obj = Firebase_Auth_SDP.getInstance();
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);

                    if (after_email.equals(model_class.getEmail())) {
                        companyName = model_class.getCompanyName();
                        storeCompanyName = companyName;
                        designation = model_class.getDesignation();
                        name = model_class.getName();
                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list2.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
                                    teamName = model.getTeamName();
                                    storeTeamName = teamName;
//                                    fetchData();
                                    list2.add(model);
                                    binding.recycleView.setAdapter(adapter2);


                                }
                                adapter2.notifyDataSetChanged();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void fetchData() {
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(storeTeamName).child(WORK).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AddTaskModel model = dataSnapshot.getValue(AddTaskModel.class);
                    list.add(model);
                    binding.recycleView.setAdapter(adapter);

                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_place, container, false);
        View view = binding.getRoot();

        list2 = new ArrayList<>();
        adapter2 = new ShowTeamAdapter2(getContext(), list2,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);


        return view;
    }

    @Override
    public void click(CreateTeam model) {
        Intent intent = new Intent(getContext(), UserTaskListActivity2.class);
        intent.putExtra("email", after_email);
        intent.putExtra("teamName", model.getTeamName());
        startActivity(intent);

    }


}