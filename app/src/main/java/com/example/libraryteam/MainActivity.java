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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LoggedInUser loggedInUser;

    private BookAdapter bookAdapter;
    private FirebaseFirestore firestore;
    private List<Book> bookList;

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

        firestore = FirebaseFirestore.getInstance();
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
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (item.getItemId() == R.id.menu_home_page) {
                        return true;
                    } else if (item.getItemId() == R.id.menu_user_settings) {
                        startActivity(new Intent(getApplicationContext(), UpdateSettingsActivity.class));
                        return true;
                    }
                    return false;
                });

                popupMenu.show();
            }
        });

        bookAdapter = new BookAdapter(bookList, book -> {
            Intent intent = new Intent(MainActivity.this, BookView.class);
            intent.putExtra("Title", book.getBookTitle());
            intent.putExtra("Author", book.getAuthor());
            intent.putExtra("Description", book.getBookDescription());
            intent.putExtra("Year", book.getBookYear());
            intent.putExtra("Image", book.getBookImage());
            startActivity(intent);
        });
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookRecyclerView.setAdapter(bookAdapter);

        loadBooksFromFirebase();
    }

    private void loadBooksFromFirebase() {
        firestore.collection("books")
                .orderBy("bookID", Query.Direction.ASCENDING)
                .limit(5) // Limit to 5 random books (you can shuffle on the backend for randomness)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookList.clear();
                    queryDocumentSnapshots.forEach(document -> {
                        Book book = new Book(
                                document.getString("author"),
                                document.getString("bookDescription"),
                                document.getLong("id") != null ? document.getLong("id").intValue() : 0,
                                document.getString("bookImage"),
                                document.getString("bookTitle"),
                                document.getString("bookYear"),
                                document.getString("isbn"),
                                document.getString("language"),
                                document.getString("publisher")
                        );
                        bookList.add(book);
                    });
                    bookAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to load books: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String getShortenedText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        }
        return text;
    }

}