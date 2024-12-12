package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.example.libraryteam.databinding.ActivityForgotPasswordBinding;
import com.example.libraryteam.databinding.ActivityRegisterBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * remind user of password class
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;


    /**
     * This is called on app creation and orientation changes to bind Views and call listeners
     * Uses view binding to automatically associate with Views
     *
     * @param savedInstanceState param for orientation changes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }


    /**
     * Sets up the button listeners
     */
    private void setListeners() {
        binding.btnBackToLogin.setOnClickListener(v -> onBackPressed());

        binding.btnSendEmail.setOnClickListener(v -> sendEmail());
    }


    /**
     * Shows a toast message
     * @param message The message to show
     */
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * tries to send a password reset email if it's valid
     */
    private void sendEmail() {
        String email = binding.etEmail.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("please enter a valid email");
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener() {
                    public void onSuccess(Object result) {
                        showToast("password reset email sent");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        showToast("couldn't send email");
                    }
                });
    }
}
