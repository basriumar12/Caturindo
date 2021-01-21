package com.caturindo.utils;

import com.caturindo.models.BaseResponse;
import com.caturindo.models.BookingDto;
import com.caturindo.models.BookingRequest;
import com.caturindo.models.CancelMeetingRequest;
import com.caturindo.models.MeetingDto;
import com.caturindo.models.MeetingRequest;
import com.caturindo.models.MeetingSubDto;
import com.caturindo.models.MeetingSubRequest;
import com.caturindo.models.RegisterDto;
import com.caturindo.models.RoomDto;
import com.caturindo.models.TaskDto;
import com.caturindo.models.TaskRequest;
import com.caturindo.models.TransportDto;
import com.caturindo.models.UpdateSubMeetingRequest;
import com.caturindo.models.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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
    Call<BaseResponse<List<TaskDto>>> getTaskById(@Query("id_task") String idTask);

  @GET("task/add_task")
    Call<BaseResponse<List<TaskDto>>> postTask(@Body TaskRequest taskRequest);


    @GET("meeting")
    Call<BaseResponse<List<MeetingDto>>> getMeeting(
            @Query("status_meeting") String status
    );
//    0 atau kosong untuk incoming atau belum di mulai
//1 untuk sedang atau telah selesai

    @GET("meeting")
    Call<BaseResponse<List<MeetingDto>>>
    getMeetingById(@Query("id_meeting") String idMeeting);

    @GET("meeting/sub_meeting")
    Call<BaseResponse<List<MeetingSubDto>>>
    getSubMeeting(@Query("status_meeting")

                          String statusMeeting);

    //    0 atau kosong untuk incoming atau belum di mulai
//1 untuk sedang atau telah selesai

    @GET("meeting/sub_meeting")
    Call<BaseResponse<List<MeetingSubDto>>>
    getSubMeetingById(@Query("id_meeting")

                          String statusMeeting);

    @GET("rooms")
    Call<BaseResponse<List<RoomDto>>> getRoom(
            @Query("time_start") String start,
            @Query("time_end") String end,
            @Query("date") String date

    );

    @GET("transport")
    Call<BaseResponse<List<TransportDto>>> getTransport(
            @Query("time_start") String start,
            @Query("time_end") String end,
            @Query("date") String date

    );

    @GET("rooms")
    Call<BaseResponse<List<RoomDto>>> getRoomById(
            @Query("id") String id

    );


    @GET("transport")
    Call<BaseResponse<List<RoomDto>>> getTransportById(
            @Query("id_transport") String id

    );

    @GET("users/search")
    Call<BaseResponse<List<UserDto>>> getUserSearch(
            @Query("name") String name

    );

    @POST("meeting/cancel_meeting")
    Call<BaseResponse> cancelMeeting(
            @Body CancelMeetingRequest cancelMeetingRequest

    );

    @PUT("meeting/update_status_meeting")
    Call<BaseResponse> updateStatusMeeting(
            @Body CancelMeetingRequest updateStatusMeetingRequest

    );

    @PUT("meeting/update_status_submeeting")
    Call<BaseResponse> updateStatusSubMeeting(
            @Body UpdateSubMeetingRequest updateStatusMeetingRequest

    );


    @POST("meeting/create")
    Call<BaseResponse<MeetingDto>> postMeeting(@Body MeetingRequest meetingRequest);

    @POST("meeting/create_sub_meeting")
    Call<BaseResponse<MeetingSubDto>> postMeetingSub(@Body MeetingSubRequest meetingRequest);

    @POST("booking/create")
    Call<BaseResponse<BookingDto>> postBooking(@Body BookingRequest bookingRequest);


}