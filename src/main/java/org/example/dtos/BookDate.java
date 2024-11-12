package org.example.dtos;

public class BookDate extends Book{
    private String date;

    public BookDate(Book book, String date) {
        this.setId(book.getId());
        this.setTitle(book.getTitle());
        this.setPublicationTimestamp(book.getPublicationTimestamp());
        this.setPages(book.getPages());
        this.setSummary(book.getSummary());
        this.setAuthor(book.getAuthor());
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return super.toString() + ", date=" + date;
    }
}
