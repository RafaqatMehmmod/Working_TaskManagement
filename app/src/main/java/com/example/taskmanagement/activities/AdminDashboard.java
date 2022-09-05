package com.example.taskmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.ActivityAdminDashboardBinding;

public class AdminDashboard extends AppCompatActivity {

    ActivityAdminDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding= DataBindingUtil.setContentView(this,R.layout.activity_admin_dashboard);

        binding.addClassBtn.setOnClickListener(view -> {
            startActivity(new Intent(AdminDashboard.this,TeamActivity.class));
            finish();

        });
    }
}