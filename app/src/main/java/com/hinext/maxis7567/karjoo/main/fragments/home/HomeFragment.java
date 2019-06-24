package com.hinext.maxis7567.karjoo.main.fragments.home;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.mstools.OnLoadMoreRecyclerView;
import com.maxis7567.msdialog.MSdialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View view;
    private Context context;
    private FragmentActivity activity;
    private int page = 1;
    private View LOADING;
    private ViewGroup viewGroup;
    private List<HomeData> dataList = new ArrayList<>();
    private LottieAnimationView loadmore;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private View DIALOG;
    public static boolean needRefresh=false;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewGroup = view.findViewById(R.id.FragHomeView);
        recyclerView = view.findViewById(R.id.FragHomeREc);
        loadmore = view.findViewById(R.id.FragHomeLoadMore);

        if (dataList.size() == 0||needRefresh) {
            needRefresh=false;
            LOADING = new MSdialog(context, viewGroup).Loading(activity.getWindow().getDecorView());
            viewGroup.addView(LOADING);
            getData();
        }else setUpView();
        return view;
    }

    private void getData() {
        loadmore.setVisibility(View.VISIBLE);
        Api.getHomeData(context, new Response.Listener<List<HomeData>>() {
            @Override
            public void onResponse(List<HomeData> response) {
                if (page == 1) {
                    viewGroup.removeView(LOADING);
                    dataList.addAll(response);
                    setUpView();
                }else {
                    if (!response.isEmpty()) {
                        dataList.addAll(response);
                        adapter.addData(response);
                    }
                    loadmore.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (page == 1) {
                    viewGroup.removeView(DIALOG);
                    DIALOG = new MSdialog(context, viewGroup).ConfirmDialog(activity.getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                            new MSdialog.MSdialogInterfaceConfirm() {
                                @Override
                                public void OnConfirmed() {
                                    viewGroup.removeView(DIALOG);
                                    getData();
                                }
                            });
                    viewGroup.addView(DIALOG);
                } else {
                    viewGroup.removeView(DIALOG);
                    DIALOG = new MSdialog(context, viewGroup).DefaultDialog(activity.getWindow().getDecorView(), "خطا", "مشکل در ارتبات با سرور", "تلاش دوباره",
                            new MSdialog.MSdialogInterfaceDefault() {
                                @Override
                                public void OnConfirmed() {
                                    viewGroup.removeView(DIALOG);
                                    getData();
                                }

                                @Override
                                public void OnCancel() {
                                    viewGroup.removeView(DIALOG);
                                }
                            });
                    viewGroup.addView(DIALOG);
                }
            }
        }, page);
    }

    private void setUpView() {
        adapter = new Adapter(context, dataList, new OnLoadMoreRecyclerView() {
            @Override
            public void onLoadMore() {
                page++;
                getData();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

}
