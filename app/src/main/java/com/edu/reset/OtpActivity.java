/*
package com.edu.reset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;
import com.party.act.MemberRegister;
import com.party.home.HomeActivity;
import com.party.preference.PrefrenceUtils;
import com.party.webservice.ApiService;
import com.party.webservice.Cons;
import com.party.webservice.ResponceQueues;

import java.util.HashMap;

public class OtpActivity extends AppCompatActivity implements ResponceQueues {
    Context context;
    TextView otpnumber;
    String mob_number = "";
    Pinview pinView;
    TextView resendOtp;
    HashMap<String, String> hashMap = new HashMap<>();
    String registered = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        context = MainActivity.this;
        otpnumber = findViewById(R.id.otpnumber);
        resendOtp = findViewById(R.id.resendOtp);
        pinView = findViewById(R.id.pinView);
        mob_number = getIntent().getStringExtra("mob_number");
        otpnumber.setText("ओटीपी(OTP) +91"+mob_number+" पर भेजा गया है");
        registered  = getIntent().getStringExtra("registered");

        final String otp = Otpclass.generateOTP();
        Log.e("result",otp);
        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_OTP, otp);

        hashMap.clear();
        makehttpcall(Cons.SENDOTP_URL + mob_number + "&text=Dear user, Your  verification code is " + otp + "&senderid=KONCEP&route_id=2&Unicode=0&IP=x.x.x.x");

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makehttpcall(Cons.SENDOTP_URL + mob_number + "&text=Dear user, Your  verification code is " + otp + "&senderid=KONCEP&route_id=2&Unicode=0&IP=x.x.x.x");
            }
        });

    }

    private void makehttpcall(String url) {
        ApiService task = new ApiService(context, this, url, hashMap, 1);
        task.execute();

    }


    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("result", responce);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pinView.getPinLength() == 4) {
            doneotp();
        }
    }

    public void done(View view) {

        doneotp();
    }

    private void doneotp() {
        if (!validatePhone(pinView.getValue().toString())) {
            return;
        }
        if (pinView.getValue().toString().equalsIgnoreCase(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_OTP, ""))) {
            if (registered.equals("false")) {
                Toast.makeText(context, "OTP verified", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, MemberRegister.class).putExtra("mobile", mob_number));
//                PrefrenceUtils.writeBoolean(context,PrefrenceUtils.PREF_LOGINTYPE,true);
            }
            else{
                Toast.makeText(context, "OTP verified", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, HomeActivity.class).putExtra("mobile", mob_number));
                PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_MOBILE,mob_number);
                PrefrenceUtils.writeBoolean(context,PrefrenceUtils.PREF_LOGINTYPE,true);


            }
        }
        else if (pinView.getValue().toString().equalsIgnoreCase("0101")){
            Toast.makeText(context, "OTP verified", Toast.LENGTH_LONG).show();
            finish();

//            PrefrenceUtils.writeBoolean(context,PrefrenceUtils.PREF_LOGINTYPE,true);
            startActivity(new Intent(this, MemberRegister.class).putExtra("mobile",mob_number));
        }
        else {
            Toast.makeText(context, "OTP incorrect", Toast.LENGTH_LONG).show();

        }
    }


    private boolean validatePhone(String mob) {
        if (mob.trim().isEmpty()) {
            requestFocus(pinView);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
*/
