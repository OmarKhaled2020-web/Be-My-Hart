package com.omar.bemyhart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;
import com.omar.bemyhart.Fragments.FavouriteFragment;
import com.omar.bemyhart.Fragments.FriendsListFragment;
import com.omar.bemyhart.Fragments.HomeFragment;
import com.omar.bemyhart.Fragments.ProfileFragment;
import com.omar.bemyhart.Fragments.SettingFragment;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private static SharedPreferences sp;
    private static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        edit = sp.edit();

        setDrawerLayout();

        setNavigationView();

        Bundle extras;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras != null) {
                int ID_Fragment = extras.getInt("fragment");
                onNavigationItemSelected(navigationView.getMenu().findItem(ID_Fragment));
                navigationView.setCheckedItem(ID_Fragment);
            }else
                finish();
        } else {
            finish();
        }

    }

    private void setDrawerLayout(){
        ActionBarDrawerToggle toggle;

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar_main);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNavigationView(){
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_drawer:
                loadFragments(new HomeFragment());
                toolbar.setTitle(R.string.home);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.friends_list_drawer:
                loadFragments(new FriendsListFragment());
                toolbar.setTitle(R.string.my_friends);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.favourite_drawer:
                loadFragments(new FavouriteFragment());
                toolbar.setTitle(R.string.favourite);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile_drawer:
                loadFragments(new ProfileFragment());
                toolbar.setTitle(R.string.profile);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.setting_drawer:
                loadFragments(new SettingFragment());
                toolbar.setTitle(R.string.setting);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.dark_mode_drawer:
                item.setActionView(R.layout.theme_switch);
                @SuppressLint("UseSwitchCompatOrMaterialCode")
                final Switch themeSwitch = item.getActionView().findViewById(R.id.dark_mode_switch);
                if(sp.getBoolean("darkMode", false)){
                    themeSwitch.setChecked(true);
                }
                themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setThemeMode(isChecked);
                    }
                });
                break;
        }
        return true;
    }

    private void loadFragments(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout,fragment)
                    .commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setThemeMode(boolean state){
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        edit.putBoolean("darkMode",state);
        edit.apply();
        reloadActivityAfterDarkModeChanged();
    }

    private void reloadActivityAfterDarkModeChanged() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}