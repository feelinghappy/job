package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/23.
 */
/*{"code":"200","msg":"success","result":[{"article_id":10,"title":"\u80e1\u841d\u535c","thumb":""}]}*/
public class FoodList {

    /**
     * article_id : 10
     * title : 胡萝卜
     * thumb :
     */

    private int article_id;
    private String title;
    private String thumb;
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
}
