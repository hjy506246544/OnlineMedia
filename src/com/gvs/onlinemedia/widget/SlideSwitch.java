package com.gvs.onlinemedia.widget;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ResourceAsColor")
public class SlideSwitch extends View{

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //鎶楅敮榻�

    boolean isOn = false;
    float curX = 0;
    float centerY; //y鍥哄畾
    float viewWidth;
    float radius;
    float lineStart; //鐩寸嚎娈靛紑濮嬬殑浣嶇疆锛堟í鍧愭爣锛屽嵆
    float lineEnd; //鐩寸嚎娈电粨鏉熺殑浣嶇疆锛堢旱鍧愭爣
    float lineWidth;
    final int SCALE = 4; // 鎺т欢闀垮害涓烘粦鍔ㄧ殑鍦嗙殑鍗婂緞鐨勫�嶆暟
    OnStateChangedListener onStateChangedListener;

    public SlideSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideSwitch(Context context) {
        super(context);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        curX = event.getX();
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            if(curX > viewWidth / 2)
            {
                curX = lineEnd;
                if(false == isOn)
                {
                    //鍙湁鐘舵�佸彂鐢熸敼鍙樻墠璋冪敤鍥炶皟鍑芥暟锛� 涓嬪悓
                    onStateChangedListener.onStateChanged(true);
                    isOn = true;
                }
            }
            else
            {
                curX = lineStart;
                if(true == isOn)
                {
                    onStateChangedListener.onStateChanged(false);
                    isOn = false;
                }
            }
        }
        /*閫氳繃鍒锋柊璋冪敤onDraw*/
        this.postInvalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*淇濇寔瀹芥槸楂樼殑SCALE / 2鍊嶏紝 鍗冲渾鐨勭洿寰�*/
        this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth() * 2 / SCALE);
        viewWidth = this.getMeasuredWidth();
        radius = viewWidth / SCALE;
        lineWidth = radius * 2f; //鐩寸嚎瀹藉害绛変簬婊戝潡鐩村緞
        curX = radius;
        centerY = this.getMeasuredWidth() / SCALE; //centerY涓洪珮搴︾殑涓�鍗�
        lineStart = radius;
        lineEnd = (SCALE - 1) * radius;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        /*闄愬埗婊戝姩鑼冨洿*/
        curX = curX > lineEnd?lineEnd:curX;
        curX = curX < lineStart?lineStart:curX;

        /*鍒掔嚎*/
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
        /*宸﹁竟閮ㄥ垎鐨勭嚎锛岀传鑹�*/
        mPaint.setColor(com.gvs.onlinemedia.R.color.purple);
        canvas.drawLine(lineStart, centerY, curX, centerY, mPaint);
        /*鍙宠竟閮ㄥ垎鐨勭嚎锛岀伆鑹�*/
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(curX, centerY, lineEnd, centerY, mPaint);

        /*鐢诲渾*/
        /*鐢绘渶宸﹀拰鏈�鍙崇殑鍦嗭紝鐩村緞涓虹洿绾挎瀹藉害锛� 鍗冲湪鐩寸嚎娈典袱杈瑰垎鍒啀鍔犱笂涓�涓崐鍦�*/
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(lineEnd, centerY, lineWidth / 2, mPaint);
        mPaint.setColor(com.gvs.onlinemedia.R.color.purple);
        canvas.drawCircle(lineStart, centerY, lineWidth / 2, mPaint);
        /*鍦嗗舰婊戝潡*/
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(curX, centerY, radius , mPaint);

    }
    /*璁剧疆寮�鍏崇姸鎬佹敼鍙樼洃鍚櫒*/
    public void setOnStateChangedListener(OnStateChangedListener o)
    {
        this.onStateChangedListener = o;
    }

    /*鍐呴儴鎺ュ彛锛屽紑鍏崇姸鎬佹敼鍙樼洃鍚櫒*/
    public interface OnStateChangedListener
    {
        public void onStateChanged(boolean state);
    }

}
