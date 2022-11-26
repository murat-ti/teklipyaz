package com.android.teklipyaz.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.ImageModel;
import com.android.teklipyaz.models.entities.Organization;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.utils.ImageHelper;
import com.android.teklipyaz.utils.ListUtil;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.OnItemClickListener;
import com.android.teklipyaz.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class PlaceCategoriesAdapter extends RecyclerView.Adapter<PlaceCategoriesAdapter.ItemViewHolder> {
    private Context context;
    private List<OrganizationCategory> items;
    private OnItemClickListener clickListener;

    public PlaceCategoriesAdapter(Context context) {
        this.items = CenterRepository.getCenterRepository().getOrganizationCategoryList();
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public PlaceCategoriesAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization_category, parent, false);
        return new PlaceCategoriesAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceCategoriesAdapter.ItemViewHolder holder, int position) {
        OrganizationCategory item = items.get(position);
        holder.set(context,item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        //Log.d("MYSIZE2", "Size: "+items.size());
        return items.size();
    }

    public void add(OrganizationCategory r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<OrganizationCategory> moveResults) {
        for (OrganizationCategory result : moveResults) {
            add(result);
        }
    }

    public void remove(OrganizationCategory r) {
        int position = items.indexOf(r);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public OrganizationCategory getItem(int position) {
        return items.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public void set(Context context, OrganizationCategory item) {
            //UI setting code
            mTitle.setText(item.getTitle(LocaleHelper.getLanguage(context)));
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getLayoutPosition());
        }
    }
}