package com.example.usuario.catering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.usuario.catering.adapters.UserListAdapter;
import com.example.usuario.catering.models.UserListModel;
import com.example.usuario.catering.models.UserModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;


public class UserListActivity extends Activity {

    private ListView userListView;
    private UserListAdapter userListAdapter;
    private UserListModel userListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initUI();
        getData();
        setClicks();

    }

    private void setClicks() {
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserModel selectedItem = (UserModel) userListAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("itemSelected", selectedItem);
                setResult(500, intent);
                finish();
            }
        });
    }

    private void initUI() {
        userListView = (ListView) findViewById(R.id.user_listView);
        setTitle(getResources().getString(R.string.choose_user));
    }

    private void getData() {
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                parseJSON(response);
                performAction();

            }

            @Override
            public void onTaskError(String error) {

            }
        }, new VisibleAnimation(findViewById(R.id.list_users_progress_bar)), "/user").execute(NetServices.WS_CALL_GET);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        userListModel = gson.fromJson(response, UserListModel.class);
        setAdapter();
    }

    private void setAdapter() {
        userListAdapter = new UserListAdapter(this, userListModel.getUserList());
        userListView.setAdapter(userListAdapter);
    }

    private void performAction() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dish_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
