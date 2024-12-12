package com.example.libraryteam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryteam.databinding.CheckoutViewBinding;

public class BookViewActivity extends AppCompatActivity {

    private ImageView bookCover;
    private TextView bookName, bookYear, bookDescription;
    private CheckoutViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view); // Updated layout name
        setBookDetails();
        setListeners();
    }

    private void setBookDetails() {
        bookCover = findViewById(R.id.imageBookCover);
        bookName = findViewById(R.id.textBookName);
        bookYear = findViewById(R.id.textBookYear);
        bookDescription = findViewById(R.id.textBookDescription);
    }

    private void setListeners() {
        findViewById(R.id.buttonCheckOut).setOnClickListener(v -> checkOutBook());
    }

    private void checkOutBook() {
        showToast("Added to checkout list");
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
