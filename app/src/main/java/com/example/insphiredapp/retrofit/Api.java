package com.example.insphiredapp.retrofit;

import com.example.insphiredapp.ApiModelEmployer.EmployerProfileModel;
import com.example.insphiredapp.ApiModelEmployer.ForgotPassword;
import com.example.insphiredapp.Api_Model.AllEmployeeListModel;
import com.example.insphiredapp.Api_Model.BookingModel;
import com.example.insphiredapp.Api_Model.CancelEmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.CancelModel;
import com.example.insphiredapp.Api_Model.ChangePasswordModel;
import com.example.insphiredapp.Api_Model.ChatCompanyModel;
import com.example.insphiredapp.Api_Model.CreateSlotModel;
import com.example.insphiredapp.Api_Model.DeleteTimeSlotModel;
import com.example.insphiredapp.Api_Model.EarningModel;
import com.example.insphiredapp.Api_Model.EditEmployerProfileModel;
import com.example.insphiredapp.Api_Model.EmployeeBookedListModel;
import com.example.insphiredapp.Api_Model.EmployeeEditProfileModel;
import com.example.insphiredapp.Api_Model.EmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.EmployeeProfileModelFirst;
import com.example.insphiredapp.Api_Model.EmployerChatEmpModel;
import com.example.insphiredapp.Api_Model.Favourite_employee_model;
import com.example.insphiredapp.Api_Model.FilterModel;
import com.example.insphiredapp.Api_Model.GetBookingDetailModel;
import com.example.insphiredapp.Api_Model.GetCategoryModel;
import com.example.insphiredapp.Api_Model.GetCreateSlotsModel;
import com.example.insphiredapp.Api_Model.GetCreditModel;
import com.example.insphiredapp.Api_Model.GetEditEmployeeProfileModel;
import com.example.insphiredapp.Api_Model.GetEmployeeHistoryModel;
import com.example.insphiredapp.Api_Model.GetFavouriteModel;
import com.example.insphiredapp.Api_Model.GetProfileDetailsModel;
import com.example.insphiredapp.Api_Model.GetReviewModel;
import com.example.insphiredapp.Api_Model.GetWithDrawModel;
import com.example.insphiredapp.Api_Model.GiveRatingModel;
import com.example.insphiredapp.Api_Model.JobCancelModel;
import com.example.insphiredapp.Api_Model.LocationModel;
import com.example.insphiredapp.Api_Model.LoginModel;
import com.example.insphiredapp.Api_Model.MsgEmployeeModel;
import com.example.insphiredapp.Api_Model.MyJobModel;
import com.example.insphiredapp.Api_Model.NotificationModel;
import com.example.insphiredapp.Api_Model.PaymentHistoryModel;
import com.example.insphiredapp.Api_Model.PostEmailOtpModel;
import com.example.insphiredapp.Api_Model.PostProfieEmpApi;
import com.example.insphiredapp.Api_Model.RegisterModel;
import com.example.insphiredapp.Api_Model.RequestTimeSlotModel;
import com.example.insphiredapp.Api_Model.ResetPasswordModel;
import com.example.insphiredapp.Api_Model.ShowCvModel;
import com.example.insphiredapp.Api_Model.UpComingJobModel;
import com.example.insphiredapp.Api_Model.UserChatModel;
import com.example.insphiredapp.Api_Model.VerifyOtpModel;
import com.example.insphiredapp.Api_Model.WithdrawModel;
import com.example.insphiredapp.FeedbackEmployee.EmployeeRating;
import com.example.insphiredapp.FeedbackEmployee.EmployeeRatingData;
import com.example.insphiredapp.ShowReviewApiModel.ShowEmployeeReview;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface Api {

    @Multipart
    @POST("user_register")
    Call<RegisterModel> REGISTER_MODEL_CALL(
            @Part("first_name") RequestBody firstname,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("Mobile") RequestBody mobile,
            @Part MultipartBody.Part intro_ideo,
            @Part MultipartBody.Part id_pdf,
            @Part("user_type") RequestBody userType);

    @FormUrlEncoded
    @POST("user_login")
    Call<LoginModel> LOGIN_MODEL_CALL(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("device_token") String token,
                                      @Field("user_type") String userType);

    @FormUrlEncoded
    @POST("user_change_password")
    Call<ChangePasswordModel> CHANGE_PASSWORD_MODEL_CALL(@Field("user_id") String userId,
                                                         @Field("old_password") String oldPassword,
                                                         @Field("new_password") String newPassword,
                                                         @Field("user_type") String userType);

    @Multipart
    @POST("employer_update_profile")
    Call<EmployerProfileModel> EMPLOYER_PROFILE_MODEL_CALL(@Part("user_id") RequestBody user_id,
                                                           @Part MultipartBody.Part image,
                                                           @Part("company_name") RequestBody company_name,
                                                           @Part("company_email") RequestBody company_email,
                                                           @Part("address") RequestBody companyAddress,
                                                           @Part("term_condition") RequestBody term_condition);

    @GET()
    Call<GetProfileDetailsModel> GET_PROFILE_DETAILS_MODEL_CALL(@Url String str);

    @Multipart
    @POST("edit_profile")
    Call<EditEmployerProfileModel> EDIT_EMPLOYER_PROFILE_MODEL_CALL(@Part("user_id") RequestBody user_id,
                                                                    @Part MultipartBody.Part image,
                                                                    @Part("user_name") RequestBody user_name,
                                                                    @Part("email") RequestBody company_email,
                                                                    @Part("mobile") RequestBody mobile,
                                                                    @Part("company_name") RequestBody companyAddress,
                                                                    @Part("location") RequestBody company_name,
                                                                    @Part("user_type") RequestBody user_type);

    @GET("all_category")
    Call<GetCategoryModel> GET_CATEGORY_MODEL_CALL();

    @GET()
    Call<EmployeeProfileModelFirst> EMPLOYEE_PROFILE_MODEL_FIRST_CALL(@Url String str);


/*
    @GET()
    Call<GetProfieEmpApi>GET_PROFIE_EMP_API_CALL(@Url String str);*/

    @Multipart
    @POST("user_update_profile")
    Call<PostProfieEmpApi> POST_PROFIE_EMP_API_CALL(@Part("user_id") RequestBody user_id,
                                                    @Part MultipartBody.Part emp_image,
                                                    @Part("emp_id") RequestBody emp_id,
                                                    @Part("cat_id") RequestBody cat_id,
                                                    @Part("daily_rate") RequestBody daily_rate,
                                                    @Part MultipartBody.Part emp_cv,
                                                    @Part("emp_history") RequestBody emp_history,
                                                    @Part("location") RequestBody location,
                                                    @Part("term_condition") RequestBody term_condition);

    @Multipart
    @POST("edit_profile")
    Call<EmployeeEditProfileModel> EMPLOYEE_EDIT_PROFILE_MODEL_CALL(@Part("user_id") RequestBody user_id,
                                                                    @Part MultipartBody.Part image,
                                                                    @Part("user_name") RequestBody user_name,
                                                                    @Part("email") RequestBody email,
                                                                    @Part("mobile") RequestBody mobile,
                                                                    @Part("daily_rate") RequestBody daily_rate,
                                                                    @Part("location") RequestBody location,
                                                                    @Part("user_type") RequestBody user_type);

    @GET()
    Call<GetEditEmployeeProfileModel> GET_EDIT_EMPLOYEE_PROFILE_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("user_forgot_password")
    Call<ForgotPassword> FORGOT_PASSWORD_CALL(@Field("mobile_no") String mobile_no,
                                              @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("otp_send")
    Call<PostEmailOtpModel> POST_EMAIL_OTP_MODEL_CALL(@Field("email") String email);

    @GET()
    Call<VerifyOtpModel> VERIFY_OTP_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("forgot_password_save")
    Call<ResetPasswordModel> RESET_PASSWORD_MODEL_CALL(@Field("new_password") String new_password,
                                                       @Field("confirm_password") String C_password,
                                                       @Field("user_id") String user,
                                                       @Field("user_type") String user_type);

    @GET()
    Call<AllEmployeeListModel> ALL_EMPLOYEE_LIST_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("add_to_fav")
    Call<Favourite_employee_model> FAVOURITE_EMPLOYEE_MODEL_CALL(@Field("employee_id") String employee_id,
                                                                 @Field("employer_id") String employer_id,
                                                                 @Field("user_type") String user_type);

    @GET()
    Call<GetFavouriteModel> GET_FAVOURITE_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("save_rating")
    Call<GiveRatingModel> GIVE_RATING_MODEL_CALL(@Field("user_id") String user_id,
                                                 @Field("employee_id") String employee_id,
                                                 @Field("rating") String rating,
                                                 @Field("comment") String comment,
                                                 @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("save_rating")
    Call<EmployeeRating> EMPLOYEE_RATING_CALL(@Field("user_id") String user_id,
                                              @Field("employer_id") String employee_id,
                                              @Field("rating") String rating,
                                              @Field("comment") String comment,
                                              @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("filter_data")
    Call<FilterModel> FILTER_MODEL_CALL(@Field("cat_id") String cat_id,
                                        @Field("location") String location,
                                        @Field("per_day") String per_day,
                                        @Field("price_range") String price_range);

    @GET()
    Call<GetBookingDetailModel> GET_BOOKING_DETAIL_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("employee_book")
    Call<BookingModel> BOOKING_MODEL_CALL(@Field("employee_id") String employee_Id,
                                          @Field("user_id") String user_id,
                                          @Field("amount") String amount,
                                          @Field("time_slot_id") String time_slot_id,
                                          @Field("user_type") String user_type);

    @GET()
    Call<EmployeeBookedListModel> EMPLOYEE_BOOKED_LIST_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("employee_cancel")
    Call<CancelModel> CANCEL_MODEL_CALL(@Field("id") String id);

    @GET()
    Call<GetEmployeeHistoryModel> GET_EMPLOYEE_HISTORY_MODEL_CALL(@Url String str);

    @GET()
    Call<GetReviewModel> GET_REVIEW_MODEL_CALL(@Url String str);

    @GET()
    Call<ShowEmployeeReview> SHOW_EMPLOYEE_REVIEW_CALL(@Url String str);

    @GET()
    Call<PaymentHistoryModel> PAYMENT_HISTORY_MODEL_CALL(@Url String str);

    @GET()
    Call<MyJobModel> MY_JOB_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("job_cancel")
    Call<JobCancelModel> JOB_CANCEL_MODEL_CALL(@Field("booking_id") String booking_id);

    @GET()
    Call<UpComingJobModel> UP_COMING_JOB_MODEL_CALL(@Url String str);

    @GET()
    Call<ChatCompanyModel> CHAT_COMPANY_MODEL_CALL(@Url String str);

    @GET()
    Call<MsgEmployeeModel> MSG_EMPLOYEE_CALL(@Url String str);

    @FormUrlEncoded
    @POST("user_chat")
    Call<UserChatModel> USER_CHAT_MODEL_CALL(@Field("from_user") String from_user,
                                             @Field("to_user") String to_user,
                                             @Field("chat_message") String chat_message,
                                             @Field("user_type") String userType);

    @GET()
    Call<EmployerChatEmpModel> EMPLOYER_CHAT_EMP_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("withdraw_amount")
    Call<WithdrawModel> WITHDRAW_MODEL_CALL(@Field("user_id") String user_id,
                                            @Field("amount") String amount,
                                            @Field("total_amount") String total_amount);

    @GET()
    Call<GetWithDrawModel> GET_WITH_DRAW_MODEL_CALL(@Url String Str);

    @GET()
    Call<GetCreditModel> GET_CREDIT_MODEL_CALL(@Url String str);

    @FormUrlEncoded
    @POST("create_timeslot")
    Call<CreateSlotModel> CREATE_SLOT_MODEL_CALL(@Field("user_id") String user_id,
                                                 @Field("start_date") String start_date,
                                                 @Field("end_date") String end_date,
                                                 @Field("start_time") String start_time,
                                                 @Field("end_time") String end_time,
                                                 @Field("location ")String location);

    @FormUrlEncoded
    @POST("time_slot_change_request")
    Call<RequestTimeSlotModel> REQUEST_TIME_SLOT_MODEL_CALL(@Field("time_slot_id") String time_slot_id,
                                                            @Field("user_id") String user_id,
                                                            @Field("employee_id") String employee_id,
                                                            @Field("start_date") String start_date,
                                                            @Field("end_date") String end_date,
                                                            @Field("user_type") String user_type);

    @GET("location")
    Call<LocationModel> LOCATION_MODEL_CALL();


    @GET()
    Call<GetCreateSlotsModel> GET_CREATE_SLOTS_MODEL_CALL(@Url String str);

    @GET()
    Call<DeleteTimeSlotModel> DELETE_TIME_SLOT_MODEL_CALL(@Url String str);


    @GET()
    Call<EarningModel> EARNING_MODEL_CALL(@Url String Str);


    @GET()
    Call<EmployeeHistoryModel> EMPLOYEE_HISTORY_MODEL_CALL(@Url String Str);


    @GET()
    Call<CancelEmployeeHistoryModel> CANCEL_EMPLOYEE_HISTORY_MODEL_CALL(@Url String Str);

    @GET()
    Call<ShowCvModel> SHOW_CV_MODEL_CALL(@Url String str);

    @GET()
    Call<NotificationModel>NOTIFICATION_MODEL_CALL(@Url String str);


}
