package com.hrg.bletest;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {
	private int mResourceId;
	private Context context;

	SharedPreferences.Editor editor;
	SharedPreferences pref;

	public SpinnerAdapter(Context context, int resource,
			List<SpinnerItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.mResourceId = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpinnerItem mSpinnerItem = getItem(position);
		
		View view = LayoutInflater.from(getContext())
				.inflate(mResourceId, null);

		TextView bluetoothName = (TextView) view.findViewById(R.id.one);
		TextView bluetoothAddress = (TextView) view.findViewById(R.id.two);

		bluetoothName.setText(mSpinnerItem.getBluetoothName());
		bluetoothAddress.setText(mSpinnerItem.getBluetoothAddress());

		return view;

	}

}
