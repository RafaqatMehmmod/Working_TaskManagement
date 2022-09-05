package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.ADMIN;
import static com.example.taskmanagement.Constant.COMPANIES;
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
import com.example.taskmanagement.activities.login.CreateHP;
import com.example.taskmanagement.adapter.RegisterUserAdapter;
import com.example.taskmanagement.databinding.ActivityProjectManagerDashboardBinding;
import com.example.taskmanagement.interfacesPackage.UserDelete;
import com.example.taskmanagement.model.RegisterCompany;
import com.example.taskmanagement.model.RegisterUserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectManagerDashboard extends AppCompatActivity implements UserDelete {

    ActivityProjectManagerDashboardBinding binding;
    Firebase_Auth_SDP obj;
    GoogleSignInAccount acct;
    String companyName;
    ArrayList<com.example.taskmanagement.model.CreateHP> list;
    RegisterUserAdapter adapter;
    String email, id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_manager_dashboard);

        obj = Firebase_Auth_SDP.getInstance();
        email = getIntent().getStringExtra("email");
        id = getIntent().getStringExtra("id");
        sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("k","Owner");
        editor.apply();
        editor.commit();

        acct = GoogleSignIn.getLastSignedInAccount(ProjectManagerDashboard.this);

        list = new ArrayList<>();
        adapter = new RegisterUserAdapter(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);


        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(ADMIN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RegisterCompany registerCompany = dataSnapshot.getValue(RegisterCompany.class);

                    if (registerCompany.getLoginID().equals(acct.getId())) {
                        companyName = registerCompany.getCompanyName();


                        if (companyName.equals(null)) {
                            binding.progressBar.setVisibility(View.VISIBLE);
                        } else {
                            binding.progressBar.setVisibility(View.INVISIBLE);
                            obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    list.clear();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

//                                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                        com.example.taskmanagement.model.CreateHP registerCompany = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);

                                        if (companyName.equals(registerCompany.getCompanyName())) {
                                            list.add(registerCompany);
//                                            Log.i("mehmood", "Adapter: " + registerCompany.getImageUrl());
//                                            Log.i("mehmood", "Adapter: " + registerCompany.getDesignation());

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
//                            binding.recycleView.setAdapter(adapter);  ....Not Working
//                            adapter.notifyDataSetChanged();
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        binding.recycleView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();


//        Log.i("rafaqat", "Current User In ProjectManagerDashboard: "+obj.getAuth().getCurrentUser().getEmail());

        binding.projectManager.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateHP.class);
            intent.putExtra("companyName", companyName);
            startActivity(intent);


        });

        binding.logout.setOnClickListener(view -> {
            showPopMenu(view);
        });
    }

    @Override
    public void deleteUser(com.example.taskmanagement.model.CreateHP model, View view, int pos) {
        showPopMenu(model, view, pos);

    }

    private void showPopMenu(com.example.taskmanagement.model.CreateHP model, View view, int pos) {
        String key = model.getKey();
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.delete_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.delete) {
                    AlertDialog dialog = new AlertDialog.Builder(ProjectManagerDashboard.this, R.style.AlertDialogStyle)
                            .setTitle("Delete")
                            .setCancelable(false)
                            .setMessage("Do you really want to delete this class?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {


                                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).child(key).
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

                return false;
            }
        });
        popupMenu.show();
    }

    private void showPopMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.logout);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.signout) {
                    AlertDialog dialog = new AlertDialog.Builder(ProjectManagerDashboard.this, R.style.AlertDialogStyle)
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