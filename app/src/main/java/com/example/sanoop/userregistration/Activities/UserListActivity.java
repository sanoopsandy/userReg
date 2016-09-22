package com.example.sanoop.userregistration.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanoop.userregistration.Adapters.LoginDataBaseAdapter;
import com.example.sanoop.userregistration.Adapters.UserInfoAdapter;
import com.example.sanoop.userregistration.R;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {


    LoginDataBaseAdapter loginDataBaseAdapter;
    ListView myList;
    ArrayList<String> userNameList;
    ArrayList<String> userLoginTimeList;
    UserInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        myList = (ListView) findViewById(R.id.lvUser);
        userNameList = new ArrayList<String>();
        userLoginTimeList = new ArrayList<String>();
        showDialog();
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String main = (String) adapter.getItem(i);
                Intent intent = new Intent(UserListActivity.this, UserDetailsActivity.class);
                intent.putExtra("USERNAME", main);
                startActivity(intent);
            }
        });
    }

    private void refreshList(){
        userNameList.clear();
        userLoginTimeList.clear();
        populateListView();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Refreshed !!", Toast.LENGTH_LONG).show();
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Welcome");
        alertDialog.setMessage("Continue with the app?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        populateListView();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void populateListView(){
        Cursor cursor = loginDataBaseAdapter.fetchAll();
        for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
            userNameList.add(cursor.getString(1));
            userLoginTimeList.add(cursor.getString(4));
        }
        adapter = new UserInfoAdapter(userNameList, userLoginTimeList, this);
        myList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences preferences = getSharedPreferences("PREFS", Activity.MODE_PRIVATE);
            preferences.edit().remove("USERNAME").apply();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }else if(id == R.id.action_refresh){
            refreshList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
