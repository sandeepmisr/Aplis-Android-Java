package com.edu.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.edu.aplis.LoginPageActivity;
import com.edu.aplis.MainActivity;
import com.edu.aplis.R;
import com.edu.home.HomeActivityNew;
import com.edu.preference.PrefrenceUtils;
import com.edu.reset.Changepassword_Activity;
import com.edu.reset.EmailResponse;
import com.edu.reset.Otp_Activity;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.web.PrivacyPolicy;
import com.edu.web.TermsConditions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {
    Context context;
    EditText textnamesaved;
    EditText ed_gendersaved;
    EditText ed_dobsaved;
    EditText ed_emailsaved;
    EditText subject_category;

    TextView text_changepswd;
    TextView navtextprivacy;
    TextView navtextterms;
    TextView logout;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_account, container, false);

        context = getActivity();

        ed_gendersaved = rootView.findViewById(R.id.ed_gendersaved);
        ed_emailsaved = rootView.findViewById(R.id.ed_emailsaved);
        ed_dobsaved = rootView.findViewById(R.id.ed_dobsaved);
        subject_category = rootView.findViewById(R.id.subject_category);
        textnamesaved = rootView.findViewById(R.id.textnamesaved);
        text_changepswd = rootView.findViewById(R.id.text_changepswd);
        navtextprivacy = rootView.findViewById(R.id.navtextprivacy);
        navtextterms = rootView.findViewById(R.id.navtextterms);
        logout = rootView.findViewById(R.id.logout);
        progressBar = rootView.findViewById(R.id.progressBar);

        ed_emailsaved.setText(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_EMAIL, ""));
        ed_gendersaved.setText(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_GENDER, ""));
        ed_dobsaved.setText(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_DOB, ""));
        textnamesaved.setText(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_NM, ""));
        subject_category.setText(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_SCHOOLTYPE, ""));

        text_changepswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
            }
        });

        navtextterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TermsConditions.class));
            }
        });

        navtextprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PrivacyPolicy.class));

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, "");
                PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, false);

                startActivity(new Intent(context, LoginPageActivity.class));
            }
        });
        return  rootView;
    }


    public void change() {
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
                        Toast.makeText(context,"Username not registered with us!!",Toast.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
                Toast.makeText(context,"Password has been changed successfully!!",Toast.LENGTH_LONG).show();


        }
    }
}
