package com.example.review_service.Service.dto;

public class UserDTO {
    public class BookDTO {

        private Integer id;
        private String username;

        // Getters, setters
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }

    }

}
