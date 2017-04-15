package com.ringov.yatrnsltr.storage_module.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.custom_views.FavoriteButton;
import com.ringov.yatrnsltr.custom_views.TrnsltrModeButton;
import com.ringov.yatrnsltr.ui_entities.UILangPair;
import com.ringov.yatrnsltr.ui_entities.UITranslation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sergey Koltsov on 13.04.2017.
 */

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.BaseViewHolder> {

    private List<UITranslation> items;
    private OnItemClickListener mListener;

    public StorageAdapter(OnItemClickListener listener) {
        items = new ArrayList<>();
        mListener = listener;
    }

    public void setTranslations(List<UITranslation> translations) {
        this.items = translations;
        notifyDataSetChanged();
    }

    public void addTransaction(UITranslation transaction) {
        this.items.add(transaction);
        notifyItemInserted(this.items.size() - 1);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener {
        void onItemClick(UITranslation translation);

        void onFooterClick();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_item_layout)
        ViewGroup mLlItemLayout;
        @BindView(R.id.tv_original)
        TextView mTvOriginal;
        @BindView(R.id.tv_translation)
        TextView mTvTranslation;
        @BindView(R.id.tv_lang_pair)
        TextView mTvLangPair;
        @BindView(R.id.fb_favorite)
        FavoriteButton mFb;
        @BindView(R.id.tmb_changed)
        TrnsltrModeButton mTmbMode;

        BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bindView(int position) {
            UITranslation crtTranslation = items.get(position);
            mTvOriginal.setText(crtTranslation.getOriginalText());
            mTvTranslation.setText(crtTranslation.getTranslations().get(0)); // todo handle many translation case
            UILangPair langPair = crtTranslation.getLangPair();
            mTvLangPair.setText(String.format(itemView.getContext().getString(R.string.lang_pair_item),
                    langPair.getSourceLangShortName(), langPair.getTargetLangShortName()));
            mFb.setChecked(crtTranslation.isFavorite());
            mTmbMode.setChecked(crtTranslation.isChanged());

            mLlItemLayout.setOnClickListener(v -> mListener.onItemClick(crtTranslation));
        }
    }
}