package com.example.odaiodeh.ex4;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;



public class MyAdapter extends BaseAdapter {
    Context context;
    public Bitmap[] pics = new Bitmap[15];




    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return pics.length;
    }

    @Override
    public Object getItem(int position)
    {
        return pics[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = new ImageView(context);
        image.setLayoutParams(new GridView.LayoutParams(400, 400));
        image.setImageBitmap(pics[position]);
        return image;
    }


    public void setter(int pos, Bitmap picBit)
    {
        pics[pos] = picBit;
    }
}
