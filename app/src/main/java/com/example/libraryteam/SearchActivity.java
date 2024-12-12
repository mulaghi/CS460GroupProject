package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search), (v, insets) -> {
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
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_items, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_search) {
                        // Handle Search
                        Toast.makeText(SearchActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });

                popupMenu.show();
            }
        });
        // Adds sample book data to the list.
        Book bookOne = new Book();
        bookOne.image = R.drawable.bookimage;
        bookOne.Title = "Everything You Need to Ace Computer Science and Coding in One Big Fat Notebook: The Complete Middle School Study Guide";
        bookOne.Author = "Workman Publishing";
        bookOne.ISBN = "1523502770";
        bookOne.Language = "English";
        bookOne.Published= "2020";
        bookOne.Publisher = "Workman Publishing Company";
        bookOne.Description= "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        bookOne.shortDescription = getShortenedText(bookOne.Description, 30);
        bookList.add(bookOne);

        Book bookTwo = new Book();
        bookTwo.image = R.drawable.bookimage;
        bookTwo.Title = "To Ace Math in One Big Fat Notebook: The Complete Middle School Study Guide (Big Fat Notebooks)";
        bookTwo.Author = "Workman Publishing";
        bookTwo.ISBN = "0761160965";
        bookTwo.Language = "English";
        bookTwo.Published= "2016";
        bookTwo.Publisher = "Workman Publishing Company";
        bookTwo.Description= "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        bookTwo.shortDescription = getShortenedText(bookTwo.Description, 30);
        bookList.add(bookTwo);

        Book bookThree = new Book();
        bookThree.image = R.drawable.bookimage;
        bookThree.Title = "Everything You Need to Ace World History in One Big Fat Notebook, 2nd Edition: The Complete Middle School Study Guide";
        bookThree.Author = "Workman Publishing";
        bookThree.ISBN = "1523502770";
        bookThree.Language = "English";
        bookThree.Published= "2020";
        bookThree.Publisher = "Workman Publishing Company";
        bookThree.Description= "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        bookThree.shortDescription = getShortenedText(bookThree.Description, 30);
        bookList.add(bookThree);

        Book bookFour = new Book();
        bookFour.image = R.drawable.bookimage;
        bookFour.Title = "Everything You Need to Ace Geometry in One Big Fat Notebook";
        bookFour.Author = "Workman Publishing";
        bookFour.ISBN = "978-1523504374";
        bookFour.Language = "English";
        bookFour.Published= "2020";
        bookFour.Publisher = "Workman Publishing Company";
        bookFour.Description= "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        bookFour.shortDescription = getShortenedText(bookFour.Description, 30);
        bookList.add(bookFour);

        Book bookFive = new Book();
        bookFive.image = R.drawable.bookimage;
        bookFive.Title = "Everything You Need to Ace Pre-Algebra and Algebra I in One Big Fat Notebook";
        bookFive.Author = "Workman Publishing";
        bookFive.ISBN = "978-1523504381";
        bookFive.Language = "English";
        bookFive.Published= "2020";
        bookFive.Publisher = "Workman Publishing Company";
        bookFive.Description= "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        bookFive.shortDescription = getShortenedText(bookFive.Description, 30);
        bookList.add(bookFive);

        BookAdapter bookAdapter = new BookAdapter(bookList, book -> {
            // Start the details activity with book information
            Intent intent = new Intent(SearchActivity.this, BookView.class);

            // Pass book details as extras
            intent.putExtra("Title", book.Title);
            intent.putExtra("Author", book.Author);
            intent.putExtra("Language", book.Language);
            intent.putExtra("Publisher", book.Publisher);
            intent.putExtra("Published", book.Published);
            intent.putExtra("ISBN", book.ISBN);
            intent.putExtra("Description", book.Description);
            intent.putExtra("Image", book.image);

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
