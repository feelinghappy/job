package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */
/*{
    "status": 1,
    "message": "成功",
    "data": {
        "hot_food": [
            {
                "id": "31",
                "name": "根茎类",
                "food": [
                    {
                        "id": "57",
                        "name": "蒜苔",
                        "cover": "http://code.healthywo.com/Public/Uploads/20170606/diet_5936437151fbc.jpg",
                        "url": "http://code.healthywo.com/index.php/api/Activity/food_show.html?id=57"
                    }
                ]
            }
        ],
        "all_food": [
            {
                "id": "31",
                "name": "根茎类",
                "food": []
            }
        ],
        "recipe": [
            {
                "id": "32",
                "name": "早餐",
                "recipe": [
                    {
                        "id": "11",
                        "name": "鸡蛋卷饼",
                        "cover": "http://code.healthywo.com/Public/Uploads/20170606/diet_5936578ab80bd.jpg",
                        "url": "http://code.healthywo.com/index.php/api/Activity/recipe_show.html?id=11"
                    }
                ]
            }
        ]
    }
}*/
public class FoodMaterial {
    public int status;
    public String message;
    public DataBean data;
    public class DataBean {
        public List<HotFoodData> hot_food;
        public List<AllFoodData> all_food;
        public List<RecipeData> recipe;

        @Override
        public String toString() {
            return "DataBean{" +
                    "hot_food=" + hot_food +
                    ", all_food=" + all_food +
                    ", recipe=" + recipe +
                    '}';
        }
    }
    public static class HotFoodData {
        public String id;
        public String name;
        public List<HotFood> food;

        @Override
        public String toString() {
            return "HotFoodData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", food=" + food +
                    '}';
        }
    }
    public static class AllFoodData {
        public String id;
        public String name;
        public List<AllFood> food;

        @Override
        public String toString() {
            return "AllFoodData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", food=" + food +
                    '}';
        }
    }

    public static class RecipeData {
        public String id;
        public String name;
        public List<Recipe> recipe;

        @Override
        public String toString() {
            return "RecipeData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", recipe=" + recipe +
                    '}';
        }
    }
    public static class HotFood {
        public String id;
        public String name;
        public String cover;
        public String url;

        @Override
        public String toString() {
            return "HotFood{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
    public static class AllFood {

        public String id;
        public String name;
        public String cover;
        public String url;

        @Override
        public String toString() {
            return "AllFood{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class Recipe {
        public String id;
        public String name;
        public String cover;
        public String url;

        @Override
        public String toString() {
            return "Recipe{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FoodMaterial{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
