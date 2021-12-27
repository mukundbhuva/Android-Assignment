package com.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    TabLayout tabContainer;
    ViewPager viewPager;
    private AppUser user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.profile_settings: startActivity(new Intent(Home.this, UpdateProfileActivity.class)); return true;
            case R.id.logout: this.user.logout();startActivity(new Intent(Home.this, MainActivity.class)); finish();
            default: return false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.user = new AppUser(this, getSharedPreferences("chat_app_session", MODE_PRIVATE));

        tabContainer =  findViewById(R.id.parentTabContainer);
        viewPager =  findViewById(R.id.parentViewPager);

        tabContainer.addTab(tabContainer.newTab().setText("Chats"), 0);
        tabContainer.addTab(tabContainer.newTab().setText("Dialogs"), 1);
        tabContainer.addTab(tabContainer.newTab().setText("Trip Survey"), 2);

        MyPagerAdapter adt = new MyPagerAdapter(this, getSupportFragmentManager(), tabContainer.getTabCount());
        viewPager.setAdapter(adt);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabContainer));

        tabContainer.addOnTabSelectedListener(
            new TabLayout.OnTabSelectedListener()
            {
                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            }
        );

    }
}