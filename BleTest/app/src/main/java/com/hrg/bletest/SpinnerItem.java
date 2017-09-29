package com.hrg.bletest;


public class SpinnerItem {
	private String bluetoothName;
	private String bluetoothAddress;

	public SpinnerItem(String deviceName, String deviceAddress) {
		this.bluetoothName = deviceName;
		// this.bitmap = bitmap;
		this.bluetoothAddress = deviceAddress;

	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

}
