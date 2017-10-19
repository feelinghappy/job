package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ArticleInfoResult {

    /**
     * article_id : 14
     * create_time : 2017-08-08 13:06:14
     * title : 健康资讯测试
     * thumb :
     * url : http://sun.healthywo.com./article/598946c655d38.html
     * view_nums : 1
     * content : <p>健康资讯测试</p>
     */

    private int article_id;
    private String create_time;
    private String title;
    private String thumb;
    private String url;
    private int view_nums;
    private String content;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getView_nums() {
        return view_nums;
    }

    public void setView_nums(int view_nums) {
        this.view_nums = view_nums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
