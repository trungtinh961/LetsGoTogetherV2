package com.example.letsgotogetherv2.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.letsgotogetherv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private ActionBar toolbar;
    FirebaseAuth mAuth;
    private  String userID;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set Title */
        toolbar = getSupportActionBar();
        toolbar.setTitle("Trang chủ");

        /* Bottom Navigation */
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        /* Get user ID */
        mAuth   = FirebaseAuth.getInstance();
    }

    fragment_add fragmentAdd = new fragment_add();
    fragment_home fragmentHome = new fragment_home();
    fragment_search fragmentSearch = new fragment_search();
    fragment_user fragmentUser = new fragment_user();

    /* Creat Navigation Menu */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentHome).commit();
                toolbar.setTitle("Danh sách chuyến");
                return true;

//            case R.id.navigation_search:
//                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentSearch).commit();
//                toolbar.setTitle("Tìm kiếm chuyến");
//                return true;

            case R.id.navigation_user:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentUser).commit();
                toolbar.setTitle("Thông tin cá nhân");
                return true;

            case R.id.navigation_add:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.container, fragmentAdd).commit();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.menuReload:
//                break;
            case R.id.menuLogOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this,SignIn.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
