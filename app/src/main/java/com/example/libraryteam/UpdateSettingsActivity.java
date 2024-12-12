package com.example.libraryteam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryteam.LoggedInUser;
import com.example.libraryteam.databinding.ActivityUserSettingsBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;


/**
 * update user settings class
 */
public class UpdateSettingsActivity extends AppCompatActivity {

    private ActivityUserSettingsBinding binding;

    private String encodeImage;

    LoggedInUser loggedInUser;


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

        loggedInUser = new LoggedInUser();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        String email = firebaseUser.getEmail();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(FirebaseApp.getInstance());
        Query usersQuery = firebaseFirestore.collectionGroup("User");
        usersQuery = usersQuery.whereEqualTo("email", email);

        usersQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            HashMap<String, Object> result = (HashMap<String, Object>)document.getData();
                            loggedInUser.email = (String)result.get("email");
                            loggedInUser.image = (String)result.get("image");
                            loggedInUser.name = (String)result.get("name");
                        }
                    }
                } else {
                    Log.d("Query.get error:", task.getException().getMessage());
                }
            }
        });

        showToast(loggedInUser.email);
        showToast(loggedInUser.name);

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

        if (
                binding.etUsername.getText().toString().trim().isEmpty()
                || binding.etEmail.getText().toString().trim().isEmpty()) {
            showToast("please enter information for all fields");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()) {
            showToast("please enter a valid email");
            return false;
        }

        return true;
    }

}
