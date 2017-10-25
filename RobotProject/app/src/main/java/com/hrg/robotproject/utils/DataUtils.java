package com.hrg.robotproject.utils;
import android.app.Activity;
import android.util.Log;

import com.hrg.robotproject.bean.AirBox;
import com.hrg.robotproject.bean.Commands;
import com.hrg.robotproject.bean.Robot;
import com.hrg.robotproject.logic.RobotStateListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import java.util.HashMap;

/**
 * Created by liutao on 2017/10/3.
 */

public class DataUtils {

    public static void monitorDataByUid(String uid, final RobotStateListener listener){
        WilddogSync.getInstance().getReference().child("robots").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Log.d("DataUtils", "test onDataChange: "+ dataSnapshot.toString());
                    listener.onSuccess(robotFromDataSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(SyncError syncError) {
                if(syncError!=null){
                    listener.onFailed(syncError.getMessage());
                }

            }
        });
    }

    private static AirBox airBoxFromDataSnapshot(DataSnapshot dataSnapshot) {
        AirBox airBox = new AirBox();
        airBox.setCh2o( dataSnapshot.child("ch2o").getValue().toString());
        airBox.setCo2(dataSnapshot.child("co2").getValue().toString());
        airBox.setHumidity(dataSnapshot.child("humidity").getValue().toString());
        airBox.setPm25(dataSnapshot.child("pm25").getValue().toString());
        airBox.setTemperature(dataSnapshot.child("temperature").getValue().toString());
        airBox.setGas(dataSnapshot.child("gas").getValue().toString());
        Log.d("liutao", "airBoxFromDataSnapshot: " + airBox.toString());
        return airBox;

    }

    private static Robot robotFromDataSnapshot(DataSnapshot dataSnapshot) {
        Robot robot = new Robot();
        robot.setUid(dataSnapshot.getKey());
        robot.setFanState(dataSnapshot.child("fanState").getValue().toString());
        robot.setName(dataSnapshot.child("name").getValue().toString());
        robot.setStatus(dataSnapshot.child("status").getValue().toString());
        robot.setElectricity(dataSnapshot.child("electricity").getValue().toString());
        robot.setType(dataSnapshot.child("type").getValue().toString());
        robot.setCurrentUser(dataSnapshot.child("currentUser").getValue().toString());
        String currentUser = (dataSnapshot.child("currentUser").getValue()).toString();
        if(dataSnapshot.child("control").child(currentUser).getValue() != null) {
            robot.setControl(dataSnapshot.child("control").child(currentUser).getValue().toString());
        }
        robot.setMonitor(dataSnapshot.child("monitor").getValue().toString());
        robot.setAirBox(airBoxFromDataSnapshot(dataSnapshot.child("airBox")));
        Log.d("liutao", "airBoxFromDataSnapshot: " + robot.toString());
        return robot;
    }

    /**
     * 向fanState节点写入风扇控制数据
     * @param state
     */
    public static void setFanState(String uid, String state){
        HashMap<String, Object> fan = new HashMap<>();
        fan.put("fanState", state);
        WilddogSync.getInstance().getReference().child("robots").child(uid).updateChildren(fan);

    }


    /**
     * 向airbox节点写入空气净化器数据
     * @param airBox
     */
    public static void setAirBox(String uid, AirBox airBox){
        HashMap<String, Object> airbox = new HashMap<>();
        airbox.put("airBox", airBox);
        WilddogSync.getInstance().getReference().child("robots").child(uid).updateChildren(airbox);
    }

    /**
     * 写入机器人控制的动作
     * @param action
     */
    public static void  setRobotMoveAction(String uid, String currentUid, String action){
//        HashMap<String, Object> control = new HashMap<>();
//        control.put(currentUid, action);
//        WilddogSync.getInstance().getReference().child("robots").child(uid).child("control").updateChildren(control);

        WilddogSync.getInstance().getReference().child("robots").child(uid).child("control").child(currentUid).setValue(action);



    }

}
