package com.hinext.maxis7567.karjoo.detail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.Skills;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {
    private Context context;
    private List<Skills> fileList;
    private boolean isSkill;

    public SkillsAdapter(Context context, List<Skills> fileList,boolean isSkill) {
        this.context = context;
        this.fileList = fileList;
        this.isSkill = isSkill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_skill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.name.setText(fileList.get(position).getSkills());
        if (isSkill) {
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                            .show();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public ViewHolder(@NonNull View v) {
            super(v);
            name=v.findViewById(R.id.ItemSkillTextView);
        }

    }
}
