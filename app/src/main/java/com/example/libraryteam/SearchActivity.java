package com.example.libraryteam;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FirebaseFirestore database;
    private EditText searchInput;
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
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

        database = FirebaseFirestore.getInstance();

        // Initialize Views
        searchInput = findViewById(R.id.searchInput);
        bookRecyclerView = findViewById(R.id.bookRecyclerView);

        // Set up RecyclerView
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, book -> {
            // Handle book click events
            Intent intent = new Intent(SearchActivity.this, BookView.class);
            intent.putExtra("Title", book.getBookTitle());
            intent.putExtra("Author", book.getAuthor());
            intent.putExtra("Description", book.getBookDescription());
            intent.putExtra("Year", book.getBookYear());
            intent.putExtra("Image", book.getBookImage());
            startActivity(intent);
        });

        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookRecyclerView.setAdapter(bookAdapter);

        // Handles searching function
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchBooks(s.toString());
                } else {
                    bookList.clear();
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Handles top left menu
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_items, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_search) {
                        // Handle Search
                        // Toast.makeText(SearchActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (item.getItemId() == R.id.menu_home_page) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;
                    } else if (item.getItemId() == R.id.menu_user_settings) {
                        // Handle Search
                        // Toast.makeText(SearchActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });

                popupMenu.show();
            }
        });
    }
    private String getShortenedText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        }
        return text;
    }

    private void searchBooks(String query) {
        if (query.length() < 3) {
            // Toast.makeText(SearchActivity.this, "Please enter at least 3 characters", Toast.LENGTH_SHORT).show();
            return; // Exit early if the query is too short
        }

        String queryLowerCase = query.toLowerCase();

        database.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Toast.makeText(SearchActivity.this, "SEARCH SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        bookList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Book book = document.toObject(Book.class);
                            // Check if the book title contains the query, case-insensitively
                            if (book.getBookTitle().toLowerCase().contains(queryLowerCase)) {
                                bookList.add(book);
                            }
                        }
                        bookAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SearchActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}



