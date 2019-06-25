package com.android.teklipyaz.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationSearchActivity extends AppCompatActivity implements PaginationAdapterCallback {
    private static final String LOG_TAG = OrganizationSearchActivity.class.getSimpleName();
    private static final int RECOGNIZER_REQ_CODE = 1234;

    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private OrganizationPaginationAdapter adapter;

    private MaterialSearchView mSearchView;
    private ImageView mBackButton;
    private ImageView mVoiceSearchButton;
    private ImageView mEmptyButton;
    private EditText mSearchTextView;

    private static final String PAGE_LANGUAGE = "en";
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalPages = 5;
    private int currentPage = PAGE_START;
    private int cityId = 0;
    private int categoryId = 0;
    private String searchText = "";

    LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    private Network network;
    private ErrorView errorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //internet connection
        network = new Network(this);

        setContentView(R.layout.activity_organization_search);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);
        mSearchView.showSearch(false);

        //mSearchView.clearFocus();
        mBackButton = (ImageView) mSearchView.findViewById(R.id.action_up_btn);
        mVoiceSearchButton = (ImageView) mSearchView.findViewById(R.id.action_voice_btn);
        mEmptyButton = (ImageView) mSearchView.findViewById(R.id.action_empty_btn);
        mSearchTextView = (EditText) mSearchView.findViewById(R.id.searchTextView);
        mVoiceSearchButton.setVisibility(View.VISIBLE);
        mVoiceSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, RECOGNIZER_REQ_CODE);
            }
        });

        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVoiceSearchButton.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
                mSearchTextView.setText("");
                if(!adapter.isEmpty()){
                    adapter.clear();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new OrganizationPaginationAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search_results);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        //error view
        errorView = new ErrorView(errorLayout, progressBar);

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

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.trim();

                if (query.length() > 0 && query.length() < 3) {
                    Snackbar.make(findViewById(R.id.container), getResources().getString(R.string.minimum_input_number_string), Snackbar.LENGTH_SHORT)
                            .show();
                }
                else {
                    searchText = query;
                    //progressBar.setVisibility(View.VISIBLE);
                    loadData();

                    adapter.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                        OrganizationModel item = adapter.getItem(position);
                        if(item != null) {
                            Intent intent = new Intent(OrganizationSearchActivity.this, OrganizationDetailActivity.class);
                            //intent.putExtra("organization_id", item.getId());
                            Gson gson = new Gson();
                            String myJsonData = gson.toJson(item);
                            intent.putExtra("myJsonData", myJsonData);
                            startActivity(intent);
                        }
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void loadData() {
        // To ensure list is visible when retry button in error view is clicked
        if(currentPage == 1)
            errorView.hide();

        ApiUtil.getServiceClass().getAllOrganization(PAGE_LANGUAGE, currentPage, cityId, categoryId, searchText).enqueue(new Callback<OrganizationResultModel>() {
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
        super.onBackPressed();
        this.finish();
    }
}