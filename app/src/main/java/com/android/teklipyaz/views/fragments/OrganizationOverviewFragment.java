package com.android.teklipyaz.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.teklipyaz.R;
import com.android.teklipyaz.domains.mock.FakeWebServer;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.views.adapters.OrganizationsInCategoryPagerAdapter;

import java.util.Set;

/**
 * Created by Lenovo on 18.10.2017.
 */

public class OrganizationOverviewFragment extends Fragment {

    private ViewPager viewPager;

        public OrganizationOverviewFragment() {
        // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
// Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.frag_organization_list, container, false);

            // Simulate Web service calls
            FakeWebServer.getFakeWebServer().getAllOrganizations(0);

            //viewPager = (ViewPager) view.findViewById(R.id.org_tab_viewpager);

            return view;
        }

    private void setupViewPager(ViewPager viewPager) {
        OrganizationsInCategoryPagerAdapter adapter = new OrganizationsInCategoryPagerAdapter(
                getActivity().getSupportFragmentManager());
        Set<String> keys = CenterRepository.getCenterRepository().getMapOfOrganizationsInCategory()
                .keySet();

        /*for (String string : keys) {
            adapter.addFrag(new OrganizationListFragment(string), string);
        }*/

        viewPager.setAdapter(adapter);
//		viewPager.setPageTransformer(true,
//				Utils.currentPageTransformer(getActivity()));
    }

}
