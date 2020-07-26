package com.edu.webservice;

import retrofit2.http.GET;
import retrofit2.http.POST;

public  class Cons {
    public static String BASE_URL = "https://samystudios.com/api";
    public static String BASE_URL1 = "http://35.173.187.82/aplis/public/api";
    public static String LOGIN_URL = BASE_URL+"/user/sign-in";
    public static String BROWSE_URL= BASE_URL+"/user/get-all-books?page=";
    public static String SIGNUP_URL = BASE_URL+"/user/sign-up";
    public static String BOOKRECENT_URL = BASE_URL+"/user/get-last-book";
    public static String BOOKDETAILS_URL = BASE_URL+"/user/book-details?book_id=";
    public static String AUTHORSDETAILS_URL = BASE_URL+"/user/get-book-by-author?author_id=";
    public static String SERIESTOBOOK_URL = BASE_URL+"/user/get-book-by-series?series_id=";
    public static String SUBCATTOBOOK_URL = BASE_URL+"/user/get-book-by-category?category_id=";
    public static String SUBCATTODETAIL_URL = BASE_URL+"/user/get-sub-categories?parent_category_id=";
    public static String GET_ALL_DISCOVER_URL = BASE_URL+"/user/get-all-discovers";
    public static String GET_DISCOVER_URLDETAILS = BASE_URL+"/user/discover-details?discover_id=";
    public static String GET_URL_SEARCHITEM = BASE_URL+"/user/search-books?search_item=";
    public static String ADD_TO_FAVURL = BASE_URL+"/user/favorite/";
    public static String UN_FAVURL = BASE_URL+"/user/unfavorite/";
    public static String GET_FAVURL = BASE_URL+"/user/my-favorites/";
    public static String GET_CHAPTERBYBOOKID = BASE_URL+"/user/get-topics?book_id=";
    public static String GET_SUBCHAPTERBYCHAPTERID = BASE_URL+"/user/get-sub-topics?topic_id=";
    public static String GET_SUB_SUBCHAPTERBYSUBID = BASE_URL+"/user/get-sub-sub-topics?sub_topic_id=";
    public static String GET_SUBCHAPTERYBDETAILCHAPTERID = BASE_URL+"/user/get-sub-topic-details?sub_topic_id=";
    public static String GET_SUBSUBCHAPTERYBDETAILCHAPTERID = BASE_URL+"/user/get-sub-sub-topic-details?sub_sub_topic_id=";
    public static String PRIVACY_POLICY= "https://mymediafiles0.s3.ap-south-1.amazonaws.com/Legal+Docs/Privacy+Policy.html";
    public static String TERMSANDCONDITIONS = "https://mymediafiles0.s3.ap-south-1.amazonaws.com/Legal+Docs/Terms.html";
    public static String GET_TIMELINEBYID = BASE_URL+"/user/get-timeline-entries?timeline_id=";


//    Call<List<Hero>> getHeroes();


}
