package br.inhonhas.feriapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import br.inhonhas.feriapp.Inhbonhapp;
import br.inhonhas.feriapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 1500;
        int uiOptionsHide = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        View decorView = getWindow().getDecorView();


        decorView.setSystemUiVisibility(uiOptionsHide);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(Inhbonhapp.isLogged()) i = new Intent(SplashActivity.this, MainActivity.class);
                else i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
