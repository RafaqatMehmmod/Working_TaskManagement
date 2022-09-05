package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.ADMIN;
import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.fragment.WorkPlace.workPlace_email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.login.LoginActivity;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.example.taskmanagement.model.RegisterCompany;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    Firebase_Auth_SDP obj;

    String designation, email, loginEmail;
    GoogleSignInAccount acct;
    String k,s_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        obj = Firebase_Auth_SDP.getInstance();
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);

        try {
            k = sharedPreferences.getString("k", "null");
            s_email = sharedPreferences.getString("kk", "null");
            if (k.equals("Owner")) {

                Intent intent = new Intent(Splash.this, ProjectManagerDashboard.class);
                startActivity(intent);
                finish();
                Log.i("mehmood", "onCreate++++++++++: " + k);
            } else if (k.equals("HR")) {

                Intent intent = new Intent(Splash.this, HRDashboard.class);
                intent.putExtra("email",s_email);
                startActivity(intent);
                finish();
                Log.i("mehmood", "onCreate++++++++++: " + k);
            } else if (k.equals("Project Manager")) {

                Intent intent = new Intent(Splash.this, PMDashboard.class);
                intent.putExtra("email",s_email);
                startActivity(intent);
                finish();
                Log.i("mehmood", "onCreate++++++++++: " + k);
            } else if (k.equals("NormalUser")) {

                Intent intent = new Intent(Splash.this, NormalUserActivity.class);
                intent.putExtra("email",s_email);
                startActivity(intent);
                finish();
                Log.i("mehmood", "onCreate++++++++++: " + k);
            } else if (k.equals("null")) {
                Intent intent = new Intent(Splash.this, MainDashboard.class);
                startActivity(intent);
                finish();
                Log.i("mehmood", "onCreate..........: ");
            }
        } catch (Exception e) {
        }



//        try {
//            loginEmail = obj.getAuth().getCurrentUser().getEmail();
//
//        } catch (Exception e) {
//
//        }
//        if (loginEmail == null) {
//            Intent intent = new Intent(Splash.this, MainDashboard.class);
//            startActivity(intent);
//            finish();
//            Log.i("rafaqat", "Splash IF..: "+loginEmail);
//
//        } else {
//            acct = GoogleSignIn.getLastSignedInAccount(Splash.this);
//            obj.getFirebaseDatabase().getReference().child(COMPANIES).child(ADMIN).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists())
//                    {
//                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
//                        {
//                            RegisterCompany model=dataSnapshot.getValue(RegisterCompany.class);
//                            if (acct.getId().equals(model.getLoginID()))
//                            {
//                                startActivity(new Intent(Splash.this,ProjectManagerDashboard.class));
//                                finish();
//                            }
//                            else
//                            {
//                                Log.i("rafaqat", "Gmail And Model Email Was Not Match: ");
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        ............................
//            obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if (snapshot.exists()) {
//
//                        Log.i("mehmood", "Exist: ");
//                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
//
//                            com.example.taskmanagement.model.CreateHP user = dataSnapshot2.getValue(com.example.taskmanagement.model.CreateHP.class);
//                            {
//                                designation = user.getDesignation();
//                                email = user.getEmail();
//
//                                Log.i("rafaqat", "onDataChange....Email: "+email);
//                                Log.i("rafaqat", "onDataChange....Des: "+designation);
//                                Log.i("rafaqat", "onDataChange....Login: "+loginEmail);
//                                if (loginEmail.equals(email) && designation.equals("HR")) {
//                                    Log.i("mehmood", "HR: " + designation);
//                                    Intent intent = new Intent(Splash.this, HRDashboard.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (loginEmail.equals(email) && designation.equals("Project Manager") ) {
//                                    Intent intent = new Intent(Splash.this, PMDashboard.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                                else
//                                {
//                                    Toast.makeText(Splash.this, "mmmmmmmmmmmmm", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        }
//
//                        if (acct != null) {
//                            Intent intent = new Intent(Splash.this, ProjectManagerDashboard.class);
//                            startActivity(intent);
//                            finish();
//                        } else if (acct == null) {
//
//                            if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser() != null) {
//                                if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser().getEmail().equals(email)) {
//                                    Log.i("mehmood", "run: " + designation);
//                                    if (loginEmail.equals(email) && designation.equals("HR")) {
//                                        Intent intent = new Intent(Splash.this, HRDashboard.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else if (loginEmail.equals(email) && designation.equals("Project Manager")) {
//                                        Intent intent = new Intent(Splash.this, PMDashboard.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }
//                            } else {
//                                Intent intent = new Intent(Splash.this, MainDashboard.class);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                        }
//                    } else {
//                        Log.i("mehmood", "Not: ");
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if (acct != null) {
//                                    Intent intent = new Intent(Splash.this, ProjectManagerDashboard.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (acct == null) {
//                                    if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser() != null) {
//                                        if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser().getEmail().equals(email)) {
//                                            Log.i("mehmood", "run: " + designation);
//                                        }
//                                    } else {
//                                        Intent intent = new Intent(Splash.this, MainDashboard.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                } else {
//                                    Intent intent = new Intent(Splash.this, MainDashboard.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//
//                            }
//                        }, 300);
//                    }
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                    Log.i("mehmood", "onCancelled: " + error.getMessage());
//                }
//            });
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}


