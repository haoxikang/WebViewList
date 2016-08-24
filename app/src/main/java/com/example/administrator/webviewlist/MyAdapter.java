package com.example.administrator.webviewlist;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 康颢曦 on 2016/8/23.
 */

public class MyAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new MyHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.view, parent,
                false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==0){
            ((MyHolder)holder).textView.setText("1");
        }else {
            ((MyHolder)holder).textView.setText("2");
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==1){
            return 0;
        }
        if (position%2==0){
            return 1;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return 21;
    }


}
