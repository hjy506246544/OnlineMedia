package com.gvs.onlinemedia.adapter;
import java.util.List;
import java.util.Map;
import com.gvs.onlinemedia.R;
import com.gvs.onlinemedia.activity.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 九宫格适配器
 * 2016-5-13
 * @author hjy
 *
 */
public class Main_GridViewAdapter extends BaseAdapter {
	private MainActivity activity;
	private List listitem;
	private Context context;
	public Main_GridViewAdapter(Context context,List listitem){
		this.context=context;
        this.listitem = listitem;
	}
	public void setData(List names){
		this.listitem=names;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listitem.size();
	}

	@Override
	public Object  getItem(int position) {
		// TODO Auto-generated method stub
		return listitem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			ViewHolder viewHolder=new ViewHolder();
			view=LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview, null);
			viewHolder.iv_headView=(ImageView) view.findViewById(R.id.iv_headview);
			viewHolder.tv_displerName=(TextView) view.findViewById(R.id.nametv);
			view.setTag(R.drawable.ic_launcher,viewHolder);
		}
		holder=(ViewHolder) view.getTag(R.drawable.ic_launcher);
		Map map = (Map) listitem.get(position);
		holder.iv_headView.setImageResource((Integer) map.get("image"));
		holder.tv_displerName.setText(map.get("title") + "");
		return view;
	}
	public class ViewHolder{
		ImageView iv_headView;
		TextView tv_displerName/*,statetv,infotv*/;
	}
}
