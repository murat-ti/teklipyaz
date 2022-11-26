package com.android.teklipyaz.views.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.dao.AppDatabase;
import com.android.teklipyaz.models.entities.Organization;
import com.android.teklipyaz.models.entities.OrganizationFav;
import com.android.teklipyaz.models.repositories.local.DBConstant;
import com.android.teklipyaz.models.repositories.local.OrganizationFavLocalRepoImpl;
import com.android.teklipyaz.models.repositories.local.OrganizationFavLocalRepo;
import com.android.teklipyaz.utils.ErrorView;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.Network;
import com.android.teklipyaz.utils.PaginationAdapterCallback;
import com.android.teklipyaz.utils.TypeConverter;
import com.android.teklipyaz.views.adapters.PlaceAdapter;
import com.android.teklipyaz.views.interfaces.PlaceFavsViewInterface;
import com.android.teklipyaz.views.presenters.PlaceFavsPresenter;

import java.util.ArrayList;
import java.util.List;

public class PlaceFavsActivity extends AppCompatActivity implements PlaceFavsViewInterface, PaginationAdapterCallback {

    private String TAG = PlaceFavsActivity.class.getSimpleName()+"11";

    private RecyclerView recyclerView;
    private PlaceAdapter adapter;

    private String currentLanguage;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    PlaceFavsPresenter placePresenter;
    ProgressBar progressBar;
    FrameLayout close_button;

    LinearLayout errorLayout;
    private Button btnRetry;
    //private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtError;
    private Network network;
    private ErrorView errorView;

    public AppDatabase localDB;
    OrganizationFavLocalRepo localItemRepo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_fav);

        setupMVP();
        setupViews();
        getItemList();
    }

    private void setupMVP() {
        localDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DBConstant.DB_NAME).build();
        localItemRepo = new OrganizationFavLocalRepoImpl(localDB.organizationFavDao());
        placePresenter = new PlaceFavsPresenter(this, localItemRepo);
    }

    private void setupViews(){

        //get language
        currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(PlaceFavsActivity.this));

        //internet connection
        network = new Network(this);

        adapter = new PlaceAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);

        //error view
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        errorView = new ErrorView(errorLayout, progressBar);

        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //detail page
        adapter.setOnItemClickListener((View view, int position) -> {
            Organization item = adapter.getItem(position);
            if(item != null) {
                CenterRepository.getCenterRepository().setOrganization(item);
                Intent intent = new Intent(PlaceFavsActivity.this, PlaceDetailsActivity.class);
                intent.putExtra("prev_activity", "placefavs");
                finish();
                startActivity(intent);
            }
        });

        btnRetry.setOnClickListener(view -> getItemList());

        close_button = (FrameLayout)findViewById(R.id.close_button);
        close_button.setOnClickListener((View view) -> {
            onBackPressed();
        });
    }

    private void getItemList() {
        errorView.hide();
        placePresenter.getItems();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(PlaceFavsActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayItems(List<OrganizationFav> results) {
        if( results != null ) {

            int lsize = results.size();
            if( lsize > 0 ) {
                progressBar.setVisibility(View.GONE);

                List<Organization> items = new ArrayList<>();

                for (int i = 0; i < lsize; i++) {
                    Organization item = TypeConverter.favToOrg(results.get(i));
                    items.add(item);
                }

                adapter.addAll(items);
            }
            else{
                errorView.show();
                txtError.setText(PlaceFavsActivity.this.getResources().getString(R.string.title_no_data));
                btnRetry.setVisibility(View.INVISIBLE);
            }
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