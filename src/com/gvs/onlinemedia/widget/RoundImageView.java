package com.gvs.onlinemedia.widget;

import com.gvs.onlinemedia.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 * 
 * @author zhy
 * 
 */
public class RoundImageView extends ImageView
{
	/**
	 * 鍥剧墖鐨勭被鍨嬶紝鍦嗗舰or鍦嗚
	 */
	private int type;
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	/**
	 * 鍦嗚澶у皬鐨勯粯璁ゅ��
	 */
	private static final int BODER_RADIUS_DEFAULT = 10;
	/**
	 * 鍦嗚鐨勫ぇ灏�
	 */
	private int mBorderRadius;

	/**
	 * 缁樺浘鐨凱aint
	 */
	private Paint mBitmapPaint;
	/**
	 * 鍦嗚鐨勫崐寰�
	 */
	private int mRadius;
	/**
	 * 3x3 鐭╅樀锛屼富瑕佺敤浜庣缉灏忔斁澶�
	 */
	private Matrix mMatrix;
	/**
	 * 娓叉煋鍥惧儚锛屼娇鐢ㄥ浘鍍忎负缁樺埗鍥惧舰鐫�鑹�
	 */
	private BitmapShader mBitmapShader;
	/**
	 * view鐨勫搴�
	 */
	private int mWidth;
	private RectF mRoundRect;

	public RoundImageView(Context context, AttributeSet attrs)
	{

		super(context, attrs);
		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);

		mBorderRadius = a.getDimensionPixelSize(
				R.styleable.RoundImageView_borderRadius, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
								BODER_RADIUS_DEFAULT, getResources()
										.getDisplayMetrics()));// 榛樿涓�10dp
		type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 榛樿涓篊ircle

		a.recycle();
	}

	public RoundImageView(Context context)
	{
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 濡傛灉绫诲瀷鏄渾褰紝鍒欏己鍒舵敼鍙榲iew鐨勫楂樹竴鑷达紝浠ュ皬鍊间负鍑�
		 */
		if (type == TYPE_CIRCLE)
		{
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mRadius = mWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}

	}

	/**
	 * 鍒濆鍖朆itmapShader
	 */
	private void setUpShader()
	{
		Drawable drawable = getDrawable();
		if (drawable == null)
		{
			return;
		}

		Bitmap bmp = drawableToBitamp(drawable);
		// 灏哹mp浣滀负鐫�鑹插櫒锛屽氨鏄湪鎸囧畾鍖哄煙鍐呯粯鍒禸mp
		if(bmp!=null){
			mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
			float scale = 1.0f;
			if (type == TYPE_CIRCLE)
			{
				// 鎷垮埌bitmap瀹芥垨楂樼殑灏忓��
				int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
				scale = mWidth * 1.0f / bSize;

			} else if (type == TYPE_ROUND)
			{
				Log.e("TAG",
						"b'w = " + bmp.getWidth() + " , " + "b'h = "
								+ bmp.getHeight());
				if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight()))
				{
					// 濡傛灉鍥剧墖鐨勫鎴栬�呴珮涓巚iew鐨勫楂樹笉鍖归厤锛岃绠楀嚭闇�瑕佺缉鏀剧殑姣斾緥锛涚缉鏀惧悗鐨勫浘鐗囩殑瀹介珮锛屼竴瀹氳澶т簬鎴戜滑view鐨勫楂橈紱鎵�浠ユ垜浠繖閲屽彇澶у�硷紱
					scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
							getHeight() * 1.0f / bmp.getHeight());
				}

			}
			// shader鐨勫彉鎹㈢煩闃碉紝鎴戜滑杩欓噷涓昏鐢ㄤ簬鏀惧ぇ鎴栬�呯缉灏�
			mMatrix.setScale(scale, scale);
			// 璁剧疆鍙樻崲鐭╅樀
			mBitmapShader.setLocalMatrix(mMatrix);
			// 璁剧疆shader
			mBitmapPaint.setShader(mBitmapShader);
		}
		
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.e("TAG", "onDraw");
		if (getDrawable() == null)
		{
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND)
		{
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);
		} else
		{
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			// drawSomeThing(canvas);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		// 鍦嗚鍥剧墖鐨勮寖鍥�
		if (type == TYPE_ROUND)
			mRoundRect = new RectF(0, 0, w, h);
	}

	/**
	 * drawable杞琤itmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable)
	{
		if (drawable instanceof BitmapDrawable)
		{
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	private static final String STATE_INSTANCE = "state_instance";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_BORDER_RADIUS = "state_border_radius";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
		bundle.putInt(STATE_TYPE, type);
		bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			super.onRestoreInstanceState(((Bundle) state)
					.getParcelable(STATE_INSTANCE));
			this.type = bundle.getInt(STATE_TYPE);
			this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
		} else
		{
			super.onRestoreInstanceState(state);
		}

	}

	public void setBorderRadius(int borderRadius)
	{
		int pxVal = dp2px(borderRadius);
		if (this.mBorderRadius != pxVal)
		{
			this.mBorderRadius = pxVal;
			invalidate();
		}
	}

	public void setType(int type)
	{
		if (this.type != type)
		{
			this.type = type;
			if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE)
			{
				this.type = TYPE_CIRCLE;
			}
			requestLayout();
		}

	}

	public int dp2px(int dpVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}

}