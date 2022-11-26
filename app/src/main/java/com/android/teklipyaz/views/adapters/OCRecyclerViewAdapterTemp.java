package com.android.teklipyaz.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.OnItemClickListener;

import java.util.List;

public class OCRecyclerViewAdapterTemp extends RecyclerView.Adapter<OCRecyclerViewAdapterTemp.UserViewHolder> {
    private Context context;
    private List<OrganizationCategory> items;
    private OnItemClickListener clickListener;

    public OCRecyclerViewAdapterTemp(Context context, List<OrganizationCategory> items) {
        this.items = items;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organization_category, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        OrganizationCategory item = items.get(position);
        holder.set(context,item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        Log.d("MYSIZE", "Size: "+items.size());
        return items.size();
    }

    public OrganizationCategory getItem(int position) {
        return items.get(position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public UserViewHolder(View itemView) {
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