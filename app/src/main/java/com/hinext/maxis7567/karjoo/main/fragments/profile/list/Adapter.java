package com.hinext.maxis7567.karjoo.main.fragments.profile.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.ListData;
import com.hinext.maxis7567.mstools.JalaliCalendar;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<ListData> listData;

    public Adapter(Context context, List<ListData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout view;
        private TextView address,date,title,viewed,desc;
        public ViewHolder(@NonNull View v) {
            super(v);
            view=v.findViewById(R.id.ItemListView);
            address=v.findViewById(R.id.ItemListAddress);
            date=v.findViewById(R.id.ItemListDate);
            title=v.findViewById(R.id.ItemListName);
            viewed=v.findViewById(R.id.ItemListEye);
            desc=v.findViewById(R.id.ItemListDesc);
        }
        public void bindItem(ListData listData){
            address.setText(listData.getProvince()+"<"+listData.getCity());
            date.setText(JalaliCalendar.gregorian_to_jalali(String.valueOf(listData.getDate()/1000)));
            title.setText(listData.getTitle());
            viewed.setText(String.valueOf(listData.getView()));
            desc.setText(listData.getDescribe());
        }
    }
}
