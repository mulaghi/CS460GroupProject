package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {

    // Declare the EditText fields and Button
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Use the XML layout for the login activity

        // Initialize views
        usernameEditText = findViewById(R.id.username);  // Make sure ID matches the XML
        passwordEditText = findViewById(R.id.password);  // Make sure ID matches the XML
        signInButton = findViewById(R.id.sign_in_button);  // Make sure ID matches the XML

        // Set up a listener for the sign-in button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        // Get the input from the EditText fields
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Basic validation to check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform login logic (e.g., check against hardcoded credentials or a backend service)
        if (username.equals("user") && password.equals("password")) {
            // If login is successful, show a toast message
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Redirect to MainActivity after successful login
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close LoginActivity so the user can't go back to it by pressing the back button
        } else {
            // If login fails, show an error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}