package com.mysafetynet.Model;

import java.util.ArrayList;

public class SectionModel {
    private String sectionLabel;
    private ArrayList<OrderModel.DataModel> itemArrayList;

    public SectionModel(String sectionLabel, ArrayList<OrderModel.DataModel> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<OrderModel.DataModel> getItemArrayList() {
        return itemArrayList;
    }

    public class DataModel {
        private String subscription_id, gender, dob;

        private String thumb_image;

        private String order_id;

        private String order_no;

        private String plan_id;

        private String id;

        private String amount;

        private String first_name;

        private String stripe_id;

        private String username;

        private String token;

        private String child_id;

        private String created_at;

        private String user_id;

        private String order_created;

        private String user_image;

        private String parent_id;

        private String trans_status;

        private String status;

        private String next_pay;

        private String deleted_at;

        private String order_comment;

        private String child_age;

        private String transaction_id;

        private String updated_at;

        private String auth_response;

        private String last_name;

        private String expiry_date;

        private String start_date;

        public String getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(String subscription_id) {
            this.subscription_id = subscription_id;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getStripe_id() {
            return stripe_id;
        }

        public void setStripe_id(String stripe_id) {
            this.stripe_id = stripe_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getChild_id() {
            return child_id;
        }

        public void setChild_id(String child_id) {
            this.child_id = child_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrder_created() {
            return order_created;
        }

        public void setOrder_created(String order_created) {
            this.order_created = order_created;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTrans_status() {
            return trans_status;
        }

        public void setTrans_status(String trans_status) {
            this.trans_status = trans_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNext_pay() {
            return next_pay;
        }

        public void setNext_pay(String next_pay) {
            this.next_pay = next_pay;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getOrder_comment() {
            return order_comment;
        }

        public void setOrder_comment(String order_comment) {
            this.order_comment = order_comment;
        }

        public String getChild_age() {
            return child_age;
        }

        public void setChild_age(String child_age) {
            this.child_age = child_age;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getAuth_response() {
            return auth_response;
        }

        public void setAuth_response(String auth_response) {
            this.auth_response = auth_response;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getExpiry_date() {
            return expiry_date;
        }

        public void setExpiry_date(String expiry_date) {
            this.expiry_date = expiry_date;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        @Override
        public String toString() {
            return "ClassPojo [subscription_id = " + subscription_id + ", thumb_image = " + thumb_image + ", order_id = " + order_id + ", order_no = " + order_no + ", plan_id = " + plan_id + ", id = " + id + ", amount = " + amount + ", first_name = " + first_name + ", stripe_id = " + stripe_id + ", username = " + username + ", token = " + token + ", child_id = " + child_id + ", created_at = " + created_at + ", user_id = " + user_id + ", order_created = " + order_created + ", user_image = " + user_image + ", parent_id = " + parent_id + ", trans_status = " + trans_status + ", status = " + status + ", next_pay = " + next_pay + ", deleted_at = " + deleted_at + ", order_comment = " + order_comment + ", child_age = " + child_age + ", transaction_id = " + transaction_id + ", updated_at = " + updated_at + ", auth_response = " + auth_response + ", last_name = " + last_name + ", expiry_date = " + expiry_date + ", start_date = " + start_date + "]";
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }
    }
}
