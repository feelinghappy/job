package  com.hrg.robot;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;



/**
 * Created by Wu on 2017/9/21.
 */
public class ZKRequest {

    Context context;
    ZKCallback zkCallback;

    public ZKRequest(Context cxt, ZKCallback callback) {
        context = cxt;
        zkCallback = callback;
        register();
    }

    private void register() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(ZKConfig.ACTION_RECEIVE_RESPONSE_MSG);

        context.registerReceiver(receiver, iFilter, ZKConfig.permission, null);
    }

    /**
     *
     * @param command 需要请求什么传什么值
     * @param value 控制开关传入开/关值，其余信息请求传任意值
     */
    public void sendRequest(int command, int value){
        Intent intent = new Intent(ZKConfig.ACTION_ZK_REQUEST);
        intent.putExtra(ZKConfig.KEY_COMMAND, command);
        intent.putExtra(ZKConfig.KEY_VALUE, value);
        context.sendBroadcast(intent, ZKConfig.permission);
    }

    /**
     * 移动控制
     * @param direction 方向
     */
    public void moveControl(MoveDirection direction){
        Intent intent = new Intent(ZKConfig.ACTION_ZK_REQUEST);
        intent.putExtra(ZKConfig.KEY_COMMAND, ZKConfig.REQ_CONTROL_MOVE);
        intent.putExtra(direction.getClass().getSimpleName(), direction.ordinal());
        context.sendBroadcast(intent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ZKConfig.ACTION_RECEIVE_RESPONSE_MSG.equals(action)) {
                ResultData resultData = (ResultData) intent.getSerializableExtra(ZKConfig.ACTION_MSG_RESPONSE);
                if (resultData != null){
                    zkCallback.resultMsg(resultData);
                }else{
                    zkCallback.error("接收数据失败");
                }
            }
        }
    };

    public void unregister(){
        context.unregisterReceiver(receiver);
    }
}