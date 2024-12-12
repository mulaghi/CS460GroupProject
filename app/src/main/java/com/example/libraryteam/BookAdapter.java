package com.example.libraryteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {


    /**
     * Called when RecyclerView needs a new {@link BookViewHolder} to represent an item.
     *
     * @param parent   The {@link ViewGroup} into which the new {@link View} will be added.
     * @param viewType The view type of the new {@link View}.
     * @return A new {@link BookViewHolder} instance.
     */

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_preview_container, parent, false));
    }

    /**
     * Binds the data from a {@link Book} object to the corresponding {@link BookViewHolder}.
     *
     * @param holder   The {@link BookViewHolder} to bind data to.
     * @param position The position of the {@link Book} in the {@link List}.
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindBook(bookList.get(position));
    }
    /**
     * Returns the total number of items in the {@link List}.
     *
     * @return The number of {@link Book} objects in the list.
     */
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private String getShortenedText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        }
        return text;
    }

    /**
     * Constructor for {@code BookAdapter}.
     *
     * @param bookList A {@link List} of {@link Book} objects to be displayed in the RecyclerView.
     */
    public BookAdapter(List<Book> bookList, onBookClickListener onBookClickListener){
        this.bookList = bookList;
        BookAdapter.onBookClickListener = onBookClickListener;
    }

    public interface onBookClickListener {
        void onBookClick(Book book);
    }
    private List<Book> bookList;
    private static onBookClickListener onBookClickListener;

    static class BookViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layoutBooks;
        View viewBackground;

        RoundedImageView imageBook;
        TextView bookTitle, bookAuthor, bookDescription, bookLanguage, bookPublished, bookPublisher, bookISBN; //bookShortDescription;

        /**
         * Constructor for {@code BookViewHolder}.
         *
         * @param itemView The {@link View} representing an individual book item.
         */
        public BookViewHolder(@NonNull View itemView){
            super(itemView);
            layoutBooks = itemView.findViewById(R.id.bookContainerLayout);
            imageBook = itemView.findViewById(R.id.bookCover);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookLanguage = itemView.findViewById(R.id.bookLanguage);
            bookPublisher = itemView.findViewById(R.id.bookPublisher);
            bookPublished = itemView.findViewById(R.id.bookPublished);
            bookISBN = itemView.findViewById(R.id.bookISBN);
            bookDescription = itemView.findViewById(R.id.bookShortDescription);
//            bookShortDescription = itemView.findViewById(R.id.bookShortDescription);
        }

        /**
         * Binds the data from a {@link Book} object to the corresponding UI elements in the layout.
         *
         * @param book The {@link Book} object containing the data to be displayed.
         */
        void bindBook(final Book book) {
            if (book != null) {
                // Handle Base64 encoded image
                if (book.getBookImage() != null && !book.getBookImage().isEmpty()) {
                    try {
                        byte[] decodedString = Base64.decode(book.getBookImage(), Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageBook.setImageBitmap(decodedBitmap);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        imageBook.setImageResource(R.drawable.placeholder_image); // Fallback image in case of decode error
                    }
                } else {
                    imageBook.setImageResource(R.drawable.placeholder_image); // Default placeholder
                }

                // Set other book details
                bookTitle.setText(book.getBookTitle());
                bookAuthor.setText(book.getAuthor());
                bookLanguage.setText(R.string.language_not_specified); // Default text for Language
                bookPublished.setText(String.valueOf(book.getBookYear())); // Convert year to String
                bookPublisher.setText(R.string.publisher_not_specified); // Default text for Publisher
                bookDescription.setText(book.getBookDescription());
                bookISBN.setText(R.string.isbn_not_specified); // Default text for ISBN
            }
        }

    }


}
