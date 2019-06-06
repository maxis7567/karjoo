package com.hinext.maxis7567.karjoo.main.fragments.offer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.karjoo.models.Jobs;
import com.hinext.maxis7567.karjoo.services.Api;
import com.hinext.maxis7567.mstools.OnLoadMoreRecyclerView;
import com.maxis7567.msdialog.MSdialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment {
    private View view;
    private ViewGroup viewGroup;
    private Context context;
    private Activity activity;
    private View LOADING;
    private View DIALOG;
    private Adapter adapter;
    private SearchAdapter searchAdapter;
    private RecyclerView searchRec,requestRec;
    private ImageView searchIc,closeIc;
    private EditText search;
    private LottieAnimationView loadMore;
    private List<HomeData> data=new ArrayList<>();
    private int page=1;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_job, container, false);
        viewGroup=view.findViewById(R.id.FragJobView);
        searchRec=view.findViewById(R.id.FragJobSearchRec);
        requestRec=view.findViewById(R.id.FragJobRec);
        searchIc=view.findViewById(R.id.FragJobSearchIc);
        closeIc=view.findViewById(R.id.FragJobCloseIc);
        search=view.findViewById(R.id.FragJobSearch);
        loadMore=view.findViewById(R.id.FragJobLoadMore);
        LOADING=new MSdialog(context,viewGroup).Loading(activity.getWindow().getDecorView());
        if (data.size()<=0){
            page=1;
            viewGroup.addView(LOADING);
            getData();
        }else setUpViews();
        setUpSearchHandler();
        // Inflate the layout for this fragment
        return view;
    }

    private void setUpViews() {
        adapter = new Adapter(context, data, new OnLoadMoreRecyclerView() {
            @Override
            public void onLoadMore() {
                page++;
                getData();
            }
        });
        requestRec.setAdapter(adapter);
        requestRec.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    private void getData() {
        Api.getOfferData(context, new Response.Listener<List<HomeData>>() {
            @Override
            public void onResponse(List<HomeData> response) {
                if (page == 1) {
                    viewGroup.removeView(LOADING);
                    data.addAll(response);
                    setUpViews();
                }else {
                    if (!response.isEmpty()) {
                        adapter.addData(response);
                    }
                    loadMore.setVisibility(View.GONE);
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
        },page);
    }

    private void setUpSearchHandler() {
        searchIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIc.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                closeIc.setVisibility(View.VISIBLE);
            }
        });
        closeIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchIc.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                closeIc.setVisibility(View.GONE);
                searchAdapter.addAll(new ArrayList<Jobs>());
                searchRec.setVisibility(View.GONE);
                search.setText("");
                adapter.addAll(data);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    searchAdapter.addAll(new ArrayList<Jobs>());
                    searchRec.setVisibility(View.GONE);
                }else {
                    getSearchData(s.toString());
                }

            }
        });
        searchAdapter=new SearchAdapter(context, new ArrayList<Jobs>(), new SearchAdapter.ISearch() {
            @Override
            public void onClick(int id) {
                     getJobListSearched(id);
            }
        });
        searchRec.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        searchRec.setAdapter(searchAdapter);
    }

    private void getJobListSearched(int id) {
        Api.getOfferSearched(context, new Response.Listener<List<HomeData>>() {
            @Override
            public void onResponse(List<HomeData> response) {
                    adapter.addSearchData(response);
                    searchAdapter.addAll(new ArrayList<Jobs>());
                    searchRec.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },id);
    }

    private void getSearchData(String q) {
        Api.jobSearch(context, new Response.Listener<List<Jobs>>() {
            @Override
            public void onResponse(List<Jobs> response) {
                searchRec.setVisibility(View.VISIBLE);
                searchAdapter.addAll(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },q);
    }

}
