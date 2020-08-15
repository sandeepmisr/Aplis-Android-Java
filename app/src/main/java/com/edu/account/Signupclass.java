package com.edu.account;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;

import java.util.HashMap;

public class Signupclass extends AppCompatActivity implements ResponceQueues {
    Button button_singin;
    TextView text_signup;
    Context context;
    HashMap<String,String> hashMap=new HashMap<>();
//    private User user;
    LinearLayout nestedScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_upform);
        context = Signupclass.this;

        final EditText edit_fname = findViewById(R.id.edit_fname);
        final EditText edit_lname = findViewById(R.id.edit_lname);
        final EditText edit_age = findViewById(R.id.edit_age);
        final EditText edit_pswd = findViewById(R.id.edit_pswd);
        final EditText edit_confrirmpswd = findViewById(R.id.edit_confrirmpswd);
        final EditText edit_email = findViewById(R.id.edit_email);
        final TextView edit_mobile = findViewById(R.id.edit_mobile);
        TextView signup_btn = findViewById(R.id.signup_btn);


        //callanalytics();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validatePhone(edit_mobile)) {
                    return;
                }
                if (!validatefName(edit_fname)) {
                    return;
                }
                if (!validatelName(edit_lname)) {
                    return;
                }
                if (!validateAge(edit_age)) {
                    return;
                }
                if (!validatePswd(edit_pswd)) {
                    return;
                }
                if (!validateCpswd(edit_confrirmpswd)) {
                    return;
                }
                if (!validateEmail(edit_email)) {
                    return;
                }

                if (!edit_pswd.getText().toString().equals(edit_confrirmpswd.getText().toString())) {
                    Toast.makeText(context, "Password doesn't match", Toast.LENGTH_LONG).show();
                } else {

                    hashMap.clear();
                    hashMap.put("first_name", edit_email.getText().toString());
                    hashMap.put("last_name", edit_lname.getText().toString());
                    hashMap.put("gender", "MALE");
                    hashMap.put("mobile", edit_mobile.getText().toString());
                    hashMap.put("email", edit_email.getText().toString());
                    hashMap.put("age", edit_age.getText().toString());
                    hashMap.put("password", edit_pswd.getText().toString());
                    makehttpcall(Cons.SIGNUP_URL);

                }
                //                startActivity(new Intent(Loginclass.this, DashboardMainActivity.class));
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }




    private void makehttpcall(String url) {
//        hashMap.put("app_id", "c6817759");
//        hashMap.put("app_key", "ba32ed8e690231157e208b6ad4a24fad");
        ApiService task = new ApiService(context, this, url, hashMap, 2);
        task.execute();

    }


    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("RESvPONSETIMESLOT","try "+responce);

    }


    private boolean validatePswd(TextView pswd) {
        if (pswd.getText().toString().trim().isEmpty()) {
            requestFocus(pswd);
            return false;
        }
        return true;
    }
    private boolean validateEmail(TextView email) {
        if (email.getText().toString().trim().isEmpty()) {
            requestFocus(email);
            return false;
        }
        return true;
    }
    private boolean validateCpswd(TextView edit_confrirmpswd) {
        if (edit_confrirmpswd.getText().toString().trim().isEmpty()) {
            requestFocus(edit_confrirmpswd);
            return false;
        }
        return true;
    }

    private boolean validatePhone(TextView mob) {
        if (mob.getText().toString().trim().isEmpty()) {
            requestFocus(mob);
            return false;
        }
        return true;
    }

    private boolean validatefName(TextView fname) {
        if (fname.getText().toString().trim().isEmpty()) {
            requestFocus(fname);
            return false;
        }
        return true;
    }
    private boolean validateAge(TextView age) {
        if (age.getText().toString().trim().isEmpty()) {
            requestFocus(age);
            return false;
        }
        return true;
    }

    private boolean validatelName(TextView lname) {
        if (lname.getText().toString().trim().isEmpty()) {
            requestFocus(lname);
            return false;
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/



}
