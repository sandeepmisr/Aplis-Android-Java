package com.edu.tutorialviewpager;
import com.edu.aplis.R;


public enum ModelObject {
    RED(R.string.red, R.layout.a_tutorial),
    BLUE(R.string.red, R.layout.b_tutorial),
    GREEN(R.string.green, R.layout.c_tutorial),
    YELLOW(R.string.yellow, R.layout.d_tutorial);


    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
