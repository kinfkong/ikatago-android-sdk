package com.ikatago.kinfkong.ikatagosdkdemo;

import androidx.appcompat.app.AppCompatActivity;
import ikatagosdk.Client;
import ikatagosdk.DataCallback;
import ikatagosdk.KatagoRunner;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DataCallbackImpl implements DataCallback {
    @Override
    public void callback(byte[] bytes) {
        // simply write the data to log
        Log.d("GTP OUTPUT: ", new String(bytes));
    }
}
public class MainActivity extends AppCompatActivity {
    private void runDemo() {
        final DataCallbackImpl callback = new DataCallbackImpl();
        // create the client
        final Client client = new Client("", "aistudio", "kinfkong", "12345678");
        final List<KatagoRunner> katagoStore = new ArrayList<>();
        // start a thread to run katago
        new Thread(new Runnable() {
            public void run() {
                try {
                    // query the server info
                    String serverInfo = client.queryServer();
                    // you can parse the json string to object
                    Log.d("SDK DEMO", serverInfo);

                    // create the katago runner
                    KatagoRunner katago = client.createKatagoRunner();
                    // set the katago weight or others
                    katago.setKataWeight("20b");
                    katagoStore.add(katago);
                    // start to run the katago
                    katago.run(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // start another thread to send GTP command
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        if (katagoStore.size() == 0) {
                            Thread.sleep(1000);
                            continue;
                        }
                        // send GTP command
                        KatagoRunner katago = katagoStore.get(0);
                        katago.sendGTPCommand("version\n");
                        katago.sendGTPCommand("kata-analyze B 50\n");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runDemo();
    }
}
