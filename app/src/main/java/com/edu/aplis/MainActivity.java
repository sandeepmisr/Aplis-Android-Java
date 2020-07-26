package com.edu.aplis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.home.HomeActivity;
import com.edu.preference.PrefrenceUtils;
import com.edu.reset.EmailResponse;
import com.edu.reset.Otp_Activity;
import com.edu.reset.Otppage;
import com.edu.reset.VerifyOtppage;
import com.edu.reset.Verify_Activity;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.retrofitapi.UserAccount;
import com.edu.transformation.FadeOutTransformation;
import com.edu.tutorialviewpager.CustomPagerAdapter;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;


import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ResponceQueues, VerifyOtppage {
    Context context;
    LinearLayout linearsignupscreen;
    LinearLayout defaultlinearbottomscreen;
    LinearLayout linearsigninscreen;
    LinearLayout linearsignupsecondscreen;

    EditText ed_fnamesignup;
    EditText ed_mobsignup;

    MaterialBetterSpinner genderlistsignup;
    EditText ed_emailsignup;
    EditText ed_passwordsignup;
    TextView text_continue;
    EditText eddobsignup;
    String getgender_value="";
    EditText ed_emailsignin;
    EditText ed_passwordsignin;
    TextView test;
    int day,month,year;
    DatePicker datePicker1;
    int mHour,mMinute;
    HashMap<String,String> hashMap=new HashMap<>();
    ArrayList<String> genderStringArrayList;
    Otppage otppage;
    VerifyOtppage verifyOtppage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        verifyOtppage = this;
        if(PrefrenceUtils.readBoolean(context,PrefrenceUtils.PREF_LOGINTYPE,false)==true){
            Log.e("TAG",PrefrenceUtils.PREF_DEVIC_TOKEN);
            startActivity(new Intent(context, HomeActivity.class));
            finish();
        }
        Log.e("TAG",PrefrenceUtils.readString(context,PrefrenceUtils.PREF_DEVIC_TOKEN,""));

        ed_fnamesignup = findViewById(R.id.ed_fnamesignup);
        ed_mobsignup = findViewById(R.id.ed_mobsignup);
        genderlistsignup = findViewById(R.id.gender_list);
        ed_emailsignup = findViewById(R.id.ed_emailsignup);
        ed_passwordsignup = findViewById(R.id.ed_passwordsignup);
        text_continue = findViewById(R.id.text_signin);
        eddobsignup = findViewById(R.id.eddobsignup);
        datePicker1 = findViewById(R.id.datePicker1);
        test = findViewById(R.id.test);

        ed_emailsignin = findViewById(R.id.ed_emailsignin);
        ed_passwordsignin = findViewById(R.id.ed_passwordsignin);


        linearsignupscreen = findViewById(R.id.linearsignupscreen);
        defaultlinearbottomscreen = findViewById(R.id.defaultlinearbottomscreen);
        linearsigninscreen = findViewById(R.id.linearsigninscreen);
        linearsignupsecondscreen = findViewById(R.id.linearsignupsecondscreen);

        progressBar = findViewById(R.id.progressBar);

        genderStringArrayList= new ArrayList<>();
//        openAR();
        final ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        callanalytics();

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, DiscoverActivity.class));
//            }
//        });

        setGenderData();


        ed_passwordsignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
if (ed_passwordsignup.getText().toString().length()>5){
    text_continue.setClickable(true);
}
else{
    text_continue.setEnabled(false);

}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed_emailsignup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                datePicker1.setVisibility(View.GONE);

            }
        });


        ed_passwordsignup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                datePicker1.setVisibility(View.GONE);

            }
        });


        eddobsignup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                datePicker1.setVisibility(View.VISIBLE);
