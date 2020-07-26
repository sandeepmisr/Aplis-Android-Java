package com.edu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.R;
import com.navdrawer.SimpleSideDrawer;

public class SliderActivity extends AppCompatActivity {
    SimpleSideDrawer slide_me;
    Button left_button, right_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(R.layout.left_menu);
        slide_me.setRightBehindContentView(R.layout.right_menu);

        left_button = (Button) findViewById(R.id.left_buton);
        right_button = (Button) findViewById(R.id.right_buton);
        left_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slide_me.toggleLeftDrawer();
            }
        });
        right_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slide_me.toggleRightDrawer();
            }
        });
    }
}
