package com.example.sampleapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.sampleapp.R;
import com.example.sampleapp.model.MyData;
import com.example.sampleapp.model.Row;

import java.util.List;


public class CustomListAdapter extends BaseAdapter {

    private Activity activity=null;
    private MyData myData;
    private List<Row> rows;

    public CustomListAdapter(Activity activity,MyData myData){
        this.activity=activity;
        this.myData=myData;
        rows= myData.getRows();
    }
     //View holder pattern for smooth scrolling of listview
    private static class ViewHolder{

        private TextView title_tv;
        private TextView description_tv;
        private ImageView image_custom;

    }


    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view==null){
            view=activity.getLayoutInflater().inflate(R.layout.listitem, null, false);
            holder=new ViewHolder();
            holder.title_tv=view.findViewById(R.id.title_tv);
            holder.description_tv=view.findViewById(R.id.description_tv);
            holder.image_custom= view.findViewById(R.id.thumb_image);
            view.setTag(holder);
        }
     else{
          holder= (ViewHolder) view.getTag();
        }

        holder.title_tv.setText(rows.get(position).getTitle());
        holder.description_tv.setText(rows.get(position).getDescription());
        Object strUrl= rows.get(position).getImageHref();
        if(strUrl!=null){
                Glide.with(activity)
                        .load(strUrl)
                        .into(holder.image_custom);
            }else {

                holder.image_custom.setImageResource(R.mipmap.default_image);
            }
        return view;
    }
}
