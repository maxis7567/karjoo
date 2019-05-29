package com.hinext.maxis7567.mstools;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyBoardManger {
    private Context context;
    private View view;

    public SoftKeyBoardManger(Context context,View view) {
        this.context = context;
        this.view = view;
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    public void showSoftKeyboard() {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                   context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
