package com.mysafetynet.Model;

import java.util.ArrayList;

public class PlanListModel {
    private String message;

    private Result result;

    private String status;

    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", result = " + result + ", status = " + status + ", success = " + success + "]";
    }

    public class Result {
        private ArrayList<Data> data;

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ClassPojo [data = " + data + "]";
        }
    }

    public class Data {
        private String id;

        private String title;

        private String updated_at;

        private String status;

        private String description;

        private String stripe_product_id;

        private String deleted_at;

        private String created_at;

        private String type;

        private String show_amount;

        private String actual_amount;

        private String currency;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStripe_product_id() {
            return stripe_product_id;
        }

        public void setStripe_product_id(String stripe_product_id) {
            this.stripe_product_id = stripe_product_id;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShow_amount() {
            return show_amount;
        }

        public void setShow_amount(String show_amount) {
            this.show_amount = show_amount;
        }

        public String getActual_amount() {
            return actual_amount;
        }

        public void setActual_amount(String actual_amount) {
            this.actual_amount = actual_amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        @Override
        public String toString() {
            return "ClassPojo [id = " + id + ", title = " + title + ", updated_at = " + updated_at + ", status = " + status + ", description = " + description + ", stripe_product_id = " + stripe_product_id + ", deleted_at = " + deleted_at + ", created_at = " + created_at + ", type = " + type + ", show_amount = " + show_amount + ", actual_amount = " + actual_amount + ", currency = " + currency + "]";
        }
    }
}
