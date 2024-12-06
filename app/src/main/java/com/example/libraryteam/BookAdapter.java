package com.example.libraryteam;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
