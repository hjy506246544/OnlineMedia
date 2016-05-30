package com.gvs.onlinemedia.widget;

import com.gvs.onlinemedia.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 页面标题栏公用控件
 */
public class Header extends RelativeLayout {


    public Header(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Header(Context context) {
        super(context);
        init();
    }

    private void init() {
         int p = (int) (getResources().getDisplayMetrics().density * 10);
         setPadding(p, 0, p, 0);
    }

    public void setBackgroundColor(int color){
	setBackgroundColor(color);
    }

    public void setTitle(String text) {
    	removeAllViews();//2015-8-4 jia
    	TextView tv = new TextView(getContext());
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        tv.setText(text);
        tv.getPaint().setFakeBoldText(true);
        setTitleView(tv);
    }

    public void setTitleView(View view) {
        RelativeLayout.LayoutParams viewLp = new RelativeLayout.LayoutParams(-2, -2);
        viewLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, viewLp);
    }

    public void setRightView(View view, OnClickListener listener) {
        RelativeLayout.LayoutParams ivLp = new RelativeLayout.LayoutParams(-2, -2);
        ivLp.addRule(RelativeLayout.CENTER_VERTICAL);
        ivLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(view, ivLp);
        view.setOnClickListener(listener);
    }

    public void setRightImageViewRes(int res, OnClickListener listener) {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(res);
        setRightView(iv, listener);
    }

    @SuppressLint("ResourceAsColor")
	public void setTextViewRes(int text, OnClickListener listener) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(14);
        tv.setGravity(500);
        tv.setTextColor(getResources().getColor(R.color.white));
        setRightView(tv, listener);
    }

    public void setLeftView(View view, OnClickListener listener) {
        RelativeLayout.LayoutParams ivLp = new RelativeLayout.LayoutParams(-2, -2);
        ivLp.addRule(RelativeLayout.CENTER_VERTICAL);
        ivLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(view, ivLp);
        view.setOnClickListener(listener);
    }

    public void setLeftImageVewRes(int res, OnClickListener listener) {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(res);
        setLeftView(iv, listener);
    }

}
