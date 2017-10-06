package com.hrg.anew;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.PrintStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.Window.FEATURE_NO_TITLE;

public class BleMainActivity extends Activity
        implements View.OnClickListener {
    public static String GetUsername;
    private static final long SCAN_PERIOD = 10000L;
    int REQUEST_ENABLE_BT = 1;
    private Bundle b;
    ListView lv;
    BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice paramBluetoothDevice, int paramInt, byte[] paramArrayOfByte) {
            final BluetoothDevice bluetoothDevice = paramBluetoothDevice;
            final int rssis = paramInt;
            BleMainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    BleMainActivity.this.mleDeviceListAdapter.addDevice(bluetoothDevice, rssis);
                    BleMainActivity.this.mleDeviceListAdapter.notifyDataSetChanged();
                }
            });
            Log.e("Address ", paramBluetoothDevice.getAddress());
            Log.e("rssi:", paramInt + "");
            System.out.println("Address:" + paramBluetoothDevice.getAddress());
            System.out.println("Name:" + paramBluetoothDevice.getName());
            System.out.println("rssi:" + paramInt);
        }

    };
    private boolean mScanning;
    LeDeviceListAdapter mleDeviceListAdapter;
    private ArrayList<Integer> rssis;

    private Button scan_btn;
    private boolean scan_flag;
    private String uname;

    private void init() {
        this.scan_btn = ((Button) findViewById(R.id.scan_dev_btn)); //public static final int scan_dev_btn = 2131165187;
        this.scan_btn.setOnClickListener(this);
        this.lv = ((ListView) findViewById(R.id.lv));
        this.mHandler = new Handler();
    }

    private void init_ble() {
        // 手机硬件支持蓝牙
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持BLE", Toast.LENGTH_SHORT).show();
            finish();
        }
        // Initializes Bluetooth adapter.
        // 获取手机本地的蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // 打开蓝牙权限
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    private void scanLeDevice(boolean paramBoolean) {
        if (paramBoolean) {
            this.mHandler.postDelayed(new Runnable() {
                                          public void run() {
                                              BleMainActivity.this.mScanning = false;
                                              BleMainActivity.this.scan_flag = true;
                                              BleMainActivity.this.scan_btn.setText("扫描设备");
                                              Log.i("SCAN", "stop.....................");
                                              BleMainActivity.this.mBluetoothAdapter.stopLeScan(BleMainActivity.this.mLeScanCallback);
                                          }
                                      }
                    , 10000L);
            Log.i("SCAN", "begin.....................");
            this.mScanning = true;
            this.scan_flag = false;
            this.scan_btn.setText("停止扫描");
            this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
            return;
        }
        Log.i("Stop", "stoping................");
        this.mScanning = false;
        this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        this.scan_flag = true;
    }

    public void onClick(View paramView) {
        if (this.scan_flag) {
            this.mleDeviceListAdapter = new LeDeviceListAdapter();
            this.lv.setAdapter(this.mleDeviceListAdapter);
            scanLeDevice(true);
            return;
        }
        scanLeDevice(false);
        this.scan_btn.setText("扫描设备");
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        //隐藏状态栏
        if(this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideVirtualKey();
        hideNavigationBar();
        setContentView(R.layout.activity_blemain);     //  public static final int activity_main = 2130903040;
        this.b = getIntent().getExtras();
        this.uname = this.b.getString(GetUsername);
        init();
        init_ble();
        this.scan_flag = true;
        this.mleDeviceListAdapter = new LeDeviceListAdapter();
        this.lv.setAdapter(this.mleDeviceListAdapter);
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                final BluetoothDevice bluetoothDevice = BleMainActivity.this.mleDeviceListAdapter.getDevice(paramInt);
                if (paramAdapterView == null)
                    return;
                Intent intent = new Intent(BleMainActivity.this, Ble_Activity.class);
                intent.putExtra(Ble_Activity.EXTRAS_DEVICE_NAME, bluetoothDevice.getName());
                intent.putExtra(Ble_Activity.EXTRAS_DEVICE_ADDRESS, bluetoothDevice.getAddress());
                intent.putExtra(Ble_Activity.EXTRAS_DEVICE_RSSI, ((Integer) BleMainActivity.this.rssis.get(paramInt)).toString());
                intent.putExtra(Ble_Activity.GetUsername, BleMainActivity.this.uname);
                if (BleMainActivity.this.mScanning) {
                    BleMainActivity.this.mBluetoothAdapter.stopLeScan(BleMainActivity.this.mLeScanCallback);
                    BleMainActivity.this.mScanning = false;
                }
                try {
                    intent = new Intent(BleMainActivity.this, Ble_Activity.class);
                    intent.putExtra(Ble_Activity.EXTRAS_DEVICE_NAME, bluetoothDevice.getName());
                    intent.putExtra(Ble_Activity.EXTRAS_DEVICE_ADDRESS, bluetoothDevice.getAddress());
                    intent.putExtra(Ble_Activity.EXTRAS_DEVICE_RSSI, ((Integer) BleMainActivity.this.rssis.get(paramInt)).toString());
                    intent.putExtra(Ble_Activity.GetUsername, BleMainActivity.this.uname);
                    BleMainActivity.this.startActivity(intent);
                    BleMainActivity.this.finish();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class LeDeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;
        private ArrayList<BluetoothDevice> mLeDevices;

        public LeDeviceListAdapter() {
            BleMainActivity.this.rssis = new ArrayList();
            this.mLeDevices = new ArrayList();
            this.mInflator = BleMainActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice paramBluetoothDevice, int paramInt) {
            if (!this.mLeDevices.contains(paramBluetoothDevice)) {
                this.mLeDevices.add(paramBluetoothDevice);
                BleMainActivity.this.rssis.add(Integer.valueOf(paramInt));
            }
        }

        public void clear() {
            this.mLeDevices.clear();
            BleMainActivity.this.rssis.clear();
        }

        public int getCount() {
            return this.mLeDevices.size();
        }


        public BluetoothDevice getDevice(int paramInt) {
            return (BluetoothDevice) this.mLeDevices.get(paramInt);
        }

        public Object getItem(int paramInt) {
            return this.mLeDevices.get(paramInt);
        }

        public long getItemId(int paramInt) {
            return paramInt;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            BluetoothDevice localBluetoothDevice = mLeDevices.get(paramInt);
            if (paramView == null) {
                LayoutInflater inflater = (LayoutInflater) paramViewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                paramView = inflater.inflate(R.layout.listitem, null);
            }
            TextView tv = (TextView) paramView.findViewById(R.id.tv_deviceAddr); // public static final int tv_deviceAddr = 2131165234;
            TextView localTextView1 = (TextView) paramView.findViewById(R.id.tv_deviceName); //  public static final int tv_deviceName = 2131165232;
            TextView localTextView2 = (TextView) paramView.findViewById(R.id.tv_rssi); // public static final int tv_rssi = 2131165236;
            tv.setText(localBluetoothDevice.getAddress());
            localTextView1.setText(localBluetoothDevice.getName());
            localTextView2.setText(BleMainActivity.this.rssis.get(paramInt) + "");
            return paramView;
        }
    }


    private  void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        int uiOpions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOpions);

    }
    /**
     * 隐藏Android底部的虚拟按键
     */
    private void hideVirtualKey(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }
}
