package com.trip.expensemanager.fragments.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.trip.expensemanager.R;
import com.trip.utils.Constants;

public class ThreeButtonFragment extends DialogFragment {

	ThreeButtonDialogListener mListener;
	private String strContent;

	public ThreeButtonFragment(ThreeButtonDialogListener listener) {
		this.mListener=listener;
	}

	public static ThreeButtonFragment newInstance(String strContent, ThreeButtonDialogListener listener) {
		ThreeButtonFragment fragment=null;
		try {
			fragment=new ThreeButtonFragment(listener);
			Bundle bundle=new Bundle();
			bundle.putString(Constants.STR_CONTENT, strContent);
			fragment.setArguments(bundle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		this.strContent=bundle.getString(Constants.STR_CONTENT);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflator = getActivity().getLayoutInflater();
		View view = inflator.inflate(R.layout.three_button_dialog, null);
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setView(view);
		
		TextView tvContent=(TextView) view.findViewById(R.id.tv_content);
		Button btnFirst=(Button) view.findViewById(R.id.btn_first);
		Button btnSecond=(Button) view.findViewById(R.id.btn_second);
		Button btnThird=(Button) view.findViewById(R.id.btn_third);
		tvContent.setText(strContent);
		btnFirst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.onDialogFirstButtonClick(ThreeButtonFragment.this);
			}
		});
		
		btnSecond.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onDialogSecondButtonClick(ThreeButtonFragment.this);
			}
		});
		
		btnThird.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onDialogThirdButtonClick(ThreeButtonFragment.this);
			}
		});
		
		return builder.create();
	}
	
}
