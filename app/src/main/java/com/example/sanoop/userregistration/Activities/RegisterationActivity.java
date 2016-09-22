package com.example.sanoop.userregistration.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sanoop.userregistration.Adapters.LoginDataBaseAdapter;
import com.example.sanoop.userregistration.Common;
import com.example.sanoop.userregistration.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String deviceId = null;
    String devicetype = null;
    String userName = null;
    EditText txtUserName;
    LoginDataBaseAdapter loginDataBaseAdapter;
    int MY_PERMISSION_READ_PHONE_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        setupSpinner();
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        txtUserName = (EditText) findViewById(R.id.edtName);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSION_READ_PHONE_STATE);
            return;
        }
    }

    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> deviceType = new ArrayList<String>();
        deviceType.add("DEVICEID");
        deviceType.add("MACID");
        deviceType.add("IMEI");
        ArrayAdapter<String> deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deviceType);
        deviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(deviceAdapter);
    }

    public void registerUser(View view){
        userName = txtUserName.getText().toString();
        if (userName.equals("")){
            Toast.makeText(this, "Username is blank. Please enter a user name", Toast.LENGTH_LONG).show();
            return;
        }
        if (deviceId.equals("")) {
            Toast.makeText(this, "Device information unavailable, please check your device.", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            loginDataBaseAdapter.insertEntry(userName, deviceId, devicetype, formattedDate);
            Toast.makeText(this, "User registered successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registeration, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        devicetype = parent.getItemAtPosition(i).toString();
        deviceId = Common.getDeviceId(parent.getContext(), devicetype);
        Toast.makeText(this, deviceId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
