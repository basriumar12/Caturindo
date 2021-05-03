package com.caturindo.utils;

import com.caturindo.activities.grup.model.ResponseGroupDto;
import com.caturindo.activities.meeting.create.model.UploadDto;
import com.caturindo.activities.meeting.model.AddMeetingCommentRequest;
import com.caturindo.activities.meeting.model.AddMemberMeetingDto;
import com.caturindo.activities.meeting.model.AddSubMeetingCommentRequest;
import com.caturindo.activities.meeting.model.MeetingCommentDto;
import com.caturindo.activities.meeting.model.MeetingNewRequest;
import com.caturindo.activities.notif.model.NotifDto;
import com.caturindo.activities.reset_pass.model.ResetPassRequest;
import com.caturindo.activities.task.add_team.model.AddMemberTaskDto;
import com.caturindo.activities.task.detail.model.AddCommentDto;
import com.caturindo.activities.task.detail.model.AddCommentRequest;
import com.caturindo.activities.task.detail.model.CommentDto;
import com.caturindo.activities.task.model.TaskNewRequest;
import com.caturindo.activities.team.model.AddTeamRequest;
import com.caturindo.activities.team.model.EditUserRequest;
import com.caturindo.activities.team.model.TeamMemberDto;
import com.caturindo.activities.team.model.UpdateUploadUserDto;
import com.caturindo.models.BaseResponse;
import com.caturindo.models.BaseResponseOther;
import com.caturindo.models.BookingDto;
import com.caturindo.models.BookingRequest;
import com.caturindo.models.CancelMeetingRequest;
import com.caturindo.models.MeetingDto;
import com.caturindo.models.MeetingDtoNew;
import com.caturindo.models.MeetingRequest;
import com.caturindo.models.MeetingSubDtoNew;
import com.caturindo.models.MeetingSubRequest;
import com.caturindo.models.RegisterDto;
import com.caturindo.models.RoomDto;
import com.caturindo.models.TaskDto;
import com.caturindo.models.TaskRequest;
import com.caturindo.models.TransportDto;
import com.caturindo.models.UpdateSubMeetingRequest;
import com.caturindo.models.UpdateTokenRequest;
import com.caturindo.models.UserDto;
import com.caturindo.models.UserDtoNew;
import com.caturindo.models.VersionDto;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("users/login")
    Call<BaseResponse<RegisterDto>> postLogin(@Field("username") String email,
                                              @Field("password") String password,
                                              @Field("token_firebase") String fcm
    );

    @FormUrlEncoded
    @PUT("users/logout")
    Call<BaseResponseOther> postLogout(@Field("id_user") String id
    );

    @FormUrlEncoded
    @POST("users/register")
    Call<BaseResponse<RegisterDto>> postRegister(@Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("username") String username,
                                                 @Field("phone") String phone,
                                                 @Field("role") int role);
 

    @GET("task")
    Call<BaseResponse<List<TaskDto>>> getTask();

    @POST("task")
    Call<BaseResponse<List<TaskDto>>> getAllTask(@Body TaskNewRequest taskNewRequest);


    @GET("task")
    Call<BaseResponse<List<TaskDto>>> getTaskById(@Query("id_task") String idTask);


    @GET("meeting")
    Call<BaseResponse<List<MeetingDtoNew>>> getMeeting(
            @Query("status_meeting") String status
    );

    @POST("meeting")
    Call<BaseResponse<List<MeetingDtoNew>>> getMeetingAll(
            @Body MeetingNewRequest meetingNewRequest
    );
//    0 atau kosong untuk incoming atau belum di mulai
//1 untuk sedang atau telah selesai

    @GET("meeting")
    Call<BaseResponse<List<MeetingDtoNew>>>
    getMeetingById(@Query("id_meeting") String idMeeting);

    @GET("submeeting")
    Call<BaseResponse<List<MeetingSubDtoNew>>>
    getSubMeeting(
            @Query("status_meeting") String statusMeeting,
            @Query("id_user") String idUser);

    //    0 atau kosong untuk incoming atau belum di mulai
