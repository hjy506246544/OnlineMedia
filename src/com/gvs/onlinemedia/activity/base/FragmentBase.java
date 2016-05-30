package com.gvs.onlinemedia.activity.base;

import java.lang.reflect.Field;

import com.gvs.onlinemedia.widget.CustomProgressDialog;
import com.gvs.onlinemedia.widget.LoadProgressDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

public class FragmentBase extends Fragment {

	protected static final int SHOW_TIME = 1;
	private Activity activity;
	private static CustomProgressDialog m_ProgressDialog;
	private LoadProgressDialog loadProgressDialog;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}

	public void ShowMsg(String pMsg) {
		Toast.makeText(activity, pMsg, SHOW_TIME).show();
	}

	public void ShowMsg(int p_ResID) {
		Toast.makeText(activity, p_ResID, SHOW_TIME).show();
	}

	protected void OpenActivity(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(activity, pClass);
		startActivity(_Intent);
	}

	protected void OpenActivityForResult(Class<?> pClass, int requestCode) {
		Intent _Intent = new Intent();
		_Intent.setClass(activity, pClass);
		startActivityForResult(_Intent, requestCode);
	}

	protected void OpenActivityFinsh(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(activity, pClass);
		startActivity(_Intent);
		activity.finish();
	}

	protected LayoutInflater GetLayouInflater() {
		LayoutInflater _LayoutInflater = LayoutInflater.from(activity);
		return _LayoutInflater;
	}

	public void SetAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field _Field = pDialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			_Field.setAccessible(true);
			_Field.set(pDialog, pIsClose);
		} catch (Exception e) {
		}
	}

	public LoadProgressDialog ShowLoadingProgressDialog(int p_MessageResID) {
		loadProgressDialog = LoadProgressDialog.createDialog(activity);
		loadProgressDialog.setMessage(getString(p_MessageResID));
		loadProgressDialog.setCanceledOnTouchOutside(false);
		loadProgressDialog.show();
		return loadProgressDialog;
	}

	public CustomProgressDialog ShowProgressDialog(int p_MessageResID) {
		if(m_ProgressDialog==null ){
			m_ProgressDialog = CustomProgressDialog.createDialog(activity);
			m_ProgressDialog.setMessage(getString(p_MessageResID));
			m_ProgressDialog.setCanceledOnTouchOutside(false);
			m_ProgressDialog.show();
			Log.e("FragmentBase", "ShowProgressDialog m_ProgressDialog-->"+m_ProgressDialog);
			Log.e("FragmentBase", "FragmentBase-->"+FragmentBase.this);
		}
		if(m_ProgressDialog!=null && !m_ProgressDialog.isShowing()){
			m_ProgressDialog.setMessage(getString(p_MessageResID));
			m_ProgressDialog.setCanceledOnTouchOutside(false);
			m_ProgressDialog.show();
			m_ProgressDialog.animationDrawable.start();
			Log.e("FragmentBase", "ShowProgressDialog m_ProgressDialog-->"+m_ProgressDialog);
			Log.e("FragmentBase", "FragmentBase-->"+FragmentBase.this);
		}
		return m_ProgressDialog ;
	}

	public void DismissLoadingProgressDialog() {
		if (loadProgressDialog != null) {
			loadProgressDialog.dismiss();
		}
	}

	public static void DismissProgressDialog() {
		Log.e("FragmentBase", "DismissProgressDialog m_ProgressDialog-->"+m_ProgressDialog);
		//Log.e("FragmentBase", "FragmentBase-->"+FragmentBase.this);
		if (m_ProgressDialog != null) {
			if(m_ProgressDialog.animationDrawable!=null){
				m_ProgressDialog.animationDrawable.stop();
			}
			m_ProgressDialog.dismiss();
		}
	}
}