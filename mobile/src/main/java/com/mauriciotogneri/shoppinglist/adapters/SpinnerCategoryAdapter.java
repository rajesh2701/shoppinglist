package com.mauriciotogneri.shoppinglist.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseListAdapter;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.SpinnerCategoryAdapter.ViewHolder;
import com.mauriciotogneri.shoppinglist.model.Category;

public class SpinnerCategoryAdapter extends BaseListAdapter<Category, ViewHolder>
{
    public SpinnerCategoryAdapter(Context context)
    {
        super(context, R.layout.view_category_header);
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        return new ViewHolder(view);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Category category, int position)
    {
        viewHolder.title.setText(category.getName());
    }

    protected static class ViewHolder
    {
        public final TextView title;

        public ViewHolder(View view)
        {
            this.title = (TextView) view.findViewById(R.id.title);
        }
    }

    @Override
    @SuppressLint("InflateParams")
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        View rowView = convertView;

        if (rowView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.view_category_dropdown, parent, false);

            viewHolder = getViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Category category = getItem(position);

        viewHolder.title.setText(category.getName());

        return rowView;
    }

    public int getPositionOf(Category category)
    {
        int result = -1;
        int limit = getCount();

        for (int i = 0; i < limit; i++)
        {
            Category current = getItem(i);

            if (TextUtils.equals(current.getName(), category.getName()))
            {
                result = i;
                break;
            }
        }

        return result;
    }
}