package com.hinext.maxis7567.karjoo.main.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.JalaliCalendar;
import com.hinext.maxis7567.mstools.OnLoadMoreRecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private DisplayMetricsUtils displayMetricsUtils;
    protected Context context;
    protected List<HomeData> homeDataList=new ArrayList<>();
    private OnLoadMoreRecyclerView onLoadMoreRecyclerView;
    public Adapter(Context context, List<HomeData> homeDataList, OnLoadMoreRecyclerView onLoadMoreRecyclerView) {
        this.context = context;
        this.homeDataList.addAll( homeDataList);
        this.onLoadMoreRecyclerView = onLoadMoreRecyclerView;
        displayMetricsUtils=new DisplayMetricsUtils(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_home_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindItem(homeDataList.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (getItemCount() - 1 == holder.getAdapterPosition()) {
            onLoadMoreRecyclerView.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return homeDataList.size();
    }

    public void addData(List<HomeData> response) {
        homeDataList.addAll(response);
        notifyItemRangeInserted(getItemCount()-response.size(),getItemCount());
    }


    protected class ViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout view;

        public LinearLayout getView() {
            return view;
        }

        private CircleImageView image;
        private TextView name,date,address,describe;
        public ViewHolder(@NonNull View v) {
            super(v);
            view=v.findViewById(R.id.ItemHomeView);
            image=v.findViewById(R.id.ItemHomeImage);
            name=v.findViewById(R.id.ItemHomeName);
            date=v.findViewById(R.id.ItemHomeDate);
            address=v.findViewById(R.id.ItemHomeAddress);
            describe=v.findViewById(R.id.ItemHomeDesc);
        }
        public void bindItem(final HomeData data){

            Picasso.get()
                    .load(Api.SERVER_ADDRESS_IMAGE + data.getImage())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.ic_man)
                    .centerInside()
                    .resize( displayMetricsUtils.convertDIPToPixels(100),displayMetricsUtils.convertDIPToPixels(100))
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Picasso", "fetch image success in first time.");

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(Api.SERVER_ADDRESS_IMAGE + data.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                    .placeholder(R.drawable.ic_man)
                                    .centerInside()
                                    .resize( displayMetricsUtils.convertDIPToPixels(100),displayMetricsUtils.convertDIPToPixels(100))
                                    .into(image, new Callback() {

                                        @Override
                                        public void onSuccess() {
                                            Log.e("Picasso", "fetch image success in try again.");
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("Picasso", e.getMessage());
                                        }

                                    });

                        }
                    });
            name.setText(data.getName());
            address.setText(data.getProvince()+"<"+data.getCity());
            date.setText(JalaliCalendar.gregorian_to_jalali(String.valueOf(data.getDate()/1000)));
            describe.setText(data.getDescribe());
        }
    }
}
