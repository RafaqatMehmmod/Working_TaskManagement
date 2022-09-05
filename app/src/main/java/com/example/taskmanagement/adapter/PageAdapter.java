package com.example.taskmanagement.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanagement.fragment.Completed;
import com.example.taskmanagement.fragment.Delay;
import com.example.taskmanagement.fragment.InProgress;


/** Created by Rafaqat Mehmood
 *  Whatsapp No:+923101025532
 *    29/06/2022
 */
public class PageAdapter extends FragmentStateAdapter {


    int tabCount;

    public PageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }



//    public PageAdapter(@NonNull Teacher_Activity fragmentActivity) {
//        super(fragmentActivity);
//
//    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0)
        {
            return new InProgress();
        }
        else if (position==1)
        {
            return new Delay();
        }
        return new Completed();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
