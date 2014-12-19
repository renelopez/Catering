package com.example.usuario.catering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.usuario.catering.adapters.DishListAdapter;
import com.example.usuario.catering.models.DishListModel;
import com.example.usuario.catering.models.DishModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;


public class DishListActivity extends Activity {

    private DishListModel dishListModel;
    private ListView dishListView;
    private DishListAdapter dishListAdapter;
    private String workingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        initUI();
        getData();
        setClicks();

    }

    private void setClicks() {
        dishListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DishModel selectedItem = dishListAdapter.getItem(position);
                selectedItem.setFormattedDate(workingDate);
                Intent intent=new Intent();
                intent.putExtra("itemSelected",selectedItem);
                setResult(500,intent);
                finish();
            }
        });
    }

    private void initUI() {
        Bundle bundleInstance = this.getIntent().getExtras();
        workingDate = bundleInstance.getString("DOW");

        dishListView= (ListView) findViewById(R.id.dish_listView);
        setTitle(getResources().getString(R.string.choose_dish));

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
        }, new VisibleAnimation(findViewById(R.id.list_dishes_progress_bar)), "/dish").execute(NetServices.WS_CALL_GET);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        dishListModel = gson.fromJson(response, DishListModel.class);
        setAdapter(dishListModel);
    }

    private void setAdapter(DishListModel dishListModel) {
        dishListAdapter = new DishListAdapter(this, dishListModel.getDishList());
        dishListView.setAdapter(dishListAdapter);
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
