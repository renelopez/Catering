package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.usuario.catering.adapters.DishesDrawerAdapter;
import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;


public class DishesActivity extends Activity implements OnFragmentInteractionListener {

    private ListView list;
    private Fragment activeFragment;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private DishesDrawerAdapter dishesDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        initUI();
        setAdapter();
        setClicks();
        setToggle();
    }

    private void setClicks() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                drawer.closeDrawers();
                openFragment(position);
            }
        });
    }

    private void openFragment(int position) {
        FragmentManager manager = getFragmentManager();
        switch (position) {
            case 0:
                manager.beginTransaction().replace(R.id.dishes_content_frame, DishList.newInstance("", "")).commit();
                // activeFragment = Fragments.LIST_FRAGMENT;
                break;
            case 1:
                manager.beginTransaction().replace(R.id.dishes_content_frame, CreateDish.newInstance("", "")).commit();
                //activeFragment = Fragments.GRID_FRAGMENT;
                break;
            case 2:
                manager.beginTransaction().replace(R.id.dishes_content_frame, DeleteDish.newInstance("", "")).commit();
                //activeFragment = Fragments.GRID_FRAGMENT;
                break;
            case 3:
                manager.beginTransaction().replace(R.id.dishes_content_frame, CreateDishMenu.newInstance("", "")).commit();
                //activeFragment = Fragments.BOTH_FRAGMENT;
                break;
            case 4:
                //manager.beginTransaction().replace(R.id.dishes_content_frame, ListMenu.newInstance("", ""), "CreateDishMenu").commit();
                //activeFragment = Fragments.BOTH_FRAGMENT;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawerToggle.syncState();
    }

    private void setToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.drawable.ic_drawer,
                R.string.action_settings,
                R.string.action_settings);
        drawer.setDrawerListener(mDrawerToggle);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setAdapter() {
        dishesDrawerAdapter = new DishesDrawerAdapter(this);
        list.setAdapter(dishesDrawerAdapter);
    }

    private void initUI() {
        list = (ListView) findViewById(R.id.dishes_left_drawer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.dishes_progress_bar).setVisibility(View.INVISIBLE);
        setTitle("Dishes Menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dishes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
