package com.hinext.maxis7567.karjoo.main.fragments.offer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.Jobs;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Jobs> jobsList;
    private ISearch iSearch;
    public SearchAdapter(Context context, List<Jobs> jobsList, ISearch iSearch) {
        this.context = context;
        this.jobsList = jobsList;
        this.iSearch = iSearch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindItems(jobsList.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSearch.onClick(jobsList.get(holder.getAdapterPosition()).getId());
            }
        });
    }
    interface ISearch{
        void onClick(int id);
    }
    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView name;
        public ViewHolder(@NonNull View v) {
            super(v);
            view=v.findViewById(R.id.ItemSearchView);
            name=v.findViewById(R.id.ItemSearchName);
        }
        void bindItems(Jobs jobs){
            name.setText(jobs.getName());
        }
    }

    void addAll(List<Jobs> list){
        jobsList.clear();
        jobsList.addAll(list);
        notifyDataSetChanged();
    }
}
