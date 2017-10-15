/*
 * @author: Illya Balakin
 * Created on 10/10/2017
 * CS4322
 * HW 2
 */

package com.illyabalakin.hw2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;


/**
 * Defines Adapter class for the RecyclerView
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<SearchItem> queryList;
    private Context context;

    MyAdapter(List<SearchItem> queryList, Context context) {
        this.queryList = queryList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(rootView, context, queryList);
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        final int position = i;

        holder.btnTag.setText(queryList.get(i).getTag());

        holder.btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).tagBtnClicked(position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).editBtnClicked(position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).deleteBtnClicked(position);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        Button btnEdit;
        Button btnTag;
        Button btnDelete;

        List<SearchItem> queryList1 = new ArrayList<>();
        Context context;

        private MyViewHolder(View itemView, Context context, List<SearchItem> queryList1) {
            super(itemView);
            this.queryList1 = queryList1;
            this.context = context;
            itemView.setOnClickListener(this);

            cv = itemView.findViewById(R.id.cardView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnTag = itemView.findViewById(R.id.btnTag);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }


        @Override
        public void onClick(View v) {

        }
    }

}

