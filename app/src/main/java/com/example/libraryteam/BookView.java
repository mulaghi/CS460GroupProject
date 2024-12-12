package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryteam.databinding.ActivityRegisterBinding;

public class BookView extends AppCompatActivity {

    private ImageView bookCoverImage;
    private TextView bookTitle, bookAuthor, bookLanguage, bookPublisher, bookPublished, bookISBN, bookDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view);

        //Linking to XML
        bookCoverImage = findViewById(R.id.imageBookCover);
        bookTitle = findViewById(R.id.textBookName);
        bookAuthor = findViewById(R.id.textAuthor);
        bookLanguage = findViewById(R.id.textLanguage);
        bookPublisher = findViewById(R.id.textPublisher);
        bookPublished = findViewById(R.id.textBookYear);
        bookISBN = findViewById(R.id.textISBN);
        bookDescription = findViewById(R.id.textBookDescription);

        // Retreive data from prior class
        Intent intent = getIntent();
        //Make sure its not empty
        if (intent != null) {
            String title = intent.getStringExtra("Title");
            String author = intent.getStringExtra("Author");
            String language = intent.getStringExtra("Language");
            String publisher = intent.getStringExtra("Publisher");
            String published = intent.getStringExtra("Published");
            String isbn = intent.getStringExtra("ISBN");
            String description = intent.getStringExtra("Description");
            bookCoverImage.setImageResource(intent.getIntExtra("Image", -1));

            // Set the data to the views
            bookTitle.setText(title != null ? title : "No Title Available");
            bookAuthor.setText(author != null ? author : "No Author Available");
            bookLanguage.setText(language != null ? language : "No Language Info");
            bookPublisher.setText(publisher != null ? publisher : "No Publisher Info");
            bookPublished.setText(published != null ? published : "No Published Date");
            bookISBN.setText(isbn != null ? isbn : "No ISBN");
            bookDescription.setText(description != null ? description : "No Description Available");

        }
        bookCoverImage.setOnClickListener(v -> {
            // Navigate back to the previous activity
            onBackPressed();
        });

        // Handle checkout button
        findViewById(R.id.buttonCheckout).setOnClickListener(v -> {
            Intent checkoutIntent = new Intent(BookView.this, BookViewActivity.class);
            checkoutIntent.putExtra("Title", bookTitle.getText().toString());
            checkoutIntent.putExtra("Author", bookAuthor.getText().toString());
            checkoutIntent.putExtra("Description", bookDescription.getText().toString());
            // Pass other data as needed
            startActivity(checkoutIntent);
        });
    }

}
