package com.vjtechsolution.aiceluckywheel;

public enum  ModelObject {
    RED(1, R.layout.view_red),
    BLUE(2, R.layout.view_blue),
    GREEN(3, R.layout.view_green);

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
