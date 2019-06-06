package com.hinext.maxis7567.karjoo.main.fragments.home;

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
    public void onBindViewHolder(@NonNull final com.hinext.maxis7567.karjoo.main.fragments.Adapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder ,position);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeDataList.get(holder.getAdapterPosition()).getType()==1){

                }else {

                }
            }
        });
    }
}
