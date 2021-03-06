package com.mauriciotogneri.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T, V> extends ArrayAdapter<T>
{
    private final int resourceId;

    public BaseListAdapter(Context context, int resourceId, List<T> list)
    {
        super(context, resourceId, list);

        this.resourceId = resourceId;
    }

    public BaseListAdapter(Context context, int resourceId)
    {
        this(context, resourceId, new ArrayList<T>());
    }

    protected abstract V getViewHolder(View view);

    protected abstract void fillView(V viewHolder, T item, int position);

    public void update()
    {
        notifyDataSetChanged();
    }

    public void update(List<T> list)
    {
        clear();
        addAll(list);
        notifyDataSetChanged();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        V viewHolder;

        View rowView = convertView;

        if (rowView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(resourceId, parent, false);

            viewHolder = getViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (V) rowView.getTag();
        }

        T item = getItem(position);

        fillView(viewHolder, item, position);

        return rowView;
    }
}