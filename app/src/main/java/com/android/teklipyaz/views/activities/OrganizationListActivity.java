package com.android.teklipyaz.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.teklipyaz.R;
import com.android.teklipyaz.models.entities.OrganizationModel;
import com.android.teklipyaz.models.entities.OrganizationResultModel;
import com.android.teklipyaz.utils.ApiUtil;
import com.android.teklipyaz.utils.ErrorView;
import com.android.teklipyaz.utils.Network;
import com.android.teklipyaz.utils.OnItemClickListener;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.utils.PaginationScrollListener;
import com.android.teklipyaz.views.adapters.OrganizationPaginationAdapter;
import com.android.teklipyaz.views.fragments.MoreFragment;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationListActivity extends AppCompatActivity implements PaginationAdapterCallback{

    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;

    //private AVLoadingIndicatorView progressBar;
    ProgressBar progressBar;
    private OrganizationPaginationAdapter adapter;
    private static final String TAG ="Organization List";

    private static final String PAGE_LANGUAGE = "en";
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 5;
    private int currentPage = PAGE_START;
    private int cityId = 0;
    private int categoryId = 0;
    private String query = "";

    LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    private Network network;
    private ErrorView errorView;

    private ImageView bm_category;

    /**
     * The double back to exit pressed once.
     */
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //internet connection
        network = new Network(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languagePref = sharedPreferences.getString(MoreFragment.PREF_LANGUAGE, "");
        MoreFragment.setLanguage(this.getBaseContext(),languagePref);
        setContentView(R.layout.activity_organization);

        //progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);

        /*
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.anim_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getmDrawerLayout().openDrawer(GravityCompat.START);
            }
        });*/

        //When category selected start
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            if(arguments.get("city_id") != null) {
                String city_id = arguments.get("city_id").toString();
                cityId = Integer.parseInt(city_id);
            }
            if(arguments.get("category_id") != null) {
                String category_id = arguments.get("category_id").toString();
                categoryId = Integer.parseInt(category_id);
            }
        }
        //When category selected finish

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        //progressBar = ((MainActivity) getActivity()).getProgressBar();

        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        bm_category = (ImageView) findViewById(R.id.bottom_menu_filter);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        //error view
        errorView = new ErrorView(errorLayout, progressBar);

        adapter = new OrganizationPaginationAdapter(this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
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

        loadData();

        //detail page
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //Log.d("TAG Postion", "My position is "+position);
                OrganizationModel item = adapter.getItem(position);
                if(item != null) {
                    Intent intent = new Intent(OrganizationListActivity.this, OrganizationDetailActivity.class);
                    //intent.putExtra("organization_id", item.getId());
                    Gson gson = new Gson();
                    String myJsonData = gson.toJson(item);
                    intent.putExtra("myJsonData", myJsonData);
                    startActivity(intent);
                }

            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        bm_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrganizationListActivity.this, OrganizationCategoriesActivity.class);
                intent.putExtra("city_id", cityId);
                startActivity(intent);
            }
        });

        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.bottom_menu_category:
                        //intent = new Intent(OrganizationListActivity.this, OrganizationCategoryListActivity.class);
                        intent = new Intent(OrganizationListActivity.this, OrganizationCategoriesActivity.class);
                        intent.putExtra("city_id", cityId);
                        startActivity(intent);
                        break;
                    case R.id.bottom_menu_city:
                        intent = new Intent(OrganizationListActivity.this, CityListActivity.class);
                        intent.putExtra("category_id", categoryId);
                        startActivity(intent);
                        break;
                    case R.id.bottom_menu_search:
                        intent = new Intent(OrganizationListActivity.this, OrganizationSearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        Toast.makeText(MainActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });*/
    }

    private void loadData() {
        // To ensure list is visible when retry button in error view is clicked
        if(currentPage == 1)
            errorView.hide();

        ApiUtil.getServiceClass().getAllOrganization(PAGE_LANGUAGE, currentPage, cityId, categoryId, query).enqueue(new Callback<OrganizationResultModel>() {
            @Override
            public void onResponse(Call<OrganizationResultModel> call, Response<OrganizationResultModel> response) {

                // Got data. Send it to adapter
                OrganizationResultModel responseBody = response.body();
                List<OrganizationModel> results = responseBody.getOrganizationModels();

                if(currentPage == 1){
                    //SET TOTAL PAGES COUNT
                    totalPages = responseBody.getMetaModel().getPageCount();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    adapter.removeLoadingFooter();
                    isLoading = false;
                }

                adapter.addAll(results);

                if (currentPage < totalPages) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<OrganizationResultModel> call, Throwable t) {
                t.printStackTrace();
                if(currentPage == 1) {
                    errorView.show();
                    txtError.setText(network.fetchErrorMessage(t));
                }
                else {
                    adapter.showRetry(true, network.fetchErrorMessage(t));
                }
            }
        });
    }

    @Override
    public void retryPageLoad() {
        loadData();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.double_click_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
