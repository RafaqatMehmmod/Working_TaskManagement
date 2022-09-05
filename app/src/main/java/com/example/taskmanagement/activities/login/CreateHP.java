package com.example.taskmanagement.activities.login;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.COMPANY_DATA;
import static com.example.taskmanagement.Constant.COMPANY_NAME;
import static com.example.taskmanagement.Constant.USERS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.CreateCompany;
import com.example.taskmanagement.activities.Dashboard;
import com.example.taskmanagement.activities.MainDashboard;
import com.example.taskmanagement.activities.ProjectManagerDashboard;
import com.example.taskmanagement.databinding.ActivityCreateHpBinding;
import com.example.taskmanagement.model.RegisterCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateHP extends AppCompatActivity {

    ActivityCreateHpBinding binding;
    Intent uri;
    String name, email, designation, key, password;
    Firebase_Auth_SDP obj;
    ProgressDialog dialog;
    String companyName;
    com.example.taskmanagement.model.CreateHP createHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_hp);

        obj = Firebase_Auth_SDP.getInstance();
        companyName = getIntent().getStringExtra("companyName");

        createHP = new com.example.taskmanagement.model.CreateHP();
        binding.setItem(createHP);

        createHP.setCompanyName(companyName);
        binding.companyName.setEnabled(false);
        binding.designation.setEnabled(false);
        String[] arraySpinner = new String[] {"HR", "Project Manager"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.designation.setAdapter(adapter);


        dialog = new ProgressDialog(this);
        binding.selectedImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);

        });

        binding.create.setOnClickListener(view -> {
            name = binding.name.getText().toString();
            email = binding.email.getText().toString();
            designation = binding.designation.getText().toString();
            password = binding.password.getText().toString();


            if (uri == null) {
                Toast.makeText(this, "Upload Logo", Toast.LENGTH_SHORT).show();
            } else if (name.isEmpty()) {
                binding.name.setError("Please Name");
            } else if (email.isEmpty()) {
                binding.email.setError("Please Email");
            } else if (designation.isEmpty()) {
                binding.designation.setError("Please Designation");
            } else if (password.isEmpty()) {
                binding.password.setError("Please Password");
            } else {

                sendData(uri.getData(), name, email, designation, key, password,"");
            }

        });
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        uri = result.getData();

                        binding.selectedImage.setImageURI(uri.getData());
                    }
                }
            });

    private void sendData(Uri uri, String name, String email, String designation, String key, String password,String normalUser) {
        dialog.show();
        dialog.setTitle("Upload Detail");
        StorageReference sRef = obj.getStorageReference().getReference("Company People").child(String.valueOf(System.currentTimeMillis()));
        key = obj.getDatabaseReference().push().getKey();
        String finalKey = key;
        sRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                   @Override
                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                       taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri downloadUri) {

                                                               com.example.taskmanagement.model.CreateHP model_class = new com.example.taskmanagement.model.CreateHP(finalKey, name, downloadUri.toString(), designation, email, password, companyName, "",normalUser);

                                                               obj.getFirebaseDatabase().getReference().child(COMPANIES).addValueEventListener(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                                                   obj.getFirebaseDatabase().getReference().child(COMPANIES).child(companyName).child(USERS).child(finalKey).setValue(model_class);
                                                                       obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).child(finalKey).setValue(model_class);
                                                                       dialog.dismiss();
                                                                       startActivity(new Intent(CreateHP.this, ProjectManagerDashboard.class));
                                                                       finish();

                                                                   }

                                                                   @Override
                                                                   public void onCancelled(@NonNull DatabaseError error) {
                                                                   }
                                                               });


                                                           }
                                                       });
                                                   }
                                               }
        ).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                dialog.setMessage("Uploaded :" + (int) percent + "%");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}