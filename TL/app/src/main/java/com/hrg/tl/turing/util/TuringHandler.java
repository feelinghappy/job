package com.hrg.tl.turing.util;
import java.util.Date;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hrg.tl.turing.MainActivity;
import com.hrg.tl.turing.domain.MessageType;
import com.hrg.tl.turing.domain.TuringBaseInfo;
import com.hrg.tl.turing.domain.TuringChildPoem;
import com.hrg.tl.turing.domain.TuringChildSong;
import com.hrg.tl.turing.domain.TuringCook;
import com.hrg.tl.turing.domain.TuringError;
import com.hrg.tl.turing.domain.TuringLink;
import com.hrg.tl.turing.domain.TuringNews;
import com.hrg.tl.turing.domain.TuringText;
import com.hrg.tl.util.LogUtil;
import com.hrg.tl.util.NotifyUtil;
import com.hrg.tl.util.ToastUtil;


public class TuringHandler {

	protected static final int ID = 0x999;
	private static Context mContext;
	private static TuringHandler instance;
	private static Gson gson = new GsonBuilder().create();

	public TuringHandler(Context context) {
		this.mContext = context;
	}

	public static TuringHandler getInstance(Context context) {
		if (instance == null) {
			instance = new TuringHandler(context);
		}
		return instance;
	}

	public static Handler handler = new Handler() {
		String result = null;
		TuringBaseInfo turingInfo = null;

		public void handleMessage(Message msg) {
			result = msg.getData().getString("result");
			switch (TuringCodeType.valueOfInt(msg.what)) {
			case NORMAL_TEXT://
				LogUtil.i(TuringCodeType.NORMAL_TEXT.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_TEXT.getValueText());
				turingInfo = gson.fromJson(result, TuringText.class);
				break;
			case NORMAL_LINK://
				LogUtil.i(TuringCodeType.NORMAL_LINK.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_LINK.getValueText());
				turingInfo = gson.fromJson(result, TuringLink.class);
				break;
			case NORMAL_NEWS://
				LogUtil.i(TuringCodeType.NORMAL_NEWS.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_NEWS.getValueText());
				turingInfo = gson.fromJson(result, TuringNews.class);
				break;
			case NORMAL_COOK://
				LogUtil.i(TuringCodeType.NORMAL_COOK.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_COOK.getValueText());
				turingInfo = gson.fromJson(result, TuringCook.class);
				break;
			case NORMAL_CHILD_SONG://
				LogUtil.i(TuringCodeType.NORMAL_CHILD_SONG.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_CHILD_SONG.getValueText());
				turingInfo = gson.fromJson(result, TuringChildSong.class);
				break;
			case NORMAL_CHILD_POEM://
				LogUtil.i(TuringCodeType.NORMAL_CHILD_POEM.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.NORMAL_CHILD_POEM.getValueText());
				turingInfo = gson.fromJson(result, TuringChildPoem.class);
				break;
			case ERROR_KEY://
				LogUtil.i(TuringCodeType.ERROR_KEY.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.ERROR_KEY.getValueText());
				turingInfo = gson.fromJson(result, TuringError.class);
				break;
			case ERROR_INFO://
				LogUtil.i(TuringCodeType.ERROR_INFO.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.ERROR_INFO.getValueText());
				turingInfo = gson.fromJson(result, TuringError.class);
				break;
			case ERROR_TOO_MANY_TIMES://
				LogUtil.i(TuringCodeType.ERROR_TOO_MANY_TIMES.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.ERROR_TOO_MANY_TIMES.getValueText());
				turingInfo = gson.fromJson(result, TuringError.class);
				break;
			case ERROR_DATA_TYPE_EXCEPTION://
				LogUtil.i(TuringCodeType.ERROR_DATA_TYPE_EXCEPTION.getValueText());
				ToastUtil.showShortDebug(mContext, TuringCodeType.ERROR_DATA_TYPE_EXCEPTION.getValueText());
				turingInfo = gson.fromJson(result, TuringError.class);
				break;

			default:
				break;
			}
			LogUtil.i(result);
			ToastUtil.showLongDebug(mContext, result);
			LogUtil.i(turingInfo.toString());
			ToastUtil.showLongDebug(mContext, turingInfo.toString());

			TuringBaseInfo turingBaseInfo = new TuringBaseInfo();
			turingBaseInfo.setMessageType(MessageType.FROM);
			turingBaseInfo.setText(turingInfo.getText());
			turingBaseInfo.setTime(new Date());
			MainActivity.getInstance().mData.add(turingBaseInfo);
			MainActivity.getInstance().mTuringInfoAdapter.notifyDataSetChanged();

			//
			NotifyUtil notifyUtil = NotifyUtil.getInstance(MainActivity.getInstance(), ID);

			Intent intent = new Intent(MainActivity.getInstance(), MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.getInstance(), ID, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			String ticker = "小墨机器人";
			String title = "小墨机器人";
			String content = turingInfo.getText();
			boolean sound = true;
			boolean vibrate = true;
			boolean lights = true;
			notifyUtil.notify_normail_moreline(pendingIntent, 0, ticker, title, content, sound, vibrate, lights);
		};
	};
}
