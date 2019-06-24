package com.hinext.maxis7567.karjoo.main.fragments.profile.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.hinext.maxis7567.karjoo.R;
import com.hinext.maxis7567.karjoo.login.RegisterActivity;
import com.hinext.maxis7567.karjoo.main.fragments.home.HomeFragment;
import com.hinext.maxis7567.karjoo.main.fragments.offer.JobFragment;
import com.hinext.maxis7567.karjoo.main.fragments.offer.SearchAdapter;
import com.hinext.maxis7567.karjoo.main.fragments.profile.create.file.AddFileActivity;
import com.hinext.maxis7567.karjoo.main.fragments.request.RequestFragment;
import com.hinext.maxis7567.karjoo.models.Create;
import com.hinext.maxis7567.karjoo.models.Jobs;
import com.hinext.maxis7567.karjoo.models.Skills;
import com.hinext.maxis7567.karjoo.services.Api;
import com.maxis7567.msdialog.MSdialog;
import com.onurkaganaldemir.ktoastlib.KToast;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private TextView title,desc,number,addTagBTN;
    private EditText search;
    private RecyclerView searchRec,tagRec;
    private ImageView saveBTN;

    private ViewGroup viewGroup;
    private View LOADING;

    private List<Skills> skillsList=new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Adapter tagAdapter;
    private View DIALOG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        viewGroup=findViewById(R.id.CreateView);
        title=findViewById(R.id.CreateTitle);
        desc=findViewById(R.id.CreateDesc);
        number=findViewById(R.id.CretaeNumber);
        addTagBTN=findViewById(R.id.CreateAddTag);
        search=findViewById(R.id.CreateSearch);
        saveBTN=findViewById(R.id.CreateSaveBTN);
        searchRec=findViewById(R.id.CreateTagResultRec);
        tagRec=findViewById(R.id.CreateTagRec);
        LOADING=new MSdialog(this,viewGroup).Loading(getWindow().getDecorView());
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    createList();
                }
            }
        });
        addTagBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTag();

            }
        });
        if (getIntent().getStringExtra("type").equals("1")){
            tagAdapter=new Adapter(this,skillsList,true,viewGroup);
        }else {
            tagAdapter=new Adapter(this,skillsList,false,viewGroup);
        }
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row

                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //you are able to break row due to your conditions. Row breaker should return true for that views
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(false)
                .build();
        tagRec.setLayoutManager(chipsLayoutManager);
        tagRec.setAdapter(tagAdapter);
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
                    addTagBTN.setVisibility(View.GONE);
                }else {
                    getSearchData(s.toString());
                }

            }
        });
        searchAdapter=new SearchAdapter(this, new ArrayList<Jobs>(), new SearchAdapter.ISearch() {
            @Override
            public void onClick(Jobs jobs) {
                Skills skills=new Skills();
                skills.setName(jobs.getName());
                skills.setId(jobs.getId());
                tagAdapter.addTag(skills);
                searchRec.setVisibility(View.GONE);
                search.setText("");
            }
        });
        searchRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        searchRec.setAdapter(searchAdapter);

    }

    private boolean isValid() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if (title.getText().length()>0){
            if (desc.getText().length()>0){
                if (CheckNumber(number.getText().toString())){
                    if (skillsList.size()>0){
                        return true;
                    }else KToast.warningToast(this,"باید حداقل یک تگ انتخاب کنید",Gravity.BOTTOM,KToast.LENGTH_SHORT);
                }else number.startAnimation(shake);
            }else desc.startAnimation(shake);
        }else {
            title.startAnimation(shake);
        }
        return false;
    }

    private void createList() {
        viewGroup.addView(LOADING);
        Create create=new Create();
        create.setDesc(desc.getText().toString());
        create.setPhoneNumber(number.getText().toString());
        create.setTitle(title.getText().toString());
        create.setSkillsList(skillsList);
        Api.createList(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(CreateActivity.this, AddFileActivity.class);
                intent.putExtra("id",response);
                startActivity(intent);
                JobFragment.needRefresh=true;
                RequestFragment.needRefresh=true;
                HomeFragment.needRefresh=true;
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },create);
    }

    private void addTag() {
        Drawable img = getResources().getDrawable( R.drawable.ic_upload );
        img.setBounds( 0, 0, 60, 60 );
        addTagBTN.setCompoundDrawables(null,null,img,null);
        Api.addTags(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Skills skills=new Skills();
                skills.setName(search.getText().toString());
                skills.setId(Integer.valueOf(response));
                tagAdapter.addTag(skills);
                searchRec.setVisibility(View.GONE);
                search.setText("");
                addTagBTN.setVisibility(View.GONE);
                addTagBTN.setCompoundDrawables(null, null, null, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },search.getText().toString(),getIntent().getStringExtra("type"));
    }

    private void getSearchData(String q) {
        if (getIntent().getStringExtra("type").equals("1")) {
            Api.requestSearch(this, new Response.Listener<List<Jobs>>() {
                @Override
                public void onResponse(List<Jobs> response) {
                    if (response.size()>0) {
                        searchRec.setVisibility(View.VISIBLE);
                        searchAdapter.addAll(response);
                    }else {
                        addTagBTN.setVisibility(View.VISIBLE);
                        searchRec.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, q);
        }else {
            Api.jobSearch(this, new Response.Listener<List<Jobs>>() {
                @Override
                public void onResponse(List<Jobs> response) {
                    if (response.size()>0) {
                        searchRec.setVisibility(View.VISIBLE);
                        searchAdapter.addAll(response);
                    }else {
                        addTagBTN.setVisibility(View.VISIBLE);
                        searchRec.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            },q);
        }
    }
    public static boolean CheckNumber(String number) {
        if (number.length() == 11) {
            String tmp = String.valueOf(number.charAt(0));
            tmp = tmp.concat(String.valueOf(number.charAt(1)));
            return tmp.equals("09");
        } else return false;

    }
}
