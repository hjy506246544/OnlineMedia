package com.gvs.onlinemedia.widget;

import java.util.HashMap;

import com.gvs.onlinemedia.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 瀛楁瘝绱㈠紩鏉�
 * 
 * @author Administrator
 * 
 */
public class QuickAlphabeticBar extends ImageButton {
	private TextView mDialogText; // 涓棿鏄剧ず瀛楁瘝鐨勬枃鏈
	private Handler mHandler; // 澶勭悊UI鐨勫彞鏌�
	private ListView mList; // 鍒楄〃
	private float mHight; // 楂樺害
	// 瀛楁瘝鍒楄〃绱㈠紩
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	// 瀛楁瘝绱㈠紩鍝堝笇琛�
	private HashMap<String, Integer> alphaIndexer;
	Paint paint = new Paint();
	boolean showBkg = false;
	int choose = -1;

	public QuickAlphabeticBar(Context context) {
		super(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 鍒濆鍖�
	public void init(Activity ctx) {
		//mDialogText = (TextView) ctx.findViewById(R.id.fast_position);
		if(mDialogText!=null){
			mDialogText.setVisibility(View.INVISIBLE);
		}
		mHandler = new Handler();
	}

	// 璁剧疆闇�瑕佺储寮曠殑鍒楄〃
	public void setListView(ListView mList) {
		this.mList = mList;
	}

	// 璁剧疆瀛楁瘝绱㈠紩鍝堝笇琛�
	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}

	// 璁剧疆瀛楁瘝绱㈠紩鏉＄殑楂樺害
	public void setHight(float mHight) {
		this.mHight = mHight;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int act = event.getAction();
		float y = event.getY();
		final int oldChoose = choose;
		// 璁＄畻鎵嬫寚浣嶇疆锛屾壘鍒板搴旂殑娈碉紝璁﹎List绉诲姩娈靛紑澶寸殑浣嶇疆涓�
		int selectIndex = (int) (y / (mHight / letters.length));

		if (selectIndex > -1 && selectIndex < letters.length) { // 闃叉瓒婄晫
			String key = letters[selectIndex];
			if (alphaIndexer.containsKey(key)) {
				int pos = alphaIndexer.get(key);
				if (mList.getHeaderViewsCount() > 0) { // 闃叉ListView鏈夋爣棰樻爮,鏈緥涓病鏈�
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}
				if(mDialogText!=null){
					mDialogText.setText(letters[selectIndex]);
				}
			}
		}
		switch (act) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			if (mHandler != null) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			if (mHandler != null) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);
						}
					}
				});
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int sigleHeight = height / letters.length; // 鍗曚釜瀛楁瘝鍗犵殑楂樺害
		for (int i = 0; i < letters.length; i++) {
			paint.setColor(Color.GRAY);
			paint.setTextSize(22);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#8E8E8E")); // 婊戝姩鏃舵寜涓嬪瓧姣嶉鑹�
				paint.setFakeBoldText(true);
			}
			// 缁樼敾鐨勪綅缃�
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			float yPos = sigleHeight * i + sigleHeight;
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}
	}

}
