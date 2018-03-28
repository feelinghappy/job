package com.hrg.tl.util;

import java.util.ArrayList;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hrg.tl.R;

@SuppressLint("NewApi")
public class NotifyUtil {

	private static final int FLAG = Notification.FLAG_INSISTENT;
	int requestCode = (int) SystemClock.uptimeMillis();
	private int NOTIFICATION_ID;
	private NotificationManager nm;
	private Notification notification;
	private NotificationCompat.Builder cBuilder;
	private Notification.Builder nBuilder;
	private Context mContext;

	// TODO add by shankes
	private static NotifyUtil instance;

	public static NotifyUtil getInstance(Context context, int ID) {
		if (instance == null) {
			instance = new NotifyUtil(context, ID);
		}
		return instance;
	}

	public NotifyUtil(Context context, int ID) {
		this.NOTIFICATION_ID = ID;
		mContext = context;
		// 获取系统初始化对象
		nm = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
		cBuilder = new NotificationCompat.Builder(mContext);
	}

	/**
	 * 设置在顶部通知栏中的各种信息
	 *
	 * @param pendingIntent
	 * @param smallIcon
	 * @param ticker
	 */
	private void setCompatBuilder(PendingIntent pendingIntent, int smallIcon, String ticker, String title,
			String content, boolean sound, boolean vibrate, boolean lights) {

		cBuilder.setContentIntent(pendingIntent);//
		if (smallIcon != 0) {
			cBuilder.setSmallIcon(smallIcon);//
		} else {
			cBuilder.setSmallIcon(R.drawable.ic_launcher);
		}
		cBuilder.setTicker(ticker);//

		cBuilder.setContentTitle(title);//
		cBuilder.setContentText(content);//
		cBuilder.setWhen(System.currentTimeMillis());


		cBuilder.setAutoCancel(true);

		cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

		int defaults = 0;

		if (sound) {
			defaults |= Notification.DEFAULT_SOUND;
		}
		if (vibrate) {
			defaults |= Notification.DEFAULT_VIBRATE;
		}
		if (lights) {
			defaults |= Notification.DEFAULT_LIGHTS;
		}

		cBuilder.setDefaults(defaults);
	}


	private void setBuilder(PendingIntent pendingIntent, int smallIcon, String ticker, boolean sound, boolean vibrate,
			boolean lights) {
		nBuilder = new Notification.Builder(mContext);

		nBuilder.setContentIntent(pendingIntent);

		if (smallIcon != 0) {
			nBuilder.setSmallIcon(smallIcon);
		} else {
			nBuilder.setSmallIcon(R.drawable.ic_launcher);
		}

		nBuilder.setTicker(ticker);
		nBuilder.setWhen(System.currentTimeMillis());
		nBuilder.setPriority(Notification.PRIORITY_MAX);

		int defaults = 0;

		if (sound) {
			defaults |= Notification.DEFAULT_SOUND;
		}
		if (vibrate) {
			defaults |= Notification.DEFAULT_VIBRATE;
		}
		if (lights) {
			defaults |= Notification.DEFAULT_LIGHTS;
		}

		nBuilder.setDefaults(defaults);
	}


	/**
	 * 普通的通知
	 * <p/>
	 * 1. 侧滑即消失，下拉通知菜单则在通知菜单显示
	 *
	 * @param pendingIntent
	 * @param smallIcon
	 * @param ticker
	 * @param title
	 * @param content
	 */
	public void notify_normal_singline(PendingIntent pendingIntent, int smallIcon, String ticker, String title,
			String content, boolean sound, boolean vibrate, boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
		sent();
	}

