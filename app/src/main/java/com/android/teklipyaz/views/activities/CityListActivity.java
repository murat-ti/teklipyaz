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
import com.android.teklipyaz.models.entities.CityModel;
import com.android.teklipyaz.models.entities.CityResultModel;
import com.android.teklipyaz.utils.ApiUtil;
import com.android.teklipyaz.utils.OnItemClickListener;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.utils.PaginationScrollListener;
import com.android.teklipyaz.views.adapters.CityPaginationAdapter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityListActivity extends AppCompatActivity implements PaginationAdapterCallback{

    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;
    LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    //private AVLoadingIndicatorView progressBar;
    ProgressBar progressBar;
    private CityPaginationAdapter adapter;
    private static final String TAG ="Organization List";

    private static final String PAGE_LANGUAGE = "en";
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private int categoryId = 0;

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

        setContentView(R.layout.activity_city);

        //When category selected start
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            if(arguments.get("category_id") != null) {
                String category_id = arguments.get("category_id").toString();
                categoryId = Integer.parseInt(category_id);
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        //progressBar = ((MainActivity) getActivity()).getProgressBar();
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        adapter = new CityPaginationAdapter(this);

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
                CityModel item = adapter.getItem(position);
                if(item != null) {
                    Intent intent = new Intent(CityListActivity.this, OrganizationListActivity.class);
                    intent.putExtra("category_id", categoryId);
                    intent.putExtra("city_id", item.getId());
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


        //City SELECT ALL or close button
        //IconTextView close_button = (IconTextView)findViewById(R.id.icon_close_category);
        TextView close_button = (TextView)findViewById(R.id.icon_close_category);
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListActivity.this, OrganizationListActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<CityModel> fetchResults(Response<CityResultModel> response) {
        CityResultModel responseBody = response.body();
        return responseBody.getCityModels();
    }

    private void setTotalPage(Response<CityResultModel> response) {
        CityResultModel responseBody = response.body();
        TOTAL_PAGES = responseBody.getMetaModel().getPageCount();
    }

    private void loadFirstPage() {
        // To ensure list is visible when retry button in error view is clicked
        hideErrorView();

        ApiUtil.getServiceClass().getAllCity(PAGE_LANGUAGE, currentPage).enqueue(new Callback<CityResultModel>() {
            @Override
            public void onResponse(Call<CityResultModel> call, Response<CityResultModel> response) {
                // Got data. Send it to adapter
                List<CityModel> results = fetchResults(response);

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
            public void onFailure(Call<CityResultModel> call, Throwable t) {
                t.printStackTrace();
                showErrorView(t);
            }
        });
    }

    private void loadNextPage() {
        ApiUtil.getServiceClass().getAllCity(PAGE_LANGUAGE, currentPage).enqueue(new Callback<CityResultModel>() {
            @Override
            public void onResponse(Call<CityResultModel> call, Response<CityResultModel> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<CityModel> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage < TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<CityResultModel> call, Throwable t) {
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
