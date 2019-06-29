package edu.arizona.uas.glucose.networking;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Uploader extends AsyncTask<String, Void, Void> {




    public void upload(String data) throws IOException {
        URL url = new URL("http://u.arizona.edu/~lxu/cscv381/local_glucose.php");
        Map<String,String> params = new LinkedHashMap<>();
        params.put("username", "aungkhant");
        params.put("password", "a2456");
        params.put("data", data);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,String> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');

            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.connect();
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
    }


    @Override
    protected Void doInBackground(String... strings) {
        try {
            upload(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
