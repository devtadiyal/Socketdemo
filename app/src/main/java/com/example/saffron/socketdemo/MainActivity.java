package com.example.saffron.socketdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    WebSocketClient mWebSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect_to_server();

    }

    public void connect_to_server() {
        URI uri;
        try {
            uri = new URI("ws://dev.tabon.projectdevelopment.co/cart?token=0395a0ce-ac41-43a6-817b-410db1456fd5&xCode=BBZ1OW");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        Map<String, String> headers = new HashMap<>();

        mWebSocketClient = new WebSocketClient(uri, new Draft_17(), headers, 0) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from websocket client");
            }

            @Override
            public void onMessage(String s) {
                Log.i("Websocket", "onMessage"+s);
            }

            @Override
            public void onClose(int code, String s, boolean b) {
                Log.i("Websocket", code + ": Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}