//                showDialog(0);
                return true;
            }


        });

    }


    private void setGenderData() {
        genderStringArrayList.add("MALE");
        genderStringArrayList.add("FEMALE");
        genderStringArrayList.add("NOT SPECIFIED");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, genderStringArrayList);
        genderlistsignup.setAdapter(adapter);

        genderlistsignup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    getgender_value = genderlistsignup.getText().toString();
                    Log.e("TAG", i + " " + getgender_value + "");

            }

        });
    }

    private void setupViewPager(final ViewPager viewPager) {
        FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();

        viewPager.setAdapter(new CustomPagerAdapter(context));
        viewPager.setPageTransformer(true, fadeOutTransformation);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                handler.postDelayed(this, 10000);
//                if (i ==3){
//                    i=0;
//                }
//                viewPager.setCurrentItem(i);
//                i++;
//                handler.postDelayed(this, 3000);
//                //Do something after 1000ms
//            }
//        },1000);

    }

    private void openAR(){
        Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
        sceneViewerIntent.setData(Uri.parse("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"));
        sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
        startActivity(sceneViewerIntent);
    }


    public void defaultlinearbottomscreenclick(View view) {
    }

    public void text_signinclick(View view) {
        linearsigninscreen.setVisibility(View.VISIBLE);
        defaultlinearbottomscreen.setVisibility(View.GONE);

    }

    public void text__continueclick(View view) {
        datePicker1.setVisibility(View.GONE);

        if (!isValidEmail(ed_emailsignup.getText().toString())){
            ed_emailsignup.setError("Enter correct email");
            Toast.makeText(context,"Enter correct email",Toast.LENGTH_LONG).show();

            return ;
        }




        if (!isValidPassword(ed_passwordsignup.getText().toString())){

            Toast.makeText(context,"Password accept only alphanumeric and must contain at least 6 character",Toast.LENGTH_LONG).show();
            return;
        }

        if((eddobsignup.getText().toString().equalsIgnoreCase(""))){
            eddobsignup.setError("Enter DOB");
            return;
        }
        linearsignupsecondscreen.setVisibility(View.VISIBLE);
        linearsigninscreen.setVisibility(View.GONE);
        linearsignupscreen.setVisibility(View.GONE);
    }

    public void text_signupfromdefaultclick(View view) {
        linearsignupscreen.setVisibility(View.VISIBLE);
        linearsigninscreen.setVisibility(View.GONE);
        defaultlinearbottomscreen.setVisibility(View.GONE);

    }

    public void textsigninaccount(View view) {
        if(!isFormValid()){
            Toast.makeText(context,"Please select all fields",Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<UserAccount> call = api.getLoginResponse(ed_emailsignin.getText().toString(),ed_passwordsignin.getText().toString());

        call.enqueue(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());
                progressBar.setVisibility(View.GONE);
                try {
                    UserAccount userAccount = response.body();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    if (userAccount.getStatus().equalsIgnoreCase("1")) {
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_PASS_GENERATED, ed_passwordsignin.getText().toString());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_NM, userAccount.getUser().getFirst_name());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, userAccount.getUser().getEmail());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DOB,userAccount.getUser().getDob());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_ID, userAccount.getUser().getId());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_GENDER,userAccount.getUser().getGender());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, userAccount.getUser().getJwtToken());
                        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, true);
                        startActivity(new Intent(context, HomeActivity.class));
                    }
                    else if(userAccount.getStatus().equalsIgnoreCase("0")){
                        if (userAccount.getMessage().equals("Your email is not verified")){
                            Toast.makeText(context,"Please verify your email",Toast.LENGTH_LONG).show();

                            Api apiverify = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

                            Call<EmailResponse> callverify = apiverify.getVerrifyEmailID(ed_emailsignin.getText().toString());

                            callverify.enqueue(new Callback<EmailResponse>() {
                                @Override
                                public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {

                                    try {
                                        Log.e("errordo","try");

                                        EmailResponse emailResponse=response.body();
                                        if (emailResponse.getStatus().equalsIgnoreCase("1")){
                                            Log.e("errordo","try "+emailResponse.getStatus());

//                                           Toast.makeText(context,emailResponse.getEmail()+"!!",Toast.LENGTH_LONG).show();
                                            startActivityForResult(new Intent(context,Verify_Activity.class),3);
                                        }
                                        else{
                                            Log.e("errordo","else");

                                        }
//                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                                    }
                                    catch (Exception e){
                                        Log.e("errordo",e+"");

                                    }
                                }

                                @Override
                                public void onFailure(Call<EmailResponse> call, Throwable t) {
                                    Log.e("errordo",t.getMessage());
                                }
                            });



                        }
                        else{
                            Toast.makeText(context,"Username or password is incorrect",Toast.LENGTH_LONG).show();
                        }
                    }

                    else{
                        Toast.makeText(context,"Username or password is incorrect",Toast.LENGTH_LONG).show();
                    }
//                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                }
                catch (Exception e){
                   Log.e("account_detail",   e+"");


                }
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });

