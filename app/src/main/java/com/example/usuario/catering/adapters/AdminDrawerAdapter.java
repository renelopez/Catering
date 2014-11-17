package com.example.usuario.catering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.usuario.catering.R;

import java.util.ArrayList;

public class AdminDrawerAdapter extends BaseAdapter {
    public ArrayList<String> menu = new ArrayList<String>();
    private LayoutInflater inflater;

    public AdminDrawerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        menu.add("View week orders");
        menu.add("Dishes");
        menu.add("Users");
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_drawer, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setText(holder, position);
        return convertView;
    }

    private void setText(ViewHolder holder, int position) {
        holder.text.setText((String) getItem(position));
    }

    private class ViewHolder {
        TextView text;
    }
}
