package com.android.teklipyaz.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationCategoryResultModel;
import com.android.teklipyaz.utils.ApiUtil;
import com.android.teklipyaz.utils.OnItemClickListener;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.utils.PaginationScrollListener;
import com.android.teklipyaz.views.adapters.OrganizationCategoryPaginationAdapter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationCategoryListActivity extends AppCompatActivity implements PaginationAdapterCallback{

    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;
    LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    //private AVLoadingIndicatorView progressBar;
    ProgressBar progressBar;
    private OrganizationCategoryPaginationAdapter adapter;
    private static final String TAG ="Organization List";

    private static final String PAGE_LANGUAGE = "en";
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private int cityId = 0;
    /**
     * The double back to exit pressed once.
     */
    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_organization_category);

        //When category selected start
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            if(arguments.get("city_id") != null) {
                String city_id = arguments.get("city_id").toString();
                cityId = Integer.parseInt(city_id);
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        //progressBar = ((MainActivity) getActivity()).getProgressBar();
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        adapter = new OrganizationCategoryPaginationAdapter(this);

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
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
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

        loadFirstPage();

        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //Log.d("TAG Postion", "My position is "+position);
                OrganizationCategory item = adapter.getItem(position);
                if(item != null) {
                    Intent intent = new Intent(OrganizationCategoryListActivity.this, OrganizationListActivity.class);
                    intent.putExtra("city_id", cityId);
                    intent.putExtra("category_id", item.getId());
                    startActivity(intent);
                    /*Toast.makeText(getApplicationContext(),
                            "ID: " + item.getId(), Toast.LENGTH_SHORT)
                            .show();*/
                }

            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFirstPage();
            }
        });


        //Category SELECT ALL or close button
        //IconTextView close_button = (IconTextView)findViewById(R.id.icon_close_category);
        TextView close_button = (TextView)findViewById(R.id.icon_close_category);
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganizationCategoryListActivity.this, OrganizationListActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<OrganizationCategory> fetchResults(Response<OrganizationCategoryResultModel> response) {
        OrganizationCategoryResultModel responseBody = response.body();
        return responseBody.getOrganizationCategoryModels();
    }

    private void setTotalPage(Response<OrganizationCategoryResultModel> response) {
        OrganizationCategoryResultModel responseBody = response.body();
        TOTAL_PAGES = responseBody.getMetaModel().getPageCount();
    }

    private void loadFirstPage() {
        // To ensure list is visible when retry button in error view is clicked
        hideErrorView();

        ApiUtil.getServiceClass().getAllCategory(PAGE_LANGUAGE, currentPage).enqueue(new Callback<OrganizationCategoryResultModel>() {
            @Override
            public void onResponse(Call<OrganizationCategoryResultModel> call, Response<OrganizationCategoryResultModel> response) {
                // Got data. Send it to adapter
                List<OrganizationCategory> results = fetchResults(response);

                //SET TOTAL PAGES COUNT
                setTotalPage(response);

                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) {
                    Log.d(TAG, "FIRST: " + currentPage + " : "+TOTAL_PAGES);
                    adapter.addLoadingFooter();
                }
                else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<OrganizationCategoryResultModel> call, Throwable t) {
                t.printStackTrace();
                showErrorView(t);
            }
        });
    }

    private void loadNextPage() {
        ApiUtil.getServiceClass().getAllCategory(PAGE_LANGUAGE, currentPage).enqueue(new Callback<OrganizationCategoryResultModel>() {
            @Override
            public void onResponse(Call<OrganizationCategoryResultModel> call, Response<OrganizationCategoryResultModel> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<OrganizationCategory> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage < TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<OrganizationCategoryResultModel> call, Throwable t) {
                t.printStackTrace();
                adapter.showRetry(true, fetchErrorMessage(t));
            }
        });
    }


    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    /**
     * @param throwable required for {@link #fetchErrorMessage(Throwable)}
     * @return
     */
    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    // Helpers -------------------------------------------------------------------------------------


    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
     *
     * @return
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else {
                    super.onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            Toast.makeText(this,"Back button clicked",Toast.LENGTH_LONG).show();
            fm.popBackStack();
        } else {
            finish();
            super.onBackPressed();
        }
    }
}
