package com.ringov.yatrnsltr.translation_module.view;

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

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.BaseViewHolder> {

    private static final int NORMAL_VIEW = 0;
    private static final int FOOTER_VIEW = 1;

    private UITranslation item;
    private List<String> translations;
    private OnItemClickListener mListener;

    public TranslationAdapter(OnItemClickListener listener) {
        translations = new ArrayList<>();
        mListener = listener;
    }

    public void setTranslation(UITranslation items) {
        this.item = items;
        translations = items.getTranslations();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == translations.size()) {
            return FOOTER_VIEW;
        }
        return NORMAL_VIEW;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        BaseViewHolder vh = null;
        switch (viewType) {
            case FOOTER_VIEW:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.translate_list_footer, parent, false);
                vh = new FooterViewHolder(v);
                break;
            case NORMAL_VIEW:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
                vh = new NormalViewHolder(v);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return translations.size();// + 1; // one additional room for footer
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void bindView(int position);
    }

    class NormalViewHolder extends BaseViewHolder {

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

        NormalViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(int position) {
            mTvOriginal.setText(item.getOriginalText());
            mTvTranslation.setText(translations.get(position));
            UILangPair langPair = item.getLangPair();
            mTvLangPair.setText(String.format(itemView.getContext().getString(R.string.lang_pair_item),
                    langPair.getSourceLangShortName(), langPair.getTargetLangShortName()));
            mLlItemLayout.setOnClickListener(v -> mListener.onItemClick(item, item.getTranslations().get(position)));

            mFb.setChecked(item.isFavorite());
            mTmbMode.setChecked(item.isChanged());
        }
    }

    class FooterViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_yandex_badge)
        View mTvYandexBadge;

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(int position) {
            mTvYandexBadge.setOnClickListener(v -> mListener.onFooterClick());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UITranslation translation, String translatingOption);

        void onFooterClick();
    }
}
