/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */
package com.android.teklipyaz.views.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.utils.ColorGenerator;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.utils.Utils.AnimationType;
import com.android.teklipyaz.views.activities.MainActivity;
//import com.android.teklipyaz.views.adapters.SimilarOrganizationsPagerAdapter;
import com.android.teklipyaz.views.customviews.ClickableViewPager;
import com.android.teklipyaz.views.customviews.LabelView;
import com.android.teklipyaz.views.customviews.TextDrawable;
import com.android.teklipyaz.views.customviews.TextDrawable.IBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

// TODO: Auto-generated Javadoc

/**
 * Fragment that appears in the "content_frame", shows a animal.
 */
@SuppressLint("ValidFragment")
public class OrganizationDetailsFragment extends Fragment {

    private int id;
    private ImageView itemImage;
    private TextView itemSellPrice;
    private TextView title;
    private TextView quanitity;
    private TextView itemdescription;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private String subcategoryKey;
    private boolean isFromCart;
    private ClickableViewPager similarOrganizationsPager;
    private ClickableViewPager topSellingPager;
    private Toolbar mToolbar;

    /**
     * Instantiates a new organization details fragment.
     */
    public OrganizationDetailsFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_organization_detail,
                container, false);

        /*mToolbar = (Toolbar) rootView.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((MainActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            //mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setTitleTextColor(Color.WHITE);*/

        /*mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });*/

        ((MainActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        /*similarOrganizationsPager = (ClickableViewPager) rootView
                .findViewById(R.id.similar_organizations_pager);*/

        /*topSellingPager = (ClickableViewPager) rootView
                .findViewById(R.id.top_selleing_pager);

        itemSellPrice = ((TextView) rootView
                .findViewById(R.id.category_discount));

        quanitity = ((TextView) rootView.findViewById(R.id.organization_title));*/

        title = ((TextView) rootView.findViewById(R.id.title));

        /*itemdescription = ((TextView) rootView
                .findViewById(R.id.item_short_desc));

        itemImage = (ImageView) rootView.findViewById(R.id.organization_image);*/

        fillOrganizationData();

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        /*rootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

//                    if (isFromCart) {
//
//                        Utils.switchContent(R.id.frag_container,
//                                Utils.SHOPPING_LIST_TAG,
//                                ((MainActivity) (getActivity())),
//                                AnimationType.SLIDE_UP);
//                    } else {

                        Utils.switchContent(R.id.frag_container,
                                Utils.ORGANIZATION_OVERVIEW_FRAGMENT_TAG,
                                ((MainActivity) (getActivity())),
                                AnimationType.SLIDE_RIGHT);
//                    }

                }
                return true;
            }
        });*/

        /*if (isFromCart) {

            similarOrganizationsPager.setVisibility(View.GONE);

            topSellingPager.setVisibility(View.GONE);

        } else {
            //showRecomondation();
        }*/

        return rootView;
    }

    /*private void showRecomondation() {

        SimilarOrganizationsPagerAdapter mCustomPagerAdapter = new SimilarOrganizationsPagerAdapter(
                getActivity(), subcategoryKey);

        similarOrganizationsPager.setAdapter(mCustomPagerAdapter);

        similarOrganizationsPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                id = position;

                fillOrganizationData();

                Utils.vibrate(getActivity());

            }
        });

        topSellingPager.setAdapter(mCustomPagerAdapter);

        topSellingPager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                id = position;

                fillOrganizationData();

                Utils.vibrate(getActivity());

            }
        });
    }
*/
    public void fillOrganizationData() {
        Toast.makeText(getActivity(),
                "Clicked: "+id,
                Toast.LENGTH_SHORT).show();
        /*if (!isFromCart) {


            //Fetch and display item from Gloabl Data Model

            title.setText(CenterRepository.getCenterRepository()
                    .getMapOfOrganizationsInCategory().get(subcategoryKey).get(id)
                    .getTitle());

            quanitity.setText(CenterRepository.getCenterRepository()
                    .getMapOfOrganizationsInCategory().get(subcategoryKey).get(id)
                    .getTelephone());

            itemdescription.setText(CenterRepository.getCenterRepository()
                    .getMapOfOrganizationsInCategory().get(subcategoryKey).get(id)
                    .getAddress());

            String sellCostString = "0";

            String buyMRP = "1";

            String costString = sellCostString + buyMRP;

            itemSellPrice.setText(costString, BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(CenterRepository.getCenterRepository()
                            .getMapOfOrganizationsInCategory().get(subcategoryKey)
                            .get(id).getTitle().charAt(0)),
                    mColorGenerator.getColor(CenterRepository
                            .getCenterRepository().getMapOfOrganizationsInCategory()
                            .get(subcategoryKey).get(id)
                            .getTitle()));

            Picasso.with(getActivity())
                    .load(CenterRepository.getCenterRepository().getMapOfOrganizationsInCategory()
                            .get(subcategoryKey).get(id)
                            .getImage()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed

                            Picasso.with(getActivity())
                                    .load(CenterRepository.getCenterRepository()
                                            .getMapOfOrganizationsInCategory()
                                            .get(subcategoryKey)
                                            .get(id)
                                            .getImage())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }
                    });

            LabelView label = new LabelView(getActivity());

            label.setText(CenterRepository.getCenterRepository().getMapOfOrganizationsInCategory()
                    .get(subcategoryKey).get(id).getTitle());
            label.setBackgroundColor(0xffE91E63);

            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);
        } else {


            //Fetch and display organizations from Shopping list

            title.setText(CenterRepository.getCenterRepository()
                    .getListOfOrganizationsInShoppingList().get(id).getTitle());

            quanitity.setText(CenterRepository.getCenterRepository()
                    .getListOfOrganizationsInShoppingList().get(id).getTelephone());

            itemdescription.setText(CenterRepository.getCenterRepository()
                    .getListOfOrganizationsInShoppingList().get(id).getAddress());

            String sellCostString = "0";

            String buyMRP = "1";

            String costString = sellCostString + buyMRP;

            itemSellPrice.setText(costString, BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(CenterRepository.getCenterRepository()
                            .getListOfOrganizationsInShoppingList().get(id)
                            .getTitle().charAt(0)),
                    mColorGenerator.getColor(CenterRepository
                            .getCenterRepository().getListOfOrganizationsInShoppingList()
                            .get(id).getTitle()));

            Picasso.with(getActivity())
                    .load(CenterRepository.getCenterRepository()
                            .getListOfOrganizationsInShoppingList().get(id)
                            .getImage()).placeholder(drawable)
                    .error(drawable).fit().centerCrop()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(itemImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed

                            Picasso.with(getActivity())
                                    .load(CenterRepository.getCenterRepository()
                                            .getListOfOrganizationsInShoppingList()
                                            .get(id)
                                            .getImage())
                                    .placeholder(drawable).error(drawable)
                                    .fit().centerCrop().into(itemImage);
                        }
                    });

            LabelView label = new LabelView(getActivity());

            label.setText(CenterRepository.getCenterRepository()
                    .getListOfOrganizationsInShoppingList().get(id).getTitle());
            label.setBackgroundColor(0xffE91E63);

            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);

        }*/
    }


}