/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrg.bletest;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 对于一个BLE设备，该activity向用户提供设备连接，显示数据，显示GATT服务和设备的字符串支持等界面，
 * 另外这个activity还与BluetoothLeService通讯，反过来与Bluetooth LE API进行通讯
 */
public class DeviceControlActivity extends Activity implements OnClickListener,
		OnLongClickListener, OnTouchListener {
	private final static String TAG = DeviceControlActivity.class
			.getSimpleName();

	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	private boolean longClicked = false;
	private boolean connectStateFlag = false;
	private static final int VISIBLE = 0;
	private static final int INVISIBLE = 4;
	private static final int GONE = 8;
	private boolean isExit = false;
	private Toast toast;
	// 连接状态
	private ProgressDialog progressDialog;
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	private TextView mConnectionState;
	private TextView mDataField;
	private String mDeviceName;
	private String mDeviceAddress;
	private String address;
	private BluetoothLeService mBluetoothLeService;

	private ImageButton bluetoothSwitch;
	private TextView deviceName;


	// 写数据
	private BluetoothGattCharacteristic writeCharacteristic;
	// 读数据
	private BluetoothGattCharacteristic readCharacteristic;

	public ListView listview;

	byte[] WriteBytes = new byte[4];

	private boolean mConnected = false;

	private Button buttonKey1;
	private Button buttonKey2;
	private Button buttonKey3;
	private ImageView lamp1;
	private ImageView lamp2;
	private ImageView lamp3;
	private byte count = 0;
	private byte keyHighByte = 0;
	private byte keyLowByte = 0;
	private byte a;

	private boolean flag;
	private byte instruction = 0;
	boolean fwriteCharacteristic;
	boolean freadCharacteristic;


	// 管理服务的生命周期
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.

			mBluetoothLeService.connect(mDeviceAddress);

		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};

	// Handles various events fired by the Service.处理服务所激发的各种事件
	// ACTION_GATT_CONNECTED: connected to a GATT server.连接一个GATT服务
	// ACTION_GATT_DISCONNECTED: disconnected from a GATT server.从GATT服务中断开连接
	// ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.查找GATT服务
	// ACTION_DATA_AVAILABLE: received data from the device. This can be a
	// result of read
	// or notification operations.从服务中接受数据
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			Log.e("onReceive",action);
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				mConnected = true;
				flag = false;
			
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				if (flag == true) {
					unbindService(mServiceConnection);
					mBluetoothLeService = null;
				}
				mConnected = false;
				connectStateFlag = false;
				bluetoothSwitch.setImageResource(R.drawable.btn_ble_off);

				Message msg = mHandler.obtainMessage();
				msg.what = (byte) 0xB1;
				msg.arg2 = 0;
				mHandler.sendMessage(msg);
				deviceName.setText("未连接");
				if (mBluetoothLeService != null && flag == false) {
					
				mBluetoothLeService.connect(mDeviceAddress);	
				
					
				}
				// updateConnectionState(R.string.disconnected);
				// invalidateOptionsMenu();
				// clearUI();
			}
			// 发现有可支持的服务
			else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
				// 写数据的服务和characteristic
				for (BluetoothGattService service : mBluetoothLeService
						.getSupportedGattServices()) {
					Log.e(TAG, "service:" + service.getUuid().toString());
					if (service.getUuid().toString()
							.equals("0000fff0-0000-1000-8000-00805f9b34fb")) {
						// Log.e(TAG,
						// "service characteristics:"+service.getCharacteristics().size());
						for (BluetoothGattCharacteristic character : service
								.getCharacteristics()) {
							Log.e(TAG, "characteristics:"
									+ character.getUuid().toString());
							if (character
									.getUuid()
									.toString()
									.equals("0000fff2-0000-1000-8000-00805f9b34fb")) {
								writeCharacteristic = character;
								fwriteCharacteristic = true;
							}
							if (character
									.getUuid()
									.toString()
									.equals("0000fff1-0000-1000-8000-00805f9b34fb")) {

								Log.e(TAG,
										"property:" + character.getProperties());

								readCharacteristic = character;
								freadCharacteristic = true;
								if ((character.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) == 0x10) {
									mBluetoothLeService
											.setCharacteristicNotification(
													readCharacteristic, true);
								}

							}
							if (fwriteCharacteristic && freadCharacteristic) {
								fwriteCharacteristic = false;
								freadCharacteristic = false;

								Message msg = mHandler.obtainMessage();
								msg.what = 99;
								mHandler.sendMessage(msg);
							}

						}
					}
				}

			}

			// 显示数据
			else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				// 将数据显示在mDataField上

				String data = intent.getStringExtra("EXTRA_DATA");
				//delay(100);
				if(data!=null)
				{
					Log.e(" BroadcastReceiver data",data + "ACTION_DATA_AVAILABLE");
				}
				else
				{
					Log.e("BroadcastReceiver data","为空");
				}

			
			} 
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		MyApplication mAPP = (MyApplication) getApplication();
		mAPP.setHandler1(mHandler);
		editor = getSharedPreferences("data", MODE_PRIVATE).edit();
		pref = getSharedPreferences("data", MODE_PRIVATE);

		
		buttonKey1 = (Button) findViewById(R.id.activity_switch_btn1);
		buttonKey2 = (Button) findViewById(R.id.activity_switch_btn2);
		buttonKey3 = (Button) findViewById(R.id.activity_switch_btn3);

		lamp1 = (ImageView) findViewById(R.id.activity_lamp_iv1);
		lamp2 = (ImageView) findViewById(R.id.activity_lamp_iv2);
		lamp3 = (ImageView) findViewById(R.id.activity_lamp_iv3);

		bluetoothSwitch = (ImageButton) findViewById(R.id.activity_ble_btn);
		deviceName = (TextView) findViewById(R.id.activity_deviceName_tv);

		bluetoothSwitch.setOnClickListener(this);

		deviceName.setClickable(true);
		deviceName.setOnLongClickListener(this);
		deviceName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (connectStateFlag == true) {
					Intent bleScanIntent = new Intent(
							DeviceControlActivity.this,
							DeviceScanActivity.class);
					startActivityForResult(bleScanIntent, 1);
				} else {
					if (toast == null) {
						toast = Toast.makeText(DeviceControlActivity.this, "",
								Toast.LENGTH_SHORT);
					}
					toast.setText("请先连接蓝牙");
					toast.show();
				}
			}

		});

		buttonKey1.setOnTouchListener(this);
		buttonKey2.setOnTouchListener(this);
		buttonKey3.setOnTouchListener(this);
	
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			isExit = false;
			instruction = (byte) msg.arg1;
            Log.e("Handle",msg.what+"");
			switch (msg.what) {
			case 99:
				connectStateFlag = true;
				progressDialog.dismiss();
				bluetoothSwitch.setImageResource(R.drawable.btn_ble_on);
				Log.e(TAG, "aaaa data: 99 " + WriteBytes[0] + " " + WriteBytes[1]
						+ " " + WriteBytes[2] + " " + WriteBytes[3]);
				if (connectStateFlag == true) {
					WriteBytes[0] = (byte) 0xB0;
					WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
					writeCharacteristic.setValue(WriteBytes);

					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);
				}
				if (pref.getString(mDeviceAddress, "").equals("")) {
					deviceName.setText(mDeviceName);
				} else {
					deviceName.setText(pref.getString(mDeviceAddress, ""));
				}
				break;
			case 10:

				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
			//		writeCharacteristic.setValue(WriteBytes);

			//		mBluetoothLeService
			//				.writeCharacteristic(writeCharacteristic);
					Log.e("10","readCharacteristic");
					mBluetoothLeService
							.setCharacteristicNotification(
									readCharacteristic, true);

				}

				break;

			case 11:

				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
			//		writeCharacteristic.setValue(WriteBytes);

			//		mBluetoothLeService
			//				.writeCharacteristic(writeCharacteristic);
					Log.e("11","readCharacteristic");
					mBluetoothLeService
							.setCharacteristicNotification(
									readCharacteristic, true);

				}

				break;
			case 20:
				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
					writeCharacteristic.setValue(WriteBytes);

					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);
				}
				break;

			case 21:
				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
					writeCharacteristic.setValue(WriteBytes);

					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);
				}
				break;
			case 30:
				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
					writeCharacteristic.setValue(WriteBytes);

					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);
				}
				break;

			case 31:
				WriteBytes[0] = (byte) 0xB0;
				WriteBytes[1] = instruction;
				WriteBytes[2] = count++;
				WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));
				if (connectStateFlag == true) {
					writeCharacteristic.setValue(WriteBytes);

					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);
				}
				break;
			case (byte) 0xB1:
				if (msg.arg2 == 0) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
				}
				if (msg.arg2 == 1) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
				}
				if (msg.arg2 == 2) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
				}
				if (msg.arg2 == 3) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
				}
				if (msg.arg2 == 4) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
				}
				if (msg.arg2 == 5) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
				}
				if (msg.arg2 == 6) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_off));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
				}
				if (msg.arg2 == 7) {
					lamp1.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp2.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
					lamp3.setBackground(getBaseContext().getResources()
							.getDrawable(R.drawable.lamp_on));
				}
				break;
		

			}

		}
	};


	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (v.getId()) {
		case R.id.activity_switch_btn1:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				Message msg = mHandler.obtainMessage();
				msg.what = 11;
				keyHighByte = (byte) 0x10;
				keyLowByte = (byte) (0x01 | keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);

				Log.e("onTouch", "activity_switch_btn1");

			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				Message msg = mHandler.obtainMessage();
				msg.what = 10;
				keyHighByte = (byte) 0x10;
				keyLowByte = (byte) (0x0e & keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);
				Log.i("aaa", "cccvv");

			}
			break;
		case R.id.activity_switch_btn2:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				Message msg = mHandler.obtainMessage();
				msg.what = 21;
				keyHighByte = (byte) 0x20;
				keyLowByte = (byte) (0x02 | keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				Message msg = mHandler.obtainMessage();
				msg.what = 20;
				keyHighByte = (byte) 0x20;
				keyLowByte = (byte) (0x0d & keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);

			}
			break;
		case R.id.activity_switch_btn3:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				Message msg = mHandler.obtainMessage();
				msg.what = 31;
				keyHighByte = (byte) 0x40;
				keyLowByte = (byte) (0x04 | keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				Message msg = mHandler.obtainMessage();
				msg.what = 30;
				keyHighByte = (byte) 0x40;
				keyLowByte = (byte) (0x0b & keyLowByte);
				a = (byte) (keyHighByte + keyLowByte);
				msg.arg1 = a;
				mHandler.sendMessage(msg);

			}
			break;
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_ble_btn:
			// 蓝牙是否连接标志位
			if (connectStateFlag == false) {
				Intent bleScanIntent = new Intent(DeviceControlActivity.this,
						DeviceScanActivity.class);
				startActivityForResult(bleScanIntent, 1);
			} else {
				flag = true;
				mBluetoothLeService.disconnect();

			}

			break;

		}
	}

	@Override
	public boolean onLongClick(View view) {
		if (connectStateFlag == true) {
			final EditText et = new EditText(DeviceControlActivity.this);

			new AlertDialog.Builder(DeviceControlActivity.this)
					.setTitle("是否更改设备名？")
					.setView(et)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String input = et.getText().toString();
									if (input.equals("")) {
										Toast.makeText(getApplicationContext(),
												"输入内容不能为空！" + input,
												Toast.LENGTH_LONG).show();
									} else {
										editor.putString(mDeviceAddress, input);
										editor.commit();
										deviceName.setText(pref.getString(
												mDeviceAddress, ""));

									}
								}
							}).setNegativeButton("取消", null).show();

		} else {
			if (toast == null) {
				toast = Toast.makeText(DeviceControlActivity.this, "",
						Toast.LENGTH_SHORT);
			}
			toast.setText("连接蓝牙后，才可更改设备名");
			toast.show();
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				if (connectStateFlag == true) {
					count = 0;
					WriteBytes[0] = (byte) 0xB0;
					WriteBytes[1] = (byte) 0;
					WriteBytes[2] = count;
					WriteBytes[3] = (byte) ((WriteBytes[0]) ^ (WriteBytes[1]) ^ (WriteBytes[2]));

					writeCharacteristic.setValue(WriteBytes);
					mBluetoothLeService
							.writeCharacteristic(writeCharacteristic);

					Message msg = mHandler.obtainMessage();
					msg.what = (byte) 0xB1;
					msg.arg2 = 0;
					mHandler.sendMessage(msg);
					unbindService(mServiceConnection);
					mBluetoothLeService = null;
					connectStateFlag = false;
				}

				mDeviceAddress = data.getStringExtra("EXTRAS_DEVICE_ADDRESS");
				mDeviceName = data.getStringExtra("EXTRAS_DEVICE_NAME");
				Intent gattServiceIntent = new Intent(this,
						BluetoothLeService.class);
				bindService(gattServiceIntent, mServiceConnection,
						BIND_AUTO_CREATE);
				progressDialog = new ProgressDialog(this);

				// progressDialog.setTitle("This is Title");
				progressDialog.setMessage("正在连接设备，请稍等。。。");
				progressDialog.setIndeterminate(true);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.show();
				progressDialog
						.setOnKeyListener(new DialogInterface.OnKeyListener() {

							public boolean onKey(DialogInterface dialog,
									int keyCode, KeyEvent event) {
								// TODO Auto-generated method stub
								// Cancel task.
								if (keyCode == KeyEvent.KEYCODE_BACK) {
									progressDialog.dismiss();
									mBluetoothLeService.disconnect();

								}
								return false;
							}
						});
			}
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);
			Log.e(TAG, "Connect request result=" + result);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mGattUpdateReceiver);
		unbindService(mServiceConnection);
		mBluetoothLeService = null;
	}


	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_RSSI);
		return intentFilter;
	}

	private void delay(int ms) {
		try {
			Thread.currentThread();
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			 mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(KeyCode, event);
	}
}
