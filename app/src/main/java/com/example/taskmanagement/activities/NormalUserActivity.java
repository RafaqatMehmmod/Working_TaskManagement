package com.example.taskmanagement.activities;

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
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.example.taskmanagement.adapter.ShowTeamAdapter;
import com.example.taskmanagement.adapter.ShowTeamAdapter2;
import com.example.taskmanagement.databinding.ActivityNormalUserBinding;
import com.example.taskmanagement.interfacesPackage.CardClickInterface;
import com.example.taskmanagement.interfacesPackage.TeamInterface;
import com.example.taskmanagement.model.CreateTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NormalUserActivity extends AppCompatActivity implements CardClickInterface {

    ActivityNormalUserBinding binding;
    public static String storeName, normal_email;
    Firebase_Auth_SDP obj;
    ArrayList<CreateTeam> list;
    ShowTeamAdapter2 adapter;
    String companyName, designation, name, email, email2, storeDesignation, storeCompanyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_normal_user);
        obj = Firebase_Auth_SDP.getInstance();
        normal_email = getIntent().getStringExtra("email");
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("k", "NormalUser");
        editor.putString("kk", normal_email);
        editor.apply();
        editor.commit();

        Log.i("raf", "onCreate: " + normal_email);

        list = new ArrayList<>();
        adapter = new ShowTeamAdapter2(this, list, this);
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

                    if (normal_email.equals(model_class.getEmail())) {
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
                        designation = model_class.getDesignation();
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

//        binding.privateTeam.setOnClickListener(view -> {
//            Intent intent = new Intent(NormalUserActivity.this, UserTaskListActivity.class);
//            intent.putExtra("email", normal_email);
//            startActivity(intent);
//        });

        binding.logout.setOnClickListener(view -> {
            showPopMenu(view);
        });
    }

    private void showPopMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.logout);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.signout) {
                    AlertDialog dialog = new AlertDialog.Builder(NormalUserActivity.this, R.style.AlertDialogStyle)
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
    @Override
    public void click(CreateTeam model) {
        Intent intent = new Intent(NormalUserActivity.this, UserTaskListActivity.class);
        intent.putExtra("email", normal_email);
        intent.putExtra("teamName", model.getTeamName());
        startActivity(intent);

    }
}