package com.android.teklipyaz.domains.api;

/**
 * Created by Admin on 02.01.2018.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.teklipyaz.R;
import com.android.teklipyaz.domains.mock.FakeWebServerCity;
import com.android.teklipyaz.utils.AppConstants;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.utils.Utils.AnimationType;
import com.android.teklipyaz.views.activities.MainActivity;
import com.android.teklipyaz.views.adapters.CategoryListAdapter;
import com.android.teklipyaz.views.fragments.OrganizationOverviewFragment;

/**
 * The Class ImageLoaderTask.
 */
public class tempOrganizationCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    public tempOrganizationCategoryLoaderTask(RecyclerView listView, Context context) {

        this.recyclerView = listView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(
                    View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        /*if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
                    context);

            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AppConstants.CURRENT_CATEGORY = position;

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new OrganizationOverviewFragment(),
                                    ((MainActivity) context), null,
                                    AnimationType.SLIDE_LEFT);

                        }
                    });
        }*/

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //FakeWebServerCity.getFakeWebServer().addCity();

        return null;
    }

}
