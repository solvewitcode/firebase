package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebase.databinding.ActivityMainBinding;
import com.example.firebase.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.file.FileStore;

public class signUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseFirestore store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= binding.nameTextView.getText().toString();
                String phone= binding.phoneTextView.getText().toString();
                String email= binding.emailTextViewSignup.getText().toString().trim();
                String password= binding.passwordTextViewSignup.getText().toString();
                progressDialog.show();
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(signUp.this,login.class));
                                progressDialog.cancel();
                                store.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(name,phone,email,password));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();

                            }
                        });

            }
        });
        binding.gotologinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signUp.this,login.class));
            }
        });

    }
}