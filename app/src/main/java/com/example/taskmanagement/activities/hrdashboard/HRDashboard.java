package com.example.taskmanagement.activities.hrdashboard;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.pmdashboard.PMDashboard;
import com.example.taskmanagement.adapter.PageAdapter;
import com.example.taskmanagement.adapter.PageAdapter2;
import com.example.taskmanagement.databinding.ActivityHrdashboardBinding;
import com.google.android.material.tabs.TabLayout;

public class HRDashboard extends AppCompatActivity {

    ActivityHrdashboardBinding binding;
    PageAdapter2 adapter;
    public String hr_dashboard_email;
    public static String after_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_hrdashboard);

//        hr_dashboard_email=getIntent().getStringExtra("email");
        after_email=getIntent().getStringExtra("email");
        Log.i("mehmood", "onCreateDashboard.....: "+after_email);
        FragmentManager manager = getSupportFragmentManager();
        adapter = new PageAdapter2(manager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        SharedPreferences sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("k","HR");
        editor.putString("kk",after_email);
        editor.apply();
        editor.commit();


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("WorkPlace"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Employee"));

//
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Load the tab using pageadapter
                binding.viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition()==1) {
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

        binding.logout.setOnClickListener(view ->
        {
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
                    AlertDialog dialog = new AlertDialog.Builder(HRDashboard.this, R.style.AlertDialogStyle)
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