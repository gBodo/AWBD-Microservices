package com.example.review_service.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Integer rating; // Rating between 1 and 5

    @Column(name = "comment", columnDefinition = "TEXT", nullable = true)
    private String comment; // Optional comment

    @Column(name="user_id",nullable = false)
    private Long userID; // FK → users.id

    @Column(name="book_id",nullable = false)
    private Long bookID; // FK → books.id

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long user) {
        this.userID = userID;
    }
    public Long getBookID() {
        return bookID;
    }
    public void setBookID(Long book) {
        this.bookID = bookID;
    }

}
