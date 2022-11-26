/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.views.adapters;

public class CategoryListAdapterTemp {

    /*public static List<OrganizationCategory> categoryList = new ArrayList<OrganizationCategory>();
    OnItemClickListener clickListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    private Context context;

    public CategoryListAdapterTemp(Context context) {

        categoryList = CenterRepository.getCenterRepository().getListOfCategory();

        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_organization_category_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder,
                                 int categoryIndex) {

        versionViewHolder.itemName.setText(categoryList.get(categoryIndex)
                .getOrganizationCategoryName());

        versionViewHolder.itemDesc.setText(categoryList.get(categoryIndex)
                .getOrganizationCategoryDescription());

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(categoryList
                        .get(categoryIndex).getOrganizationCategoryName().charAt(0)),
                mColorGenerator.getColor(categoryList.get(categoryIndex)
                        .getOrganizationCategoryName()));

        ImageUrl = categoryList.get(categoryIndex).getOrganizationCategoryImageUrl();

        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(versionViewHolder.imagView);

        LabelView label = new LabelView(context);
        label.setText(categoryList.get(categoryIndex)
                .getOrganizationCategoryDiscount());
        label.setBackgroundColor(0xffE91E63);
        label.setTargetView(versionViewHolder.imagView, 10,
                LabelView.Gravity.RIGHT_TOP);

    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
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
        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        ImageView imagView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemName.setSelected(true);

            imagView = ((ImageView) itemView.findViewById(R.id.imageView));

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }*/

}
