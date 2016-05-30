package com.gvs.onlinemedia.activity;
import com.gvs.onlinemedia.R;
import com.gvs.onlinemedia.activity.base.FragmentActivityBase;
import com.gvs.onlinemedia.widget.Header;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 在线影院主界面
 * @author hjy
 * 2016-5-30
 *
 */
public class OnlineCinemaActivity extends FragmentActivityBase {
	public Header header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinecinema_activity);
		initView();
		initData();
		initListener();
    }

    private void initData() {
    	header.setTitle(getResources().getString(R.string.zxcinema_title));
    	header.setLeftImageVewRes(R.drawable.return2,new OnClickListener() {

			@Override
			public void onClick(View v) {
				OnlineCinemaActivity.this.finish();
			}
		});
	}

	private void initListener() {
	}

	private void initView() {
		header = (Header) findViewById(R.id.header);
	}
}
