package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.ADMIN;
import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.MEMBER;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.example.taskmanagement.adapter.RegisterUserAdapter;
import com.example.taskmanagement.adapter.RegisterUserAdapter2;
import com.example.taskmanagement.databinding.ActivityListOfMemberBinding;
import com.example.taskmanagement.interfacesPackage.AddInterface;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.CreateTeam;
import com.example.taskmanagement.model.RegisterCompany;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListOfMember extends AppCompatActivity implements AddInterface {

    ActivityListOfMemberBinding binding;
    Firebase_Auth_SDP obj;
    String companyName,teamName,email;
    ArrayList<com.example.taskmanagement.model.CreateHP> list;
    RegisterUserAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_of_member);

        obj = Firebase_Auth_SDP.getInstance();

        companyName=getIntent().getStringExtra("companyName");
        email=getIntent().getStringExtra("email");
        teamName = getIntent().getStringExtra("teamName");
        list = new ArrayList<>();
        adapter = new RegisterUserAdapter2(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

//                                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    com.example.taskmanagement.model.CreateHP registerCompany = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);

                    if (companyName.equals(registerCompany.getCompanyName())) {
                        list.add(registerCompany);

                        binding.recycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                    // }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     CreateTeam createTeam=dataSnapshot.getValue(CreateTeam.class);

//                     if (email.equals(createTeam.getEmail()))
//                     {
//                         teamName=createTeam.getTeamName();
//
//                     }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void addUser(CreateHP model) {
        String m_email=model.getEmail();
        String cName=model.getCompanyName();
        String img=model.getImageUrl();
        String name=model.getName();
        Log.i("mehmood", "addUser: "+teamName);
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(TEAMS).child(teamName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                CreateHP model=new CreateHP("",name,img,"",m_email,"",cName,"","");
                obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(TEAMS).child(teamName).child(MEMBER).child(name).setValue(model);
//                obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(TEAMS).child(teamName).child("Member").child(cName).setValue(cName);
//                obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(TEAMS).child(teamName).child("Member").child(cName).setValue(img);
                  Intent intent=new Intent(ListOfMember.this, PMDashboard.class);
                  intent.putExtra("email",email);
                  startActivity(intent);
                  finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}