	/**
	 * 进行多项设置的通知(在小米上似乎不能设置大图标，系统默认大图标为应用图标)
	 *
	 * @param pendingIntent
	 * @param smallIcon
	 * @param ticker
	 * @param title
	 * @param content
	 */
	public void notify_mailbox(PendingIntent pendingIntent, int smallIcon, int largeIcon,
			ArrayList<String> messageList, String ticker, String title, String content, boolean sound, boolean vibrate,
			boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);



		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), largeIcon);
		cBuilder.setLargeIcon(bitmap);

		cBuilder.setDefaults(Notification.DEFAULT_ALL);

		cBuilder.setAutoCancel(true);

		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		for (String msg : messageList) {
			inboxStyle.addLine(msg);
		}
		inboxStyle.setSummaryText("[" + messageList.size() + "条]" + title);
		cBuilder.setStyle(inboxStyle);
		sent();
	}


	public void notify_customview(RemoteViews remoteViews, PendingIntent pendingIntent, int smallIcon, String ticker,
			boolean sound, boolean vibrate, boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, null, null, sound, vibrate, lights);

		notification = cBuilder.build();
		notification.contentView = remoteViews;

		nm.notify(NOTIFICATION_ID, notification);
	}


	public void notify_normail_moreline(PendingIntent pendingIntent, int smallIcon, String ticker, String title,
			String content, boolean sound, boolean vibrate, boolean lights) {

		final int sdk = Build.VERSION.SDK_INT;
		if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
			notify_normal_singline(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
			Toast.makeText(mContext, "Android 4.1.2", Toast.LENGTH_SHORT).show();
		} else {
			setBuilder(pendingIntent, smallIcon, ticker, true, true, false);
			nBuilder.setContentTitle(title);
			nBuilder.setContentText(content);
			nBuilder.setPriority(Notification.PRIORITY_HIGH);
			notification = new Notification.BigTextStyle(nBuilder).bigText(content).build();

			nm.notify(NOTIFICATION_ID, notification);
		}
	}


	public void notify_progress(PendingIntent pendingIntent, int smallIcon, String ticker, String title,
			String content, boolean sound, boolean vibrate, boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);


		new Thread(new Runnable() {
			@Override
			public void run() {
				int incr;
				for (incr = 0; incr <= 100; incr += 10) {

					cBuilder.setProgress(100, incr, false);
					// cBuilder.setProgress(0, 0, true);
					sent();
					try {
						Thread.sleep(1 * 500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				cBuilder.setContentText("\"下载完成\"").setProgress(0, 0, false);
				sent();
			}
		}).start();
	}


	public void notify_bigPic(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content,
			int bigPic, boolean sound, boolean vibrate, boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, title, null, sound, vibrate, lights);
		NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = true;
		options.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bigPic, options);
		picStyle.bigPicture(bitmap);
		picStyle.bigLargeIcon(bitmap);
		cBuilder.setContentText(content);
		cBuilder.setStyle(picStyle);
		sent();
	}

	/**
	 * 里面有两个按钮的通知
	 *
	 * @param smallIcon
	 * @param leftbtnicon
	 * @param lefttext
	 * @param leftPendIntent
	 * @param rightbtnicon
	 * @param righttext
	 * @param rightPendIntent
	 * @param ticker
	 * @param title
	 * @param content
	 */
	public void notify_button(int smallIcon, int leftbtnicon, String lefttext, PendingIntent leftPendIntent,
			int rightbtnicon, String righttext, PendingIntent rightPendIntent, String ticker, String title,
			String content, boolean sound, boolean vibrate, boolean lights) {

		requestCode = (int) SystemClock.uptimeMillis();
		setCompatBuilder(rightPendIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
		cBuilder.addAction(leftbtnicon, lefttext, leftPendIntent);
		cBuilder.addAction(rightbtnicon, righttext, rightPendIntent);
		sent();
	}

	public void notify_HeadUp(PendingIntent pendingIntent, int smallIcon, int largeIcon, String ticker, String title,
			String content, int leftbtnicon, String lefttext, PendingIntent leftPendingIntent, int rightbtnicon,
			String righttext, PendingIntent rightPendingIntent, boolean sound, boolean vibrate, boolean lights) {

		setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
		cBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), largeIcon));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			cBuilder.addAction(leftbtnicon, lefttext, leftPendingIntent);
			cBuilder.addAction(rightbtnicon, righttext, rightPendingIntent);
		} else {
			Toast.makeText(mContext, "版本低于Andriod5.0，无法体验HeadUp样式通知", Toast.LENGTH_SHORT).show();
		}
		sent();
	}

	/**
	 * 发送通知
	 */
	private void sent() {
		notification = cBuilder.build();
		// 发送该通知
		nm.notify(NOTIFICATION_ID, notification);
	}

	/**
	 * 根据id清除通知
	 */
	public void clear() {
		// 取消通知
		nm.cancelAll();
	}
}
