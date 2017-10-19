package com.longcai.medical.service;

import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.fragment.FamilyFragment;
import com.longcai.medical.ui.fragment.HomeFragment;
import com.longcai.medical.ui.fragment.MineFragment;

import java.util.HashMap;

/**
 * 生产fragment工厂
 *
 * @author Kevin
 * @date 2015-10-27
 */
public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int pos) {
        // 先从集合中取, 如果没有,才创建对象, 提高性能
        BaseFragment fragment = mFragmentMap.get(pos);
        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new FamilyFragment();
                    break;
                case 2:
//                    fragment = new DiscoverFragment();
//                    break;
//                case 3:
                    fragment = new MineFragment();
                    break;
                default:
                    break;
            }
            mFragmentMap.put(pos, fragment);// 将fragment保存在集合中
        }
        return fragment;
    }
}
