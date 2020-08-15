package com.edu.reset;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.preference.PrefrenceUtils;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Verify_Activity extends AppCompatActivity implements
        View.OnClickListener,VerifyOtppage {

    public Activity c;
    String mob;
    EditText edittext_otp;
    String otp= "";
    HashMap<String, String> hashMap = new HashMap<>();

    public Dialog d;
    public Button verify;
    VerifyOtppage otppage;
    String mob_number;
    EditText ed_npswd;
    EditText ed_cpswd;
    ImageView cancel_image;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.verifyemail);
        context= this;
        otppage =this;
        edittext_otp = findViewById(R.id.edittext_otp);
        ed_npswd = findViewById(R.id.ed_npswd);
        ed_cpswd = findViewById(R.id.ed_cpswd);
        verify = findViewById(R.id.text_reset);
        cancel_image = findViewById(R.id.cancel_image);

        //callanalytics();
        cancel_image.setOnClickListener(this);
        verify.setOnClickListener(this);




//        makeOTPcall(otp);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_reset:
                if (edittext_otp.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
                else {
                    otppage.verifyotp(edittext_otp.getText().toString(),ed_npswd.getText().toString());
                }


                break;


            default:
                break;
        }

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
                        Intent intent=new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

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

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/

}
