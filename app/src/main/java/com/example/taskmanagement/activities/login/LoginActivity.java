package com.example.taskmanagement.activities.login;

import static com.example.taskmanagement.Constant.COMPANIES;
import static com.example.taskmanagement.Constant.USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.taskmanagement.Firebase_Auth_SDP;
import com.example.taskmanagement.R;
import com.example.taskmanagement.activities.Dashboard;
import com.example.taskmanagement.databinding.ActivityLoginBinding;
import com.example.taskmanagement.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Firebase_Auth_SDP obj;
    String str_email, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        obj = Firebase_Auth_SDP.getInstance();

        User user = new User("", str_email, "", "", str_password,"");
        binding.setItem(user);


        binding.Login.setOnClickListener(view -> {
            str_email = binding.email.getText().toString();
            str_password = binding.password.getText().toString();
            if (TextUtils.isEmpty(str_email)) {
                binding.email.setError("Enter Email");
            } else if (TextUtils.isEmpty(str_password)) {
                binding.password.setError("Enter Password");
            } else {

                loginInApp_Admin(str_email, str_password);


            }
        });
    }

    private void loginInApp_Admin(String s_email, String s_password) {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Wait....");
        dialog.setMessage("Detail Match in Database");
        dialog.show();
//        obj.getAuth().signInWithEmailAndPassword(s_email, s_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if (task.isSuccessful()) {
//                    String uid = FirebaseAuth.getInstance().getUid();
//                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
//                    intent.putExtra("email", str_email);
//                    intent.putExtra("uID", uid);
//                    startActivity(intent);
//                    finish();
//                    dialog.dismiss();
//
//
//                } else {
//                    dialog.dismiss();
//                    String error = task.getException().toString();
//                    binding.email.setError("Email not registered!");
//                    binding.email.requestFocus();
//                }
//            }
//
//        });

        obj.getFirebaseDatabase().getReference().child(COMPANIES).child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    com.example.taskmanagement.model.CreateHP model_class = dataSnapshot.getValue(com.example.taskmanagement.model.CreateHP.class);

                    if (model_class.getEmail().equals(s_email))
                    {
                        if ( model_class.getPassword().equals(s_password)) {
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            intent.putExtra("email", str_email);
                            startActivity(intent);
                            finish();
                            Log.i("mehmood", "Login: " + model_class.getEmail());
                            Log.i("mehmood", "Login: " + s_email);
                        }
//                        else
//                        {
//                            Toast.makeText(LoginActivity.this, "ll", Toast.LENGTH_SHORT).show();
//                        }

                    }
//                    else
//                    {
//                        dialog.dismiss();
//                        binding.email.setError("Email not registered!");
//                        binding.email.requestFocus();
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}