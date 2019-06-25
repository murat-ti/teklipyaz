package com.android.teklipyaz.views.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.teklipyaz.R;
import com.android.teklipyaz.base.view.BaseActivity;
import com.android.teklipyaz.models.dao.OrganizationCategoryDao;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.OrganizationCategoriesRepo;
import com.android.teklipyaz.models.repositories.OrganizationCategoriesRepoImpl;
import com.android.teklipyaz.models.repositories.local.DBConstant;
import com.android.teklipyaz.models.repositories.local.LocalDB;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepoImpl;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepo;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepoImpl;
import com.android.teklipyaz.utils.OnItemClickListener;
import com.android.teklipyaz.views.adapters.OCRecyclerViewAdapter;
import com.android.teklipyaz.views.presenter.OrganizationCategoriesPresenter;
import com.android.teklipyaz.views.presenter.OrganizationCategoriesPresenterImpl;
import com.android.teklipyaz.views.presenter.OrganizationCategoriesView;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class OrganizationCategoriesActivity extends BaseActivity<OrganizationCategoriesPresenter> implements OrganizationCategoriesView {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerViewItem;
    private OCRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    FrameLayout close_button;

    public LocalDB localDB;
    OrganizationCategoriesLocalRepo localItemsRepo;

    private int cityId = 0;

    @Override
    protected OrganizationCategoriesPresenter createPresenter() {
        OrganizationCategoriesRemoteRepo remoteRepo = new OrganizationCategoriesRemoteRepoImpl();
        localDB = Room.databaseBuilder(getApplicationContext(), LocalDB.class, DBConstant.DB_NAME).build();
        localItemsRepo = new OrganizationCategoriesLocalRepoImpl(localDB.categoriesDao());
        OrganizationCategoriesRepo itemsRepo = new OrganizationCategoriesRepoImpl(remoteRepo, localItemsRepo);
        return new OrganizationCategoriesPresenterImpl(itemsRepo, AndroidSchedulers.mainThread());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        recyclerViewItem = findViewById(R.id.scrollableview);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);

        getPresenter().getOrganizationCategories();
    }

    @Override
    public void showOrganizationCategories(List<OrganizationCategory> items) {
        Log.d(TAG, "showItems() returned: " + items.size());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewItem.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewItem.setLayoutManager(layoutManager);

        //add divider for spacing
        /*recyclerViewItem.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));*/
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_item_bottom_border));
        recyclerViewItem.addItemDecoration(itemDecorator);

        // specify an adapter (see also next example)
        adapter = new OCRecyclerViewAdapter(getApplicationContext(), items);
        recyclerViewItem.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.d("TAG Postion", "My position is "+position);
                OrganizationCategory item = adapter.getItem(position);
                if(item != null) {
                    Intent intent = new Intent(OrganizationCategoriesActivity.this, OrganizationListActivity.class);
                    intent.putExtra("city_id", cityId);
                    intent.putExtra("category_id", item.getId());
                    startActivity(intent);
                    /*Toast.makeText(getApplicationContext(),
                            "ID: " + item.getId(), Toast.LENGTH_SHORT)
                            .show();*/
                }

            }
        });

        close_button = (FrameLayout)findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Observable<List<OrganizationCategory>> list1 = localItemsRepo.getAllOrganizationCategories();
//                System.out.println("###################################### SIZE: "+list1.size());

                Intent intent = new Intent(OrganizationCategoriesActivity.this, OrganizationListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "showLoading() returned: ");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "hideLoading() returned: ");
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError() returned: " + error);
    }
}
