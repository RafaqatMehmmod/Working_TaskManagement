package com.example.taskmanagement.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.databinding.ActivityGoogleLoginBinding;
import com.example.taskmanagement.model.RegisterCompany;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GoogleLoginActivity extends AppCompatActivity {

    ActivityGoogleLoginBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> startActivityIntent;
    GoogleSignInAccount acct;
    Firebase_Auth_SDP obj;
    String email,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_login);

        obj = Firebase_Auth_SDP.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.google.setOnClickListener(view -> {
            signIn();
        });

        startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);


                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityIntent.launch(signInIntent);


    }

    private void handleSignInResult(com.google.android.gms.tasks.Task<GoogleSignInAccount> completedTask) {

        acct = GoogleSignIn.getLastSignedInAccount(GoogleLoginActivity.this);

        if (acct != null) {
            email = acct.getEmail();
             id = acct.getId();
        }
        obj.getFirebaseDatabase().getReference().child("Companies").child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        RegisterCompany model = dataSnapshot.getValue(RegisterCompany.class);

                        if (model.getLoginID().equals(id))
                        {
                            Log.i("mehmood", "onDataChange: " + model.getLoginID());
                            Intent intent=new Intent(GoogleLoginActivity.this,ProjectManagerDashboard.class);
                            intent.putExtra("email",email);
                            intent.putExtra("id",id);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
//                if (acct != null) {

                    Intent intent=new Intent(GoogleLoginActivity.this,CreateCompany.class);
                    intent.putExtra("email",email);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}