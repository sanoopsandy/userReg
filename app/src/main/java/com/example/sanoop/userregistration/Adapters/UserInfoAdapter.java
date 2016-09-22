package com.example.sanoop.userregistration.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sanoop.userregistration.Activities.UserListActivity;
import com.example.sanoop.userregistration.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sanoop on 9/22/2016.
 */
public class UserInfoAdapter extends BaseAdapter{

    ArrayList<String> names;
    ArrayList<String> times;
    Context context;
    LayoutInflater inflater;

    public UserInfoAdapter(ArrayList<String> userInfoList, ArrayList<String> userTimings, Context c) {
        names = userInfoList;
        times = userTimings;
        context = c;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_row_layout, viewGroup, false);
        }
        TextView name = (TextView) view.findViewById(R.id.txtName);
        TextView date = (TextView) view.findViewById(R.id.txtTime);
        name.setText(names.get(i));
        date.setText(times.get(i));
        return view;
    }
}
