package com.example.letsgotogetherv2;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBar toolbar;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        toolbar.setTitle("Trang chủ");
    }

    fragment_add       fragmentaddFragment = new fragment_add();
    fragment_home     fragmenthomeFragment = new fragment_home();
    fragment_search fragmentsearchFragment = new fragment_search();
    fragment_user     fragmentuserFragment = new fragment_user();

    /* Creat Navigation Menu */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmenthomeFragment).commit();
                toolbar.setTitle("Trang chủ");
                return true;

            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentsearchFragment).commit();
                toolbar.setTitle("Tìm kiếm chuyến");
                return true;

            case R.id.navigation_user:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentuserFragment).commit();
                toolbar.setTitle("Thông tin cá nhân");
                return true;

            case R.id.navigation_add:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentaddFragment).commit();
                toolbar.setTitle("Thêm chuyến");
                return true;
        }
        return true;
    }

    /* Create menu reload */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
