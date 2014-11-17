package com.example.usuario.catering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usuario.catering.models.LoginModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {

    EditText userNameTxt, passwordTxt;
    Button loginBtn;
    ProgressBar loginProgressBar;
    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        setClicks();

    }

    private void initUI() {
        loginProgressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        loginProgressBar.setVisibility(View.INVISIBLE);
        userNameTxt = (EditText) findViewById(R.id.username);
        passwordTxt = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.signIn);
    }

    private void setClicks() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logUser();
            }
        });
    }

    private void logUser() {
        List<NameValuePair> listBody = new ArrayList<NameValuePair>(2);
        listBody.add(new BasicNameValuePair("username", userNameTxt.getText().toString()));
        listBody.add(new BasicNameValuePair("password", passwordTxt.getText().toString()));
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                parseJSON(response);
                performAction();
            }

            @Override
            public void onTaskError(String error) {

            }
        }, new VisibleAnimation(findViewById(R.id.login_progress_bar)), listBody, "/api/login").execute(NetServices.WS_CALL_POST);
    }

    private void performAction() {
        if (loginModel.isValid()) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username and/or Password are invalid.Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        loginModel = gson.fromJson(response, LoginModel.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
