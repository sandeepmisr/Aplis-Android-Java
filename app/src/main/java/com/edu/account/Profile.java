package com.edu.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.MainActivity;
import com.edu.aplis.R;
import com.edu.preference.PrefrenceUtils;
import com.edu.reset.Changepassword_Activity;
import com.edu.reset.EmailResponse;
import com.edu.reset.Otp_Activity;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    Context context;
    TextView textnamesaved;
    EditText ed_gendersaved;
    EditText ed_dobsaved;
    EditText ed_emailsaved;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        context = Profile.this;

        ed_gendersaved = findViewById(R.id.ed_gendersaved);
        ed_emailsaved = findViewById(R.id.ed_emailsaved);
        ed_dobsaved = findViewById(R.id.ed_dobsaved);
        textnamesaved = findViewById(R.id.textnamesaved);
        progressBar = findViewById(R.id.progressBar);

        ed_emailsaved.setText(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_EMAIL,""));
        ed_gendersaved.setText(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_GENDER,""));
        ed_dobsaved.setText(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_DOB,""));
        textnamesaved.setText(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_NM,""));

    }

    public void imageback(View view) {
        finish();
    }

    public void change(View view) {
        progressBar.setVisibility(View.VISIBLE);

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<EmailResponse> call = api.getVerrifyEmailID(ed_emailsaved.getText().toString());

        call.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                progressBar.setVisibility(View.GONE);

                PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_EMAIL, ed_emailsaved.getText().toString());

                try {
                    EmailResponse emailResponse=response.body();
                    if (emailResponse.getStatus().equalsIgnoreCase("1")){
                        Toast.makeText(context,emailResponse.getEmail()+"!!",Toast.LENGTH_LONG).show();
                        startActivityForResult(new Intent(context, Changepassword_Activity.class),2);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (resultCode==RESULT_OK){
                Toast.makeText(context,"Password has been changed successfully!!",Toast.LENGTH_LONG).show();

            }
        }
    }
}
