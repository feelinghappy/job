package  com.hrg.healthmanager.service;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hrg.healthmanager.Ble_Activity.HEART_RATE_MEASUREMENT;

public class BluetoothLeService extends Service {
    public static final String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public static final String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";
    private static final int STATE_CONNECTED = 2;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_DISCONNECTED = 0;
    private static final String TAG = "BluetoothLeService";
    private final IBinder mBinder = new LocalBinder();
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    public BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private int mConnectionState = 0;
    private List<Sensor> mEnabledSensors = new ArrayList();
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
            System.out.println("++++++++++++++++");
            BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", paramBluetoothGattCharacteristic);
        }


        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }


        public void onCharacteristicWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt) {
            Log.e("BluetoothLeService", "--onCharacteristicWrite--: " + paramInt);
            BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", paramBluetoothGattCharacteristic);
        }

        public void onConnectionStateChange(BluetoothGatt paramBluetoothGatt, int paramInt1, int paramInt2) {
            if (paramInt2 == 2) {
                BluetoothLeService.this.mConnectionState = 2;
                BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
                Log.e("BluetoothLeService", "Connected to GATT server.");
                Log.e("BluetoothLeService", "Attempting to start service discovery:" + BluetoothLeService.this.mBluetoothGatt.discoverServices());
            } else {
                BluetoothLeService.this.mConnectionState = 0;
                Log.e("BluetoothLeService", "Disconnected from GATT server.");
                BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
            }
        }

        public void onDescriptorRead(BluetoothGatt paramBluetoothGatt, BluetoothGattDescriptor paramBluetoothGattDescriptor, int paramInt) {
            Log.e("BluetoothLeService", "----onDescriptorRead status: " + paramInt);
            final byte[] data = paramBluetoothGattDescriptor.getValue();
            if (paramBluetoothGatt != null)
                Log.e("BluetoothLeService", "----onDescriptorRead value: " + new String(data));
        }

        public void onDescriptorWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattDescriptor paramBluetoothGattDescriptor, int paramInt) {
            Log.e("BluetoothLeService", "--onDescriptorWrite--: " + paramInt);
        }

        public void onReadRemoteRssi(BluetoothGatt paramBluetoothGatt, int paramInt1, int paramInt2) {
            Log.e("BluetoothLeService", "--onReadRemoteRssi--: " + paramInt2);
            BluetoothLeService.this.broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", paramInt1);
        }

        public void onReliableWriteCompleted(BluetoothGatt paramBluetoothGatt, int paramInt) {
            Log.e("BluetoothLeService", "--onReliableWriteCompleted--: " + paramInt);
        }

        @Override
        // New services discovered
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }
    };
    private OnDataAvailableListener mOnDataAvailableListener;

    private void broadcastUpdate(String paramString) {
        sendBroadcast(new Intent(paramString));
    }

    private void broadcastUpdate(String paramString, int paramInt) {
        Intent intent = new Intent(paramString);
        intent.putExtra("com.example.bluetooth.le.EXTRA_DATA", String.valueOf(paramInt));
        sendBroadcast(intent);
    }
  private void broadcastUpdate(final String action,
                               final BluetoothGattCharacteristic characteristic) {
      final Intent intent = new Intent(action);

      // This is special handling for the Heart Rate Measurement profile. Data
      // parsing is carried out as per profile specifications.
      // 心率监测规范的特殊处理
      // 数据解析在每个规范中完成
      if (HEART_RATE_MEASUREMENT.equals(characteristic.getUuid().toString())) {
          int flag = characteristic.getProperties();
          int format = -1;
          if ((flag & 0x01) != 0) {
              format = BluetoothGattCharacteristic.FORMAT_UINT16;
              Log.d(TAG, "心率格式 UINT16.");
          } else {
              format = BluetoothGattCharacteristic.FORMAT_UINT8;
              Log.d(TAG, "心率格式 UINT8.");
          }
          final int heartRate = characteristic.getIntValue(format, 1);
          Log.e(TAG, String.format("接收到心跳检测 : %d", heartRate));
          if(heartRate>10 && heartRate<180)
          {
              intent.putExtra("com.example.bluetooth.le.EXTRA_DATA", "on");
              Log.e("service","on");
          }
          else if(heartRate>179)
          {
              intent.putExtra("com.example.bluetooth.le.EXTRA_DATA", "err");
              Log.e("service","error");
          }

          else if(heartRate<11)
          {
              intent.putExtra("com.example.bluetooth.le.EXTRA_DATA", "off");
              Log.e("service","off");
          }
      } else {
          // 对于其它的规范, 写出 HEX 十六进制格式的数据
          final byte[] data = characteristic.getValue();
          if (data != null && data.length > 0) {
              final StringBuilder stringBuilder = new StringBuilder(data.length);
              for(byte byteChar : data)
                  stringBuilder.append(String.format("%02X ", byteChar));
              intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
                      stringBuilder.toString());

          }
      }
      sendBroadcast(intent);
  }
    public void close() {
        if (this.mBluetoothGatt == null)
            return;
        this.mBluetoothGatt.close();
        this.mBluetoothGatt = null;
    }
    public boolean connect(String paramString) {
        Log.e("connect",paramString);
        if ((this.mBluetoothAdapter == null) || (paramString == null)) {
            if(paramString == null)
            {
                Log.e("connect","paramString == null");
            }
            Log.w("BluetoothLeService", "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        if ((this.mBluetoothDeviceAddress != null) && (paramString.equals(this.mBluetoothDeviceAddress)) && (this.mBluetoothGatt != null)) {
            Log.d("BluetoothLeService", "Trying to use an existing mBluetoothGatt for connection.");
            if (this.mBluetoothGatt.connect()) {
                this.mConnectionState = 1;
                return true;
            }
            return false;
        }
        BluetoothDevice localBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(paramString);
        if (localBluetoothDevice == null) {
            Log.e("BluetoothLeService", "Device not found.  Unable to connect.");
            return false;
        }
        this.mBluetoothGatt = localBluetoothDevice.connectGatt(this, false, this.mGattCallback);
        Log.e("BluetoothLeService", "Trying to create a new connection.");
        this.mBluetoothDeviceAddress = paramString;
        this.mConnectionState = 1;
        System.out.println("device.getBondState==" + localBluetoothDevice.getBondState());
        return true;
    }

    public void disconnect() {
        Log.e("disconnect","disconnect");
        if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            return;
        }
        this.mBluetoothGatt.disconnect();
    }

    public void getCharacteristicDescriptor(BluetoothGattDescriptor paramBluetoothGattDescriptor) {

        if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            return;
        }
        this.mBluetoothGatt.readDescriptor(paramBluetoothGattDescriptor);
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (this.mBluetoothGatt == null)
            return null;
        return this.mBluetoothGatt.getServices();
    }

    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE));
            if (this.mBluetoothManager == null) {
                Log.e("BluetoothLeService", "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter == null) {
            Log.e("BluetoothLeService", "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        return true;
    }

    public IBinder onBind(Intent paramIntent) {
        return this.mBinder;
    }

    public boolean onUnbind(Intent paramIntent) {
        close();
        return super.onUnbind(paramIntent);
    }

    public void readCharacteristic(BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
        if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }
            return;
        }
        this.mBluetoothGatt.readCharacteristic(paramBluetoothGattCharacteristic);
    }

    public void readRssi() {
        if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }
            return;
        }
        this.mBluetoothGatt.readRemoteRssi();
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic paramBluetoothGattCharacteristic, boolean enabled) {
        if((mBluetoothAdapter == null) || (mBluetoothGatt == null)) {
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            return;
        }
        this.mBluetoothGatt.setCharacteristicNotification(paramBluetoothGattCharacteristic, enabled);
        List<BluetoothGattDescriptor> descriptors=paramBluetoothGattCharacteristic.getDescriptors();
        for(BluetoothGattDescriptor dp:descriptors)
        {
            dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(dp);
        }

        UUID uuid = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        BluetoothGattDescriptor clientConfig = paramBluetoothGattCharacteristic.getDescriptor(uuid);

        if(enabled) {
            clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            clientConfig.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        mBluetoothGatt.writeDescriptor(clientConfig);
    }

    public void setOnDataAvailableListener(OnDataAvailableListener paramOnDataAvailableListener) {
        this.mOnDataAvailableListener = paramOnDataAvailableListener;
    }

    public void writeCharacteristic(BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
        if ((this.mBluetoothAdapter == null) || (this.mBluetoothGatt == null)) {
            Log.e("BluetoothLeService", "BluetoothAdapter not initialized");
            if(mBluetoothGatt==null)
            {
                Log.e("mBluetoothGatt==null","mBluetoothGatt==null");
            }

            return;
        }
        this.mBluetoothGatt.writeCharacteristic(paramBluetoothGattCharacteristic);
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    public static abstract interface OnDataAvailableListener {
        public abstract void onCharacteristicChanged(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic);

        public abstract void onCharacteristicRead(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt);

        public abstract void onCharacteristicWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic);
    }
}
