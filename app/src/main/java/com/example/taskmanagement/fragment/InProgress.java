package com.example.taskmanagement.fragment;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.MEMBER;
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
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.ProjectManagerDashboard;
import com.example.taskmanagement.adapter.InProgressAdapter;
import com.example.taskmanagement.adapter.RegisterUserAdapter2;
import com.example.taskmanagement.databinding.FragmentInProgressBinding;
import com.example.taskmanagement.interfacesPackage.WorkerDelete;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateHP;
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


public class InProgress extends Fragment implements WorkerDelete {

    Firebase_Auth_SDP obj;
    String companyName, storeCompanyName, teamName, storeTeamName, designation;
    ArrayList<AddTaskModel> list;
    InProgressAdapter adapter;
    FragmentInProgressBinding binding;
    String currentDay,currentMonth,currentYear;
    int i_day,i_month,i_year;
    String s_currentDate,name;
    String TAG="Ali";
    public InProgress() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        obj = Firebase_Auth_SDP.getInstance();
        s_currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

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


                    if (email.equals(model_class.getEmail())) {

                        companyName = model_class.getCompanyName();
                        storeCompanyName = companyName;
                        designation = model_class.getDesignation();
                        name=model_class.getName();
                        Log.i(TAG, "User: "+companyName);
                        Log.i(TAG, "User: "+storeCompanyName);
                        Log.i(TAG, "User: "+designation);
                        Log.i(TAG, "User: "+name);
                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
                                    teamName = model.getTeamName();
                                    storeTeamName = teamName;

                                    if (s_teamName.equals(storeTeamName)) {
                                        fetchData();

                                        Log.i(TAG, "User..2: " + teamName);
                                        Log.i(TAG, "User..2: " + storeTeamName);
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

    private void fetchData() {
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(storeTeamName).child(WORK).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AddTaskModel model = dataSnapshot.getValue(AddTaskModel.class);

                    if(model.getStatus().equals("InProgress")) {
                        list.add(model);
                        binding.recycleView.setAdapter(adapter);

                    }
                    String s = dataSnapshot.getKey();
                    Log.i(TAG, "Fetch Outside: " + s);
                    String date = model.getTo();
                    Log.i(TAG, "Fetch Outside: " + date);
                    Log.i(TAG, "Fetch Outside: " + s_currentDate);
                    if (s_currentDate.compareTo(date) > 0) {
                        obj.getFirebaseDatabase().getReference().child(COMPANIES)
                                .child(storeCompanyName).child(TEAMS).child(s_teamName).child(WORK).child(s).child("status").setValue("Delay");
                        Log.i(TAG, "Fetch In: " + date);
                        Log.i(TAG, "Fetch In: " + s_currentDate);

                    }



//
//                    if (s_currentDate.compareTo(date) > 0)
//                    {    Log.i("app", "Date1 is after Date2");
//                        Toast.makeText(getContext(), "IF", Toast.LENGTH_SHORT).show();
//                    }
//                    else if (s_currentDate.compareTo(date) < 0)
//                    {
//                        Log.i("app", "Date1 is before Date2"+s_currentDate);
//                        Toast.makeText(getContext(), "Eslse if", Toast.LENGTH_SHORT).show();
//                        //03 Date
//                    }
//                    else if (s_currentDate.compareTo(date) == 0)
//                    {    Log.i("app", "Date1 is equal to Date2");
//                        Toast.makeText(getContext(), "Else if 2", Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_in_progress, container, false);
        View view = binding.getRoot();

        list = new ArrayList<>();
        adapter = new InProgressAdapter(getContext(), list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        binding.recycleView.setLayoutManager(linearLayoutManager);

        return view;

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

                                        Log.i("Amir", "onClick: " + key);
                                        Log.i("Amir", "onClick: " + storeCompanyName);
                                        Log.i("Amir", "onClick: " + s_teamName);


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