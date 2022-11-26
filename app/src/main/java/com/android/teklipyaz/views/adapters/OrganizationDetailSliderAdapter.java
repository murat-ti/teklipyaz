/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.teklipyaz.R;
import com.android.teklipyaz.utils.ImageHelper;
import com.databasefirst.base.remote.RemoteConfiguration;

public class OrganizationDetailSliderAdapter extends PagerAdapter{
    Context context;
    String images[];
    LayoutInflater layoutInflater;
    private ProgressBar mProgressBar;


    public OrganizationDetailSliderAdapter(Context context, String images[]) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image, container, false);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.organization_progress);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        //imageView.setImageURI().setImageResource(images[position]);
        //Glide.with(context).load(RemoteConfiguration.BASE_IMAGE_URL + images[position]).into(imageView);
        ImageHelper imageUtil = new ImageHelper(context,imageView, mProgressBar);
        imageUtil.show(images[position]);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}