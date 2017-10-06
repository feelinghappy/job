package com.hrg.anew;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import com.huicheng.service.BluetoothLeService.LocalBinder;    //反编译找不到这个文件
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static android.view.Window.FEATURE_NO_TITLE;

public class Ble_Activity extends Activity
        implements View.OnClickListener {
    private static final int UPDATE_TEXT = 1;
    public static String GetUsername;
    private static final String TAG = Ble_Activity.class.getSimpleName();
    public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static String EXTRAS_DEVICE_RSSI = "RSSI";
    private static BluetoothGattCharacteristic target_chara = null;
    private static BluetoothLeService mBluetoothLeService;
    private Bundle b;
    Button btn_startButton;
    private ImageView compassimgImageView;
    private TextView connect_state;
    private MyDatabaseHelper dbHelper;
    int disconectnunber = 0;
    int imageID = 0;
    float lastRotateDegree = 0.0F;
    int loop = 0;
    ImageView ls1;
    ImageView ls2;
    ImageView ls3;
    ImageView ls4;
    ImageView ls5;
    ImageView ls6;
    private boolean mConnected = false;
    private String mDeviceAddress;
    private String mDeviceName;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    Thread Displayhread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                if (!Ble_Activity.this.startflag)
                    return;
                Ble_Activity.this.displayData("");
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException.printStackTrace();
                }
            }

        }
    });

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramContext, Intent paramIntent) {

            String action = paramIntent.getAction();
            Log.e("onReceive", action);
            if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals(action)) {
                Ble_Activity.this.mConnected = true;
                Ble_Activity.this.status = "connected";
                System.out.println("BroadcastReceiver :device connected");
            } else if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals(action)) {
                Ble_Activity.this.mConnected = false;
                Ble_Activity.this.status = "连接失败";
                Ble_Activity.this.updateConnectionState(Ble_Activity.this.status);
                System.out.println("BroadcastReceiver :device disconnected");

            } else if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
                Log.e("fxl", "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
                Ble_Activity.this.displayGattServices(Ble_Activity.mBluetoothLeService.getSupportedGattServices());
                System.out.println("BroadcastReceiver :device SERVICES_DISCOVERED");

            } else if ("com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(action)) {
                Log.e("ACTION_DATA_AVAILABLE", paramIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA"));
                Ble_Activity.this.displayData(paramIntent.getStringExtra("com.example.bluetooth.le.EXTRA_DATA"));
            }
        }
    };
    private String mRssi;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder) {
            Ble_Activity.mBluetoothLeService = ((BluetoothLeService.LocalBinder) paramIBinder).getService();
            if (!Ble_Activity.mBluetoothLeService.initialize()) {
                Log.e(Ble_Activity.TAG, "Unable to initialize Bluetooth");
                Ble_Activity.this.finish();
            }
            Ble_Activity.mBluetoothLeService.connect(Ble_Activity.this.mDeviceAddress);
        }

        public void onServiceDisconnected(ComponentName paramComponentName) {
            Ble_Activity.mBluetoothLeService = null;
        }
    };
    String manPatchString = "man";
    int manpictrueindex = 1;
    private Handler mhandler = new Handler();
    private Handler myHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case UPDATE_TEXT:
                    String str = paramMessage.getData().getString("connect_state");
                    rev_str = str;
                    Ble_Activity.this.connect_state.setText(str);
                    break;
                default:
                    break;

            }

        }
    };
    private int recnumbers = 0;
    int recnunber2 = 0;
    private String rev_str = "";
    private ScrollView rev_sv;
    private TextView rev_tv;
    private Button send_btn;
    private EditText send_et;
    boolean startflag = false;
    private String status = "就绪";
    private Button stop_btn;
    private int uage;
    private String uname;
    private String usex;

    private void childrenitems(int paramInt) {
        switch (paramInt) {
            default:
                return;
            case 0:
                this.connect_state.setText("微量元素");
                return;
            case 1:
                this.connect_state.setText("维生素");
                return;
            case 2:
                this.connect_state.setText("少儿多动症");
                return;
            case 3:
                this.connect_state.setText("少儿生长指数");
                return;
            case 4:
        }
        this.connect_state.setText("青少年智力");
    }

    int i = 0;

    private void displayData(String rev_string) {
        final String strrev = rev_string;
        runOnUiThread(new Runnable() {
            public void run() {
                int ran = 0;
                if (startflag) {
                    if (strrev.equalsIgnoreCase("off")) {
                        Log.e("displayData", "off");
                        Ble_Activity.this.connect_state.setText("连接断开");
                        return;
                    }
                    if (strrev.equalsIgnoreCase("err")) {
                        Log.e("displayData", "error");
                        connect_state.setText("连接错误");
                        return;
                    }
                    if (loop == 1) {
                        manpictrueindex = 1;
                        loop = 0;
                        Log.e("loop", loop + "");
                        return;

                    }
                    if (loop < 1) {
                        connect_state.setText("检测当中");
                        String dot = "";
                    }
                }
            }
        });
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        // 服务数据,可扩展下拉列表的第一级数据
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

        // 特征数据（隶属于某一级服务下面的特征值集合）
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // 部分层次，所有特征值集合
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {

            // 获取服务列表
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            // 查表，根据该uuid获取对应的服务名称。SampleGattAttributes这个表需要自定义。
            gattServiceData.add(currentServiceData);

            System.out.println("Service uuid:" + uuid);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            // 从当前循环所指向的服务中读取特征值列表
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            // 对于当前循环所指向的服务中的每一个特征值
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                final BluetoothGatt bluetoothGatt = mBluetoothLeService.mBluetoothGatt;
                bluetoothGatt.readCharacteristic(gattCharacteristic);
                Log.e("Uuid()", gattCharacteristic.getUuid().toString());
                if (gattCharacteristic.getUuid().toString().equals(HEART_RATE_MEASUREMENT)) {
                    int charaProp = gattCharacteristic.getProperties();
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        Log.e("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid());
                        Log.e("nihao", "gattCharacteristic的属性为:  可读");
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                        Log.e("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid());
                        Log.e("nihao", "gattCharacteristic的属性为:  可写");
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        Log.e("nihao", "gattCharacteristic的UUID为:" + gattCharacteristic.getUuid() + gattCharacteristic);
                        Log.e("nihao", "gattCharacteristic的属性为:  具备通知属性");
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_BROADCAST) > 0) {

                        Log.e("nihao", "gattCharacteristic的属性为:  具备广播属性");
                    }

                    Log.e("Uuid()", gattCharacteristic.getUuid().toString());
                    // 测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBluetoothLeService.readCharacteristic(gattCharacteristic);
                        }
                    }, 200);
                    // 接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                    target_chara = gattCharacteristic;
                }
                List<BluetoothGattDescriptor> descriptors = gattCharacteristic.getDescriptors();
                for (BluetoothGattDescriptor descriptor : descriptors) {
                    System.out.println("---descriptor UUID:" + descriptor.getUuid());
                    // 获取特征值的描述
                    mBluetoothLeService.getCharacteristicDescriptor(descriptor);
                }
                gattCharacteristicGroupData.add(currentCharaData);
            }// 按先后顺序，分层次放入特征值集合中，只有特征值
            // 构件第二级扩展列表（服务下面的特征值）
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    private void displayitems(int paramInt) {
        switch (paramInt) {
            default:
                return;
            case 0:
                this.connect_state.setText("心脑血管");
                return;
            case 1:
                this.connect_state.setText("脑神经");
                return;
            case 2:
                this.connect_state.setText("肝功能");
                return;
            case 3:
                this.connect_state.setText("胃肠功能");
                return;
            case 4:
                this.connect_state.setText("胆功能");
                return;
            case 5:
                this.connect_state.setText("肺功能");
                return;
            case 6:
                this.connect_state.setText("肾功能");
                return;
            case 7:
                this.connect_state.setText("男性性功能");
                return;
            case 8:
                this.connect_state.setText("前列腺");
                return;
            case 9:
                this.connect_state.setText("乳腺");
                return;
            case 10:
                this.connect_state.setText("妇科");
                return;
            case 11:
                this.connect_state.setText("肾脏功能");
                return;
            case 12:
                this.connect_state.setText("微量元素");
                return;
            case 13:
                this.connect_state.setText("维生素");
                return;
            case 14:
                this.connect_state.setText("风湿骨病");
                return;
            case 15:
                this.connect_state.setText("骨密度");
                return;
            case 16:
                this.connect_state.setText("骨病");
                return;
            case 17:
                this.connect_state.setText("内分泌系统");
                return;
            case 18:
                this.connect_state.setText("免疫系统");
                return;
            case 19:
                this.connect_state.setText("基本体质");
                return;
            case 20:
                this.connect_state.setText("氨基酸");
                return;
            case 21:
                this.connect_state.setText("蛋白质");
                return;
            case 22:
                this.connect_state.setText("眼部");
                return;
            case 23:
                this.connect_state.setText("皮肤");
                return;
            case 24:
                this.connect_state.setText("胰腺功能");
                return;
            case 25:
                this.connect_state.setText("血糖");
                return;
            case 26:
                this.connect_state.setText("辅酶");
                return;
            case 27:
                this.connect_state.setText("人体毒素");
                return;
            case 28:
        }
        this.connect_state.setText("检查完成");
    }

    private void init() {
        this.connect_state = ((TextView)findViewById(R.id.connect_state));//public static final int connect_state = 2131165188;
        this.send_btn = ((Button)findViewById(R.id.btn_result));// public static final int send_btn = 2131165191;
        this.stop_btn = ((Button)findViewById(R.id.btn_stop));//public static final int btn_stop = 2131165190;
        this.connect_state.setText(this.status);
        this.send_btn.setOnClickListener(this);
        this.ls1 = ((ImageView)findViewById(R.id.layouts1));//public static final int layouts1 = 2131165193;
        this.ls2 = ((ImageView)findViewById(R.id.layouts2));//public static final int layouts2 = 2131165195;
        this.ls3 = ((ImageView)findViewById(R.id.layouts3));
        this.ls4 = ((ImageView)findViewById(R.id.layouts4));
        this.ls5 = ((ImageView)findViewById(R.id.layouts5));
        this.ls6 = ((ImageView)findViewById(R.id.layouts6));
        this.btn_startButton = ((Button)findViewById(R.id.btn_start));// public static final int btn_start = 2131165189;
        this.btn_startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Ble_Activity.this.recnumbers = 0;
                Ble_Activity.this.recnunber2 = 0;
                Ble_Activity.this.startflag = true;
                Ble_Activity.this.send_btn.setEnabled(false);
                Ble_Activity.this.btn_startButton.setEnabled(false);

            }
        });
        this.stop_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Ble_Activity.this.startflag = false;
                Ble_Activity.this.btn_startButton.setEnabled(true);
                Ble_Activity.this.connect_state.setText("就绪");
            }
        });
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_DATA_AVAILABLE");
        return localIntentFilter;
    }

    private void updateConnectionState(String paramString) {
        Message localMessage = new Message();
        localMessage.what = UPDATE_TEXT;
        Bundle localBundle = new Bundle();
        localBundle.putString("connect_state", paramString);
        localMessage.setData(localBundle);
        this.myHandler.sendMessage(localMessage);
        System.out.println("connect_state:" + paramString);
    }

    void displayresult(int paramInt) {
        switch (paramInt) {
            default:
                return;
            case 0:
                this.ls1.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 1:
                this.ls2.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 2:
                this.ls3.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 3:
                this.ls4.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 4:
                this.ls5.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 5:
                this.ls6.setBackgroundColor(Color.argb(255, 138, 138, 138));
                return;
            case 6:
        }
        this.ls1.setBackgroundColor(Color.argb(255, 0, 0, 0));
        this.ls2.setBackgroundColor(Color.argb(255, 0, 0, 0));
        this.ls3.setBackgroundColor(Color.argb(255, 0, 0, 0));
        this.ls4.setBackgroundColor(Color.argb(255, 0, 0, 0));
        this.ls5.setBackgroundColor(Color.argb(255, 0, 0, 0));
        this.ls6.setBackgroundColor(Color.argb(255, 0, 0, 0));

    }

    public void onClick(View paramView) {
       Log.e("onClick","onClick");
        Intent intent = new Intent(this, checktheitems.class);
        intent.putExtra(checktheitems.GetUsername, this.uname);
        intent.putExtra("displaymode", "newdisplay");
        startActivity(intent);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.test);
        //隐藏状态栏  
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏状态栏
        this.b = getIntent().getExtras();
        this.uname = this.b.getString(GetUsername);
        Log.e("uname",uname);
        this.mDeviceName = this.b.getString(EXTRAS_DEVICE_NAME);
        this.mDeviceAddress = this.b.getString(EXTRAS_DEVICE_ADDRESS);
        this.mRssi = this.b.getString(EXTRAS_DEVICE_RSSI);
        this.dbHelper = new MyDatabaseHelper(this, "LiangZiUser.db", null, 2);
        Cursor cursor;
        cursor = this.dbHelper.getReadableDatabase().rawQuery("select * from liangziuser where name=?", new String[] { this.uname });
        if (cursor.moveToFirst())
        {
            this.usex = cursor.getString(cursor.getColumnIndex("sex"));
            this.uage = cursor.getInt(cursor.getColumnIndex("age"));
        }
        if (this.usex.endsWith("男"))
        {
            this.manPatchString = "man";
        }
        else
        {
            this.manPatchString = "woman";
        }
        Button titleEdit = (Button)findViewById(R.id.btn_back);
        //   public static final int title_edit = 2131165254;
        ((Button)findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent intent = new Intent(Ble_Activity.this, MainActivity.class);
                Ble_Activity.this.startActivity(intent);
                Ble_Activity.this.finish();
            }
        });
        Intent  intent = new Intent(this, BluetoothLeService.class);
        bindService(intent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
        init();

    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mGattUpdateReceiver);
        unbindService(this.mServiceConnection);
        mBluetoothLeService = null;
    }

    protected void onResume() {
        super.onResume();
        registerReceiver(this.mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            boolean bool = mBluetoothLeService.connect(this.mDeviceAddress);
            Log.e(TAG, "Connect request result=" + bool);
        }
    }
}
