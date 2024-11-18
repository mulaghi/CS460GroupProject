package com.example.libraryteam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * update user settings class
 */
public class updateSettingsActivity extends AppCompatActivity {

    private ActivityUpdateSettingsBinding binding;


    /**
     * Sets up the updateSettingsActivity and sets button listeners
     * @param savedInstanceState Default savedInstanceState argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }


    /**
     * Sets up the button listeners
     */
    private void setListeners() {
        binding.btnCancelUserSettings.setOnClickListener(v -> onBackPressed());

        binding.btnUpdateUserSettings.setOnClickListener(v -> {
            if (isValidUpdateDetails()) {
                showToast("update details are valid");
            }
        });
    }


    /**
     * Shows a toast message
     * @param message The message to show
     */
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Checks if the user's updated info is valid
     * @return true if the user's updated info is valid, otherwise false
     */
    private Boolean isValidUpdateDetails() {

        String password = binding.etPassword.getText().toString().trim();
        String confirmedPassword = binding.etConfirmPassword.getText().toString().trim();

        if (
                binding.etEmail.getText().toString().trim().isEmpty()
                || binding.etDateOfBirth.getText().toString().trim().isEmpty()
                || binding.etPhoneNumber.getText().toString().trim().isEmpty()
                || password.isEmpty()
                || confirmedPassword.isEmpty()) {
            showToast("please enter information for all fields");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()) {
            showToast("please enter a valid email");
            return false;
        }
        if (!password.equals(confirmedPassword)) {
            showToast("password and confirmed password don't match");
            return false;
        }

        return true;
    }

}
