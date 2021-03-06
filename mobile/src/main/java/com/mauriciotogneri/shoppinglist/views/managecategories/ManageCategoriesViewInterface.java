package com.mauriciotogneri.shoppinglist.views.managecategories;

import android.content.Context;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.shoppinglist.model.Category;

import java.util.List;

public interface ManageCategoriesViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(Context context, List<Category> list, ManageCategoriesViewObserver observer);

    void showError();

    void fillList(List<Category> list);
}