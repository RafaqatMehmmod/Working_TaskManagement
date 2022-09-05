package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.COMPANY_NAME;
import static com.example.taskmanagement.Constant.SUPPORTIVE_HAND;

import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.example.taskmanagement.databinding.ActivityDashboardBinding;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {


    private ActivityDashboardBinding binding;
    private String uID, companyName, designation,normalUser;
    private Firebase_Auth_SDP obj;
    String storeDesignation;
    String email,storeNormalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        obj = Firebase_Auth_SDP.getInstance();
        email = getIntent().getStringExtra("email");
//        Log.i("rafaqat", "email..: " + email);



//        User user = new User("", "", "", designation, "",companyName);
//        binding.setItem(user);


//        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
////
////                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
//                            User user = dataSnapshot2.getValue(User.class);
//                            {
//
//                                if (user.getuID().equals(uID)) {
//                                    companyName = user.getCompanyName();
//                                    designation = user.getDesignation();
//                                    binding.companyName.setText("Welcome " + companyName);
//                                    binding.designation.setText("Welcome " + designation);
//                                }
//                            }
////                        }
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Log.i("mehmood", "onCancelled: " + error.getMessage());
//            }
//        });


        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

//                    User model_class = dataSnapshot.getValue(User.class);
                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);
                    companyName = model_class.getCompanyName();
                    designation = model_class.getDesignation();
                    normalUser=model_class.getNormalUser();

                    Log.i("rafaqat", "onDataChange....: "+model_class.getEmail());


                    if (email.equals(model_class.getEmail())) {
                        binding.companyName.setText(companyName);
                        binding.designation.setText("("+designation+")");
                        storeDesignation=designation;
                        storeNormalUser=normalUser;


                    }


                    //  }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.join.setOnClickListener(view -> {
            ProgressDialog dialog = new ProgressDialog(Dashboard.this);
            dialog.setTitle("Wait....");
            dialog.setMessage("Detail Match in Database");
            dialog.show();

            obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                        com.example.taskmanagement.model.CreateHP user = dataSnapshot2.getValue(com.example.taskmanagement.model.CreateHP.class);
                        {


                            if (email.equals(user.getEmail()) && storeDesignation.equals(user.getDesignation())) {

                                if (storeDesignation.equals("HR")) {
                                    Intent intent=new Intent(Dashboard.this, HRDashboard.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    finish();
                                    Log.i("mehmood", "onDataChange.......: " + user.getEmail());
                                    Log.i("mehmood", "onDataChange.......: " + user.getDesignation());
                                    dialog.dismiss();
                                }
                                else if (storeDesignation.equals("Project Manager"))
                                {
                                    Intent intent=new Intent(Dashboard.this, PMDashboard.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                                else if (storeNormalUser.equals("NormalUser"))
                                {
                                    Intent intent=new Intent(Dashboard.this, NormalUserActivity.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                            }

                            }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.i("mehmood", "onCancelled: " + error.getMessage());
                }
            });

        });

    }
}