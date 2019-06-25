package com.android.teklipyaz.domains.api;

/**
 * Created by Admin on 02.01.2018.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.entities.CityModel;
import com.android.teklipyaz.utils.AppConstants;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.utils.Utils.AnimationType;
import com.android.teklipyaz.views.activities.MainActivity;
import com.android.teklipyaz.views.adapters.tempCityListAdapter;
import com.android.teklipyaz.views.adapters.tempCityListAdapter.OnItemClickListener;
import com.android.teklipyaz.views.fragments.OrganizationOverviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ImageLoaderTask.
 */
public class CityLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    //private static final String API_SERVER_URL ="http://192.168.1.100/teklipyaz/";
    private static final String API_SERVER_URL ="https://teklipyaz.com/";
    private static final String API_SERVER_PATH =API_SERVER_URL+"api/web/ru/city";
    private static final String API_IMAGE_PATH =API_SERVER_URL+"images/store/";
    private List<CityModel> cityList = new ArrayList<CityModel>();
    private static final String TAG ="MYTAG";
    private tempCityListAdapter simpleRecyclerAdapter;

    public CityLoaderTask(RecyclerView listView, Context context) {

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
        System.out.println("onPostExecute in");
        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        if (recyclerView != null) {
            System.out.println("Adapter initialized");
            simpleRecyclerAdapter = new tempCityListAdapter(
                    context);

            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AppConstants.CURRENT_CITY = position;

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new OrganizationOverviewFragment(),
                                    ((MainActivity) context), null,
                                    AnimationType.SLIDE_LEFT);

                        }
                    });
        }
        else{
            System.out.println("#######################There is not response");
        }

    }

    @Override
    protected Void doInBackground(String... params) {
        System.out.println("doInBackground in");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //FakeWebServerCity.getFakeWebServer().addCity();

        /*RequestQueue queue = Volley.newRequestQueue(context);
        System.out.println("Volley new request");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_SERVER_PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                cityList = Arrays.asList(gson.fromJson(response, CityModel[].class));
                ArrayList<CityModel> ListOfCity = new ArrayList<CityModel>();
                for (int i = 0; i < cityList.size(); i++) {
                    //if(cityList.get(i).getImage() == null)
                        //cityList.get(i).setImage("placeholder.jpg");
                    Log.d(TAG, "ImageHelper: "+API_IMAGE_PATH+cityList.get(i).getImage());
                    ListOfCity.add(new CityModel(
                                    cityList.get(i).getTitleTm(),
                                    cityList.get(i).getTitleRu(),
                                    cityList.get(i).getTitleEn(),
                                    cityList.get(i).getSlug(),
                                    cityList.get(i).getStatus(),
                            //"https://teklipyaz.com/images/store/Citys/City1/3d2851.jpg",
                                    API_IMAGE_PATH+cityList.get(i).getImage(),
                                    cityList.get(i).getId()
                                    ));
                }
                Log.d(TAG, "ListOfCity: "+ListOfCity.size());
                CenterRepository.getCenterRepository().setListOfCity(ListOfCity);
                System.out.println("NotifyDataChanged");
                simpleRecyclerAdapter.notifyDataSetChanged();
                System.out.println("List of city has been set");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "#########################Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);*/


        System.out.println("doInBackground out");
        return null;
    }

}
