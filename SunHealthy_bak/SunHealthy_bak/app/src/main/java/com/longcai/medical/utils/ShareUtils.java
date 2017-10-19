package com.longcai.medical.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.longcai.medical.gloabe.Constant.QQ_APP_ID;
import static com.longcai.medical.gloabe.Constant.WX_APP_ID;

/**
 * @author _H_JY
 *         2015-8-27下午4:26:35
 *         <p>
 *         分享工具类：可以分享到微信好友、微信收藏、微信朋友圈、QQ好友、QQ空间、短信
 */
public class ShareUtils {

    public IWXAPI iwxapi;
    private Tencent tencent;

    public ShareUtils() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 要分享必须先注册(微信)
     */
    public void regToWX(Context context) {

        iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.registerApp(WX_APP_ID);

    }

    /**
     * 要分享必须先注册(QQ)
     */
    public void regToQQ(Context context) {
        tencent = Tencent.createInstance(QQ_APP_ID, context);
    }


    public IWXAPI getIwxapi() {
        return iwxapi;
    }

    public void setIwxapi(IWXAPI iwxapi) {
        this.iwxapi = iwxapi;
    }

    public Tencent getTencent() {
        return tencent;
    }

    public void setTencent(Tencent tencent) {
        this.tencent = tencent;
    }

    public String getWxAppId() {
        return WX_APP_ID;
    }

    public String getQqAppId() {
        return QQ_APP_ID;
    }

    /**
     * 分享到短信
     */
    public Intent sendSMS(String description) {

        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body", description);
        sendIntent.setType("vnd.android-dir/mms-sms");

        return sendIntent;

    }

    /**
     * 分享到微信收藏
     */
    public void shareToWXSceneFavorite(String url, String shareTitle, String description) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(webpageObject);
        wxMediaMessage.title = shareTitle;
        wxMediaMessage.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneFavorite;
        iwxapi.sendReq(req);
    }

    // 0-分享给朋友  1-分享到朋友圈
    public void weiChat(int flag, String url, String shareTitle, String description) {
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareTitle;
        msg.description = description;
//        Bitmap thumBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);//图片大小有限制，太大分享不了
//        msg.thumbData = getBitmapByte(thumBmp,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }

    public void weiChat(int flag, String url, String shareTitle, String description, Bitmap bitmap) {
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareTitle;
        msg.description = description;
//        Bitmap thumBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);//图片大小有限制，太大分享不了
        msg.thumbData = bitmap2Bytes(bitmap, 32);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }

    public byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    /**
     * 分享到QQ好友
     */
    public void shareToQQ(Activity activity, String imageUrl,String url, String shareTitle, String description, IUiListener uiListener) {

        Bundle qqParams = new Bundle();
        qqParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        qqParams.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);
        qqParams.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        qqParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        qqParams.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        //qqParams.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "APP名称");
        tencent.shareToQQ(activity, qqParams, uiListener);

    }

    /**
     * 分享到QQ空间
     */
    public void shareToQzone(final Activity activity, String url, String imageUrl, String shareTitle, String description, IUiListener uiListener) {

        Bundle qzoneParams = new Bundle();
        qzoneParams.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);//必填
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        ArrayList<String> imageUrlList = new ArrayList<String>();
        imageUrlList.add(imageUrl);// imageUrl cannot be null
        qzoneParams.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrlList);
        tencent.shareToQzone(activity, qzoneParams, uiListener);

    }

    public void shareToSinaWeiBo(WbShareHandler shareHandler, String title, String url, Bitmap thumb) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj(title, url);
        ImageObject imageObj = getImageObj(thumb);
        if (null != imageObj) {
            weiboMessage.imageObject = imageObj;
        }
        shareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String title, String url) {
        TextObject textObject = new TextObject();
        textObject.title = title;
        textObject.actionUrl = url;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     */
    public ImageObject getImageObj(String thumb) throws IOException {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = getBitmapFromPath(thumb);
        if (null == bitmap) {
            return null;
        } else {
            imageObject.setImageObject(bitmap);
            return imageObject;
        }
    }

    public ImageObject getImageObj(Bitmap bitmap)  {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    public Bitmap getBitmapFromPath(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
