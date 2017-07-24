package com.binaryic.customerapp.orbymart.Model;

/**
 * Created by HP on 30-Mar-17.
 */

public class RatingModel {
   String id,customer_id,customer_name,stars,title,review_message,created_at;

    public RatingModel(String id, String customer_id, String customer_name, String stars, String title, String review_message, String created_at) {
        this.id = id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.stars = stars;
        this.title = title;
        this.review_message = review_message;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview_message() {
        return review_message;
    }

    public void setReview_message(String review_message) {
        this.review_message = review_message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
