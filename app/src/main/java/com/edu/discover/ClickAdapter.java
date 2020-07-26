package com.edu.discover;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

public interface ClickAdapter {
    void clickoncard(ImageView view, int position, String mimetype, String content, String title, String long_desc , List<Books> booksList, String image_Ar
    , String discover_id);
}
