package vn.com.jvit.musicdemoapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 11/21/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Wait...");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
    }


    protected void showLoadingDialog(){
        if(mProgressDialog!=null) mProgressDialog.show();
    }

    protected void hideLoadingDialog(){
        if(mProgressDialog!=null) mProgressDialog.dismiss();
    }
}
