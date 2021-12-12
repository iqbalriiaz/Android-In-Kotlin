package com.example.bluetooth_from_scratch

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Bluetooth setup is accomplished in two steps using the
        1.bluetoothAdapter: bluetoothAdapter represents
          the device's own Bluetooth adapter (the Bluetooth radio).
        2.Enable Bluetooth: ensure that Bluetooth is enabled.
         */

        //bluetoothAdapter
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        //Enable Bluetooth
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val requestEnableBt = 0
            startActivityForResult(enableBtIntent, requestEnableBt)
        }

        //Query paired devices
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val forEach = pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
        }

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }
}