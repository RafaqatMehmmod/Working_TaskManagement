package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.MEMBER;
import static com.example.taskmanagement.Constant.TEAMS;
import static com.example.taskmanagement.Constant.USERS;
import static com.example.taskmanagement.activities.TaskShowActivity.s_teamName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;

import com.example.taskmanagement.databinding.ActivityAddTaskBinding;
import com.example.taskmanagement.model.AddTaskModel;
import com.example.taskmanagement.model.CreateHP;
import com.example.taskmanagement.model.CreateTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    ActivityAddTaskBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String fromDate, toDate, latitudeAndLong;
    private ArrayList<String> list;
    private ArrayAdapter assignTo;
    Firebase_Auth_SDP obj;
    String companyName,storeCompanyName,teamName,storeTeamName,testingTN;
    String title,description,t_pos,designation;
    String status="InProgress";
    String toDay,toMonth,toYear,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_task);


        binding.to.setFocusable(false);
        binding.from.setFocusable(false);
        obj = Firebase_Auth_SDP.getInstance();
        email=getIntent().getStringExtra("email");
        binding.from.setOnClickListener(view -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                                                        int monthOfYear, int dayOfMonth) {

                            if (dayOfMonth<=9) {
                                if ((monthOfYear+1)<=9) {
                                    Log.i("mehmood", "onDateSet: " + "0" + dayOfMonth);
                                    Log.i("mehmood", "onDateSet: " + "0" + (monthOfYear + 1));
                                    fromDate = "0" + dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                    binding.from.setText(fromDate);
                                }
                                else
                                {
                                    fromDate = "0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.from.setText(fromDate);
                                }

                            }
                            else
                            {
                                if ((monthOfYear+1)<=9) {
                                    Log.i("mehmood", "onDateSet: " + "0" + dayOfMonth);
                                    Log.i("mehmood", "onDateSet: " + "0" + (monthOfYear + 1));
                                    fromDate = dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                    binding.from.setText(fromDate);
                                }
                                else
                                {
                                    fromDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.from.setText(fromDate);
                                }

                            }




                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        });



        binding.to.setOnClickListener(view -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if (dayOfMonth<=9) {
                                if ((monthOfYear+1)<=9) {
                                    Log.i("mehmood", "onDateSet: " + "0" + dayOfMonth);
                                    Log.i("mehmood", "onDateSet: " + "0" + (monthOfYear + 1));
                                    fromDate = "0" + dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                    binding.to.setText(fromDate);
                                }
                                else
                                {
                                    fromDate = "0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.to.setText(fromDate);
                                }

                            }
                            else
                            {
                                if ((monthOfYear+1)<=9) {
                                    Log.i("mehmood", "onDateSet: " + "0" + dayOfMonth);
                                    Log.i("mehmood", "onDateSet: " + "0" + (monthOfYear + 1));
                                    fromDate = dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                    binding.to.setText(fromDate);
                                }
                                else
                                {
                                    fromDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.to.setText(fromDate);
                                }

                            }



                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });


        //Adapter of selected Teacher
        list = new ArrayList<>();
        list.add("All");
        assignTo = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        binding.assignTo.setAdapter(assignTo);


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
                        designation=model_class.getDesignation();
                        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    CreateTeam model = dataSnapshot.getValue(CreateTeam.class);
                                    teamName=model.getTeamName();
                                    storeTeamName=teamName;
                                    if (storeTeamName.equals(s_teamName))
                                    {

                                        testingTN=storeTeamName;

                                        fetchData();
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



        binding.assignTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.blue());
                    t_pos = adapterView.getItemAtPosition(i).toString();
                } catch (Exception e) {
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }

    private void doneWork(String cName,String tName,String s_email )
    {
        cName=storeCompanyName;
        tName=testingTN;

        String finalCName = cName;
        String finalTName = tName;
        binding.done.setOnClickListener(view -> {

            title=binding.title.getText().toString();
            description=binding.description.getText().toString();
            fromDate=binding.from.getText().toString();
            toDate=binding.to.getText().toString();

            if (title.isEmpty())
            {
                binding.title.setError("Please Title");
            }
            else if (description.isEmpty())
            {
                binding.description.setError("Please Description");
            }
            else if (fromDate.isEmpty())
            {
                binding.from.setError("Select Start Date");
            }
            else if (toDate.isEmpty())
            {
                binding.to.setError("Select End Date");
            }
            else
            {
                sendData(title,description,fromDate,toDate,t_pos,finalCName,finalTName,designation,status,toDay,toMonth,toYear,s_email);
//
            }

        });

    }
    private void fetchData() {
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(storeCompanyName).child(TEAMS).child(storeTeamName).child(MEMBER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CreateHP model = dataSnapshot.getValue(CreateHP.class);
                    if (s_teamName.equals(testingTN)) {
                        list.add(model.getName());
                        Log.i("mehmood", "onDataChange...++: "+storeTeamName);
                        Log.i("mehmood", "onDataChange...++: "+testingTN);
                        doneWork(storeCompanyName,testingTN,model.getEmail());
                    }
                }
                assignTo.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void sendData(String title, String description, String fromDate, String toDate, String t_pos, String cName, String tName, String des, String sts,String day, String month, String year,String s_email) {
        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(cName).child(TEAMS).child(tName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AddTaskModel model=new AddTaskModel(title,description,fromDate,toDate,t_pos,des,sts,day,month,year,s_email);
                obj.getFirebaseDatabase().getReference().child(COMPANIES).child(cName).child(TEAMS).child(tName).child("Worker").child(t_pos).setValue(model);
                Intent intent=new Intent(AddTaskActivity.this,TaskShowActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("teamName",tName);
                startActivity(intent);
                Toast.makeText(AddTaskActivity.this, "Save Record Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}