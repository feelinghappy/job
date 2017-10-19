package com.longcai.medical.rob.logic;

import android.util.Log;

import com.longcai.medical.rob.bean.AirBox;
import com.longcai.medical.rob.bean.Robot;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import java.util.HashMap;

/**
 * Created by liutao on 2017/9/24.
 */

public class RobotDataSync {

    private SyncReference sync;
    private String robotUid;
    private String currentUid;

    private Robot robot;
    private AirBox airBox;

    public RobotDataSync(String robotUid, String currentUid){
        this.currentUid = currentUid;
        //this.robotUid = robotUid;
        //sync = WilddogSync.getInstance().getReference().child("roots").child(robotUid);
        sync = WilddogSync.getInstance().getReference();
        robot = new Robot();
        airBox = new AirBox();
    }

    public void getRobotData(String robotUid,final RobotStateListener listener){
        //sync.child(robotUid).
        this.robotUid = robotUid;
        sync.child("roots").child(robotUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    Log.d("liutao", "onDataChange: " + dataSnapshot.toString());
                    robotFromDataSnapshot(dataSnapshot);
                    listener.onSuccess(robot);
                }
                listener.onFailed("Server Error");
            }

            @Override
            public void onCancelled(SyncError syncError) {
                if(syncError!=null){
                    listener.onFailed(syncError.getMessage());
                }
                listener.onFailed("Server Error");
            }
        });
    }

    public void getAirBoxData(){
        sync.child(robotUid).child("airBox").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    airBoxFromDataSnapshot(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(SyncError syncError) {
                if(syncError!=null){

                }

            }
        });
    }

    private void airBoxFromDataSnapshot(DataSnapshot dataSnapshot) {
        airBox.setCh2o((String) dataSnapshot.child("ch2o").getValue());
        airBox.setCo2((String)dataSnapshot.child("co2").getValue());
        airBox.setHumidity((String)dataSnapshot.child("humidity").getValue());
        airBox.setPm25((String)dataSnapshot.child("pm25").getValue());
        airBox.setTemperature((String)dataSnapshot.child("temperature").getValue());
        airBox.setGas((String)dataSnapshot.child("gas").getValue());
    }

    private void robotFromDataSnapshot(DataSnapshot dataSnapshot) {
        robot.setStatus((String)dataSnapshot.child("status").getValue());
        robot.setElectricity((String)dataSnapshot.child("electricity").getValue());
        robot.setType((String)dataSnapshot.child("type").getValue());
        robot.setCurrentUser((String)dataSnapshot.child("currentUser").getValue());
        robot.setMonitor((String)dataSnapshot.child("monitor").getValue());
        airBoxFromDataSnapshot(dataSnapshot.child("airBox"));
    }

    /**
     * 向fanState节点写入风扇控制数据
     * @param state
     */
    public void setFanState(String state){
        HashMap<String, Object> fan = new HashMap<>();
        fan.put("fanState", state);
        sync.updateChildren(fan);

    }

    /**
     * 写入机器人控制的动作
     * @param action
     */
    public void setRobotMoveAction(String action){
        HashMap<String, Object> control = new HashMap<>();
        control.put(this.currentUid, action);
        sync.child("control").updateChildren(control);

    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}