//        hashMap.clear();
//        hashMap.put("email",ed_emailsignin.getText().toString());
//        hashMap.put("password", ed_passwordsignin.getText().toString());
//

//        makeHttpCall(Cons.LOGIN_URL);
    }

    private void dosignin() {
        if(!isFormValid()){
//            Toast.makeText(context,"Please select all fields",Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<UserAccount> call = api.getLoginResponse(ed_emailsignin.getText().toString(),ed_passwordsignin.getText().toString());

        call.enqueue(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                progressBar.setVisibility(View.GONE);

                PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());

                try {
                    UserAccount userAccount = response.body();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    if (userAccount.getStatus().equalsIgnoreCase("1")) {
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_NM, userAccount.getUser().getFirst_name());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, userAccount.getUser().getEmail());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DOB,userAccount.getUser().getDob());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_ID, userAccount.getUser().getId());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_GENDER,userAccount.getUser().getGender());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, userAccount.getUser().getJwtToken());
                        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, true);

                        startActivity(new Intent(context, HomeActivity.class));
                    }
                    else if(userAccount.getStatus().equalsIgnoreCase("0")){
                        if (userAccount.getMessage().equals("Your email is not verified")){
                            Toast.makeText(context,"Please verify your email",Toast.LENGTH_LONG).show();

                            Api apiverify = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

                            Call<EmailResponse> callverify = apiverify.getVerrifyEmailID(ed_emailsignin.getText().toString());

                            callverify.enqueue(new Callback<EmailResponse>() {
                                @Override
                                public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {

                                    try {
                                        Log.e("errordo","try");

                                        EmailResponse emailResponse=response.body();
                                        if (emailResponse.getStatus().equalsIgnoreCase("1")){
                                            Log.e("errordo","try "+emailResponse.getStatus());

                                            startActivityForResult(new Intent(context,Verify_Activity.class),3);

                                        }
                                        else{
                                            Log.e("errordo","try "+"else");

                                        }
//                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                                    }
                                    catch (Exception e){
                                        Log.e("errordo",e+"");

                                    }
                                }

                                @Override
                                public void onFailure(Call<EmailResponse> call, Throwable t) {
                                    Log.e("errordo",t.getMessage());
                                }
                            });



                        }

                    }

                    else{
                        Toast.makeText(context,"Username or password is incorrect",Toast.LENGTH_LONG).show();
                    }
                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                }
                catch (Exception e){
//                   Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());


                }
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });



    }

    private void makeHttpCall(String url) {
        trustEveryone();

        ApiService apiService = new ApiService(context,this,url ,hashMap,2);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {

        try{
            JSONObject jsonObject = new JSONObject(responce);

            if (url.contains(Cons.LOGIN_URL)) {
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_ID, jsonObject.getJSONObject("user").getString("id"));
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, jsonObject.getJSONObject("user").getString("jwtToken"));
                    startActivity(new Intent(context, HomeActivity.class));
                }
                else{
                    Log.e("RESPONSE",responce);

                    Toast.makeText(context,"Username or password is incorrect"+responce,Toast.LENGTH_LONG).show();
                }
            }
            else if (url.contains(Cons.SIGNUP_URL)) {
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignup.getText().toString());
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_ID, jsonObject.getJSONObject("data").getString("id"));
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DOB, jsonObject.getJSONObject("data").getString("dob"));
//                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_GENDER, jsonObject.getJSONObject("data").getString("last_name"));
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_NM, jsonObject.getJSONObject("data").getString("first_name"));
                    PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, jsonObject.getJSONObject("data").getString("jwtToken"));
                    ed_emailsignin.setText(ed_emailsignup.getText().toString());
                    ed_passwordsignin.setText(ed_passwordsignup.getText().toString());
                    dosignin();
//                    startActivity(new Intent(context, HomeActivity.class));
                } else if (jsonObject.getString("status").equalsIgnoreCase("0")) {
                    Toast.makeText(context, jsonObject.getString("message") + responce, Toast.LENGTH_LONG).show();

                }
            }
