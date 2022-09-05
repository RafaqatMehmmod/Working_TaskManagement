package com.example.taskmanagement.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.login.LoginActivity;

import com.example.taskmanagement.databinding.ActivityMainDashboardBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainDashboard extends AppCompatActivity {

    ActivityMainDashboardBinding binding;
    Firebase_Auth_SDP obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main_dashboard);

        obj=Firebase_Auth_SDP.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainDashboard.this);

        binding.createCompany.setOnClickListener(view -> {
//            if (acct!=null) {
//                startActivity(new Intent(MainDashboard.this, ProjectManagerDashboard.class));
//            }
//            else
//            {

                Intent intent=new Intent(MainDashboard.this, GoogleLoginActivity.class);
                startActivity(intent);
                finish();

            //}
        });

        binding.joinCompany.setOnClickListener(view -> {
//            if (Firebase_Auth_SDP.getInstance().getAuth().getCurrentUser()!=null)
//            {
//                startActivity(new Intent(MainDashboard.this, PMDashboard.class));
//            }
//            else {
                startActivity(new Intent(MainDashboard.this, LoginActivity.class));
            //}
        });
    }
}