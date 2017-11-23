package vn.com.jvit.musicdemoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.com.jvit.utils.AppLog;

public class PlayMusicFromLocal extends AsyncTask<Void, Void, List<File>> {


    private File mFile;
    private Context mContext;
    private ListView mLv_list_songs;
    private DownloadListener mListener;
    private List<File> arrPath;


    public void setListener(DownloadListener listener) {
        mListener = listener;
    }


    public PlayMusicFromLocal(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) mListener.onStartLoadFile();

    }

    @Override
    protected List<File> doInBackground(Void... params) {

        File directory = Environment.getExternalStorageDirectory();
        mFile = new File(directory + "/");

        if(mFile.exists()) {
            File list[] = mFile.listFiles();

            arrPath = new ArrayList<>();

            for (File file : list) {

                if(!file.isDirectory()&& getFileExtension(file).equalsIgnoreCase("mp3")){
                    arrPath.add(file);
                }
            }
        }
        return arrPath;



    }



    @Override
    protected void onPostExecute(List<File> result) {

        super.onPostExecute(result);
        AppLog.d("Giang",arrPath.toString()+"    "+result.toString());

        if (mListener != null) mListener.onLoadFileComplete(result);
    }


    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }


    public interface DownloadListener {
        void onStartLoadFile();

        void onLoadFileComplete(List<File> arrFiles);

        void onFailed();
    }


}