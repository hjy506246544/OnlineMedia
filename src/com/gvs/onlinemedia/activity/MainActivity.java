package com.gvs.onlinemedia.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gvs.onlinemedia.R;
import com.gvs.onlinemedia.activity.base.FragmentActivityBase;
import com.gvs.onlinemedia.adapter.Main_GridViewAdapter;
import com.gvs.onlinemedia.widget.Header;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 在线媒体主界面
 * @author hjy
 * 2016-5-30
 *
 */
public class MainActivity extends FragmentActivityBase {
	public Header header;
	private GridView listgv;
	private Main_GridViewAdapter gridViewAdapter;
	// 设置适配器的图片资源
    private int[] imageId = new int[] {
    		R.drawable.main_music_, R.drawable.main_cinema_,
            R.drawable.main_fm_};
    // 设置标题
    private String[] title = new String[] {
    		"在线音乐", "在线影院", "在线FM"};
    private List listitem = new ArrayList();
	private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();
    }

    private void initData() {
    	header.setTitle(getResources().getString(R.string.app_name));

    	// 将上述资源转化为list集合
        for (int i = 0; i < title.length; i++) {
            Map map = new HashMap();
            map.put("image", imageId[i]);
            map.put("title", title[i]);
            listitem.add(map);
        }

		gridViewAdapter=new Main_GridViewAdapter(MainActivity.this,listitem);
		listgv.setAdapter(gridViewAdapter);
	}

	private void initListener() {
		listgv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					//在线音乐
				if(position==0){
					Intent intent  = new Intent(MainActivity.this,OnlineMusicActivity.class);
					startActivity(intent);
				}else if (position==1) {
					//在线影院
					Intent intent  = new Intent(MainActivity.this,OnlineCinemaActivity.class);
					startActivity(intent);
				}else if (position==2) {
					//在线FM
					Intent intent  = new Intent(MainActivity.this,OnlineFMActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	private void initView() {
		listgv = (GridView) findViewById(R.id.listgv);
		header = (Header) findViewById(R.id.header);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event){
		if (event.getAction() == KeyEvent.KEYCODE_HOME) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
