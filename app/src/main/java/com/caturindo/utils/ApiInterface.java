package com.caturindo.utils;

import com.caturindo.models.BaseResponse;
import com.caturindo.models.MeetingDto;
import com.caturindo.models.RoomDto;
import com.caturindo.models.UserModel;
import com.caturindo.models.RegisterDto;
import com.caturindo.models.TaskDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("users/login")
    Call<BaseResponse<RegisterDto>> postLogin(@Field("username") String email,
                                              @Field("password") String password);

    @FormUrlEncoded
    @POST("users/register")
    Call<BaseResponse<RegisterDto>> postRegister(@Field("email") String email,
                                                       @Field("password") String password,
                                                       @Field("username") String username,
                                                       @Field("phone") String phone,
                                                       @Field("role") int role);

    @GET("task")
    Call<BaseResponse<List<TaskDto>>> getTask();

    @GET("task")
    Call<BaseResponse<List<TaskDto>>> getTaskById(@Field("id_task")String idTask);


    @GET("meeting")
    Call<BaseResponse<List<MeetingDto>>> getMeeting();

    @GET("meeting")
    Call<BaseResponse<List<MeetingDto>>> getMeetingById(@Field("d_meeting")String idMeeting);

    @GET("room")
    Call<BaseResponse<List<RoomDto>>> getRoom(
            @Field("status_booking")String status

    );

    @GET("room")
    Call<BaseResponse<List<RoomDto>>> getRoomById(
            @Field("id")String id

    );
}