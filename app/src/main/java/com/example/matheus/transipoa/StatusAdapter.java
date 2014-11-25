package com.example.matheus.transipoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


public class StatusAdapter extends ArrayAdapter<TwitterStatusViewModel> {

    private final Context context;
    private final List<TwitterStatusViewModel> itemsArrayList;

    public StatusAdapter(Context context, List<TwitterStatusViewModel> itemsArrayList) {

        super(context, R.layout.list_item, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        // 3. Get the two text view from the rowView
        TextView tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        TextView tv_author = (TextView) itemView.findViewById(R.id.tv_author);
        TextView tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        ImageView iv_author_pic = (ImageView) itemView.findViewById(R.id.iv_author_pic);

        // 4. Set the text for textView
        tv_status.setText(itemsArrayList.get(position).status);
        tv_author.setText("@" + itemsArrayList.get(position).author);
        tv_date.setText(itemsArrayList.get(position).getTimeAgo());

        //iv_author_pic.setImageURI(new Uri(itemsArrayList.get(position).author_pic));
	    Picasso.with(context).load(itemsArrayList.get(position).author_pic).into(iv_author_pic);

        // 5. return rowView
        return itemView;
    }

}
