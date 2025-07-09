package com.example.review_service.Service;

import com.example.review_service.Model.Book;
import com.example.review_service.Model.Review;
import com.example.review_service.Model.User;
import com.example.review_service.Repository.BookRepository;
import com.example.review_service.Repository.ReviewRepository;
import com.example.review_service.Repository.UserRepository;
import com.example.review_service.RequestBody.AddReviewBody;
import com.example.review_service.RequestBody.ViewReviewResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,UserRepository userRepository,BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository=userRepository;
        this.bookRepository=bookRepository;
    }

    public List<ViewReviewResponseBody> getReviewsByBookId(Integer bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);

        return reviews.stream()
                .map(review -> {
                    ViewReviewResponseBody viewReview = new ViewReviewResponseBody();
                    viewReview.setId(review.getId());
                    viewReview.setRating(review.getRating());
                    viewReview.setComment(review.getComment());
                    return viewReview;
                })
                .collect(Collectors.toList());
    }

    public String addReview(Integer id, AddReviewBody reviewBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;

        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();
        review.setRating(reviewBody.getRating());
        review.setComment(reviewBody.getComment());
        review.setUser(user);
        review.setBook(book);

        Review savedReview = reviewRepository.save(review);

        ViewReviewResponseBody responseBody = new ViewReviewResponseBody();
        responseBody.setId(savedReview.getId());
        responseBody.setRating(savedReview.getRating());
        responseBody.setComment(savedReview.getComment());

        return "Review sent!";
    }
}
