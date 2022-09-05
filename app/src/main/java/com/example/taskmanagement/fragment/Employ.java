package com.example.taskmanagement.fragment;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.EMPLOY;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.activities.hrdashboard.HRDashboard.after_email;
import static com.example.taskmanagement.fragment.WorkPlace.designation;
import static com.example.taskmanagement.fragment.WorkPlace.storeCompanyName;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.AddEmployActivity;
import com.example.taskmanagement.adapter.RegisterEmployAdapter;
import com.example.taskmanagement.adapter.ShowTeamAdapter;
import com.example.taskmanagement.adapter.WorkPlaceAdapter;
import com.example.taskmanagement.databinding.ActivityPmdashboardBinding;
import com.example.taskmanagement.databinding.FragmentEmployBinding;
import com.example.taskmanagement.interfacesPackage.UserDelete2;
import com.example.taskmanagement.model.CreateTeam;
import com.example.taskmanagement.model.RegisterEmployModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Employ extends Fragment implements UserDelete2 {
    FragmentEmployBinding binding;
    Firebase_Auth_SDP obj;
    String cName;
    ArrayList<RegisterEmployModel> list;
    RegisterEmployAdapter adapter;

    public Employ() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        obj = Firebase_Auth_SDP.getInstance();
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    RegisterEmployModel model = dataSnapshot.getValue(RegisterEmployModel.class);
                    if (model.getEmployCreatedBy().equals(""))
                    {
//                        if (obj.getAuth().getCurrentUser().getEmail().equals(model.getEmployCreatedBy())) {

                       // }
                    }
                    else if (model.getCompanyName().equals(storeCompanyName))
                    {
                        list.add(model);
                        binding.recycleView.setAdapter(adapter);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employ, container, false);
        View view = binding.getRoot();

        list = new ArrayList<>();
        adapter = new RegisterEmployAdapter(getContext(), list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);


        binding.hr.setOnClickListener(view1 -> {
            Intent intent=new Intent(getContext(),AddEmployActivity.class);
            intent.putExtra("companyName",storeCompanyName);
            intent.putExtra("designation",designation);
            intent.putExtra("email",after_email);
            startActivity(intent);
        });

        return view;

    }

    @Override
    public void deleteUser(RegisterEmployModel model, View view, int pos) {

    }
}