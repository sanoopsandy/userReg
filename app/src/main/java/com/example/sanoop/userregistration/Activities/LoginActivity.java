package com.example.sanoop.userregistration.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanoop.userregistration.Adapters.LoginDataBaseAdapter;
import com.example.sanoop.userregistration.Common;
import com.example.sanoop.userregistration.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    EditText txtUserName;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        txtUserName = (EditText) findViewById(R.id.edtUserName);
    }

    public void login(View view){
        String userName= txtUserName.getText().toString();
        ArrayList<String> deviceInfo = loginDataBaseAdapter.getSingleEntry(userName);
        if (deviceInfo.isEmpty()){
            Toast.makeText(this, "Username does not match any name in the DB. Please register if a new user.", Toast.LENGTH_LONG).show();
        }else {
            String deviceId = deviceInfo.get(0);
            String deviceType = deviceInfo.get(1);
            String currentDeviceId = Common.getDeviceId(this, deviceType);

            if (currentDeviceId.equals(deviceId)) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                loginDataBaseAdapter.updateEntry(userName, deviceId, deviceType, formattedDate);
                Toast.makeText(this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, UserListActivity.class);
                startActivity(i);
                saveUserState(userName);
            } else {
                Toast.makeText(this, "User Name or Device does not match", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void register(View view){
        Intent i = new Intent(this, RegisterationActivity.class);
        startActivity(i);
    }

    private void saveUserState(String userName){
        SharedPreferences mySharedPreferences =
                this.getSharedPreferences("PREFS", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("USERNAME", userName);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mySharedPreferences =
                this.getSharedPreferences("PREFS", Activity.MODE_PRIVATE);
        String userName = mySharedPreferences.getString("USERNAME", null);
        if (userName != null){
            Intent i = new Intent(this, UserListActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
