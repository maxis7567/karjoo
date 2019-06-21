package com.hinext.maxis7567.karjoo.main.fragments.home;

import android.content.Context;

import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.mstools.OnLoadMoreRecyclerView;

import java.util.List;

public class Adapter extends com.hinext.maxis7567.karjoo.main.fragments.profile.Adapter {
    public Adapter(Context context, List<HomeData> homeDataList, OnLoadMoreRecyclerView onLoadMoreRecyclerView) {
        super(context, homeDataList, onLoadMoreRecyclerView);
    }


}
