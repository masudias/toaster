package com.masudias.toaster;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.masudias.toaster.ToastUtil.showToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast(this, getString(R.string.some_toast));
    }
}
