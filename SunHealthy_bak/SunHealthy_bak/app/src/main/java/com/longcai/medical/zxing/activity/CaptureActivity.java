package com.longcai.medical.zxing.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.gloabe.ToastPrompt;
import com.longcai.medical.ui.activity.GloabeActivitySecond;
import com.longcai.medical.ui.activity.person.PersonDeviceActivity;
import com.longcai.medical.utils.AppManager;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.camera.CameraManager;
import com.longcai.medical.zxing.decoding.CaptureActivityHandler;
import com.longcai.medical.zxing.decoding.InactivityTimer;
import com.longcai.medical.zxing.decoding.RGBLuminanceSource;
import com.longcai.medical.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import static com.longcai.medical.R.id.scanner_toolbar_back;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends AppCompatActivity implements Callback {

    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageView back;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    //	private Button cancelScanButton;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

    private static final String TAG = "CaptureActivity";
    private boolean isWatchReplace = false; // 标示是否是要更换手表设备
    private boolean isRobot = false;// 标示是否是扫描机器人
    private boolean isRobotReplace = false; // 标示是否是要更换机器人
    private boolean isDevice = false;
    private boolean isWatch = false;
    private String mOldWatchImei, mOldRobotImei;
    private String mWatchImei;

    private TextView tvTitle;
    private HashMap<String, String> map = new HashMap<>();
    private boolean isSn = false;
    //    private boolean isHistory;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_scanner);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        try {
            if (!isCameraCanUse()) {
                ToastUtils.showToast(this, "已被禁止使用相机权限");
            }
            CameraManager.init(getApplication());

            initView();
            initIntentData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.scanner_title);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_content);
        back = (ImageView) findViewById(scanner_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
              /*
        * 手动输入 点击跳转
        * */
        scannerInput = (TextView) findViewById(R.id.scanner_input);
        scannerInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureActivity.this, CaptureInputActivity.class);
                intent.putExtra(Constant.SCAN_MARK, scanMark);
//                intent.putExtra("isRobot", isRobot);
                startActivityForResult(intent, 3);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        //添加toolbar
