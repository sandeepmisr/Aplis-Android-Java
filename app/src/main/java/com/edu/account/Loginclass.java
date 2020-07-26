package com.edu.account;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.edu.aplis.R;
import com.edu.dashboard.DashboardMainActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import java.util.HashMap;

public class Loginclass extends AppCompatActivity  {
    Button button_singin;
    TextView text_signup;
    Context context;
//    private User user;
    LinearLayout nestedScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context = Loginclass.this;

        nestedScrollView= findViewById(R.id.nestedScrollView);
//        Util.onActivityCreateSetTheme(this, PrefrenceUtils.readInteger(this, PrefrenceUtils.PREF_THEME, 0));

        button_singin = findViewById(R.id.button_singin);
        text_signup = findViewById(R.id.text_signup);

        setAnimation();
//        openAR();

    }


    public void setAnimation(){
        ActivityOptions.makeSceneTransitionAnimation(this);
    }
    private void openAR(){
        Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
        sceneViewerIntent.setData(Uri.parse("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"));
        sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
        startActivity(sceneViewerIntent);
    }

    private void openLoginPopup(){
            final Dialog dialog = new Dialog(new ContextThemeWrapper(this, R.style.DialogAnimation));

            //tell the Dialog to use the dialog.xml as it's layout description
            dialog.setContentView(R.layout.sign_inform);
            dialog.setTitle("LOGIN FORM");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            getWindow().setGravity(Gravity.BOTTOM);
//       TextView txt = (TextView) dialog.findViewById(R.id.txt);
//
//       txt.setText("This is an Android custom Dialog Box Example! Enjoy!");
//
            final EditText login_id = dialog.findViewById(R.id.login_id);
            final EditText password = dialog.findViewById(R.id.password);
            TextView dialogsigninButton =  dialog.findViewById(R.id.login_btn);

//            Button dialogsignupButton = (Button) dialog.findViewById(R.id.btn_Signup);

            dialogsigninButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
if (!validateEmail(login_id)){
    return;
}if (!validatePswd(password)){
    return;
}


                    //                startActivity(new Intent(Loginclass.this, DashboardMainActivity.class));
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    dialog.dismiss();
                }
            });

//            dialogsignupButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(context, SignupActivity.class));
//                    dialog.dismiss();
//                }
//            });

            dialog.show();

        }






    public void textsignup(View view) {
       startActivity(new Intent(context,Signupclass.class));
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


    public void textsignin(View view) {
        openLoginPopup();
    }


}
