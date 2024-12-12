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
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentReference;
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
    private String email;

    private LoggedInUser loggedInUser;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
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

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        email = firebaseUser.getEmail();

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

                            binding.etUsername.setText(loggedInUser.name);
                            binding.etEmail.setText(loggedInUser.email);

                            if (loggedInUser.image != null) {
                                byte[] bytes = Base64.decode(loggedInUser.image, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                binding.imageProfile.setImageBitmap(bitmap);
                            }

                            encodeImage = loggedInUser.image;
                        } else {
                            showToast("NullDocument");
                            Log.d("NullDocument:", "the QueryDocumentSnapshot object was null");
                        }
                    }
                } else {
                    showToast("QueryGetError");
                    Log.d("QueryGetError:", task.getException().getMessage());
                }
            }
        });

        setListeners();
    }


    /**
     * Sets up the button listeners
     */
    private void setListeners() {
        binding.btnBackToMain.setOnClickListener(v -> onBackPressed());

        binding.btnUpdateUserSettings.setOnClickListener(v -> {

            if (isValidUpdateDetails()) {
                updateUser();
            }


        });

        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }


    /**
     * Updates the user's settings in the database
     */
    private void updateUser() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(FirebaseApp.getInstance());
        DocumentReference userRef = firebaseFirestore.collection("User").document(firebaseUser.getUid());

        HashMap<String, Object> updates = new HashMap<String, Object>();
        updates.put("email", binding.etEmail.getText().toString());
        updates.put("image", encodeImage);
        updates.put("name", binding.etUsername.getText().toString());

        userRef.update(updates)
                .addOnSuccessListener(aVoid -> showToast("user info updated"))
                .addOnFailureListener(e -> {
                    showToast("couldn't update user info");
                    Log.d("DocUpdateError:", e.getMessage());
                });

        firebaseUser.updateEmail(binding.etEmail.getText().toString())
                .addOnSuccessListener(aVoid -> {
                        auth.updateCurrentUser(firebaseUser)
                                .addOnFailureListener(e -> {
                            Log.d("EmailUpdateError:", e.getMessage());
                        });
                })
                .addOnFailureListener(e -> {
                    Log.d("EmailUpdateError:", e.getMessage());
                });

        loggedInUser.email = binding.etEmail.getText().toString();
        loggedInUser.image = encodeImage;
        loggedInUser.name = binding.etUsername.getText().toString();
    }


    /**
     * Shows a toast message
     * @param message The message to show
     */
    private void showToast(String message) {
        if (message == null) {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
        }
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
