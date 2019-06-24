package com.hinext.maxis7567.karjoo.detail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.File;
import com.hinext.maxis7567.karjoo.services.Api;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private Context context;
    private List<File> fileList;

    public FileAdapter(Context context, List<File> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(fileList.get(position).getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileList.get(holder.getAdapterPosition()).getDesc()!=null) {
                    TextView title = new TextView(context);
                    title.setText("توضیحات");
                    title.setBackgroundColor(Color.BLACK);
                    title.setPadding(10, 15, 15, 10);
                    title.setGravity(Gravity.RIGHT);
                    title.setBackgroundColor(Color.WHITE);
                    title.setTextSize(22);
                    new AlertDialog.Builder(context)
                            .setCustomTitle(title)
                            .setMessage(fileList.get(holder.getAdapterPosition()).getDesc())
                            .setCancelable(true)
                            .setPositiveButton("دانلود", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent iaa = new Intent(Intent.ACTION_VIEW);
                                    iaa.setData(Uri.parse(Api.SERVER_ADDRESS_IMAGE + fileList.get(holder.getAdapterPosition()).getUrl()));
                                    context.startActivity(iaa);
                                }
                            })
                            .show();
                }else {
                    Intent iaa = new Intent(Intent.ACTION_VIEW);
                    iaa.setData(Uri.parse(Api.SERVER_ADDRESS_IMAGE + fileList.get(holder.getAdapterPosition()).getUrl()));
                    context.startActivity(iaa);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout view;
        private TextView name;
        public ViewHolder(@NonNull View v) {
            super(v);
            view=v.findViewById(R.id.ItemFileView);
            name=v.findViewById(R.id.ItemFileName);
        }

    }
}
