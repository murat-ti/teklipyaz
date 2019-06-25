/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.CityModel;
import com.android.teklipyaz.utils.ApiUtil;
import com.android.teklipyaz.utils.ColorGenerator;
import com.android.teklipyaz.views.customviews.TextDrawable;
import com.android.teklipyaz.views.customviews.TextDrawable.IBuilder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class tempCityListAdapter extends
        RecyclerView.Adapter<tempCityListAdapter.VersionViewHolder> {

    public static List<CityModel> cityList = new ArrayList<CityModel>();
    OnItemClickListener clickListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    private Context context;

    public tempCityListAdapter(Context context) {
        cityList = CenterRepository.getCenterRepository().getListOfCity();
        System.out.println("Result: "+cityList.size());
        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_city_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder,
                                 int cityIndex) {

        versionViewHolder.itemName.setText(cityList.get(cityIndex)
                .getTitleRu());

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(cityList
                        .get(cityIndex).getTitleRu().charAt(0)),
                mColorGenerator.getColor(cityList.get(cityIndex)
                        .getTitleRu()));

        /*ImageUrl = ApiUtil.BASE_IMAGE_URL+cityList.get(cityIndex).getImage();
        System.out.println("ImageHelper: "+ImageUrl);

        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(versionViewHolder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView itemName;
        ImageView imageView;

        public VersionViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemName.setSelected(true);
            imageView = ((ImageView) itemView.findViewById(R.id.imageView));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

}
