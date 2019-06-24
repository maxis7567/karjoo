package com.hinext.maxis7567.karjoo.main.fragments.profile.create;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hinext.maxis7567.karjoo.detail.SkillsAdapter;
import com.hinext.maxis7567.karjoo.models.Skills;
import com.maxis7567.msdialog.MSdialog;

import java.util.List;

public class Adapter extends SkillsAdapter {
    private  View DIALOG ;
    private final ViewGroup viewGroup;

    public Adapter(Context context, List<Skills> fileList, boolean isSkill, ViewGroup viewGroup) {
        super(context, fileList, isSkill);
        this.viewGroup = viewGroup;
    }

    @Override
    public void onBindViewHolder(@NonNull final SkillsAdapter.ViewHolder holder, int position) {
        holder.name.setText(fileList.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSkill){
                    DIALOG= new MSdialog(context,viewGroup).SpecificsDialog(((Activity) context).getWindow().getDecorView(), new MSdialog.MSdialogInterfaceSpecifics() {
                        @Override
                        public void OnConfirmed(String detail) {
                            fileList.get(holder.getAdapterPosition()).setDescribe(detail);
                            viewGroup.removeView(DIALOG);
                        }

                        @Override
                        public void OnCancel() {
                                        viewGroup.removeView(DIALOG);
                        }
                    });
                    viewGroup.addView(DIALOG);
                }
            }
        });

    }

    public void addTag(Skills skills){
        fileList.add(skills);
        notifyItemInserted(fileList.size());
    }
}
