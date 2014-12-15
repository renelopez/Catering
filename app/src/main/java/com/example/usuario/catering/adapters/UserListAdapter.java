package com.example.usuario.catering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.usuario.catering.R;
import com.example.usuario.catering.models.UserModel;

import java.util.ArrayList;

/**
 * Created by rene.lopez on 12/10/2014.
 */
public class UserListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final ArrayList<UserModel> userList;

    public UserListAdapter(Context context, ArrayList<UserModel> userList) {
        inflater = LayoutInflater.from(context);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return userList.get(i).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        UserViewHolder userViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_row_drawer, null);
            userViewHolder = new UserViewHolder();
            initUI(userViewHolder, convertView);
            convertView.setTag(userViewHolder);
        } else {
            userViewHolder = (UserViewHolder) convertView.getTag();
        }

        setText(userViewHolder, position);
        return convertView;
    }

    private void setText(UserViewHolder userViewHolder, int position) {
        UserModel model = userList.get(position);
        userViewHolder.text_name.setText(model.getFirstName() + " " + model.getLastName());
        // dishViewHolder.text_description.setText(model.getDescription());
    }

    private void initUI(UserViewHolder userViewHolder, View convertView) {
        userViewHolder.text_name = (TextView) convertView.findViewById(R.id.username_row);
        //dishViewHolder.text_description = (TextView) convertView.findViewById(R.id.dishDescription);
    }

    private class UserViewHolder {
        TextView text_name;
        TextView text_description;
    }
}
