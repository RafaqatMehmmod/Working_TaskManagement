package com.example.taskmanagement.activities;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.EMPLOY;
import static com.example.taskmanagement.Constant.USERS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.hrdashboard.HRDashboard;
import com.example.taskmanagement.activities.login.CreateHP;
import com.example.taskmanagement.databinding.ActivityAddEmployBinding;
import com.example.taskmanagement.fragment.Employ;
import com.example.taskmanagement.model.RegisterEmployModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEmployActivity extends AppCompatActivity {

    ActivityAddEmployBinding binding;
    Intent uri;
    String name, s_email, role, key, s_password;
    Firebase_Auth_SDP obj;
    ProgressDialog dialog;
    String companyName, des;
    RegisterEmployModel model;
    String af_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_employ);

        obj = Firebase_Auth_SDP.getInstance();
        model = new RegisterEmployModel();
        binding.setItem(model);

        companyName = getIntent().getStringExtra("companyName");
        binding.companyName.setEnabled(false);
        des = getIntent().getStringExtra("designation");
        af_email = getIntent().getStringExtra("email");
        model.setCompanyName(companyName);

        dialog = new ProgressDialog(this);
        binding.selectedImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);

        });

        binding.register.setOnClickListener(view -> {
            name = binding.name.getText().toString();
            role = binding.role.getText().toString();
            s_email = binding.email.getText().toString();
            s_password = binding.password.getText().toString();


            if (uri == null) {
                Toast.makeText(this, "Upload Logo", Toast.LENGTH_SHORT).show();
            } else if (name.isEmpty()) {
                binding.name.setError("Please Name");
            } else if (role.isEmpty()) {
                binding.role.setError("Please Role");
            } else if (s_email.isEmpty()) {
                binding.email.setError("Please Email");
            } else if (s_password.isEmpty()) {
                binding.password.setError("Please Password");
            } else if (role.equals("HR") || role.equals("Project Manager") || role.equals("hr") || role.equals("project manager")) {
                binding.role.setError("Only admin can create HR & Project Manager");
            }else {

                sendData(name, role, s_email, s_password, uri.getData(), key,"NormalUser");
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


    private void sendData(String s_name, String role, String email, String password, Uri uri, String key,String normalUser) {
        dialog.show();
        dialog.setTitle("Upload Detail");
        StorageReference sRef = obj.getStorageReference().getReference("Register Employ People").child(String.valueOf(System.currentTimeMillis()));
        key = obj.getDatabaseReference().push().getKey();
        String finalKey = key;
        sRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                   @Override
                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                       taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                           @Override
                                                           public void onSuccess(Uri downloadUri) {


                                                               RegisterEmployModel model = new RegisterEmployModel(s_name, email, password, downloadUri.toString(), companyName, finalKey, af_email, role,normalUser);

                                                               obj.getFirebaseDatabase().getReference().child(COMPANIES).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                       obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).child(finalKey).setValue(model);
                                                                       dialog.dismiss();
                                                                       Intent intent=new Intent(AddEmployActivity.this, HRDashboard.class);
                                                                       intent.putExtra("email",af_email);
                                                                       startActivity(intent);
                                                                       finish();
//                                                                                    finish();

//                                                                                   FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                                                                                   fragmentTransaction.replace(R.id.lay_container,new Employ()).commit();
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