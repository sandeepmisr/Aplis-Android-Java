package com.edu.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.DrawerModel;
import com.edu.aplis.R;
import com.edu.browse.BrowseFragment;
import com.edu.discover.DiscoverActivity;
import com.edu.search.SearchResultActivity;
import com.edu.slider.AddTaskActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainFragment extends Fragment implements AddTaskActivity.Messageinterface {
    TabLayout tabLayout;
    Context context;
    Boolean isshow = false;
    private AddTaskActivity addTaskActivity;
    ArrayList<DrawerModel> drawerModelArrayList;
    DrawerModel drawerModel;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        context = getActivity();


//        hideSystemUI();
        tabLayout = rootView.findViewById(R.id.tabs);
//        addTaskActivity = new AddTaskActivity(this);
//        drawerModelArrayList = new ArrayList<>();
//
//        for (int i = 0; i < 7; i++) {
//            drawerModel = new DrawerModel();
//            drawerModel.setTitle("Favorites");
//            drawerModelArrayList.add(drawerModel);
//        }
        tabLayout.setSelectedTabIndicatorHeight(10);
        tabLayout.addTab(tabLayout.newTab().setText("Explore"));
        tabLayout.addTab(tabLayout.newTab().setText("Browse"));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(context);
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                if (i == 0) {
                    tabTextView.setTextSize(24);
                    tabTextView.setTextColor(getResources().getColor(R.color.textColblack));
                    Typeface font = Typeface.createFromAsset(context.getAssets(),
                            "fonts/playfairdisplaybold.ttf");
                    tabTextView.setTypeface(font);


                }

            }


            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, new DiscoverActivity()).commit();

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();

                    switch (tab.getPosition()) {
                        case 1:
                            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                            ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                            int tabChildsCount = vgTab.getChildCount();
                            for (int i = 0; i < tabChildsCount; i++) {
                                View tabViewChild = vgTab.getChildAt(i);
                                if (tabViewChild instanceof TextView) {
                                    ((TextView) tabViewChild).setTextSize(24);
                                    ((TextView) tabViewChild).setTextColor(getResources().getColor(R.color.textColblack));
                                    Typeface font = Typeface.createFromAsset(context.getAssets(),
                                            "fonts/playfairdisplaybold.ttf");
                                    ((TextView) tabViewChild).setTypeface(font);
                                }
                            }
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.pager, new BrowseFragment()).commit();
                            break;

                        case 0:
                            ViewGroup vg1 = (ViewGroup) tabLayout.getChildAt(0);
                            ViewGroup vgTab1 = (ViewGroup) vg1.getChildAt(tab.getPosition());
                            int tabChildsCount1 = vgTab1.getChildCount();
                            for (int i = 0; i < tabChildsCount1; i++) {
                                View tabViewChild = vgTab1.getChildAt(i);
                                if (tabViewChild instanceof TextView) {
                                    ((TextView) tabViewChild).setTextSize(24);
                                    ((TextView) tabViewChild).setTextColor(getResources().getColor(R.color.textColblack));
                                    ((TextView) tabViewChild).setTextColor(getResources().getColor(R.color.textColblack));
                                    Typeface font = Typeface.createFromAsset(context.getAssets(),
                                            "fonts/playfairdisplaybold.ttf");
                                    ((TextView) tabViewChild).setTypeface(font);
                                }
                            }
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.pager, new DiscoverActivity()).commit();
                            // Continue for each tab in TabLayout
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                    ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                    int tabChildsCount = vgTab.getChildCount();
                    for (int i = 0; i < tabChildsCount; i++) {
                        View tabViewChild = vgTab.getChildAt(i);
                        if (tabViewChild instanceof TextView) {
                            ((TextView) tabViewChild).setTextSize(14);
                        }
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }


            });

        }
        return rootView;
    }


//    public void hamburger(View view) {
//        Log.e("clockhamburger","click"+isshow+"");
//
//        if (isshow){
//            addTaskActivity.hide();
//            isshow = false;
//        }
//        else {
//            addTaskActivity.showdrawer(context,drawerModelArrayList);
//            isshow = true;
//        }
//    }

    public void image_search(View view) {
        startActivity(new Intent(context, SearchResultActivity.class));
    }

    @Override
    public void msg() {

    }
//    public static void onLogout(){
//        PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_DEVIC_TOKEN,"");
//        context.startActivity(new Intent(context, MainActivity.class));
//        context.finishAffinity();
//
//
//    }

//    public void hideSystemUI(){
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//    }


}
