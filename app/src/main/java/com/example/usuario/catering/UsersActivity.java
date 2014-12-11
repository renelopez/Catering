package com.example.usuario.catering;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.usuario.catering.R;
import com.example.usuario.catering.adapters.UsersDrawerAdapter;
import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;

public class UsersActivity extends Activity implements OnFragmentInteractionListener {

    private ListView list;
    private DrawerLayout drawer;
    private UsersDrawerAdapter usersDrawerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initUI();
        setAdapter();
        setClicks();
        setToggle();
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
                manager.beginTransaction().replace(R.id.users_content_frame, UserList.newInstance("", "")).commit();
                // activeFragment = Fragments.LIST_FRAGMENT;
                break;
            case 1:
                manager.beginTransaction().replace(R.id.users_content_frame, CreateUser.newInstance("", "")).commit();
                //activeFragment = Fragments.GRID_FRAGMENT;
                break;
            case 2:
                manager.beginTransaction().replace(R.id.users_content_frame, DeleteUser.newInstance("", "")).commit();
                //activeFragment = Fragments.GRID_FRAGMENT;
                break;
        }
    }

    private void setAdapter() {
        usersDrawerAdapter = new UsersDrawerAdapter(this);
        list.setAdapter(usersDrawerAdapter);
    }

    private void initUI() {
        list = (ListView) findViewById(R.id.users_left_drawer);
        drawer = (DrawerLayout) findViewById(R.id.users_drawer_layout);
        findViewById(R.id.users_progress_bar).setVisibility(View.INVISIBLE);
        setTitle("Users Menu");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users, menu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
