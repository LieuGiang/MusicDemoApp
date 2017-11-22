package vn.com.jvit.musicdemoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import vn.com.jvit.utils.AppLog;

/**
 * Created by User on 11/21/2017.
 */

class LoadMP3FromURL extends AsyncTask<String, String, String> {
    ProgressDialog mProgressDialog;

    private Context mContext;

    public LoadMP3FromURL(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();

            URL url = new URL(aurl[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            Log.e("AAA",url.toString());
            InputStream input = new BufferedInputStream(url.openStream(),8192);
            String subName = subString(url.toString());
            OutputStream output = new FileOutputStream(root+"/"+subName+".mp3");

            Log.e("AAA",subString(url.toString())+"  ");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            //AppLog.d("AAA",e.toString());
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {
        Log.d("ANDRO_ASYNC", progress[0]);
        mProgressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String unused) {
        mProgressDialog.cancel();
    }

    private String subString(String url) {
        int n = url.lastIndexOf("/");
        int m = url.lastIndexOf(".");
        if(m!=-1){
            String name = url.substring(n+1,m);
            return name;
        }
        String name = url.substring(n+1,url.length());

        return name;
    }
}
