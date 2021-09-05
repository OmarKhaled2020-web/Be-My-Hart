package com.omar.bemyhart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.omar.bemyhart.Fragments.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private static SharedPreferences sp;
    private static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        edit = sp.edit();

        //setDefaultTheme();

        setDrawerLayout();

        setupBottomNavigationView();

        setupViewPager();

        setupNavigationView();

    }

    private void setDefaultTheme(){
        Set_Default_Theme theme = new Set_Default_Theme(this);
        theme.setDefaultTheme();
    }

    private void setDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_main);
        toolbar = findViewById(R.id.toolBar_main);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.dark_mode_drawer) {
                    item.setActionView(R.layout.theme_switch);
                    @SuppressLint("UseSwitchCompatOrMaterialCode")
                    final Switch themeSwitch = item.getActionView().findViewById(R.id.dark_mode_switch);
                    if (sp.getBoolean("darkMode", false)) {
                        themeSwitch.setChecked(true);
                    }
                    themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            ChangeThemeMode(isChecked);
                        }
                    });
                } else {
                    StartIntentActionNavDrawer(item.getItemId());
                }
                return false;
            }
        });
    }

    private void StartIntentActionNavDrawer(int ID_Fragment){
        Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
        intent.putExtra("fragment",ID_Fragment);
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private  void setupBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.near_me:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.someThing_add:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.fans:
                viewPager.setCurrentItem(3);
                return true;
            case R.id.profile:
                viewPager.setCurrentItem(4);
                return true;
        }
        return false;
    }

    private void setupViewPager(){

        viewPager = findViewById(R.id.parent_container);
        viewPager.setRotationY(180);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.near_me).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.someThing_add).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.fans).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void ChangeThemeMode(boolean state){
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        edit.putBoolean("darkMode",state);
        edit.apply();
        reloadActivityAfterDarkModeChanged();
    }

    public void reloadActivityAfterDarkModeChanged() {
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
            if (bottomNavigationView.getSelectedItemId() == R.id.home){
                super.onBackPressed();
            }else {
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        }
    }

}