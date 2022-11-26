package com.android.teklipyaz.views.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.dao.AppDatabase;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.local.DBConstant;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepoImpl;
import com.android.teklipyaz.utils.ErrorView;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.views.adapters.PlaceCategoriesAdapter;
import com.android.teklipyaz.views.interfaces.PlaceCategoriesViewInterface;
import com.android.teklipyaz.views.presenters.PlaceCategoriesPresenter;

import java.util.List;

public class PlaceCategoriesActivity extends AppCompatActivity implements PlaceCategoriesViewInterface, PaginationAdapterCallback {
    private RecyclerView recyclerView;
    private String TAG = PlaceCategoriesActivity.class.getSimpleName()+"11";
    private PlaceCategoriesAdapter adapter;//RecyclerView.Adapter
    private String currentLanguage;

    PlaceCategoriesPresenter placeCategoriesPresenter;
    ProgressBar progressBar;
    FrameLayout close_button;

    LinearLayout errorLayout;
    private Button btnRetry;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtError;
    private ErrorView errorView;
    private int cityId = 0;
    public AppDatabase localDB;
    OrganizationCategoriesLocalRepo localItemsRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(PlaceCategoriesActivity.this);
        setContentView(R.layout.activity_organization_category_temp);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.category));
        }

        setupMVP();
        setupViews();
        getItemList();
    }



    private void setupMVP() {
        localDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBConstant.DB_NAME).build();
        localItemsRepo = new OrganizationCategoriesLocalRepoImpl(localDB.organizationCategoryDao());
        placeCategoriesPresenter = new PlaceCategoriesPresenter(this, localItemsRepo);
    }

    private void setupViews(){

        //get language
        currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(PlaceCategoriesActivity.this));

        //When category selected start
        /*Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            if(arguments.get("city_id") != null) {
                String city_id = arguments.get("city_id").toString();
                cityId = Integer.parseInt(city_id);
            }
        }*/

        //error view
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        errorView = new ErrorView(errorLayout, progressBar);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        btnRetry.setOnClickListener(view -> getItemList());

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        adapter = new PlaceCategoriesAdapter(PlaceCategoriesActivity.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //return to list page
        adapter.setOnItemClickListener((View view, int position) -> {
            OrganizationCategory item = adapter.getItem(position);
            if(item != null) {
                closePage(item.getId());
            }
        });

        close_button = (FrameLayout)findViewById(R.id.close_button);
        close_button.setOnClickListener((View view) -> {
            closePage("0");
        });
    }

    private void getItemList() {
        errorView.hide();
        placeCategoriesPresenter.getItems(currentLanguage);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(PlaceCategoriesActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayItems(List<OrganizationCategory> results) {
        if( results != null ) {
            progressBar.setVisibility(View.GONE);
            //Log.d("MYTEST", "Count: "+results.size());
            adapter.clear();
            adapter.addAll(results);
        }else{
            Log.d(TAG,"Items response null");
        }
    }

    @Override
    public void displayError(Throwable t) {
        errorView.show();
        txtError.setText("DB error");//network.fetchErrorMessage(t)
    }

    @Override
    public void retryPageLoad() {
        getItemList();
    }

    public void closePage(String id) {
        Intent intent = new Intent(PlaceCategoriesActivity.this, PlaceActivity.class);
        //intent.putExtra("city_id", cityId);
        intent.putExtra("category_id", id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        closePage("0");
    }
}