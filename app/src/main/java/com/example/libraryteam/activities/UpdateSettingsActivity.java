package com.example.libraryteam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryteam.databinding.ActivityUserSettingsBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * update user settings class
 */
public class UpdateSettingsActivity extends AppCompatActivity {

    private ActivityUserSettingsBinding binding;

    private String encodeImage;


    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        binding.imageProfile.setImageBitmap(bitmap);
                        encodeImage = encodeImage(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );


    /**
     * Sets up the UpdateSettingsActivity and sets button listeners
     * @param savedInstanceState Default savedInstanceState argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserSettingsBinding.inflate(getLayoutInflater());
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

        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
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
     * Encodes an image to JPEG format and returns it as a String object
     * @param bitmap The image to encode.
     * @return The image as a String object
     */
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();

        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * Checks if the user's updated info is valid
     * @return true if the user's updated info is valid, otherwise false
     */
    private Boolean isValidUpdateDetails() {

        String password = binding.etPassword.getText().toString().trim();
        String confirmedPassword = binding.etConfirmPassword.getText().toString().trim();

        if (
                binding.etUsername.getText().toString().trim().isEmpty()
                || binding.etEmail.getText().toString().trim().isEmpty()
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
