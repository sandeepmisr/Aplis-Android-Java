package com.edu.slider;

import android.content.res.Resources;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.DrawerModel;
import com.edu.aplis.R;
import com.edu.dashboard.MainFragment;

import java.util.ArrayList;

public class AddTaskActivity{
    public static class SlideMenuAdapter extends ArrayAdapter<SlideMenuAdapter.MenuDesc> {
        Activity act;
        Boolean isshow = false;
        MenuDesc[] items;
        class MenuItem {
            public TextView label;
            public ImageView icon;
        }

        static class MenuDesc {
            public int icon;
            public String label;
        }
        public SlideMenuAdapter(Activity act, MenuDesc[] items) {
            super(act, R.id.menu_label, items);
            this.act = act;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = act.getLayoutInflater();
                rowView = inflater.inflate(R.layout.menu_listitem, null);
                MenuItem viewHolder = new MenuItem();
                viewHolder.label = (TextView) rowView.findViewById(R.id.menu_label);
                viewHolder.icon = (ImageView) rowView.findViewById(R.id.menu_icon);
                rowView.setTag(viewHolder);
            }

            MenuItem holder = (MenuItem) rowView.getTag();
//            String s = items[position].label;
            holder.label.setText("logout");

            holder.label.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    MainFragment.onLogout();
                }
            });
            holder.icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Messageinterface messageinterface = new AddTaskActivity();
                }
            });

            return rowView;
        }
    }

    private static boolean menuShown = false;
    private static View menu;
    private static LinearLayout content;
    private static FrameLayout parent;
    private static int menuSize;
    private static int statusHeight = 0;
    private Activity act;
    Boolean isShow =true;
    public AddTaskActivity(Activity act) {
        this.act = act;
    }
    //call this in your onCreate() for screen rotation
    public void checkEnabled(Activity context) {
        if(menuShown)
            this.show(context,false);
    }

    public void showdrawer(Activity context, ArrayList<DrawerModel> drawerModelArrayList) {
//get the height of the status bar
        if(statusHeight == 0) {
            Rect rectgle = new Rect();
            Window window = act.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
            statusHeight = rectgle.top;
        }

            this.show(context, true);


    }


    public interface Messageinterface{
        void msg();
    }
    public void show(Activity context, boolean animate) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        menuSize = width/2;
        content = ((LinearLayout) act.findViewById(android.R.id.content).getParent());
        FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
        parm.setMargins(menuSize, 0, -menuSize, 0);
        content.setLayoutParams(parm);
//animation for smooth slide-out
        TranslateAnimation ta = new TranslateAnimation(-menuSize, 0, 0, 0);
        ta.setDuration(500);
        if(animate)
        content.startAnimation(ta);
        parent = (FrameLayout) content.getParent();
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menu = inflater.inflate(R.layout.menu, null);

        LinearLayout menu_layoutdrawer = menu.findViewById(R.id.menu_layoutdrawer);
        ViewGroup.LayoutParams params = menu_layoutdrawer.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = width/2;
        menu_layoutdrawer.setLayoutParams(params);
        FrameLayout.LayoutParams lays = new FrameLayout.LayoutParams(-1, -1, 3);
        lays.setMargins(0,statusHeight, 0, 0);
        menu.setLayoutParams(lays);
        parent.addView(menu);
        ListView list = (ListView) act.findViewById(R.id.menu_listview);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //handle your menu-click
            }
        });
        if(animate)
            menu.startAnimation(ta);
        menu.findViewById(R.id.overlay).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskActivity.this.hide();
            }
        });
        Functions.enableDisableViewGroup((LinearLayout) parent.findViewById(android.R.id.content).getParent(), false);
//        ((ExtendedViewPager) act.findViewById(R.id.viewpager)).setPagingEnabled(false);
//        ((ExtendedPagerTabStrip) act.findViewById(R.id.viewpager_tabs)).setNavEnabled(false);
        menuShown = true;
        this.fill();
    }
    public void fill() {
        ListView list = (ListView) act.findViewById(R.id.menu_listview);
        SlideMenuAdapter.MenuDesc[] items = new SlideMenuAdapter.MenuDesc[7];
        //fill the menu-items here
        SlideMenuAdapter adap = new SlideMenuAdapter(act, items);
        list.setAdapter(adap);
    }
    public void hide() {
        TranslateAnimation ta = new TranslateAnimation(0, -menuSize, 0, 0);
        ta.setDuration(500);
        menu.startAnimation(ta);
        parent.removeView(menu);

        TranslateAnimation tra = new TranslateAnimation(menuSize, 0, 0, 0);
        tra.setDuration(500);
        content.startAnimation(tra);
        FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
        parm.setMargins(0, 0, 0, 0);
        content.setLayoutParams(parm);
        Functions.enableDisableViewGroup((LinearLayout) parent.findViewById(android.R.id.content).getParent(), true);
//        ((ExtendedViewPager) act.findViewById(R.id.viewpager)).setPagingEnabled(true);
//        ((ExtendedPagerTabStrip) act.findViewById(R.id.viewpager_tabs)).setNavEnabled(true);
        menuShown = false;
    }

    private static class Functions {
        public static int dpToPx(int dp, Context ctx) {
            Resources r = ctx.getResources();
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        }
        //originally: http://stackoverflow.com/questions/5418510/disable-the-touch-events-for-all-the-views
//modified for the needs here
        public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if(view.isFocusable())
                    view.setEnabled(enabled);
                if (view instanceof ViewGroup) {
                    enableDisableViewGroup((ViewGroup) view, enabled);
                } else if (view instanceof ListView) {
                    if(view.isFocusable())
                        view.setEnabled(enabled);
                    ListView listView = (ListView) view;
                    int listChildCount = listView.getChildCount();
                    for (int j = 0; j < listChildCount; j++) {
                        if(view.isFocusable())
                            listView.getChildAt(j).setEnabled(false);
                    }
                }
            }
        }
    }
}