//        addToolbar();
    }

    private String scanMark = "";
    /**
     * 初始化 Intent传过来的值
     */
    String Robot_create_MARK;

    private void initIntentData() {
        Intent intent = getIntent();
        scanMark = intent.getStringExtra(Constant.SCAN_MARK);
        if (scanMark.equals(Constant.SCAN_MARK_robot)) {
            // 扫描机器人
            isRobot = true;
            tvTitle.setText("绑定机器人");
            String robotMark = intent.getStringExtra(Constant.Robot_MARK);
            if (robotMark.equals(Constant.ROBOT_REPLACE)) {
                isRobotReplace = true;
                mOldRobotImei = intent.getStringExtra(Constant.ROBOT_imei);
                mFamilyId = intent.getStringExtra(Constant.ROBOT_Replace_familyId);
                LogUtils.i(TAG, "机器人更换..." + mOldWatchImei);
            } else if (robotMark.equals(Constant.ROBOT_BUDLING)) {
                isRobotReplace = false;
                Robot_create_MARK = intent.getStringExtra(Constant.Robot_create_MARK);
                LogUtils.i(TAG, "机器人绑定");
            }
        } else if (scanMark.equals(Constant.SCAN_MARK_watch)) {
            // 扫描手表设备
            isWatch = true;
            tvTitle.setText("绑定手表");
            isRobot = false;
            String whatchMark = intent.getStringExtra(Constant.WHATCH_MARK);
            if (whatchMark.equals(Constant.WHATCH_REPLACE)) {
                isWatchReplace = true;
                mOldWatchImei = intent.getStringExtra(Constant.WHATCH_imei);
                WHATCH_Budling_From_markF = intent.getStringExtra(Constant.WHATCH_Budling_From_markF);
                LogUtils.i(TAG, "手表更换..." + mOldWatchImei);

            } else if (whatchMark.equals(Constant.WHATCH_BUDLING)) {
                isWatchReplace = false;
                WHATCH_Budling_From_markF = intent.getStringExtra(Constant.WHATCH_Budling_From_markF);
                LogUtils.i(TAG, "手表绑定");
            }
        } else if (scanMark.equals(Constant.SCAN_MARK_device)) {
            isDevice = true;
            tvTitle.setText("绑定设备");
//            String deviceMark = intent.getStringExtra(Constant.DEVICE_MARK);
//            if (deviceMark.equals(Constant.DEVICE_BUDLING)) {
//
//            }
        } else if (scanMark.equals(Constant.SCAN_MARK_Ruku_Sn)) {//入库 条形码
            isSn = true;
            tvTitle.setText("扫码入库");
        }

    }

    String WHATCH_Budling_From_markF;

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        //TODO  扫码结果返回
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText(); //扫描的结果
        Log.i(TAG, "扫码结果 result-->" + resultString);
        if (!isWriteId) {
            HandleData(resultString); //处理扫码得到的结果
        }
    }

    private void HandleData(String result) {
        //TODO 处理扫描得到的结果
        if (!result.isEmpty()) {
            if (isSn) {
                String substring = result.substring(0, 3);
                LogUtils.d("截取的=="+substring);
                if (result.substring(0,3).equals("YYD")){
                    Constant.Robot_imeiResult = result;
                    Intent intent = new Intent();
                    intent.putExtra("goods_sn", result);
                    setResult(Constant.STORE_CODE, intent);
                    finish();
                }else {
                    ToastUtils.showToast(CaptureActivity.this,"请选择正确的机器人sn码");
                }
            }
            if (isDevice) {
                if (result.length() == 23) {
                    Constant.Robot_imeiResult = result;
                    String robotId = StringUtils.getSubString(0, result.indexOf("-"), result);
                    String robotSerial = StringUtils.getSubString(result.indexOf("-") + 1, result.length(), result);
                    toBudlingRobot(robotId, robotSerial, result);
                }

                if (result.contains("imei")) {
                /*
                 * 验证是否是手表的二维码 在执行绑定的过程中，是扫描框不可见，并且提示框提示进度是更换手表，同上。
				 */
                    mWatchImei = StringUtils.getSubString(
                            (result.indexOf("imei") + 5), result.length(), result);

                    //  Constant.WatchImei_ScannerResult = mWatchImei;
                    LogUtils.i(TAG, "mWatchImei  .." + mWatchImei);
                    setWhatchBudlingOrRelieve(); //从机器人跳入的绑定手表和从穿戴设备管理跳入的更换手表
                }
            }
            if (isRobot) {
                // 是机器人
                /*判断扫码结果是否符合机器人的编码格式*/
                LogUtils.i(TAG, "机器人的编码格式  " + result + "  长度  " + result.length() + "");
                if (result.length() == 23) {
                    Constant.Robot_imeiResult = result;
                /*1.截取扫码结果中的机器人(robotId - 机器人的id    robotSerial - 机器人序列号)
                * 2.判断是绑定还是更换
                *     (1)绑定，跳转到选择绑定界面（注意：要先绑定到机器人服务器）
                *     (2)更换，要先解除之前的绑定，然后 先绑定到机器人服务器成功后，再绑定到自己的服务器
                */
                    // 验证是否是机器人的二维码 // ZK20C1494818532823-YF86
                    String robotId = StringUtils.getSubString(0, result.indexOf("-"), result);
                    String robotSerial = StringUtils.getSubString(result.indexOf("-") + 1, result.length(), result);
                    if (!isRobotReplace) {
                        // TODO bind robot
                        // setRobotRelieveToRobotService(robotId, robotSerial);//执行绑定机器人操作
                        toBudlingRobot(robotId, robotSerial, result);//执行绑定机器人操作,跳转到绑定机器人界面
                    } else {
                    /*执行换帮机器人操作
                       结束扫码，返回结果*/
                        if (!mOldRobotImei.equals(result)) {
                            Intent intent = new Intent();
                            setResult(Constant.Manager_family_Replace_CODE, intent);
                            finish();
                        } else {
                            Toast.makeText(CaptureActivity.this, "请选择不同的机器人", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                } else {
                    Toast.makeText(CaptureActivity.this, "请选择正确的机器人编码", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
            if (isWatch) {
                // 是手表穿戴设备
                // ToastUtils.showToast(this, "手表穿戴设备绑定");
                /*判断扫码结果是否符合手表穿戴设备的编码格式
                * 第一代手表：http://yun.nbwearable.com/GTAPPMP/GTT9/index.html?imei=B234567890A021  长度  69
                * 第二代手表：http://a.app.qq.com/o/simple.jsp?pkgname=com.longcai.medical
                * 第三代手表：http://a.app.qq.com/o/simple.jsp?pkgname=com.longcai.medical&imei=B23456EF9AA0A1*/
                LogUtils.i(TAG, "手表穿戴设备的编码格式  " + result + "  长度  " + result.length() + "");
                int lengths = result.length();
                // if (lengths == 80) {
                if (result.contains("imei")) {
                /*
                 * 验证是否是手表的二维码 在执行绑定的过程中，是扫描框不可见，并且提示框提示进度是更换手表，同上。
				 */
                    mWatchImei = StringUtils.getSubString(
                            (result.indexOf("imei") + 5), result.length(), result);
                    //mWatchImei = Constant.WHATCH_CODE;
                    if (isWatchReplace) {
                        if (mOldWatchImei.equals(mWatchImei)) {
                            Toast.makeText(CaptureActivity.this, "请选择不同的穿戴设备", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    //  Constant.WatchImei_ScannerResult = mWatchImei;
                    LogUtils.i(TAG, "mWatchImei  .." + mWatchImei);
                    if (WHATCH_Budling_From_markF.equals(Constant.WHATCH_Budling_From_Robot)) {
                        setWhatchBudlingOrRelieve(); //从机器人跳入的绑定手表和从穿戴设备管理跳入的更换手表
                    } else if (WHATCH_Budling_From_markF.equals(Constant.WHATCH_Budling_From_CreateFamilyMun)) {
                        //从创建家庭成员跳入
                        Intent intent = new Intent();
                        setResult(Constant.Whatch_Create_family_Replace_CODE, intent);
                        finish();

                    } else if (WHATCH_Budling_From_markF.equals(Constant.WHATCH_Budling_From_ManagerFamilyMun)) {
                        //从管理家庭成员跳入
                        Intent intent = new Intent();
                        setResult(Constant.Whatch_Manager_family_Replace_CODE, intent);
                        finish();

                    } else if (WHATCH_Budling_From_markF.equals(Constant.WHATCH_Budling_From_FamilyDetailMun)) {
                        //从管理家庭详情跳入
                        isFromFamilyDetial = true;
                        setWhatchBudlingOrRelieve(); //从机器人跳入的绑定手表和从穿戴设备管理跳入的更换手表
                    } else if (WHATCH_Budling_From_markF.equals(Constant.WHATCH_Budling_From_History)) {
                        // 从历史记录跳入
                        setWhatchBudlingOrRelieve();
                    }
                } else {
                    Toast.makeText(CaptureActivity.this, "请选择正确的手表设备编码", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    boolean isFromFamilyDetial = false;
    //跳转到绑定机器人界面

    private void toBudlingRobot(String robotId, String robotSerial, String scanerResult) {
        Constant.RobotId = robotId;
        Constant.RobotSerial = robotSerial;
        LogUtils.i(TAG, "ConstantY.ROBOT_Create   " + Constant.ROBOT_Create);
        if (isRobot && Robot_create_MARK.equals(Constant.ROBOT_Create)) {
            Intent intent = new Intent();
            setResult(Constant.Create_family_CODE, intent);
            finish();
        } else if (isRobot && Robot_create_MARK.equals(Constant.ROBOT_Add)) {
            Intent intent = new Intent(this, GloabeActivitySecond.class);
            intent.putExtra(Constant.FRAGMENT_MARK, Constant.ROBOTBINDINGFG);
            startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
            return;
        } else if (isRobot && Robot_create_MARK.equals(Constant.ROBOT_manager)) {
            Intent intent = new Intent();
            setResult(Constant.Manager_family_CODE, intent);
            finish();
            return;
        }/*else if (isRobot && Robot_create_MARK.equals(Constant.ROBOT_diaohuo)){
            //TODO yst 把扫码结果返回显示在仓库列表
            Intent intent = new Intent();
            setResult(Constant.STORE_CODE, intent);
            finish();
        }*/
        if (isDevice) {
            Intent intent = new Intent(this, GloabeActivitySecond.class);
            intent.putExtra(Constant.FRAGMENT_MARK, Constant.ROBOTBINDINGFG);
            intent.putExtra("from_devices", true);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
        }
    }

    String mFamilyId;

    private void setWhatchBudlingOrRelieve() {
        // TODO 绑定及更换手表
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("watch_imei", mWatchImei);
        if (isWatchReplace) {
            map.put("old_watch_imei", mOldWatchImei);
        }
        HttpUtils.xOverallHttpPost(this, MyUrl.WHATCH_BUDLING_OR_REPLACE, map, new
                HttpUtils.OnxHttpCallBack<List<?>>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(final List<?> robotList) {
                        processBudingWatchData();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        if (code == 11212) {
                            ToastUtils.showToast(CaptureActivity.this, "该手表已被绑定");
                            CaptureActivity.this.finish();
                        }
                    }
                });
    }

    Intent intent;

    private void processBudingWatchData() {
        // TODO 解析供求网络数据
        ToastUtils.showToast(CaptureActivity.this,
                ToastPrompt.BUDLING_SUCCESS);

        if (isDevice) {
            intent = new Intent(this, PersonDeviceActivity.class);
            intent.putExtra("refresh_devices", true);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
            return;
        }
        if (isFromFamilyDetial) {
            isFromFamilyDetial = false;
            setResult(Constant.WhatchBudlingSuccessToManaDet_CODE);
        } else {
            setResult(Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_suc);
        }
        CaptureActivity.this.finish();
        /*if (code.equals("-5")) {//手表编码错误，无法换绑
            setResult(Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_fail, intent);
            ToastUtils.showToast(CaptureActivity.this, msg);
            CaptureActivity.this.finish();
        } else if (code.equals("-6")) {//	手表编码已绑定，请解绑后重试
            setResult(Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_fail, intent);
            ToastUtils.showToast(CaptureActivity.this, msg);
            CaptureActivity.this.finish();
        } else {
            ToastUtils.showToast(CaptureActivity.this,
                    msg);
            setResult(Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_fail, intent);
            CaptureActivity.this.finish();
        }*/
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    TextView scannerInput;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.scanner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    boolean isWriteId = false; //标示是否是手动输入的id
    private String writeWhatchScaner = "http://a.app.qq.com/o/simple.jsp?pkgname=com.longcai.medical&imei=";
    String WriteIdResultString = null; //手动输入最终返回结果

    @Override
    //TODO 处理返回码
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Constant.WhatchBudlingSuccessToInput_CODE:
                //返回手动输入id
                isWriteId = true;
                String id = data.getStringExtra("id");
                WriteIdResultString = id;
                if (isRobot) {
                    //手动输入只有一栏 输入id+serial
/*                    String Serial = data.getStringExtra("Serial");
                        WriteIdResultString = id + "-" + Serial;*/
                    WriteIdResultString = id;
                } else if (isSn) {
                    WriteIdResultString = id;
                } else {
                    WriteIdResultString = writeWhatchScaner + id;
                }
                LogUtils.i(TAG, WriteIdResultString);
                if (WriteIdResultString != null) {
                    HandleData(WriteIdResultString);
                }

                break;
        }
        if (requestCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    //获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();

                    mProgress = new ProgressDialog(CaptureActivity.this);
                    mProgress.setMessage("正在扫描...");
                    mProgress.setCancelable(false);
                    mProgress.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Result result = scanningImage(photo_path);
                            if (result != null) {
//                                Message m = handler.obtainMessage();
//                                m.what = R.id.decode_succeeded;
//                                m.obj = result.getText();
//                                handler.sendMessage(m);
                                Intent resultIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString(INTENT_EXTRA_KEY_QR_SCAN, result.getText());
//                                Logger.d("saomiao",result.getText());
//                                bundle.putParcelable("bitmap",result.get);
                                resultIntent.putExtras(bundle);
                                CaptureActivity.this.setResult(RESULT_CODE_QR_SCAN, resultIntent);

                            } else {
                                Message m = handler.obtainMessage();
                                m.what = R.id.decode_failed;
                                m.obj = "Scan failed!";
                                handler.sendMessage(m);
                            }
                        }
                    }).start();
                    break;
            }
        } else {
            Intent intent = new Intent();
            setResult(resultCode, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    public boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }
}