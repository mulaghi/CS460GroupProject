package com.example.libraryteam;

public class Book {
    private String author;
    private String bookDescription;
    private int bookID;
    private String bookImage;
    private String bookTitle;
    private String bookYear;

    // No-argument constructor for Firebase
    public Book() {
    }

    // Constructor with parameters (optional)
    public Book(String author, String bookDescription, int bookID, String bookImage, String bookTitle, String bookYear) {
        this.author = author;
        this.bookDescription = bookDescription;
        this.bookID = bookID;
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookYear = bookYear;
    }

    // Getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", bookID=" + bookID +
                ", bookImage='" + bookImage + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookYear=" + bookYear +
                '}';
    }
}
