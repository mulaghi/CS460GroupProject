package com.example.libraryteam;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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


        Book bookOne = new Book();
        bookOne.Title = "Everything You Need to Ace Computer Science and Coding in One Big Fat Notebook: The Complete Middle School Study Guide";
        bookOne.Author = "Workman Publishing";
        bookOne.ISBN = "1523502770";
        bookOne.Language = "English";
        bookOne.Published= "2020";
        bookOne.Publisher = "Workman Publishing Company";

        Book bookTwo = new Book();
        bookTwo.Title = "To Ace Math in One Big Fat Notebook: The Complete Middle School Study Guide (Big Fat Notebooks)";
        bookTwo.Author = "Workman Publishing";
        bookTwo.ISBN = "0761160965";
        bookTwo.Language = "English";
        bookTwo.Published= "2016";
        bookTwo.Publisher = "Workman Publishing Company";

        Book bookThree = new Book();
        bookThree.Title = "Everything You Need to Ace World History in One Big Fat Notebook, 2nd Edition: The Complete Middle School Study Guide";
        bookThree.Author = "Workman Publishing";
        bookThree.ISBN = "1523502770";
        bookThree.Language = "English";
        bookThree.Published= "2020";
        bookThree.Publisher = "Workman Publishing Company";

        Book bookFour = new Book();
        bookFour.Title = "Everything You Need to Ace Geometry in One Big Fat Notebook";
        bookFour.Author = "Workman Publishing";
        bookFour.ISBN = "978-1523504374";
        bookFour.Language = "English";
        bookFour.Published= "2020";
        bookFour.Publisher = "Workman Publishing Company";

        Book bookFive = new Book();
        bookFive.Title = "Everything You Need to Ace Pre-Algebra and Algebra I in One Big Fat Notebook";
        bookFive.Author = "Workman Publishing";
        bookFive.ISBN = "978-1523504381";
        bookFive.Language = "English";
        bookFive.Published= "2020";
        bookFive.Publisher = "Workman Publishing Company";

    }
}