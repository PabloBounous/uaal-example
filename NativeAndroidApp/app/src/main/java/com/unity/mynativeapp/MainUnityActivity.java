package com.unity.mynativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayerActivity;

import com.company.product.OverrideUnityActivity;

//public class MainUnityActivity extends UnityPlayerActivity {
public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToUnityFrame();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(intent == null || intent.getExtras() == null) return;

        if(intent.getExtras().containsKey("doQuit"))
            if(mUnityPlayer != null) {
                finish();
            }
    }

    //@Override
    protected void showMainActivity(String setToColor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("setColor", setToColor);
        startActivity(intent);
    }

    @Override public void onUnityPlayerUnloaded() {
        showMainActivity("");
    }

    public void addControlsToUnityFrame() {
        FrameLayout layout = mUnityPlayer;
        {
            Button myButton = new Button(this);
            myButton.setText("Show Main");
            myButton.setX(10);
            myButton.setY(300);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   showMainActivity("");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Next Filter");
            myButton.setX(320);
            myButton.setY(300);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("FilterPlayer", "OnNextFilter", "");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Screenshot");
            myButton.setX(320);
            myButton.setY(600);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Capture", "CaptureScreenshot", "");
                }
            });
            layout.addView(myButton, 300, 200);
        }
        {
            Button myButton = new Button(this);
            myButton.setText("VideoStart");
            myButton.setX(320);
            myButton.setY(900);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Capture", "CaptureVideoStart", "");
                }
            });
            layout.addView(myButton, 300, 200);
        }
        {
            Button myButton = new Button(this);
            myButton.setText("VideoStop");
            myButton.setX(320);
            myButton.setY(1200);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Capture", "CaptureVideoStop", "");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Unload");
            myButton.setX(630);
            myButton.setY(300);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Finish");
            myButton.setX(630);
            myButton.setY(600);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            layout.addView(myButton, 300, 200);
        }
    }

    static String TAG = "NATIVE CODE";
    // Callbacks Unity->Android
    @Override protected void unloadUnity(String msg) {

        Log.d(TAG, "Callback unloadUnity");
    }
    @Override protected void loadUnityExperienceComplete(String msg) {

        Log.d(TAG, "Callback loadUnityExperienceComplete");
    }
    @Override protected void captureScreenshotCallback(String msg) {

        Log.d(TAG, "Callback captureScreenshotCallback");
    }
    @Override protected void captureVideoCallback(String msg) {

        Log.d(TAG, "Callback captureVideoCallback");
    }
}
