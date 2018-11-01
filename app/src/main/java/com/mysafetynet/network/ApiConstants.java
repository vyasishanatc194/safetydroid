package com.mysafetynet.network;

public class ApiConstants {
    static final String PARENT_LOGIN_URL = "parent/login";
    public static final String PARENT_LOGOUT_URL = "parent/logout";
    public static final String PARENT_FORGOT_PASSWORD_URL = "parent/forgot_password";
    public static final String PARENT_REGISTER_URL = "parent/register";
    public static final String PARENT_CHILD_LIST_URL = "parent/view_child";
    public static final String PARENT_ORDER_LIST_URL = "parent/order_detail";
    public static final String PARENT_ADDD_CHILD_URL = "parent/child-register";
    public static final String PARENT_DETAIL_URL = "parent/detail";
    public static final String PARENT_EDIT_DETAIL_URL = "parent/register/edit";
    public static final String PARENT_CHANGE_PASSWORD_URL = "parent/change_password";
    public static final String PARENT_PLAN_LIST_URL = "parent/subscription-list";
    public static final String PARENT_UNSUBSCRIBE_URL = "parent/unsubscribe";
    public static final String PARENT_EXISTS_CHILD_URL = "parent/unique_child_user_name";
    public static final String PARENT_EXISTS_MOBILE_URL = "parent/unique_child_number";
    public static final String CHILD_LOGIN_URL = "child/login";
    public static final String CHILD_LOGOUT_URL = "child/logout";
    public static final String CHILD_FORGOT_PASSWORD_URL = "child/forgot_password";
    public static final String CHILD_UPDATE_PASSWORD_URL = "child/update_forgot_password";
    public static final String CHILD_DETAIL_URL = "child/childDetail";
    public static final String CHILD_CHANGE_PASSWORD_URL = "child/change_password";
    public static final String CHILD_REQUEST_URL = "child/view_batch_child";
    public static final String CHILD_EDIT_URL = "child/register/edit";
    public static final String CHILD_APPROVE_URL = "child/send_notification";
    public static final String SHOW_PAGE_URL = "get-page";
    public static final String USER_CHILD = "CU";
    public static final String USER_PARENT = "PU";
    public static final String DEVICE_TYPE = "android";

    public static class TAGS {
        public static final String Authorization = "Authorization";

        public static final String email="email";
        public static final String password="password";
        public static final String user_type="user_type";
        public static final String device_type="device_type";
        public static final String fire_base_token="fire_base_token";
        public static final String slug="slug";
        public static final String token="token";
        public static final String user_name="user_name";
        public static final String phone="phone";
        public static final String child_id="child_id";
        public static final String username="username";
        public static final String otp="otp";
        public static final String confirm_password="confirm_password";
        public static final String batch_id = "batch_id";
        public static final String first_name="first_name";
        public static final String last_name="last_name";
        public static final String age="age";
        public static final String gender="gender";
        public static final String dob="dob";
        public static final String parent_mob_no="parent_mob_no";
        public static final String school_name="school_name";
        public static final String school_district_no="school_district_no";
        public static final String state="state";
        public static final String plan_id="plan_id";
        public static final String amount="amount";
        public static final String token_id="token_id";
        public static final String status = "status";
        public static final String message = "message";
        public static final String subscription_id="subscription_id";
        public static final String stripe_id="stripe_id";
        public static final String target_screen="target_screen";
        public static final String title="title";
    }
}
