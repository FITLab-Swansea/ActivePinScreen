package com.fitlab.tacpiano;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public BluetoothAdapter btAdapter;
    public BluetoothDevice btDevice;
    public BluetoothSocket btSocket;

    public static final String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID
    public static final String SERVICE_ADDRESS = "20:18:03:19:34:95"; // HC-05 BT ADDRESS


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer playdoremi = MediaPlayer.create(this, R.raw.doremi);
        final MediaPlayer mpdu = MediaPlayer.create(this, R.raw.du);
        final MediaPlayer mpre = MediaPlayer.create(this, R.raw.re);
        final MediaPlayer mpmi = MediaPlayer.create(this, R.raw.mi);
        final MediaPlayer mpfa = MediaPlayer.create(this, R.raw.fa);

        final Button play = (Button) this.findViewById(R.id.play);
        Button du = (Button) this.findViewById(R.id.du);
        Button re = (Button) this.findViewById(R.id.re);
        Button mi = (Button) this.findViewById(R.id.mi);
        Button fa = (Button) this.findViewById(R.id.fa);


        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btDevice = btAdapter.getRemoteDevice(SERVICE_ADDRESS);

        if(btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not available", Toast.LENGTH_LONG).show();
        }
        else {
            if(!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 3);
            } else {
                ConnectThread connectThread = new ConnectThread(btDevice);
                connectThread.start();
            }
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playdoremi.start();
                String codel = "$G000";
                byte[] bleft = codel.getBytes();

                if (btSocket != null){
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(bleft);
                    } catch (IOException e) {
                    }
                }
            }
        });

        du.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpdu.start();
                String codel = "$G001";
                byte[] bleft = codel.getBytes();

                if (btSocket != null){
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(bleft);
                    } catch (IOException e) {
                    }
                }

            }
        });

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpre.start();
                String codel = "$G002";
                byte[] bleft = codel.getBytes();

                if (btSocket != null){
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(bleft);
                    } catch (IOException e) {
                    }
                }
            }
        });

        mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpmi.start();
                String codel = "$G003";
                byte[] bleft = codel.getBytes();

                if (btSocket != null){
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(bleft);
                    } catch (IOException e) {
                    }
                }
            }
        });

        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpfa.start();
                String codel = "$G004";
                byte[] bleft = codel.getBytes();

                if (btSocket != null){
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(bleft);
                    } catch (IOException e) {
                    }
                }
            }
        });



    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket thisSocket;
        private final BluetoothDevice thisDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            thisDevice = device;

            try {
                tmp = thisDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
            } catch (IOException e) {
                Log.e("TEST", "Can't connect to service");
            }
            thisSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            btAdapter.cancelDiscovery();

            try {
                thisSocket.connect();
                Log.d("TESTING", "Connected to shit");
            } catch (IOException connectException) {
                try {
                    thisSocket.close();
                } catch (IOException closeException) {
                    Log.e("TEST", "Can't close socket");
                }
                return;
            }

            btSocket = thisSocket;

        }
        public void cancel() {
            try {
                thisSocket.close();
            } catch (IOException e) {
                Log.e("TEST", "Can't close socket");
            }
        }
    }

}
