package com.mysafetynet.network;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @POST(ApiConstants.PARENT_LOGIN_URL)
    Call<JsonElement> doParentLogin(
            @Query(ApiConstants.TAGS.email) String email,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.user_type) String user_type,
            @Query(ApiConstants.TAGS.device_type) String device_type,
            @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token
    );

    @GET(ApiConstants.SHOW_PAGE_URL)
    Call<JsonElement> doShowPage(
            @Query(ApiConstants.TAGS.slug) String slug
    );

    @POST(ApiConstants.PARENT_EXISTS_CHILD_URL)
    Call<JsonElement> doExistsChild(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.token) String token,
            @Query(ApiConstants.TAGS.user_name) String user_name
    );

    @POST(ApiConstants.PARENT_EXISTS_MOBILE_URL)
    Call<JsonElement> doExistsMobile(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.token) String token,
            @Query(ApiConstants.TAGS.phone) String phone
    );

    @POST(ApiConstants.CHILD_LOGIN_URL)
    Call<JsonElement> doChildLogin(
            @Query(ApiConstants.TAGS.username) String username,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.user_type) String user_type,
            @Query(ApiConstants.TAGS.device_type) String device_type,
            @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token
    );

    @GET(ApiConstants.CHILD_DETAIL_URL)
    Call<JsonElement> doChildDetail(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.child_id) String child_id
    );

    @POST(ApiConstants.CHILD_REQUEST_URL)
    Call<JsonElement> doChildRequest(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.batch_id) String batch_id,
            @Query(ApiConstants.TAGS.device_type) String device_type
    );

    @POST(ApiConstants.PARENT_FORGOT_PASSWORD_URL)
    Call<JsonElement> doParentForgotPassword(
            @Query(ApiConstants.TAGS.email) String email,
            @Query(ApiConstants.TAGS.user_type) String user_type
    );

    @POST(ApiConstants.CHILD_FORGOT_PASSWORD_URL)
    Call<JsonElement> doChildForgotPassword(
            @Query(ApiConstants.TAGS.phone) String phone
    );

    @POST(ApiConstants.CHILD_UPDATE_PASSWORD_URL)
    Call<JsonElement> doChildUpdatePassword(
            @Query(ApiConstants.TAGS.username) String username,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.otp) String otp,
            @Query(ApiConstants.TAGS.confirm_password) String confirm_password
    );

    @GET(ApiConstants.CHILD_LOGOUT_URL)
    Call<JsonElement> doChildLogout(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.user_type) String user_type
    );

    @GET(ApiConstants.PARENT_LOGOUT_URL)
    Call<JsonElement> doParentLogout(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.user_type) String user_type
    );

    @POST(ApiConstants.CHILD_EDIT_URL)
    @Multipart
    Call<JsonElement> doChildEdit(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.first_name) String first_name,
            @Query(ApiConstants.TAGS.last_name) String last_name,
            @Query(ApiConstants.TAGS.age) String age,
            @Query(ApiConstants.TAGS.gender) String gender,
            @Query(ApiConstants.TAGS.dob) String dob,
            @Query(ApiConstants.TAGS.phone) String phone,
            @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token,
            @Query(ApiConstants.TAGS.child_id) String child_id,
            @Part MultipartBody.Part image
    );

    @GET(ApiConstants.PARENT_CHILD_LIST_URL)
    Call<JsonElement> doChildList(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken
    );

    @POST(ApiConstants.PARENT_CHANGE_PASSWORD_URL)
    Call<JsonElement> doParentChangePassword(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.email) String email,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.confirm_password) String confirm_password
    );

    @GET(ApiConstants.PARENT_DETAIL_URL)
    Call<JsonElement> doParentDetail(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken
    );

    @POST(ApiConstants.PARENT_EDIT_DETAIL_URL)
    Call<JsonElement> doParentEdit(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.email) String email,
            @Query(ApiConstants.TAGS.first_name) String first_name,
            @Query(ApiConstants.TAGS.last_name) String last_name,
            @Query(ApiConstants.TAGS.phone) String phone
    );

    @GET(ApiConstants.PARENT_ORDER_LIST_URL)
    Call<JsonElement> doOrderList(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken
    );

    @POST(ApiConstants.PARENT_REGISTER_URL)
    Call<JsonElement> doParentSignUp(
            @Query(ApiConstants.TAGS.first_name) String first_name,
            @Query(ApiConstants.TAGS.last_name) String last_name,
            @Query(ApiConstants.TAGS.parent_mob_no) String parent_mob_no,
            @Query(ApiConstants.TAGS.email) String email,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.user_type) String user_type,
            @Query(ApiConstants.TAGS.device_type) String device_typeail,
            @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token
    );

    @GET(ApiConstants.PARENT_PLAN_LIST_URL)
    Call<JsonElement> doPlanList(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken
    );

    @POST(ApiConstants.PARENT_ADDD_CHILD_URL)
    @Multipart
    Call<JsonElement> doChildAdd(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.username) String username,
            @Query(ApiConstants.TAGS.first_name) String first_name,
            @Query(ApiConstants.TAGS.last_name) String last_name,
            @Query(ApiConstants.TAGS.age) String age,
            @Query(ApiConstants.TAGS.gender) String gender,
            @Query(ApiConstants.TAGS.dob) String dob,
            @Query(ApiConstants.TAGS.phone) String phone,
            @Query(ApiConstants.TAGS.school_name) String school_name,
            @Query(ApiConstants.TAGS.school_district_no) String school_district_no,
            @Query(ApiConstants.TAGS.state) String state,
            @Query(ApiConstants.TAGS.user_type) String user_type,
            @Query(ApiConstants.TAGS.plan_id) String plan_id,
            @Query(ApiConstants.TAGS.amount) String amount,
            @Query(ApiConstants.TAGS.token_id) String token_id,
            @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token,
            @Part MultipartBody.Part image
    );


    @POST(ApiConstants.PARENT_UNSUBSCRIBE_URL)
    Call<JsonElement> doUnsubscribe(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.subscription_id) String subscription_id,
            @Query(ApiConstants.TAGS.stripe_id) String stripe_id
    );

    @POST(ApiConstants.CHILD_CHANGE_PASSWORD_URL)
    Call<JsonElement> doChildChangePassword(
            @Header(ApiConstants.TAGS.Authorization) String bearertoken,
            @Query(ApiConstants.TAGS.password) String password,
            @Query(ApiConstants.TAGS.confirm_password) String confirm_password,
            @Query(ApiConstants.TAGS.username) String username
    );
}
