package com.example.craigpauga.reality.Adapters;

/**
 * Created by CraigPauga on 10/8/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.craigpauga.reality.LauncherActivity;
import com.example.craigpauga.reality.LoginActivity;
import com.example.craigpauga.reality.MainActivity;
import com.example.craigpauga.reality.PinLockActivity;
import com.example.craigpauga.reality.R;
import com.example.craigpauga.reality.SetPinLockActivity;
import com.example.craigpauga.reality.Utilities.Property;
import com.example.craigpauga.reality.Utilities.propertyInfo;
import com.example.craigpauga.reality.propertyDetailsActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import static com.example.craigpauga.reality.LauncherActivity.propertyList;

public class ProspectsListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<String> propertyNameList = new ArrayList<>();
    public DatabaseReference mPropInfo;
    public DatabaseReference mDatabase;
    public String key;
    public String current_value;
    private String propertyName;
    private String propertyPic;
    private String amountFunded;
    private float amountFunded_int;
    private String amountTotal;
    private float amountTotal_int;
    RoundCornerProgressBar progressBar;
    float progress;

    public ProspectsListViewAdapter(LayoutInflater inflater, Context context){

        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        int propListSize = propertyList.size();
        return propListSize;
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
        final int position2 = position;
        ////////////// Pull Property Information//////////////////////
        propertyName = propertyList.get(position).getPropertyName();
        propertyPic = propertyList.get(position).getPropertyPic();
        amountFunded = propertyList.get(position).getAmountFunded();
        amountTotal = propertyList.get(position).getAmountTotal();


        amountFunded_int = Integer.parseInt(propertyList.get(position).getAmountFunded());
        amountTotal_int = Integer.parseInt(propertyList.get(position).getAmountTotal());
        progress = amountFunded_int / amountTotal_int;
        float progress_adjustment = progress*(320);
        //////////////////////////////////////////////////////////////


        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        RoundCornerProgressBar progressBar = (RoundCornerProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setProgress(progress_adjustment);

        holder = new ViewHolder(view);

        Picasso.with(inflater.getContext())
                .load(propertyPic)
                .into(holder.image);

        holder.text.setText(propertyName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, propertyDetailsActivity.class);
                intent.putExtra("position",position2);
                context.startActivity(intent);
            }
        });
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