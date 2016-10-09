package com.example.craigpauga.reality.Adapters;

/**
 * Created by CraigPauga on 10/8/16.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.craigpauga.reality.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WatchlistListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;

    public WatchlistListViewAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        holder = new ViewHolder(view);

        Picasso.with(inflater.getContext())
                .load("http://lorempixel.com/200/200/sports/" + (position+1))
                .into(holder.image);

        holder.text.setText("This is a text for the image number: "+position);

        return view;
    }

    static class ViewHolder{
        @InjectView(R.id.image_in_item)
        ImageView image;
        @InjectView(R.id.textview_in_item)
        TextView text;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}