package com.gvs.onlinemedia.activity.base;

import java.lang.reflect.Field;

import com.gvs.onlinemedia.R;
import com.gvs.onlinemedia.widget.CustomProgressDialog;
import com.gvs.onlinemedia.widget.LoadProgressDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

public abstract class FragmentActivityBase extends FragmentActivity {
	private boolean isRun;
	protected static final int SHOW_TIME = 1;
	private CustomProgressDialog m_ProgressDialog;
	private LoadProgressDialog loadProgressDialog;

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	protected void OpenActivity(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pClass);
		startActivity(_Intent);
	}

	protected void OpenActivityForResult(Class<?> pClass, int requestCode) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pClass);
		startActivityForResult(_Intent, requestCode);
	}

	protected void OpenActivityFinsh(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pClass);
		startActivity(_Intent);
		this.finish();
	}

	protected LayoutInflater GetLayouInflater() {
		LayoutInflater _LayoutInflater = LayoutInflater.from(this);
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

	public interface MyDialogInterface {
		public void onClick(DialogInterface dialog);
	}

	public CustomProgressDialog ShowProgressDialog(int p_MessageResID) {
		if(m_ProgressDialog==null ){
			m_ProgressDialog = CustomProgressDialog.createDialog(this);
			m_ProgressDialog.setMessage(getString(p_MessageResID));
			m_ProgressDialog.setCanceledOnTouchOutside(false);
			m_ProgressDialog.show();
		}
		if(m_ProgressDialog!=null && !m_ProgressDialog.isShowing()){
			m_ProgressDialog.setMessage(getString(p_MessageResID));
			m_ProgressDialog.setCanceledOnTouchOutside(false);
			m_ProgressDialog.show();
		}
		return m_ProgressDialog;
	}

	public LoadProgressDialog ShowLoadingProgressDialog(int p_MessageResID) {
		loadProgressDialog = LoadProgressDialog.createDialog(this);
		loadProgressDialog.setMessage(getString(p_MessageResID));
		loadProgressDialog.setCanceledOnTouchOutside(false);
		loadProgressDialog.show();
		return loadProgressDialog;
	}

	protected AlertDialog ShowAlertDialog(int p_TitelResID,String p_Message,DialogInterface.OnClickListener p_YesClickListener,DialogInterface.OnClickListener p_NoClickListener)
	{
		String _Title = getResources().getString(p_TitelResID);
		return ShowAlertDialog(_Title, p_Message, p_YesClickListener,p_NoClickListener);
	}

	protected AlertDialog ShowAlertDialog(String p_Title,String p_Message,DialogInterface.OnClickListener p_YesClickListener,DialogInterface.OnClickListener p_NoClickListener)
	{
		return new AlertDialog.Builder(this)
		.setTitle(p_Title)
		.setMessage(p_Message)
		.setPositiveButton(R.string.app_btnyes, p_YesClickListener)
		.setNegativeButton(R.string.app_btnno, p_NoClickListener)
		.show();
	}
	protected AlertDialog ShowViewAlertDialog(String p_Title,View view,DialogInterface.OnClickListener p_YesClickListener,DialogInterface.OnClickListener p_NoClickListener)
	{
		return new AlertDialog.Builder(this)
		.setTitle(p_Title)
		.setView(view)
		.setPositiveButton(R.string.app_btnyes, p_YesClickListener)
		.setNegativeButton(R.string.app_btnno, p_NoClickListener)
		.show();
	}

	public void DismissLoadingProgressDialog() {
		if (loadProgressDialog != null) {
			loadProgressDialog.dismiss();
		}
	}

	public void DismissProgressDialog() {
		if (m_ProgressDialog != null) {
			m_ProgressDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		onExit();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		onRun();
	}

	protected void onExit() {
		// TODO Auto-generated method stub
		isRun=false;
	}

	protected void onRun() {
		// TODO Auto-generated method stub
		isRun=true;
	}

}
