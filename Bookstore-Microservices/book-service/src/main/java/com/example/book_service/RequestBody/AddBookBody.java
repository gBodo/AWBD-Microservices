package com.example.book_service.RequestBody;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AddBookBody {
    @NotBlank
    @Size(min = 3 , max = 16)
    private String author;
    @NotBlank
    @Size(min = 8 , max = 100)
    private String cover_image_url;
    @NotNull
    @Positive
    private double price;
    @NotBlank
    private String title;
    @NotNull
    @Positive
    private Integer category_id;

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCover_image_url() {
        return cover_image_url;
    }
    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }


}