//            finish();
        }
        catch (Exception e){

        }
        Log.e("RESPONSE",responce);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            eddobsignup.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);
        }
    };



    public void text__createaccoutnclick(View view) {
        if(!isSignupFormValid()){
//            Toast.makeText(context,"Please select all fields",Toast.LENGTH_LONG).show();
            return;
        }

        hashMap.clear();
        hashMap.put("first_name",ed_fnamesignup.getText().toString());
//        hashMap.put("last_name",getgender_value);
        hashMap.put("gender",getgender_value);
        hashMap.put("dob",eddobsignup.getText().toString());
        hashMap.put("mobile",ed_mobsignup.getText().toString());
        hashMap.put("email",ed_emailsignup.getText().toString());
        hashMap.put("password",ed_passwordsignup.getText().toString());

//        Log.e("ALL_DATA",hashMap.get("first_name")+ " "+hashMap.get("last_name")+ " "+hashMap.get("gender")+ " "+hashMap.get("dob")+ " "+hashMap.get("mobile")+ " "+hashMap.get("email")+ " "+hashMap.get("password")+ " ");
        makeHttpCall(Cons.SIGNUP_URL);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        datePicker1.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                try {
                    eddobsignup.setText(datePicker1.getDayOfMonth() + "-" + (datePicker1.getMonth() + 1) + "-" + datePicker1.getYear());
                }
                catch (Exception e){

                }
            }
        });

        return super.dispatchTouchEvent(ev);
    }

    private boolean isFormValid(){
        if((ed_emailsignin.getText().toString().equalsIgnoreCase(""))){
            ed_emailsignin.setError("Enter email");
            return false;
        } if((ed_passwordsignin.getText().toString().equalsIgnoreCase(""))){
            ed_passwordsignin.setError("Enter password here");
            return false;
        }



        return true;
    }
    private boolean isSignupFormValid(){
        if((ed_emailsignup.getText().toString().equalsIgnoreCase(""))){
            ed_emailsignup.setError("Enter email");
            return false;
        }
        if((getgender_value.toString().equalsIgnoreCase(""))){
            Toast.makeText(context,"Enter Gender information",Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isValidEmail(ed_emailsignup.getText().toString())){
            ed_emailsignup.setError("Enter correct email");
            Toast.makeText(context,"Enter correct email",Toast.LENGTH_LONG).show();

            return false;
        }
        if((ed_passwordsignup.getText().toString().equalsIgnoreCase(""))){
            ed_passwordsignup.setError("Enter password here");
            return false;
        }

        if (!isValidPassword(ed_passwordsignup.getText().toString())){
            Toast.makeText(context,"Create a strong Password",Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }


    public  boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;


        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z]).{6,20})";
//        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
//        final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])$";
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!_-])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    public  boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }


    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }


    public void troublelogin(View view) {
        if((ed_emailsignin.getText().toString().equalsIgnoreCase(""))){
            ed_emailsignin.setError("Enter email");
            Toast.makeText(context,"Enter email first",Toast.LENGTH_LONG).show();

            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<EmailResponse> call = api.getVerrifyEmailID(ed_emailsignin.getText().toString());

        call.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                progressBar.setVisibility(View.GONE);

                PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsignin.getText().toString());

                try {
                    EmailResponse emailResponse=response.body();
                    if (emailResponse.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(context,emailResponse.getEmail()+"!!",Toast.LENGTH_LONG).show();
                       startActivityForResult(new Intent(context,Otp_Activity.class),2);
                    }
                    else{
                        Toast.makeText(context,"Email not registered with us!!",Toast.LENGTH_LONG).show();
                    }
//                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });


    }


    @Override
    public void verifyotp(String otp, String password) {
        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<EmailResponse> call = api.doverifyemail(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_EMAIL,""),otp);

        call.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {

                try {
                    EmailResponse emailResponse=response.body();
                    if (emailResponse.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(context,emailResponse.getEmail()+"!!",Toast.LENGTH_LONG).show();
                      dosignin();

                    }
                    else{
                        Toast.makeText(context,"OTP not correct",Toast.LENGTH_LONG).show();
                    }
//                    Log.e("account_detail",   userAccount.getUser().getEmail()+" " + jsonObject + " " + response.toString());
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (resultCode==RESULT_OK){

            }
        }
        else if (requestCode==3){
            if (resultCode==RESULT_OK){
dosignin();
            }
        }
    }


    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }
}
