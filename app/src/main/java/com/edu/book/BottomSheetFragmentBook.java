package com.edu.book;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.edu.aplis.R;
import com.edu.fav.Fav_Booklists;
import com.edu.search.Search_Booklists;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.HashMap;

public class BottomSheetFragmentBook extends BottomSheetDialogFragment implements ResponceQueues, View.OnClickListener {
    TextView submit;
    TextView swipe;
    EditText user_name;
    Context context;
    RelativeLayout add_to_fav;
    RelativeLayout add_to_sessions;
    HashMap<String,String> hashMap = new HashMap<>();
    String book_id= "";
    ImageView img_fav;
    Boolean isFav;

    public BottomSheetFragmentBook(String book_id,Boolean isFav) {
        this.book_id = book_id;
        this.isFav = isFav;
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, null);
        dialog.setContentView(view);
        context = getActivity();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        add_to_fav  =dialog.findViewById(R.id.add_to_fav);
        add_to_sessions  =dialog.findViewById(R.id.add_to_sessions);
        img_fav  =dialog.findViewById(R.id.img_fav);

        add_to_fav.setOnClickListener(this);
        add_to_sessions.setOnClickListener(this);

        if (isFav){
            img_fav.setImageResource(R.drawable.favafter);
        }
//        submit  =dialog.findViewById(R.id.submit);
//        swipe  =dialog.findViewById(R.id.swipe);
//        linear_register  =dialog.findViewById(R.id.linear_register);
//        linear_otp  =dialog.findViewById(R.id.linear_otp);
//        parent  =dialog.findViewById(R.id.parent);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                android.R.layout.simple_dropdown_item_1line, STORES);
//         MaterialBetterSpinner materialBetterSpinner = dialog.findViewById(R.id.stores_list);
//        materialBetterSpinner.setAdapter(adapter);
//
//
//        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                int selectedvalue = i;
//                Log.e("select","spiner"+i+"");
//
//                switch (selectedvalue){
//                    case 0:
//                        location_id = "dlf";
//                        break;
//                    case 1:
//                        location_id = "IDfs01";
//                        break;
//                    case 2:
//                        location_id = "kochair";
//                        break;
//                    case 3:
//                        location_id = "kolair";
//                        break;
//                    case 4:
//                        location_id = "mumair";
//                        break;
//                }
//                Log.e("select",location_id+"");
//
//
//            }
//
//        });
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                makehttpcall(Cons.SIGNUP_URL+PrefrenceUtils.readString(context,PrefrenceUtils.PREF_MOBILE,"")+"&locationId="+location_id+"&name="+user_name.getText().toString());
//
//                Log.e("TAGBTMSHEET","mobile"+location_id);
//            }
//        });
//
//        swipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("TAGBTMSHEET","OTP is"+PrefrenceUtils.readString(context,PrefrenceUtils.PREF_OTP,""));
//                if (pinView.getValue().equalsIgnoreCase(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_OTP,""))) {
////                    Log.e("OTP", Otpclass.generateOTP() + "");
//
////                Log.e("TAGBTMSHEET",user_mob.getText().toString());
//
//                    toggle(linear_register);
//                }
//                else{
//                    Toast.makeText(getContext(), "Invalid or incorrect OTP", Toast.LENGTH_SHORT).show();
//                }
////                makehttpcall(Cons.SIGNUP_URL+user_mob.getText().toString()+"&locationId=20092547abcd" );
//
//            }
//        });
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
//        CoordinatorLayout.Behavior behavior = params.getBehavior();
//
//        if (behavior != null && behavior instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//                @Override
//                public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                    String state = "";
//
//                    switch (newState) {
//                        case BottomSheetBehavior.STATE_DRAGGING: {
//                            state = "DRAGGING";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_SETTLING: {
//                            state = "SETTLING";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_EXPANDED: {
//                            state = "EXPANDED";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_COLLAPSED: {
//                            state = "COLLAPSED";
//                            break;
//                        }
//                        case BottomSheetBehavior.STATE_HIDDEN: {
//                            dismiss();
//                            state = "HIDDEN";
//                            break;
//                        }
//                    }
//
////                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
////                    Log.e("TAGBTMSHEETSLIDE",user_name.getText().toString());
//
//                }
//            });
//        }
//    }
//
//
//    private void makehttpcall(String url) {
////        hashMap.put("app_id", "c6817759");
////        hashMap.put("app_key", "ba32ed8e690231157e208b6ad4a24fad");
//        ApiService task = new ApiService(context, this, url, hashMap, 1);
//        task.execute();
//
//    }
//
//    @Override
//    public void responceQue(String responce, String url, String extra_text) {
//        if (url.contains(Cons.SIGNUP_URL)){
//            try{
//                JSONObject jsonObject = new JSONObject(responce);
//                String status  = jsonObject.getString("status");
//                if (status.equalsIgnoreCase("true")){
//                    String message  = jsonObject.getString("message");
//                    PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_NAME,message);
//                    showsnackbar();
//                }
//            }
//            catch (Exception e){
//
//            }
//        }
//        Log.e("REGISTER",responce+"");
//
//    }
//
//    private void toggle(LinearLayout parent) {
//        linear_otp.setVisibility(View.GONE);
//        Transition transition = new Slide(Gravity.BOTTOM);
//        transition.setDuration(600);
//        transition.addTarget(R.id.linear_register);
//        TransitionManager.beginDelayedTransition(parent, transition);
//        parent.setVisibility(View.VISIBLE);
//    }
//
//    private void showsnackbar() {
//        Toast.makeText(context,"Thank You and Welcome to Koncept",Toast.LENGTH_LONG).show();
//        new CountDownTimer(2000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//            @Override
//            public void onFinish() {
//                startActivity(new Intent(context,Feedback_Activity.class).putExtra("intentvalue","null"));
//
//            }
//        }.start();
//        Snackbar snackbar = Snackbar.make(parent, "Thank You and Welcome To Koncept", Snackbar.LENGTH_LONG);
//        View sbView = snackbar.getView();
//        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//        snackbar.setActionTextColor(getResources().getColor(R.color.white));
//        snackbar.show();
//        new CountDownTimer(2000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//            @Override
//            public void onFinish() {
////                startActivity(new Intent(context,Feedback_Activity.class).putExtra("intentvalue","null"));
//            }
//        }.start();

    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {

        if (url.contains(Cons.GET_FAVURL)){
            startActivity(new Intent(context, Fav_Booklists.class) .putExtra("title", "")
                    .putExtra("description","")
                    .putExtra("unique_id", "")
                    .putExtra("unique_url", Cons.GET_FAVURL));
        }
        else {
            img_fav.setImageResource(R.drawable.favafter);
        }
        Log.e("favresponse","response"+responce);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.add_to_fav:
                makeHttpCall(Cons.ADD_TO_FAVURL+book_id,2);
                break;

                case R.id.add_to_sessions:
                makeHttpCall(Cons.GET_FAVURL,1);
                break;

        }

    }

    private void makeHttpCall(String url,int posttype) {
        Log.e("URLFAV",url);
        hashMap.clear();
        ApiService apiService = new ApiService(context,this,url ,hashMap,posttype);
        apiService.execute();
    }


}
