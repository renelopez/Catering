package com.example.usuario.catering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.usuario.catering.R;
import com.example.usuario.catering.models.DishModel;

import java.util.ArrayList;

/**
 * Created by Usuario on 28/11/2014.
 */
public class DishListAdapter extends BaseAdapter {
    public ArrayList<DishModel> dishList;
    private LayoutInflater inflater;

    public DishListAdapter(Context context, ArrayList<DishModel> dishList) {
        inflater = LayoutInflater.from(context);
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        DishViewHolder dishViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dish_row_drawer, null);
            dishViewHolder = new DishViewHolder();
            initUI(dishViewHolder, convertView);
            convertView.setTag(dishViewHolder);
        } else {
            dishViewHolder = (DishViewHolder) convertView.getTag();
        }

        setText(dishViewHolder, position);
        return convertView;
    }

    private void setText(DishViewHolder dishViewHolder, int position) {
        DishModel model = dishList.get(position);
        dishViewHolder.text_name.setText(model.getName());
        dishViewHolder.text_description.setText(model.getDescription());
    }

    private void initUI(DishViewHolder dishViewHolder, View convertView) {
        dishViewHolder.text_name = (TextView) convertView.findViewById(R.id.dishName);
        dishViewHolder.text_description = (TextView) convertView.findViewById(R.id.dishDescription);
    }

    private class DishViewHolder {
        TextView text_name;
        TextView text_description;
    }
}
