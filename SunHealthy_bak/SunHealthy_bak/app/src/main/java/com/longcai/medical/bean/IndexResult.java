package com.longcai.medical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class IndexResult implements Serializable {
    private static final long serialVersionUID = 1992093460303126819L;
    private AdvertListResult start_advert;
    private AdvertListResult banner_advert;
    private List<ArticleListResult> article_list;
    private List<GetInfoGoodsResult> goods_info;

    public AdvertListResult getStart_advert() {
        return start_advert;
    }

    public void setStart_advert(AdvertListResult start_advert) {
        this.start_advert = start_advert;
    }

    public AdvertListResult getBanner_advert() {
        return banner_advert;
    }

    public void setBanner_advert(AdvertListResult banner_advert) {
        this.banner_advert = banner_advert;
    }

    public List<ArticleListResult> getArticle_list() {
        return article_list;
    }

    public void setArticle_list(List<ArticleListResult> article_list) {
        this.article_list = article_list;
    }

    public List<GetInfoGoodsResult> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(List<GetInfoGoodsResult> goods_info) {
        this.goods_info = goods_info;
    }

    @Override
    public String toString() {
        return "IndexResult{" +
                "start_advert=" + start_advert +
                ", banner_advert=" + banner_advert +
                ", article_list=" + article_list +
                '}';
    }
}
