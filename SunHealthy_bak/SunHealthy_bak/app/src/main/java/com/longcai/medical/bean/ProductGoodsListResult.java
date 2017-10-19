package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ProductGoodsListResult {
    private List<ProductCategoryResult> category;
    private List<ProductGoodsResult> goods_list;

    public List<ProductCategoryResult> getCategory() {
        return category;
    }

    public void setCategory(List<ProductCategoryResult> category) {
        this.category = category;
    }

    public List<ProductGoodsResult> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<ProductGoodsResult> goods_list) {
        this.goods_list = goods_list;
    }
}