//1 untuk sedang atau telah selesai

    @GET("meeting/sub_meeting")
    Call<BaseResponse<List<MeetingSubDtoNew>>>
    getSubMeetingById(@Query("id_meeting")

                              String statusMeeting);

    @GET("rooms")
    Call<BaseResponse<List<RoomDto>>> getRoom(
            @Query("time_start") String start,
            @Query("time_end") String end,
            @Query("date") String date

    );

    @GET("rooms")
    Call<BaseResponse<List<RoomDto>>> getRoomAll(

    );

    @GET("transport")
    Call<BaseResponse<List<TransportDto>>> getTransport(
            @Query("time_start") String start,
            @Query("time_end") String end,
            @Query("date") String date

    );

    @GET("transport")
    Call<BaseResponse<List<TransportDto>>> getAllTransport(

    );

    @GET("rooms")
    Call<BaseResponse<List<RoomDto>>> getRoomById(
            @Query("id") String id

    );


    @GET("transport")
    Call<BaseResponse<List<TransportDto>>> getTransportById(
            @Query("id_transport") String id

    );

    @GET("users/search")
    Call<BaseResponse<List<UserDto>>> getUserSearch(
            @Query("name") String name

    );

    @GET("users")
    Call<BaseResponse<UserDtoNew>> getUserDetail(
            @Query("id_user") String idUser

    );

    @GET("users/search")
    Call<BaseResponse<List<UserDto>>> getAllDataUser(
    );

    @GET("team/search_user")
    Call<BaseResponse<List<UserDto>>> getAllDataUsers(
    );


    @PUT("users/edit_user")
    Call<BaseResponseOther> editUser(
            @Body EditUserRequest editUserRequest);


    @Multipart
    @POST("users/edit_image_profile")
    Call<BaseResponse<UpdateUploadUserDto>> uploadImageProfile(
            @Part("id_user") RequestBody idUser,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("users/edit_background_profile")
    Call<BaseResponse<UpdateUploadUserDto>> uploadBackgroundProfile(
            @Part("id_user") RequestBody idUser,
            @Part MultipartBody.Part file);


    @POST("meeting/cancel_meeting")
    Call<BaseResponseOther> cancelMeeting(
            @Body CancelMeetingRequest cancelMeetingRequest

    );

    @PUT("meeting/update_status_meeting")
    Call<BaseResponseOther> updateStatusMeeting(
            @Body CancelMeetingRequest updateStatusMeetingRequest

    );

    @PUT("submeeting/update_status_submeeting")
    Call<BaseResponseOther> updateStatusSubMeeting(
            @Body UpdateSubMeetingRequest updateStatusMeetingRequest

    );

    @POST("submeeting/cancel")
    Call<BaseResponseOther> cancelStatusSubMeeting(
            @Body UpdateSubMeetingRequest updateStatusMeetingRequest

    );

    @PUT("users/update_token_firebase")
    Call<BaseResponseOther> updateTokenUser(
            @Body UpdateTokenRequest updateTokenRequest

    );


    @POST("meeting/create")
    Call<BaseResponse<MeetingDto>> postMeeting(@Body MeetingRequest meetingRequest);

    @POST("submeeting/create_sub_meeting")
    Call<BaseResponse<MeetingSubDtoNew>> postMeetingSub(@Body MeetingSubRequest meetingRequest);

    @POST("booking/create")
    Call<BaseResponse<ArrayList<BookingDto>>> postBooking(@Body BookingRequest bookingRequest);


    @POST("users/reset_password")
    Call<BaseResponse<List<String>>> postResetPass(@Body ResetPassRequest resetPassRequest);

    @POST("task/add_task")
    Call<BaseResponse<TaskDto>> postTask(@Body TaskRequest taskRequest);


    @POST("task/add_comment")
    Call<BaseResponse<AddCommentDto>> postComment(@Body AddCommentRequest taskRequest);

    @POST("team/add")
    Call<BaseResponseOther> postAddTeam(@Body AddTeamRequest taskRequest);

    @POST("team/hapus_member")
    Call<BaseResponseOther> postDeleteTeam(@Body AddTeamRequest taskRequest);

    @GET("team")
    Call<BaseResponse<TeamMemberDto>> getTeamMember(@Query("id_user") String idUser);

    @GET("team")
    Call<BaseResponse<TeamMemberDto>> getTeamMemberWithGrup(
            @Query("id_user") String idUser,
            @Query("id_group") String idGrup

    );

    @GET("notification/app_version")
    Call<BaseResponseOther> getVersion(@Query("version") String version);


    @POST("submeeting/add_comment_sub_meeting")
    Call<BaseResponseOther> postSubMeetingComment(@Body AddSubMeetingCommentRequest taskRequest);

    @POST("meeting/add_comment")
    Call<BaseResponse<MeetingCommentDto>> postMeetingComment(@Body AddMeetingCommentRequest taskRequest);


    @GET("task/comment")
    Call<BaseResponse<List<CommentDto>>> getComment(@Query("id_task") String idTask);

    @GET("meeting/comment")
    Call<BaseResponse<List<CommentDto>>> getMeetingComment(@Query("id_meeting") String idTask);

    @GET("submeeting/comment_sub_meeting")
    Call<BaseResponse<List<CommentDto>>> getSubMeetingComment(@Query("id_sub_meeting") String idTask);


    @GET("notification")
    Call<BaseResponse<List<NotifDto>>> getNotif(@Query("id_user") String idUser);


    @FormUrlEncoded
    @POST("task/add_member")
    Call<BaseResponse<AddMemberTaskDto>> postAddMemberTask(@Field("id_user") String iduser,
                                                           @Field("id_task") String idTask);

    @FormUrlEncoded
    @POST("group/create_group")
    Call<BaseResponse<ResponseGroupDto>> postAddGroup(@Field("id_user") String iduser,
                                                      @Field("nama_team") String namaTeam);

    @GET("group")
    Call<BaseResponse<List<ResponseGroupDto>>> getGroup(@Query("id_user") String iduser);

    @GET("group/delete_group")
    Call<BaseResponseOther> deleteGroup(@Query("id_group") String iduser);

    @FormUrlEncoded
    @PUT("task/update_status")
    Call<BaseResponseOther> postUpdateTask(@Field("id_task") String idTask);

    @FormUrlEncoded
    @PUT("task/cancel")
    Call<BaseResponseOther> postCancelTask(@Field("id_task") String idTask);

    @FormUrlEncoded
    @POST("meeting/add_member_meeting")
    Call<BaseResponse<AddMemberMeetingDto>> postAddMemberMeeting(@Field("username") String username,
                                                                 @Field("id_meeting") String idMeeting);

    @Multipart
    @POST("meeting/upload_file")
    Call<BaseResponse<UploadDto>> uploadFIle(@Part MultipartBody.Part file);


}