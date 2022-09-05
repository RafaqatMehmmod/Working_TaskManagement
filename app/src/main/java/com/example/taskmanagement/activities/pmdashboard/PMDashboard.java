package com.example.taskmanagement.activities.pmdashboard;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.ListOfMember;
import com.example.taskmanagement.activities.ProjectManagerDashboard;
import com.example.taskmanagement.activities.TaskShowActivity;
import com.example.taskmanagement.activities.TeamActivity;
import com.example.taskmanagement.adapter.RegisterUserAdapter;
import com.example.taskmanagement.adapter.ShowTeamAdapter;
import com.example.taskmanagement.databinding.ActivityPmdashboardBinding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.interfacesPackage.TeamInterface;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.CreateTeam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PMDashboard extends AppCompatActivity implements TeamInterface, CardClickInterface {

    ActivityPmdashboardBinding binding;

    String companyName, designation, name, email, email2, storeDesignation, storeName, storeCompanyName;
    Firebase_Auth_SDP obj;
    ArrayList<CreateTeam> list;
    ShowTeamAdapter adapter;
    public static String pm_after_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pmdashboard);


        obj = Firebase_Auth_SDP.getInstance();

        pm_after_email=getIntent().getStringExtra("email");
        SharedPreferences sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("k","Project Manager");
        editor.putString("kk",pm_after_email);
        editor.apply();
        editor.commit();

//       email2=getIntent().getStringExtra("email");

        list = new ArrayList<>();
        adapter = new ShowTeamAdapter(this, list, this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);
//                    companyName = model_class.getCompanyName();
//                    name=model_class.getName();
//                    email=model_class.getEmail();

                    if (pm_after_email.equals(model_class.getEmail())) {
//                        designation = model_class.getDesignation();
//                        name = model_class.getName();
////                        email = model_class.getEmail();
//                        storeDesignation=designation;
//                        storeName=name;
                        companyName = model_class.getCompanyName();
                        name = model_class.getName();
                        Log.i("mehmood", "PMDashboard: " + companyName);
                        Log.i("mehmood", "PMDashboard: " + name);
                        storeCompanyName = companyName;
                        designation=model_class.getDesignation();
                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                list.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
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
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.projectManager.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
            intent.putExtra("companyName", companyName);
//           intent.putExtra("designation",storeDesignation);
            intent.putExtra("name", name);
            intent.putExtra("email", pm_after_email);
            intent.putExtra("designation",designation);
            startActivity(intent);


        });


        binding.logout.setOnClickListener(view -> {
            showPopMenu(view);
        });


    }

    @Override
    public void deleteTeam(CreateTeam model, View view) {
        showPopMenu(model,view);

    }
    private void showPopMenu(CreateTeam model, View view) {
        String key = model.getTeamName();
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.team_item);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.delete) {
                    AlertDialog dialog = new AlertDialog.Builder(PMDashboard.this, R.style.AlertDialogStyle)
                            .setTitle("Delete")
                            .setCancelable(false)
                            .setMessage("Do you really want to delete this class?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {

                                        Log.i("mehmood", "onClick: " + key);
                                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(key).
                                                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                        adapter.notifyDataSetChanged();



                                                    }
                                                });
                                    } catch (Exception e) {

                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create();
                    dialog.show();
                }
                else if (id==R.id.addMember)
                {
                     Intent intent=new Intent(PMDashboard.this, ListOfMember.class);
                     intent.putExtra("companyName",storeCompanyName);
                     intent.putExtra("email",pm_after_email);
                    intent.putExtra("teamName",model.getTeamName());
                    startActivity(intent);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void click(CreateTeam model) {
        Intent intent=new Intent(PMDashboard.this, TaskShowActivity.class);
        intent.putExtra("email",pm_after_email);
        intent.putExtra("teamName",model.getTeamName());
        startActivity(intent);

    }

    private void showPopMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.logout);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.signout) {
                    AlertDialog dialog = new AlertDialog.Builder(PMDashboard.this, R.style.AlertDialogStyle)
                            .setTitle("Delete")
                            .setCancelable(false)
                            .setMessage("Do you really want to delete this class?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    SharedPreferences sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("k","null");
                                    editor.putString("kk","null");
                                    editor.apply();
                                    editor.commit();
                                    finishAffinity();

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create();
                    dialog.show();
                }
                return false;
            }
        });
        popupMenu.show();
    }
}