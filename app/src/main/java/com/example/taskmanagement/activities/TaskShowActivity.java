package com.example.taskmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagement.R;
import com.example.taskmanagement.adapter.PageAdapter;
import com.example.taskmanagement.databinding.ActivityTaskShowBinding;
import com.google.android.material.tabs.TabLayout;

public class TaskShowActivity extends AppCompatActivity {

    ActivityTaskShowBinding binding;
    PageAdapter adapter;
    public static String email;
    public  static String s_teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_task_show);



        email=getIntent().getStringExtra("email");
        s_teamName=getIntent().getStringExtra("teamName");
        FragmentManager manager = getSupportFragmentManager();
        adapter = new PageAdapter(manager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("InProgress"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Delay"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed"));
//
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Load the tab using pageadapter
                binding.viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition()==1 || tab.getPosition()==2) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//
//        //Scroll r swipe fragment
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        binding.addTask.setOnClickListener(view -> {
            Intent intent=new Intent(TaskShowActivity.this,AddTaskActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("teamName",s_teamName);
            startActivity(intent);
        });
    }


}