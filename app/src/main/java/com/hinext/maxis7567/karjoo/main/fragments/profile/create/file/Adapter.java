package com.hinext.maxis7567.karjoo.main.fragments.profile.create.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.maxis7567.msdialog.MSdialog;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    List<MyFile> list;
    private View DIALOG;
    private ViewGroup viewGroup;

    public Adapter(Context context, List<MyFile> list,ViewGroup viewGroup) {
        this.context = context;
        this.list = list;

        this.viewGroup = viewGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.item_file, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            holder.name.setText(list.get(position).getName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DIALOG= new MSdialog(context,viewGroup).SpecificsDialog(((Activity) context).getWindow().getDecorView(), new MSdialog.MSdialogInterfaceSpecifics() {
                        @Override
                        public void OnConfirmed(String detail) {
                            list.get(holder.getAdapterPosition()).setDesc(detail);
                            viewGroup.removeView(DIALOG);
                        }

                        @Override
                        public void OnCancel() {
                            viewGroup.removeView(DIALOG);
                        }
                    });
                    viewGroup.addView(DIALOG);
                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private ImageView image;
        private TextView name;
        public ViewHolder(@NonNull View v) {
            super(v);
            view=v.findViewById(R.id.ItemFileView);
            image=v.findViewById(R.id.ItemFileImage);
            name=v.findViewById(R.id.ItemFileName);
        }
    }
    public void addFile(List<MyFile> list1){
        list.addAll(list1);
        notifyDataSetChanged();
    }


}
