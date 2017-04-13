package com.ringov.yatrnsltr.translation_module.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {

    private UITranslation item;
    private List<String> translations;

    public TranslationAdapter() {
        translations = new ArrayList<>();
    }

    public void setTranslation(UITranslation items) {
        this.item = items;
        translations = items.getTranslations();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.translate_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_original)
        TextView mTvOriginal;
        @BindView(R.id.tv_translation)
        TextView mTvTranslation;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(int position) {
            mTvOriginal.setText(item.getOriginalText());
            mTvTranslation.setText(translations.get(position));
        }
    }
}
