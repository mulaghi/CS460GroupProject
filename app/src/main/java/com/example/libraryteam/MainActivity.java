package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView bookRecyclerView = findViewById(R.id.bookRecyclerView);
        List<Book> bookList = new ArrayList<>();

        ImageView menuIcon = findViewById(R.id.menu_icon);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_items, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_search) {
                        // Handles "Search"
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (item.getItemId() == R.id.menu_user_settings) {
                        // Handles "User Settings"
                        Intent intent = new Intent(getApplicationContext(), UpdateSettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                });

                popupMenu.show();
            }
        });

        // Adds sample book data to the list.
        Book bookFive = new Book();
        bookFive.setBookTitle("Everything You Need to Ace Pre-Algebra and Algebra I in One Big Fat Notebook");
        bookFive.setAuthor("Workman Publishing");
        bookFive.setBookDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        bookFive.setBookYear("2020"); // Year as an integer
        bookFive.setBookImage(""); // If no image is available, set it as an empty string or a default value
        bookFive.setBookID(5); // Assign a unique ID for this book if required
        // Add shortened description if needed
        String shortDescription = getShortenedText(bookFive.getBookDescription(), 30);
        bookList.add(bookFive);

        BookAdapter bookAdapter = new BookAdapter(bookList, book -> {
            // Start the details activity with book information
            Intent intent = new Intent(MainActivity.this, BookView.class);

            // Pass book details as extras using getters from the Book class
            intent.putExtra("Title", book.getBookTitle());
            intent.putExtra("Author", book.getAuthor());
            intent.putExtra("Description", book.getBookDescription());
            intent.putExtra("Year", book.getBookYear());
            intent.putExtra("Image", book.getBookImage()); // Assuming it's a Base64 encoded string

            startActivity(intent); // Start the new activity
        });
        bookRecyclerView.setAdapter(bookAdapter);


    }

    private String getShortenedText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        }
        return text;
    }

}