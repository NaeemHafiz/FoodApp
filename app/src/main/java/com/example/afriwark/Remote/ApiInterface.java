package com.example.afriwark.Remote;

import com.example.afriwark.Models.Cart.AddToCart;
import com.example.afriwark.Models.CategoryClasses.Category;
import com.example.afriwark.Models.Payment.Payment;
import com.example.afriwark.Models.SearchClasses.SearchRestaurant;
import com.example.afriwark.Models.SubCategory.SubCategory;
import com.example.afriwark.Models.UserSignInSignUp.Login.UserLogin;
import com.example.afriwark.Models.UserSignInSignUp.Register.UserRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<UserRegister> signUp(@Field("user_name") String username,
                              @Field("email") String email, @Field("password") String password,
                              @Field("cpassword") String cpassword);

    @FormUrlEncoded
    @POST("user/login")
    Call<UserLogin> signIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("resturnt/search")
    Call<SearchRestaurant> searchRestaurant(@Field("latitude") double latitude, @Field("longitude") double longitude);

    @POST("resturnt/{resturant_id}/category")
    Call<Category> showCategoryData(@Path("resturant_id") int categoryId);

    @POST("resturnt/{category_id}/subCategory")
    Call<SubCategory> showSubCategory(@Path("category_id") int categoryId);

    @FormUrlEncoded
    @POST("cart/{user_id}/add")
    Call<AddToCart> addToCart(@Field("category_id") String category_id,
                              @Field("sub_category_id") String sub_category_id, @Field("qty") String qty,
                              @Field("total") String total, @Field("price") String price, @Path("user_id") String loginid);

    @GET("cart/getPaymentMethod")
    Call<Payment> getPayment();


}
