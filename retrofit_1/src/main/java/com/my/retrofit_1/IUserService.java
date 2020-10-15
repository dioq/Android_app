package com.my.retrofit_1;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 创建业务请求接口
 */
public interface IUserService {
    /**
     * GET请求
     */
    @GET("/user_info")
    Call<User> getUser(@Query("name") String name);

    /**
     * POST请求
     */
    @FormUrlEncoded
    @POST("/login")
    Call<User> postUser(@Field("name") String name, @Field("password") String password);
}