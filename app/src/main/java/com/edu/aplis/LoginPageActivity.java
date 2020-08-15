package com.edu.aplis;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.edu.home.HomeActivityNew;
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

public class LoginPageActivity extends AppCompatActivity implements ResponceQueues, VerifyOtppage {
    Context context;
    TextView text_continue;

    EditText ed_emailsignin;
    EditText ed_passwordsignin;
    TextView test;
    VerifyOtppage verifyOtppage;
    ProgressBar progressBar;
    TextView button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loginpage);
        context = LoginPageActivity.this;
        verifyOtppage = this;
        if(PrefrenceUtils.readBoolean(context,PrefrenceUtils.PREF_LOGINTYPE,false)==true){
            Log.e("TAG",PrefrenceUtils.PREF_DEVIC_TOKEN);
            startActivity(new Intent(context, HomeActivityNew.class));
            finish();
        }
        Log.e("TAG",PrefrenceUtils.readString(context,PrefrenceUtils.PREF_DEVIC_TOKEN,""));

        text_continue = findViewById(R.id.text_signin);
        ed_emailsignin = findViewById(R.id.ed_emailsignin);
        ed_passwordsignin = findViewById(R.id.ed_passwordsignin);
        progressBar = findViewById(R.id.progressBar);
        button1 =  findViewById(R.id.button1);

        //callanalytics();

        ed_passwordsignin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        ed_passwordsignin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    ed_passwordsignin.setCompoundDrawablesWithIntrinsicBounds( 0, 0,0, 0);

                }
                else {
                    ed_passwordsignin.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.right_arrow, 0);
                }

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textsigninaccount();
            }
            });



    }
    public void text__continueclick(View view) {

        if (!isValidEmail(ed_emailsignin.getText().toString())){
            ed_emailsignin.setError("Enter correct email");
            Toast.makeText(context,"Enter correct email",Toast.LENGTH_LONG).show();

            return ;
        }

        if (!isValidPassword(ed_passwordsignin.getText().toString())){

            Toast.makeText(context,"Password accept only alphanumeric and must contain at least 6 character",Toast.LENGTH_LONG).show();
            return;
        }

    }


    public void textsigninaccount() {
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
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_NM, userAccount.getUser().getName());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, userAccount.getUser().getEmail());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DOB,userAccount.getUser().getDob());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_ID, userAccount.getUser().getId());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_GENDER,userAccount.getUser().getGender());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, userAccount.getUser().getJwtToken());
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_SCHOOLTYPE, userAccount.getUser().getSchool_type());
                        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, true);
                        startActivity(new Intent(context, HomeActivityNew.class));
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
                        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_SCHOOLTYPE, userAccount.getUser().getSchool_type());

                        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, true);

                        startActivity(new Intent(context, HomeActivityNew.class));
                        finish();
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

    @Override
    public void responceQue(String responce, String url, String extra_text) {

        Log.e("RESPONSE",responce);
    }






    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

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


/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/
}
