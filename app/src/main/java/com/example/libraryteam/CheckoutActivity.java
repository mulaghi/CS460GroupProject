package com.example.libraryteam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.libraryteam.databinding.CheckoutViewBinding;


public class CheckoutActivity extends AppCompatActivity {

    private ImageView bookCover, pageOne, pageTwo, pageThree;
    private TextView bookName, bookYear, bookDescription;

    private CheckoutViewBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        setBookDetails();
        setListeners();
    }

    public void setBookDetails(){
        bookCover = findViewById(R.id.imageBookCover);
        pageOne = findViewById(R.id.imagePageOne);
        pageTwo = findViewById(R.id.imagePageTwo);
        pageThree = findViewById(R.id.imagePageThree);

        bookName = findViewById(R.id.textBookName);
        bookYear = findViewById(R.id.textBookYear);
        bookDescription = findViewById(R.id.textBookDescription);

    }

    public void setListeners(){
        binding.buttonCheckOut.setOnClickListener(v ->
               checkOutBook());
    }


    public void checkOutBook(){
        showToast("Added to checkout list");
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}