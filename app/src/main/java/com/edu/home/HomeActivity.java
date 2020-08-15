package com.edu.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu.account.Loginclass;
import com.edu.account.Profile;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.MainActivity;
import com.edu.aplis.R;
import com.edu.dashboard.MainFragment;
import com.edu.fav.Fav_Booklists;
import com.edu.preference.PrefrenceUtils;
import com.edu.search.SearchResultActivity;
import com.edu.web.PrivacyPolicy;
import com.edu.web.TermsConditions;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView allsong, albums, fav;
    DrawerLayout drawer_layout;
    Boolean isVisibleFragment=false;
    Boolean mSlideState=false;
    ImageView imageview_slider;
    TextView text_darkmode;
    ImageView imageview_back;
    ImageView imageView_search;
    Context context;
    public Boolean isVisible=false;
    ImageView imageview_settings;
    private GoogleApiClient client;
    FrameLayout framgment_container;
    TextView navtextsupport;
    String c_theme="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tIcon(android.R.color.transparent);
        setContentView(R.layout.activity_home);
        context=HomeActivity.this;

//        allsong =  findViewById(R.id.home);
//        albums =  findViewById(R.id.fav);
//        fav =  findViewById(R.id.account);
        drawer_layout =  findViewById(R.id.drawer_layout);
        imageview_settings = findViewById(R.id.imageview_settings);
        imageview_slider = findViewById(R.id.imageview_slider);
//        imageview_back = findViewById(R.id.imageview_back);
        imageView_search =  findViewById(R.id.imageview_search);
        framgment_container = findViewById(R.id.framgment_container);
        text_darkmode = findViewById(R.id.text_darkmode);
        navtextsupport = findViewById(R.id.navtextsupport);

        NavigationView nav_view =findViewById(R.id.nav_view);

        nav_view.bringToFront();
        client = new GoogleApiClient.Builder(context).addApi(AppIndex.API).build();


// Obtain the shared Tracker instance.
        //callanalytics();



//        allsong.setOnClickListener(this);
//        albums.setOnClickListener(this);
//        fav.setOnClickListener(this);
        imageview_settings.setOnClickListener(this);
        imageview_slider.setOnClickListener(this);
//        imageview_back.setOnClickListener(this);
        imageView_search.setOnClickListener(this);
//        drawer_layout.closeDrawers();

//        settext_darkmode();
        setFragment(0);



    }

//    private void settext_darkmode() {
//        if (PrefrenceUtils.readInteger(context,PrefrenceUtils.PREF_THEME,0)==1){
//            PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_THEME_NAME,getResources().getString(R.string.lightmode));
//            text_darkmode.setText(getResources().getString(R.string.lightmode));
//            c_theme = getResources().getString(R.string.lightmode);
//        }
//        else{
//            PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_THEME_NAME,getResources().getString(R.string.darkmode));
//            text_darkmode.setText(getResources().getString(R.string.darkmode));
//            c_theme = getResources().getString(R.string.darkmode);
//
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();

//        if (!c_theme.equalsIgnoreCase(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_THEME_NAME,""))){
//            this.recreate();
//        }
//        this.recreate();
    }

    private void setFragment(int position) {
        switch (position) {
            case 0:
                replaceFragment(new MainFragment());
                break;
            case 1:
//                replaceFragment(new CourseSubjects(this));

//                replaceFragment(new FavouriteFragment());
                break;
            case 2:
//                replaceFragment(new SettingsFragment());
                break;


        }
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }

        public void replaceFragment(Fragment fragment) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.framgment_container, fragment);

            if(isVisibleFragment) {
                ft.addToBackStack("root_fragment");
            }
            isVisibleFragment=true;

            ft.commit();
        }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.navtextsupport:
                showhelpmethod("support@aplis.com");
                break;
            case R.id.navtextfav:
                startActivity(new Intent(context, Fav_Booklists.class));

                break;
            case R.id.navtextlogout:
                startActivity(new Intent(context, MainActivity.class));
                finishAffinity();
                break;


            case R.id.home:
                setFragment(0);

                break;
            case R.id.account:
                setFragment(2);
                break;

                case R.id.imageview_search:
                    startActivity(new Intent(context, SearchResultActivity.class));
//                    finishAffinity();
//                    getFragmentManager().popBackStack();
                break;

            case R.id.imageview_settings:
                if (isVisible){
                    isVisible=false;
                    AppIndex.AppIndexApi.end(client, getIndexApiAction());
                    client.disconnect();

                    disconnect();

//                    FirebaseUserActions.getInstance().end(getAction());


                }
                else {
                    isVisible = true;
                    startActivity(new Intent("android.settings.CAST_SETTINGS"));
                }
                break;
            case  R.id.imageview_slider:
                if(!drawer_layout.isDrawerOpen(Gravity.LEFT)){
                    drawer_layout.openDrawer(Gravity.LEFT);

                }
                else{
                    drawer_layout.closeDrawers();

                }
                break;

            case R.id.fav:
                startActivity(new Intent(context, Fav_Booklists.class));

                    drawer_layout.closeDrawers();

                break;
//                case R.id.logout:
//                startActivity(new Intent(context, Loginclass.class));
//
//                    drawer_layout.closeDrawers();
//                    finishAffinity();
//
//                break;
            default:
                break;
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    private void disconnect() {
        MediaRouter mMediaRouter = (MediaRouter) context
                .getSystemService(Context.MEDIA_ROUTER_SERVICE);
        mMediaRouter.selectRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO,
                mMediaRouter.getDefaultRoute());
        mMediaRouter.clearUserRoutes();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            this.recreate();
        }
    }

    private void showhelpmethod(String email_id) {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email_id });

//        intent.putExtra(Intent.EXTRA_TEXT, "Mobile:"+ ""+PrefrenceUtils.readString(context,PrefrenceUtils.PREF_LASTNAME,""));
//        intent.putExtra(Intent.EXTRA_TEXT, "Email Id:"+ ""+PrefrenceUtils.readString(context,PrefrenceUtils.PREF_LASTNAME,""));
        startActivity(intent);
    }


    public void support(View view) {
        showhelpmethod("support@aplis.com");

    }

    public void favrite(View view) {
        startActivity(new Intent(context, Fav_Booklists.class));

    }

    public void logouts(View view) {
        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, "");
        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, false);

        startActivity(new Intent(context, MainActivity.class));
        finishAffinity();
    }

    public void profileoption(View view) {
        startActivity(new Intent(context, Profile.class));
    }

    public void terms(View view) {
        startActivity(new Intent(context, TermsConditions.class));

    }

    public void privacy(View view) {
        startActivity(new Intent(context, PrivacyPolicy.class));

    }

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/


}
