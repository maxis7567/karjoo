package com.hinext.maxis7567.karjoo.main.fragments.request;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.mstools.OnLoadMoreRecyclerView;

import java.util.List;

public class Adapter extends com.hinext.maxis7567.karjoo.main.fragments.Adapter {
    public Adapter(Context context, List<HomeData> homeDataList, OnLoadMoreRecyclerView onLoadMoreRecyclerView) {
        super(context, homeDataList, onLoadMoreRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    void addAll(List<HomeData> list){
        homeDataList.clear();
        homeDataList.addAll(list);
        notifyDataSetChanged();
    }
    void addSearchData(List<HomeData> list){
        homeDataList.clear();
        homeDataList.addAll(list);
        notifyDataSetChanged();
    }
}
