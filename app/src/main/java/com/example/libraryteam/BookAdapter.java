package com.example.libraryteam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindBook(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public BookAdapter(List<Book> bookList){
        this.bookList = bookList;
    }
    private List<Book> bookList;


    static class BookViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layoutBooks;
        View viewBackground;

        RoundedImageView imageBook;
        TextView bookTitle, bookAuthor, bookDescription, bookLanguage, bookPublished, bookPublisher, bookISBN;

        public BookViewHolder(@NonNull View itemView){
            super(itemView);
            layoutBooks = itemView.findViewById(R.id.layoutBooks);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            imageBook = itemView.findViewById(R.id.imageBookCover);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookLanguage = itemView.findViewById(R.id.bookLanguage);
            bookPublished = itemView.findViewById(R.id.bookPublished);
            bookPublisher = itemView.findViewById(R.id.bookPublisher);
            bookISBN = itemView.findViewById(R.id.bookISBN);
            bookDescription = itemView.findViewById(R.id.bookDescription);
        }

        //Title, Author, Language, Published, Publisher, ISBN
        void  bindBook(final Book book){
            imageBook.setImageResource(book.image);
            bookTitle.setText(book.Title);
            bookAuthor.setText(book.Author);
            bookLanguage.setText(book.Language);
            bookPublished.setText(book.Published);
            bookPublisher.setText(book.Publisher);
            bookDescription.setText(book.Description);
        }

    }
}
