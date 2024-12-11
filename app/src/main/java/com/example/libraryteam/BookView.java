package com.example.libraryteam;

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
        //Place i correct View
//        bookCoverImage = findViewById(R.id.bookCoverImage); // Replace with your actual ImageView ID
//        bookTitle = findViewById(R.id.bookTitle);
//        bookAuthor = findViewById(R.id.bookAuthor);
//        bookLanguage = findViewById(R.id.bookLanguage);
//        bookPublisher = findViewById(R.id.bookPublisher);
//        bookPublished = findViewById(R.id.bookPublished);
//        bookISBN = findViewById(R.id.bookISBN);
//        bookDescription = findViewById(R.id.bookDescription);
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String title = intent.getStringExtra("Title");
//            String author = intent.getStringExtra("Author");
//            String language = intent.getStringExtra("Language");
//            String publisher = intent.getStringExtra("Publisher");
//            String published = intent.getStringExtra("Published");
//            String isbn = intent.getStringExtra("ISBN");
//            String description = intent.getStringExtra("Description");
//            int imageResId = intent.getIntExtra("Image", 0); // Default to 0 if no image is passed
//
//            // Set data to views
//            bookCoverImage.setImageResource(imageResId);
//            bookTitle.setText(title);
//            bookAuthor.setText(author);
//            bookLanguage.setText(language);
//            bookPublisher.setText(publisher);
//            bookPublished.setText(published);
//            bookISBN.setText(isbn);
//            bookDescription.setText(description);
        }


    private void setListeners() {
    }

}
