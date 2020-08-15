package com.edu.browse;

import android.widget.ImageView;

import com.edu.discover.Books;

import java.util.List;

public interface ClickBrowseAdapter {
    void clickoncard(ImageView view, String book_id, String title,String url);
}
