package com.edu.retrofitapi;

import com.barchartpojo.BarFirstRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subFirstDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subSecondDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subSecondRetrofitModel;
import com.editorsecondsubactivitypojo.SecondRetrofitModel;
import com.editorsub_sub_subpojo.EditorSub_Sub_SubFirstRetrofitModel;
import com.editorsub_sub_subpojo.Editorsub_sub_subFirstDetailsRetrofitModel;
import com.edu.book.BookFirstRetrofitModel;
import com.edu.browse.BrowseFirstRetrofitModel;
import com.edu.discover.DiscoverRetrofitArrayModel;
import com.edu.discover.DiscoverRetrofitModel;
import com.edu.editortemplatetwo.EditorFirstRetrofitModel;
import com.edu.fav.FavRetrofitModel;
import com.edu.fav.FavUnFavResponse;
import com.edu.reset.EmailResponse;
import com.edu.webservice.Cons;
import com.piechartpojo.PieFirstRetrofitModel;
import com.timelinepojo.TimelineFirstRetrofitModel;
import com.viewactivitypojo.ViewActivityFirstRetrofitModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface Api {

    String BASE_URL = "https://samystudios.com/api/";
    @FormUrlEncoded
    @POST("user/sign-in")
    Call<UserAccount> getLoginResponse(@Field("email") String title, @Field("password") String body);


    @GET("user/get-all-discovers")
    Call<DiscoverRetrofitModel> getDiscoverList();

    @GET("user/get-all-books?page=")
    Call<BrowseFirstRetrofitModel> getBrowseBookList();

    @GET
    Call<BookFirstRetrofitModel> getBookCourseDetails(@Url String url);

    @GET
    Call<EditorFirstRetrofitModel> getChapterByBookId(@Url String url);
    @GET
    Call<SecondRetrofitModel> getSUBCHAPTERBYCHAPTERID(@Url String url);

    @GET
    Call<ViewActivityFirstRetrofitModel> getGET_SUBCHAPTERYBDETAILCHAPTERID(@Url String url);

    @GET
    Call<Editorsub_subFirstDetailsRetrofitModel> getEditorSecondGET_SUBCHAPTERYBDETAILCHAPTERID(@Url String url);

    @GET
    Call<Editorsub_subSecondRetrofitModel> getGETSUB_SUBCHAPTERYBSUBID(@Url String url);

    @GET
    Call<BarFirstRetrofitModel> getGETBarChhart(@Url String url);

    @GET
    Call<PieFirstRetrofitModel> getGETPieChart(@Url String url);

    @GET
    Call<TimelineFirstRetrofitModel> getTimelinePage(@Url String url);

    @GET
    Call<Editorsub_sub_subFirstDetailsRetrofitModel> getEditorSecondSub_sub_subActivity(@Url String url);

    @GET
    Call<FavRetrofitModel> getFavBookLists(@Url String url);


    @FormUrlEncoded
    @POST("user/send-verification-code")
    Call<EmailResponse> getVerrifyEmailID(@Field("email") String title);

    @FormUrlEncoded
    @POST("user/reset-password")
    Call<EmailResponse> getResetPswd(@Field("email") String email,@Field("verification_code") String verification_code,@Field("password") String password);

    @FormUrlEncoded
    @POST("user/verify-email")
    Call<EmailResponse> doverifyemail(@Field("email") String email,@Field("verification_code") String verification_code);

    @POST
    Call<FavUnFavResponse> dofavUnfavbook(@Url String url);



    @GET
    Call<Editorsub_subSecondDetailsRetrofitModel> getGET_SECONDSUB_SUBCHAPTERYBDETAILCHAPTERID(@Url String url);
}
