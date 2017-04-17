package com.ringov.yatrnsltr.common_module.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    List<String> items;

    LanguagesAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_language_item, parent, false);
        return new LanguagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLanguages(List<String> languages) {
        this.items = languages;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_language_name)
        TextView mTvLanguageName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(int position) {
            mTvLanguageName.setText(items.get(position));
        }
    }
}
