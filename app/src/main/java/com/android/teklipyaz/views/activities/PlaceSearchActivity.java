package com.android.teklipyaz.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class PlaceSearchActivity extends AppCompatActivity implements PlaceViewInterface, PaginationAdapterCallback {

    private String TAG = PlaceSearchActivity.class.getSimpleName()+"11";

    private MaterialSearchView mSearchView;
    private ImageView mBackButton;
    private ImageView mVoiceSearchButton;
    private ImageView mEmptyButton;
    private EditText mSearchTextView;
    private static final int RECOGNIZER_REQ_CODE = 1234;

    private RecyclerView recyclerView;
    private PlaceAdapter adapter;

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
    ProgressBar progressBar;

    LinearLayout errorLayout;
    private Button btnRetry;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtError;
    private Network network;
    private ErrorView errorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_search);

        setupMVP();
        setupViews();
    }

    private void setupMVP() {
        placePresenter = new PlacePresenter(this);
    }

    private void setupViews(){

        //get language
        currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(PlaceSearchActivity.this));

        //get city
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PlaceSearchActivity.this);
        //preferences.getString(SettingsFragment.PREF_CITY, "");
        cityId = Integer.parseInt(preferences.getString(SettingsFragment.PREF_CITY, "0"));

        //internet connection
        network = new Network(this);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);
        mSearchView.showSearch(false);

        //mSearchView.clearFocus();
        mBackButton = (ImageView) mSearchView.findViewById(R.id.action_up_btn);
        mVoiceSearchButton = (ImageView) mSearchView.findViewById(R.id.action_voice_btn);
        mEmptyButton = (ImageView) mSearchView.findViewById(R.id.action_empty_btn);
        mSearchTextView = (EditText) mSearchView.findViewById(R.id.searchTextView);
        mVoiceSearchButton.setVisibility(View.VISIBLE);
        mVoiceSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, RECOGNIZER_REQ_CODE);
        });

        mEmptyButton.setOnClickListener(v -> {
            mVoiceSearchButton.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
            mSearchTextView.setText("");
            if(!adapter.isEmpty()){
                adapter.clear();
            }
        });

        mBackButton.setOnClickListener(v -> onBackPressed());

        adapter = new PlaceAdapter(this);
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
                    if(!adapter.isEmpty()){
                        adapter.clear();
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    getItemList();

                    //detail page
                    adapter.setOnItemClickListener((View view, int position) -> {
                        Organization item = adapter.getItem(position);
                        if(item != null) {
                            CenterRepository.getCenterRepository().setOrganization(item);
                            Intent intent = new Intent(PlaceSearchActivity.this, PlaceDetailsActivity.class);
                            startActivity(intent);
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

        btnRetry.setOnClickListener(view -> getItemList());
    }

    private void getItemList() {
        errorView.hide();
        placePresenter.getItems(currentLanguage, currentPage, cityId, categoryId, searchText);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(PlaceSearchActivity.this,str,Toast.LENGTH_LONG).show();
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
        super.onBackPressed();
        this.finish();
    }
}