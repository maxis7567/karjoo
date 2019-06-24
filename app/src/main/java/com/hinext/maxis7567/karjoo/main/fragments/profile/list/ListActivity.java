package com.hinext.maxis7567.karjoo.main.fragments.profile.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.models.ListData;
import com.hinext.maxis7567.karjoo.services.Api;
import com.maxis7567.msdialog.MSdialog;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private View LOADING;
    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        viewGroup=findViewById(R.id.ListView);
        title=findViewById(R.id.ListTitle);
        recyclerView=findViewById(R.id.ListRec);
        LOADING=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        viewGroup.addView(LOADING);
        getList();
    }

    private void getList() {
        Api.getList(this, new Response.Listener<List<ListData>>() {
            @Override
            public void onResponse(List<ListData> response) {
                Adapter adapter=new Adapter(ListActivity.this,response);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this,RecyclerView.VERTICAL,false));
                viewGroup.removeView(LOADING);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
