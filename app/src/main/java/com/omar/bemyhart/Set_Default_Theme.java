package com.omar.bemyhart;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class Set_Default_Theme {

    private static SharedPreferences sp;
    private Context context;

    public Set_Default_Theme(Context context) {
        this.context = context;
    }

    public void setDefaultTheme(){
        sp = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);

        if(sp.getBoolean("darkMode",false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
