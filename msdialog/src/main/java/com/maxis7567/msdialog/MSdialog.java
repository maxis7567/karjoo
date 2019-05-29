package com.maxis7567.msdialog;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hinext.maxis7567.mstools.FaNumToEnNum;
import com.hinext.maxis7567.mstools.priceConvertor;

import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;


public class MSdialog {
    private Context context;
    private ViewGroup parent;
    private TextView mTitle;
    private TextView mDescribe;
    private Button mSuccess;
    private Button mCancel;

    private BlurView blurView;


    public MSdialog(Context context, ViewGroup parent) {
        this.context = context;
        this.parent = parent;
    }


    public View DefaultDialog(View decorView, String subject, String describe, String buttonTitle, final MSdialogInterfaceDefault mSdialogInterfaceDefault) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.default_dialog, parent, false);
        blurView = dialogView.findViewById(R.id.Default_Dialog);
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDescribe = dialogView.findViewById(R.id.Dialog_Describe);
        mTitle = dialogView.findViewById(R.id.Dialog_Title);
        mSuccess = dialogView.findViewById(R.id.Dialog_Success);
        mCancel = dialogView.findViewById(R.id.Dialog_Cancel);
        mTitle.setText(subject);
        mDescribe.setText(describe);
        mSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSdialogInterfaceDefault.OnConfirmed();
            }
        });
        if (!(buttonTitle == null)) {
            mSuccess.setText(buttonTitle);
        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSdialogInterfaceDefault.OnCancel();
            }
        });
        return dialogView;
    }


    public View ConfirmDialog(View decorView, String title, String describe, String buttonTitle, final MSdialogInterfaceConfirm OnConfirmed) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.confirm_dialog, parent, false);
        mDescribe = dialogView.findViewById(R.id.DialogCN_Describe);
        mTitle = dialogView.findViewById(R.id.DialogCN_Title);
        mSuccess = dialogView.findViewById(R.id.DialogCN_Success);
        blurView = dialogView.findViewById(R.id.DialogCN_BlurView);
        if (title == null) {
            mTitle.setVisibility(View.GONE);
        }
        if (!(buttonTitle == null)) {
            mSuccess.setText(buttonTitle);
        }
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTitle.setText(title);
        mDescribe.setText(describe);
        mSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnConfirmed.OnConfirmed();
            }
        });
        return dialogView;
    }

    public View MessageDialog(View decorView,String title ,String message, final MSdialogInterfaceMessage OnClose) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.message_dialog, parent, false);
        ImageView mCloseBTN = dialogView.findViewById(R.id.DialogMS_CloseBTN);
        mTitle=dialogView.findViewById(R.id.Message_Title);
        mDescribe = dialogView.findViewById(R.id.DialogMS_Describe);
        blurView = dialogView.findViewById(R.id.message_blur);
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClose.onDismmis();
            }
        });
        mDescribe.setText(message);
        mCloseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClose.onDismmis();
            }
        });
        return dialogView;
    }

    public View Spinner(View decorView, List<SpinnerProvince> list, final MSdialogInterfaceSpinner OnSelect) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.spiner_view, parent, false);
        blurView = dialogView.findViewById(R.id.spinner_blurView);
        RecyclerView recyclerView = dialogView.findViewById(R.id.spinner_recycler);
        EditText searchView = dialogView.findViewById(R.id.spinner_search);
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSelect.OnCancel();
            }
        });
        final SpinnerAdapter spinnerAdapter=new SpinnerAdapter(context, list, new SpinnerAdapter.SpinnerAdapterInterface() {
            @Override
            public void OnClick(SpinnerProvince s) {
            OnSelect.OnSelect(s);
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(spinnerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spinnerAdapter.search(s.toString());
            }
        });
        return dialogView;
    }

    public View Loading(View decorView) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.loading_dialog, parent, false);
        ConstraintLayout View = dialogView.findViewById(R.id.Loading_View);
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                //do nothing
            }
        });
        blurView = dialogView.findViewById(R.id.Loading_blurView);
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }else {
            View.setBackgroundColor(Color.WHITE);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return dialogView;
    }
    public View Factor(View decorView, long cout, long price, long discount,long finalPrice, final MSdialogInterfaceDefault OnConfirmed) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.factoor, parent, false);
        TextView coutTx,priceTx,discountTx,findalTx;
        ImageView closeBtn=dialogView.findViewById(R.id.Factor_CloseBTN);
        Button confirmBtn=dialogView.findViewById(R.id.Facto_Confirm);
        coutTx=dialogView.findViewById(R.id.Factor_cout);
        priceTx=dialogView.findViewById(R.id.Factor_Price);
        discountTx=dialogView.findViewById(R.id.Factor_Discount);
        findalTx=dialogView.findViewById(R.id.Factor_FinalPrice);
        blurView = dialogView.findViewById(R.id.Factor_blur);
        coutTx.setText(FaNumToEnNum.convertTofa(String.valueOf(cout))+" عدد");
        priceTx.setText(priceConvertor.Convert(price)+" تومان");
        discountTx.setText(priceConvertor.Convert(discount)+" تومان");
        findalTx.setText(priceConvertor.Convert(finalPrice)+" تومان");
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurView.setupWith(rootView).windowBackground(windowBackground)
                    .blurAlgorithm(new RenderScriptBlur(context)) //Preferable algorithm, needs RenderScript support mode enabled
                    .blurRadius(10);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnConfirmed.OnConfirmed();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnConfirmed.OnCancel();
            }
        });
        return dialogView;
    }

    public interface MSdialogInterfaceComment {
        void OnCommentCancelled();
    }

    public interface MSdialogInterfaceDefault {
        void OnConfirmed();

        void OnCancel();
    }

    public interface MSdialogInterfaceConfirm {
        void OnConfirmed();
    }

    public interface MSdialogInterfaceMessage {
        void onDismmis();
    }

    public interface MSdialogInterfaceSpinner {
        void OnSelect(SpinnerProvince s);

        void OnCancel();
    }
}
