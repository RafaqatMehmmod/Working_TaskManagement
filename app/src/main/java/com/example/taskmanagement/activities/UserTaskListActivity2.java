package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.Constant.WORK;
import static com.example.taskmanagement.activities.NormalUserActivity.normal_email;
import static com.example.taskmanagement.activities.hrdashboard.HRDashboard.after_email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.adapter.ShowTeamAdapter2;
import com.example.taskmanagement.adapter.UserTaskAdapter;
import com.example.taskmanagement.adapter.UserTaskAdapter2;
import com.example.taskmanagement.databinding.ActivityUserTaskList2Binding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.interfacesPackage.TaskUpdate;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserTaskListActivity2 extends AppCompatActivity implements TaskUpdate {

    ActivityUserTaskList2Binding binding;
    Firebase_Auth_SDP obj;
    ArrayList<AddTaskModel> list;
    UserTaskAdapter2 adapter;
    String companyName, storeCompanyName, teamName, storeTeamName, designation;
    String currentDate, name;
    String tName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_user_task_list2);

        obj = Firebase_Auth_SDP.getInstance();

        after_email = getIntent().getStringExtra("email");
        tName = getIntent().getStringExtra("teamName");
        list = new ArrayList<>();
        adapter = new UserTaskAdapter2(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);



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

//                        Log.i("raf", "onDataChange: "+normal_email);
//                        Log.i("raf", "onDataChange: "+model_class.getEmail());
//                        Log.i("raf", "onDataChange: "+storeCompanyName);
//                        Log.i("raf", "onDataChange: "+name);

                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
                                    teamName = model.getTeamName();
                                    storeTeamName = teamName;

//                                    Log.i("raf", "onDataChange: "+normal_email);
//                                    Log.i("raf", "onDataChange: "+model.getEmail());
//                                    Log.i("raf", "onDataChange: "+storeTeamName);

                                    if (storeTeamName.equals(tName)) {
                                        fetchData();
                                        Log.i("raf", "Me: ");
                                    }


                                }


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


    @Override
    public void UpdateTask(AddTaskModel model) {

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



                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}