package com.example.taskmanagement.fragment;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.Constant.WORK;
import static com.example.taskmanagement.activities.TaskShowActivity.email;
import static com.example.taskmanagement.activities.TaskShowActivity.s_teamName;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.adapter.CompleteAdapter;
import com.example.taskmanagement.adapter.DelayAdapter;
import com.example.taskmanagement.databinding.FragmentCompletedBinding;
import com.example.taskmanagement.databinding.FragmentDelayBinding;
import com.example.taskmanagement.interfacesPackage.WorkerDelete;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateTeam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Completed extends Fragment implements WorkerDelete {
    Firebase_Auth_SDP obj;
    String companyName, storeCompanyName, teamName, storeTeamName, designation;
    ArrayList<AddTaskModel> list;
    CompleteAdapter adapter;
    FragmentCompletedBinding binding;
    String currentDay,currentMonth,currentYear;
    int i_day,i_month,i_year;
    String currentDate,name;
    String TAG="Ali";

    public Completed() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obj = Firebase_Auth_SDP.getInstance();
        currentDate = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault()).format(new Date());

        currentDay= new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        currentMonth= new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        currentYear= new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        i_day=Integer.parseInt(currentDay);
        i_month=Integer.parseInt(currentMonth);
        i_year=Integer.parseInt(currentYear);

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);
//                    companyName = model_class.getCompanyName();
//                    name=model_class.getName();
//                    email=model_class.getEmail();

                    if (email.equals(model_class.getEmail())) {
//                        designation = model_class.getDesignation();
//                        name = model_class.getName();
////                        email = model_class.getEmail();
//                        storeDesignation=designation;
//                        storeName=name;
                        companyName = model_class.getCompanyName();
                        storeCompanyName = companyName;
                        designation = model_class.getDesignation();
                        name=model_class.getName();
                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
                                    teamName = model.getTeamName();
                                    storeTeamName = teamName;
                                    if (s_teamName.equals(storeTeamName)) {
                                        fetchData();

                                        Log.i(TAG, "User.Completed.2: " + teamName);
                                        Log.i(TAG, "User.Completed.2: " + storeTeamName);
                                    }

                                }


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed, container, false);
        View view = binding.getRoot();

        list = new ArrayList<>();
        adapter = new CompleteAdapter(getContext(), list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recycleView.setLayoutManager(linearLayoutManager);

        return view;
    }
    private void fetchData() {
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(storeTeamName).child(WORK).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AddTaskModel model = dataSnapshot.getValue(AddTaskModel.class);

                    if(model.getStatus().equals("Completed")) {
                        list.add(model);
                        binding.recycleView.setAdapter(adapter);
                    }
                    String s=dataSnapshot.getKey();
                    Log.i("mehmood", "onDataChange: "+s);

                    String date=model.getTo();


//                    if (currentDate.compareTo(date)>0)
//                    {
//                        obj.getFirebaseDatabase().getReference().child(COMPANIES)
//                                .child(storeCompanyName).child(TEAMS).child(storeTeamName).child(WORK).child(s).child("status").setValue("Delay");
//                    }

                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void deleteUser(AddTaskModel model, View view) {
        showPopMenu(model, view);
    }

    private void showPopMenu(AddTaskModel model, View view) {
        String key = model.getAssignTo();
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.delete_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle)
                            .setTitle("Delete")
                            .setCancelable(false)
                            .setMessage("Do you really want to delete this class?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {

                                        Log.i("mehmood", "onClick: " + key);
                                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(s_teamName).child(WORK).child(key).
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
}