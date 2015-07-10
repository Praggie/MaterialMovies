package com.roodie.materialmovies.views.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by Roodie on 01.07.2015.
 */
public abstract class ListFragment<E extends AbsListView> extends BaseFragment {

    ListAdapter mAdapter;
    E mListView;

    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();


        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * Attach to listView once when it was created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Detach from listView.
     */
    @Override
    public void onDestroyView() {
        mListView = null;
        super.onDestroyView();
    }

    public abstract E createListView(Context context, LayoutInflater inflater);

    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    public void setListAdapter(ListAdapter mAdapter) {
        this.mAdapter = mAdapter;
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * Get the position of the currently selected list item.
     */
    public int getSelectedItemPosition() {
        return mListView.getSelectedItemPosition();
    }

    /**
     * Get the cursor row ID of the currently selected list item.
     */
    public long getSelectedItemId() {
        return mListView.getSelectedItemId();
    }

    public E getListView() {
        return mListView;
    }


    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override it.
     */
    public void onListItemClick(E l, View v, int position, long id) {
    }

    final private AdapterView.OnItemClickListener mOnClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onListItemClick((E)parent, view, position, id);
                }
            };

}
