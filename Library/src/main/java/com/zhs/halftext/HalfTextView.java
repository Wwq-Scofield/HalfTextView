package com.zhs.halftext;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/25.
 */

public class HalfTextView extends TextView {
    private int mRealWidth;

    public HalfTextView(Context context) {
        this(context, null);
    }

    public HalfTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     */
    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams1 = getLayoutParams();
                int margin = 0;
                if (layoutParams1 instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutParams1;
                    margin = layoutParams.leftMargin + layoutParams.rightMargin;
                } else if (layoutParams1 instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layoutParams1;
                    margin = layoutParams.leftMargin + layoutParams.rightMargin;
                }
                Log.d("wwq", "margin= " + margin);
                mRealWidth =  getScreenWidth() - getPaddingLeft() + getPaddingRight() - margin;
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d("wwq", "-----------------------" + getLineCount());
                if (getLineCount() >= 3) {
                    int line = 2;
                    int lineStartIndex = getLayout().getLineStart(line);
                    int lineEndIndex = getLayout().getLineEnd(line);
                    caculeate(lineStartIndex, lineEndIndex);
                    String s = "";
                    if (currentIndex < lineEndIndex) {
                        s = getText().subSequence(0, currentIndex - 1) + "...";
                    } else {
                        s = getText().subSequence(0, currentIndex) + "";
                    }
                    setText(s);
                }
            }
        });
    }

    /**
     * setText 调用这个方法即可
     *
     * @param text
     */
    public void reSetText(String text) {
        Log.d("wwq", "text= " + text);
        StringBuilder sb = new StringBuilder();
        for (int m = 0; m < text.length(); m++) {
            char c = text.charAt(m);
            if (c != '\n') {
                sb.append(c);
            }
        }
        Log.d("wwq", "sb= " + sb.toString());
        setText(sb.toString());
        init();
    }
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }
    int currentIndex;

    private void caculeate(int lineStartIndex, int lineEndIndex) {
        int count = 0;
        currentIndex = 0;
        Log.d("wwq", "lineStart= " + lineStartIndex + "  lineEndIndex=" + lineEndIndex);
        for (int i = lineStartIndex; i < lineEndIndex; i++) {
            char c = getText().charAt(i);
            count = count + (int) getPaint().measureText(String.valueOf(c));
            if (count >= mRealWidth / 2) {
                currentIndex = i;
                Log.d("wwq", "i==" + i + "  index=" + currentIndex);
                break;
            } else {
                currentIndex = lineEndIndex;
            }
        }
        Log.d("wwq", "count= " + count);
    }
}
