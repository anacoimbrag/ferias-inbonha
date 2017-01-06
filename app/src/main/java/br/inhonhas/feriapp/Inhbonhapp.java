package br.inhonhas.feriapp;

import android.app.Application;

/**
 * Created by Ana Coimbra on 05/01/2017.
 */

public class Inhbonhapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.initializeInstance(this);
    }

    public static boolean isLogged() {
        return PreferencesManager.getInstance().getString("uid") != null;
    }
}
