package com.android.teklipyaz.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.Organization;
import com.android.teklipyaz.models.entities.OrganizationResult;
import com.android.teklipyaz.utils.ErrorView;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.Network;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.utils.PaginationScrollListener;
import com.android.teklipyaz.views.adapters.PlaceAdapter;
import com.android.teklipyaz.views.fragments.SettingsFragment;
import com.android.teklipyaz.views.interfaces.PlaceViewInterface;
import com.android.teklipyaz.views.presenters.PlacePresenter;

public class PlaceActivity extends AppCompatActivity implements PlaceViewInterface, PaginationAdapterCallback {
    private RecyclerView recyclerView;
    private String TAG = PlaceActivity.class.getSimpleName()+"11";
    private PlaceAdapter adapter;//RecyclerView.Adapter
    private String currentLanguage;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 5;
    private int currentPage = PAGE_START;
    private int cityId = 0;
    private int categoryId = 0;
    private String searchText = "";

    PlacePresenter placePresenter;
    private AppCompatImageView bm_home;
    private AppCompatImageView bm_category;
    private AppCompatImageView bm_search;
    private AppCompatImageView bm_favs;
    ProgressBar progressBar;

    LinearLayout errorLayout;
    private Button btnRetry;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtError;
    private Network network;
    private ErrorView errorView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        setupMVP();
        setupViews();
        getItemList();
    }



    private void setupMVP() {
        placePresenter = new PlacePresenter(this);
    }

    private void setupViews(){

        //get language
        currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(PlaceActivity.this));

        //get city
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PlaceActivity.this);
        //preferences.getString(SettingsFragment.PREF_CITY, "");
        cityId = Integer.parseInt(preferences.getString(SettingsFragment.PREF_CITY, "0"));

        //When category selected start
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            if(arguments.get("category_id") != null) {
                String category_id = arguments.get("category_id").toString();
                categoryId = Integer.parseInt(category_id);
            }
        }

        //internet connection
        network = new Network(this);

        bm_home = (AppCompatImageView) findViewById(R.id.bottom_menu_home);
        bm_category = (AppCompatImageView) findViewById(R.id.bottom_menu_filter);
        bm_search = (AppCompatImageView) findViewById(R.id.bottom_menu_search);
        bm_favs = (AppCompatImageView) findViewById(R.id.bottom_menu_favs);


        //error view
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        errorView = new ErrorView(errorLayout, progressBar);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        btnRetry.setOnClickListener(view -> getItemList());

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        adapter = new PlaceAdapter(PlaceActivity.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(() -> {
                    getItemList();
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //detail page
        adapter.setOnItemClickListener((View view, int position) -> {
            Organization item = adapter.getItem(position);
            if(item != null) {
                CenterRepository.getCenterRepository().setOrganization(item);
                Intent intent = new Intent(PlaceActivity.this, PlaceDetailsActivity.class);
                startActivity(intent);
            }
        });

        //home page
        bm_home.setOnClickListener((View v) -> onBackPressed());

        //categories page
        bm_category.setOnClickListener((View v) -> {
            Intent intent = new Intent(PlaceActivity.this, PlaceCategoriesActivity.class);
            intent.putExtra("city_id", cityId);
            startActivity(intent);
            onBackPressed();
        });

        //search page
        bm_search.setOnClickListener((View v) -> {
            Intent intent = new Intent(PlaceActivity.this, PlaceSearchActivity.class);
            startActivity(intent);
            //onBackPressed();
        });

        //favourite organizations page
        bm_favs.setOnClickListener((View v) -> {
            Intent intent = new Intent(PlaceActivity.this, PlaceFavsActivity.class);
            startActivity(intent);
            //onBackPressed();
        });
    }

    private void getItemList() {
        errorView.hide();
        placePresenter.getItems(currentLanguage, currentPage, cityId, categoryId, searchText);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(PlaceActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayItems(OrganizationResult results) {
        if( results != null ) {
            if(currentPage == 1){
                //SET TOTAL PAGES COUNT
                totalPages = results.getMetaModel().getPageCount();
                progressBar.setVisibility(View.GONE);
            }
            else{
                adapter.removeLoadingFooter();
                isLoading = false;
            }

            adapter.addAll(results.getOrganizations());

            if (currentPage < totalPages) adapter.addLoadingFooter();
            else isLastPage = true;

        }else{
            Log.d(TAG,"Items response null");
        }
    }

    @Override
    public void displayError(Throwable t) {
        if(currentPage == 1) {
            errorView.show();
            txtError.setText(network.fetchErrorMessage(t));
        }
        else {
            adapter.showRetry(true, network.fetchErrorMessage(t));
        }
    }

    @Override
    public void retryPageLoad() {
        getItemList();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }
}