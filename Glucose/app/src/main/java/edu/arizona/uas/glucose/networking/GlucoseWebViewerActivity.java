package edu.arizona.uas.glucose.networking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.arizona.uas.glucose.R;

public class GlucoseWebViewerActivity extends AppCompatActivity {
    WebView web_viewer;
    private static String url = "http://u.arizona.edu/~lxu/cscv381/myglucose.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose_web_viewer);

        web_viewer = (WebView) findViewById(R.id.web_viewer);
        loadWebPage();
    }

    private void loadWebPage() {
        web_viewer.getSettings().setJavaScriptEnabled(true);
        web_viewer.setWebViewClient(new WebViewClient());
        web_viewer.loadUrl(url);
    }
}
