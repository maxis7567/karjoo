package com.maxis7567.msdialog;

import android.content.Context;
import android.icu.text.StringSearch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ViewHolder> {

    private Context context;
    private List<SpinnerProvince> list;
    private List<SpinnerProvince> listTmp= new ArrayList<>();
    private SpinnerAdapterInterface spinnerAdapterInterface;

    public SpinnerAdapter(Context context, List<SpinnerProvince> list, SpinnerAdapterInterface spinnerAdapterInterface) {
        this.context = context;
        this.list = list;
        listTmp.addAll(list);
        this.spinnerAdapterInterface = spinnerAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_spinner, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.BindItems(listTmp.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerAdapterInterface.OnClick(listTmp.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTmp.size();
    }

    public void search(String s) {
        if(s.length()>0) {
            for (int i = 0; i < listTmp.size(); i++) {
               if(! StringContains.Spelling(s,listTmp.get(i).getName())){
                   listTmp.remove(i);
               }
            }
            notifyDataSetChanged();
        }else {
            listTmp=new ArrayList<>();
            listTmp.addAll(list);
            notifyDataSetChanged();
        }
    }

    interface SpinnerAdapterInterface {
        void OnClick(SpinnerProvince s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_spinner_text);
        }

        void BindItems(SpinnerProvince spinnerProvince) {
            textView.setText(spinnerProvince.getName());
        }
    }
}
