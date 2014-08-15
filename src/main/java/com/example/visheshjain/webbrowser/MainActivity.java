package com.example.visheshjain.webbrowser;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import io.onthego.ari.KeyDecodingException;
import io.onthego.ari.android.ActiveAri;
import io.onthego.ari.android.Ari;
import io.onthego.ari.event.DownSwipeEvent;
import io.onthego.ari.event.HandEvent;
import io.onthego.ari.event.ClosedHandEvent;
import io.onthego.ari.event.LeftSwipeEvent;
import io.onthego.ari.event.OpenHandEvent;
import io.onthego.ari.event.RightSwipeEvent;
import io.onthego.ari.event.UpSwipeEvent;


public class MainActivity extends Activity implements Ari.StartCallback,
        Ari.ErrorCallback,
        ClosedHandEvent.Listener,
        LeftSwipeEvent.Listener,
        OpenHandEvent.Listener,
        RightSwipeEvent.Listener,UpSwipeEvent.Listener,DownSwipeEvent.Listener {

    private ActiveAri mAri;
    WebView mWebview;
    EditText urlEditText;


    final static String TAG = "Message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebview = (WebView) findViewById(R.id.mwebView);
        urlEditText = (EditText) findViewById(R.id.editText);
        mWebview.setWebViewClient(new MyBrowser());
        urlEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    ((EditText) view).selectAll();
                }
            }
        });

        try {

            mAri = ActiveAri.getInstance(
                    getString(R.string.ari_api_key),
                    getApplicationContext())
                    .addErrorCallback(this)
                    .addListeners(this);
            // Log.i(TAG,"Ari started");
        } catch (KeyDecodingException e) {
            Log.e(TAG, "Failed to init Ari: " + e);
            finish();
        }
    }

    protected void onResume() {
        super.onResume();

        mAri.start(this);


    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mAri != null) {
            mAri.stop();
        }
    }

    @Override
    public void onAriStart() {

        mAri.enable(HandEvent.Type.UP_SWIPE);
        mAri.enable(HandEvent.Type.DOWN_SWIPE);
    }

    @Override
    public void onAriError(Exception exception) {
        Log.e(TAG, "Ari had an error: " + exception);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle click on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            urlEditText.setText(mWebview.getUrl());
            return true;


        }


    }

    @Override
    public void onClosedHandEvent(ClosedHandEvent event) {

        mWebview.scrollBy(-140, -150);


    }

    public void onUpSwipeEvent(UpSwipeEvent event) {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
            urlEditText.setText(mWebview.getOriginalUrl());
            Toast.makeText(getApplicationContext(), "Got Up hand",
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "Got hand swipe: " + event);
        }
        //mWebview.scrollBy(-100,-100);
        Log.i(TAG,"Up Swipe");
    }

    public void onDownSwipeEvent(DownSwipeEvent event) {

       /* if (mWebview.canGoForward()) {
            mWebview.goForward();
            urlEditText.setText(mWebview.getOriginalUrl());
            Toast.makeText(getApplicationContext(), "Got down hand",
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "Got right hand");
        }*/
       // mWebview.scrollBy(140,150);

    }

    @Override
    public void onOpenHandEvent(OpenHandEvent event) {
        /*String url = urlEditText.getText().toString();
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setHorizontalScrollBarEnabled(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebview.loadUrl(url);
        Log.i(TAG, "opening");*/
        mWebview.scrollBy(140,150);


    }

    @Override
    public void onLeftSwipeEvent(LeftSwipeEvent event) {
        if (mWebview.canGoForward()) {
            mWebview.goForward();
            urlEditText.setText(mWebview.getOriginalUrl());
            Toast.makeText(getApplicationContext(), "Got left  hand",
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "Got right hand");}
       // mWebview.reload();
        /*if (mWebview.canGoBack()) {
            mWebview.goBack();
            urlEditText.setText(mWebview.getOriginalUrl());
            Toast.makeText(getApplicationContext(), "Got leftswipe hand",
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "Got hand swipe: " + event);
        }*/

    }


    @Override
    public void onRightSwipeEvent(RightSwipeEvent event) {
        String url = urlEditText.getText().toString();
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setHorizontalScrollBarEnabled(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebview.loadUrl(url);
        Log.i(TAG, "opening");
    /* String url=urlEditText.getText().toString();
     mWebview.getSettings().setLoadsImagesAutomatically(true);
     mWebview.getSettings().setJavaScriptEnabled(true);
     mWebview.setHorizontalScrollBarEnabled(true);
     mWebview.getSettings().setBuiltInZoomControls(true);
     mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
     mWebview.loadUrl(url);
     Log.i(TAG, "opening");
        if (mWebview.canGoForward()) {
            mWebview.goForward();
            urlEditText.setText(mWebview.getOriginalUrl());
            Log.i(TAG, "Got right hand");
        }*/


    }

}