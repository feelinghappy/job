package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ArticleListResult implements Serializable {
    private static final long serialVersionUID = -4468804902379942964L;

    /**
     * code : 200
     * msg : success
     * result : [{"article_id":16,"title":"阿萨","thumb":"http://sun.healthywo.com/article/","view_nums":0},{"article_id":14,"title":"健康资讯测试","thumb":"http://sun.healthywo.com/article/","view_nums":0},{"article_id":13,"title":"坚朗走","thumb":"http://sun.healthywo.com/article/","view_nums":0},{"article_id":12,"title":"健康资讯测试二","thumb":"http://sun.healthywo.com/article/","view_nums":0},{"article_id":11,"title":"健康资讯","thumb":"http://sun.healthywo.com/article/","view_nums":0}]
     */
    /**
     * {"article_id":16,"title":"\u963f\u8428","thumb":"","view_nums":4,"url":"http:\/\/sun.healthywo.com\/article\/598948abba35f.html"}
     * article_id : 16
     * title : 阿萨
     * thumb : http://sun.healthywo.com/article/
     * view_nums : 0
     */

    private int article_id;
    private String title;
    private String thumb;
    private int view_nums;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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

    public int getView_nums() {
        return view_nums;
    }

    public void setView_nums(int view_nums) {
        this.view_nums = view_nums;
    }
}
