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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu.account.Profile;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.MainActivity;
import com.edu.aplis.R;
import com.edu.browse.BrowseFragment;
import com.edu.dashboard.MainFragment;
import com.edu.discover.DiscoverActivity;
import com.edu.fav.Fav_Booklists;
import com.edu.preference.PrefrenceUtils;
import com.edu.search.SearchResultActivity;
import com.edu.web.PrivacyPolicy;
import com.edu.web.TermsConditions;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class HomeActivityNew extends AppCompatActivity implements View.OnClickListener {


    BottomNavigationView bottomNavigation;



    private ImageView allsong, albums, fav;
    DrawerLayout drawer_layout;
    Boolean isVisibleFragment=false;
    TextView text_tool;
    ImageView image_search;
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
        setContentView(R.layout.main_activitynew);
        context= HomeActivityNew.this;
        bottomNavigation = findViewById(R.id.bottom_navigation);
        text_tool = findViewById(R.id.text_tool);
        image_search = findViewById(R.id.image_search);

        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SearchResultActivity.class));
            }
        });
        openFragment(new DiscoverActivity());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_discover:
                        text_tool.setText("Discover");
                        openFragment(new DiscoverActivity());

                        return true;
                    case R.id.navigation_browse:
                        text_tool.setText("Browse");

                        openFragment(new BrowseFragment());
                        return true;
                    case R.id.navigation_favourites:
                        text_tool.setText("Favorites");
                        openFragment(new Fav_Booklists());
                        return true;
                    case R.id.navigation_account:
                        text_tool.setText("Account");
                        openFragment(new Profile());
                        return true;
                }
                return false;
            }
        });
//        drawer_layout =  findViewById(R.id.drawer_layout);
//        imageview_settings = findViewById(R.id.imageview_settings);
//        imageview_slider = findViewById(R.id.imageview_slider);
//        imageView_search =  findViewById(R.id.imageview_search);
//        framgment_container = findViewById(R.id.framgment_container);
//        text_darkmode = findViewById(R.id.text_darkmode);
//        navtextsupport = findViewById(R.id.navtextsupport);




//        NavigationView nav_view =findViewById(R.id.nav_view);
//
//        nav_view.bringToFront();
        client = new GoogleApiClient.Builder(context).addApi(AppIndex.API).build();

        //callanalytics();

//        imageview_settings.setOnClickListener(this);
//        imageview_slider.setOnClickListener(this);
//        imageView_search.setOnClickListener(this);
//        setFragment(0);



    }


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
        final Intent intent = new Intent(Intent.ACTION_SEND);
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


//    public void support(View view) {
//        showhelpmethod("support@aplis.com");
//
//    }
//
//    public void favrite(View view) {
//        startActivity(new Intent(context, Fav_Booklists.class));
//
//    }
//
//    public void logouts(View view) {
//        PrefrenceUtils.writeString(context, PrefrenceUtils.PREF_DEVIC_TOKEN, "");
//        PrefrenceUtils.writeBoolean(context, PrefrenceUtils.PREF_LOGINTYPE, false);
//
//        startActivity(new Intent(context, MainActivity.class));
//        finishAffinity();
//    }
//
//    public void profileoption(View view) {
//        startActivity(new Intent(context, Profile.class));
//    }
//
//    public void terms(View view) {
//        startActivity(new Intent(context, TermsConditions.class));
//
//    }
//
//    public void privacy(View view) {
//        startActivity(new Intent(context, PrivacyPolicy.class));
//
//    }

 /*   private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/


    public void openFragment(Fragment fragment) {
        Log.e("FRAGMENT","true");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }



}
