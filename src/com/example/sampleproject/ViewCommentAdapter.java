package com.example.sampleproject;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCommentAdapter extends ArrayAdapter {

private Context context;

List list = new ArrayList();

public ViewCommentAdapter(Context context, int resource) {
    super(context, resource);
    this.context = context;
    // TODO Auto-generated constructor stub
}


public void add(CommentClass ob) {
    // TODO Auto-generated method stub
    super.add(ob);
    list.add(ob);
}


@Override
public int getCount() {
    // TODO Auto-generated method stub
    return this.list.size();
}

@Override
public Object getItem(int position) {
    return this.list.get(position);
}


@Override
public View getView(final int position, View convertView, ViewGroup parent) {
    View row;
    row = convertView;

    if(row==null) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.commentitem,parent,false);
    }

            ImageView pro_picImg2 = (ImageView) row.findViewById(R.id.image222);
            TextView user_nameTv2 = (TextView) row.findViewById(R.id.name222);
            TextView timestamTv2 = (TextView) row.findViewById(R.id.time222);
            TextView Comment=(TextView) row.findViewById(R.id.tvcom);
  

            CommentClass cd = (CommentClass) getItem(position);

            pro_picImg2.setImageBitmap(cd.getPro_pic_bitmap());
            user_nameTv2.setText(cd.getUserName());
            timestamTv2.setText(cd.getTimePost());
            Comment.setText(cd.getCommentPost());
            
        return row;
}

}