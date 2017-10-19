package com.longcai.medical.service;

import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.fragment.DeviceRobot;
import com.longcai.medical.ui.fragment.DeviceWhatch;

import java.util.HashMap;




/**
 * 生产fragment工厂
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class DeviceFragmentFactory {

	private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int pos) {
		// 先从集合中取, 如果没有,才创建对象, 提高性能
		BaseFragment fragment = mFragmentMap.get(pos);

		if (fragment == null) {
			switch (pos) {
			case 0:
				fragment = new DeviceRobot();
				break;
			case 1:
				fragment = new DeviceWhatch();
				break;

			default:
				break;
			}

			mFragmentMap.put(pos, fragment);// 将fragment保存在集合中
		}

		return fragment;
	}
}
