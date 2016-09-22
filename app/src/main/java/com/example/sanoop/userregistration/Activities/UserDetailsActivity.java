package com.example.sanoop.userregistration.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanoop.userregistration.Adapters.LoginDataBaseAdapter;
import com.example.sanoop.userregistration.Common;
import com.example.sanoop.userregistration.R;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    TextView txtUser, txtDeviceType, txtDeviceId, txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtDeviceId = (TextView) findViewById(R.id.txtDeviceId);
        txtDeviceType = (TextView) findViewById(R.id.txtDeviceType);
        txtTime = (TextView) findViewById(R.id.txtTime);
        Intent i = getIntent();
        String userName = i.getStringExtra("USERNAME");
        populateUserData(userName);
    }

    private void populateUserData(String userName) {
        ArrayList<String> deviceInfo = loginDataBaseAdapter.getSingleEntry(userName);
        if (!deviceInfo.isEmpty()){
            String deviceId = deviceInfo.get(0);
            String deviceType = deviceInfo.get(1);
            String time = deviceInfo.get(2);
            txtUser.setText(userName);
            txtDeviceId.setText(deviceId);
            txtDeviceType.setText(deviceType);
            txtTime.setText(time);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
