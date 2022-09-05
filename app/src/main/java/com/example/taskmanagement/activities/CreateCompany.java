package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.ADMIN;
import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.COMPANY_DATA;
import static com.example.taskmanagement.Constant.COMPANY_NAME;

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
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.ActivityCreateCompanyBinding;
import com.example.taskmanagement.model.RegisterCompany;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateCompany extends AppCompatActivity {


    ActivityCreateCompanyBinding binding;
    Intent uri;
    String companyName, companyEmail, companyType, companyCountry;
    Firebase_Auth_SDP obj;
    ProgressDialog dialog;
    String email, id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_company);

        obj = Firebase_Auth_SDP.getInstance();


        email = getIntent().getStringExtra("email");
        id = getIntent().getStringExtra("id");
        binding.companyEmail.setText(email);
        binding.companyEmail.setEnabled(false);
        Log.i("mehmood", "onCreate: " + email);
        Log.i("mehmood", "onCreate: " + id);


        RegisterCompany registerCompany = new RegisterCompany();
        binding.setItem(registerCompany);
        registerCompany.setCompanyEmail(email);
        sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
        dialog = new ProgressDialog(this);
        binding.companyImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);

        });


        binding.registerCompany.setOnClickListener(view -> {
            companyName = binding.companyName.getText().toString();
            companyEmail = binding.companyEmail.getText().toString();
            companyType = binding.companyType.getText().toString();
            companyCountry = binding.companyCountry.getText().toString();

            if (uri == null) {
                Toast.makeText(this, "Upload Logo", Toast.LENGTH_SHORT).show();
            } else if (companyName.isEmpty()) {
                binding.companyName.setError("Please Company Name");
            } else if (companyEmail.isEmpty()) {
                binding.companyName.setError("Please Company Email");
            } else if (companyType.isEmpty()) {
                binding.companyName.setError("Please Company Type");
            } else if (companyCountry.isEmpty()) {
                binding.companyName.setError("Please Company Country");
            } else {

                sendData(uri.getData(), companyName, companyEmail, companyType, companyCountry);
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
                        binding.companyImage.setImageURI(uri.getData());
                    }
                }
            });


    private void sendData(Uri uri, String companyName, String companyEmail, String companyType, String companyCountry) {
        dialog.show();
        dialog.setTitle("Upload Detail");
        StorageReference sRef = obj.getStorageReference().getReference("Company Logo").child(String.valueOf(System.currentTimeMillis()));
        sRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                   @Override
                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                       dialog.dismiss();
                                                       taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri downloadUri) {

                                                               RegisterCompany model_class = new RegisterCompany(downloadUri.toString(), companyName, companyEmail, companyType, companyCountry, id);

                                                               obj.getFirebaseDatabase().getReference().child(COMPANIES).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                       obj.getFirebaseDatabase().getReference().child(COMPANIES).child(ADMIN).child(id).setValue(model_class);
//                                                                       finishAffinity();
                                                                       SharedPreferences.Editor editor=sharedPreferences.edit();
                                                                       editor.putString("k","Owner");
                                                                       editor.apply();
                                                                       editor.commit();

                                                                       Intent intent = new Intent(CreateCompany.this, ProjectManagerDashboard.class);
                                                                       startActivity(intent);
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
}