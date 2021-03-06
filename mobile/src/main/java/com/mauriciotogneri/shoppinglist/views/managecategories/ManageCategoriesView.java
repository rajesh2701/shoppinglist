package com.mauriciotogneri.shoppinglist.views.managecategories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseDialog;
import com.mauriciotogneri.common.base.BaseDialog.OnAccept;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.ListCategoryAdapter;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter;
import com.mauriciotogneri.shoppinglist.adapters.MenuItemAdapter.Option;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogConfirmation;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogEditCategory;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogError;
import com.mauriciotogneri.shoppinglist.views.dialogs.DialogMenu;
import com.mauriciotogneri.shoppinglist.views.managecategories.ManageCategoriesView.UiContainer;

import java.util.ArrayList;
import java.util.List;

public class ManageCategoriesView extends BaseView<UiContainer> implements ManageCategoriesViewInterface<UiContainer>
{
    private ListCategoryAdapter adapter;

    @Override
    public void initialize(final Context context, List<Category> list, final ManageCategoriesViewObserver observer)
    {
        adapter = new ListCategoryAdapter(context);

        ui.list.setAdapter(adapter);

        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Category category = (Category) parent.getItemAtPosition(position);
                observer.onCategorySelected(category);
            }
        });

        ui.list.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                Category category = (Category) parent.getItemAtPosition(position);
                displayCategoryOptions(context, category, observer);

                return true;
            }
        });

        ui.buttonCreateCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editCategory(context, null, observer);
            }
        });

        fillList(list);
    }

    private void displayCategoryOptions(final Context context, final Category category, final ManageCategoriesViewObserver observer)
    {
        final int EDIT_CATEGORY = 0;
        final int REMOVE_CATEGORY = 1;

        List<Option> optionsList = new ArrayList<>();
        optionsList.add(new Option(context.getString(R.string.icon_edit), context.getString(R.string.button_edit)));
        optionsList.add(new Option(context.getString(R.string.icon_remove), context.getString(R.string.button_remove)));

        DialogMenu dialog = new DialogMenu(context, category.getName());
        ListAdapter menuItemAdapter = new MenuItemAdapter(context, optionsList);
        dialog.setAdapter(menuItemAdapter, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int index)
            {
                switch (index)
                {
                    case EDIT_CATEGORY:
                        editCategory(context, category, observer);
                        break;

                    case REMOVE_CATEGORY:
                        removeCategory(context, category, observer);
                        break;
                }
            }
        });

        dialog.display();
    }

    private void editCategory(Context context, Category category, ManageCategoriesViewObserver observer)
    {
        int title = (category == null) ? R.string.label_product_new_category : R.string.label_product_edit_category;

        DialogEditCategory dialog = new DialogEditCategory(context, title);
        dialog.initialize(category, observer);
        dialog.display();
    }

    private void removeCategory(Context context, final Category category, final ManageCategoriesViewObserver observer)
    {
        DialogConfirmation dialog = new DialogConfirmation(context, category.getName());
        dialog.initialize(R.string.confirmation_remove_category, new OnAccept()
        {
            @Override
            public void onAccept(BaseDialog dialog)
            {
                dialog.close();
                observer.onRemoveCategory(category);
            }
        });
        dialog.display();
    }

    @Override
    public void showError()
    {
        DialogError dialog = new DialogError(getContext());
        dialog.initialize(R.string.error_category_in_use);
        dialog.display();
    }

    @Override
    public void fillList(List<Category> list)
    {
        adapter.update(list);

        checkEmptyList();
    }

    private void checkEmptyList()
    {
        if (!adapter.isEmpty())
        {
            ui.list.setVisibility(View.VISIBLE);
            ui.emptyLabel.setVisibility(View.GONE);
        }
        else
        {
            ui.list.setVisibility(View.GONE);
            ui.emptyLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_manage_categories;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;
        private final TextView emptyLabel;
        private final TextView buttonCreateCategory;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.emptyLabel = (TextView) findViewById(R.id.empty_label);
            this.buttonCreateCategory = (TextView) findViewById(R.id.create_category);
        }
    }
}