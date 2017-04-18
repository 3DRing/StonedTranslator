package com.ringov.yatrnsltr.common_module.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.common_module.entities.UILanguage;
import com.ringov.yatrnsltr.ui_entities.UILangPair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private OnLangPairChangedListener mListener;
    private List<UILanguage> items;

    private ViewHolder crtFromHolder;
    private ViewHolder crtToHolder;

    private UILangPair crtLangPair;

    LanguagesAdapter(OnLangPairChangedListener listener) {
        items = new ArrayList<>();
        this.mListener = listener;
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

    public void setLanguages(List<UILanguage> languages) {
        this.items = languages;
        notifyDataSetChanged();
    }

    public void setLanguagePair(UILangPair languagePair) {
        this.crtLangPair = languagePair;
    }

    public UILangPair getCrtLangPair() {
        return crtLangPair;
    }

    interface OnLangPairChangedListener {
        void onLangChanged(UILangPair langPair);
    }

    // todo refactor this mess
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_language_name)
        TextView mTvLanguageName;
        @BindView(R.id.btn_from)
        ToggleButton mBtnFrom;
        @BindView(R.id.btn_to)
        ToggleButton mBtnTo;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btn_from)
        void onFromClick() {
            if (crtFromHolder != null) {
                crtFromHolder.mBtnTo.setEnabled(true);
                crtFromHolder.mBtnFrom.setChecked(false);
            }
            crtFromHolder = this;
            mBtnFrom.setChecked(true);
            mBtnTo.setEnabled(false);

            if (!items.get(position).equals(crtLangPair.getSourceLang())) {
                crtLangPair.setSourceLang(items.get(position));
                mListener.onLangChanged(crtLangPair);
            }
        }

        @OnClick(R.id.btn_to)
        void onToClick() {
            if (crtToHolder != null) {
                crtToHolder.mBtnFrom.setEnabled(true);
                crtToHolder.mBtnTo.setChecked(false);
            }
            crtToHolder = this;
            mBtnTo.setChecked(true);
            mBtnFrom.setEnabled(false);

            if (!items.get(position).equals(crtLangPair.getTargetLang())) {
                crtLangPair.setTargetLang(items.get(position));
                mListener.onLangChanged(crtLangPair);
            }
        }

        // todo rewrite logic in a more neat way
        void bindView(int position) {
            this.position = position;

            UILanguage crtLang = items.get(position);

            mTvLanguageName.setText(crtLang.getFullName());

            if (crtLang.equals(crtLangPair.getSourceLang())) {
                mBtnFrom.setChecked(true);
                mBtnTo.setEnabled(false);
                crtFromHolder = this;
            } else {
                mBtnFrom.setChecked(false);
                mBtnTo.setEnabled(true);
            }
            if (crtLang.equals(crtLangPair.getTargetLang())) {
                mBtnFrom.setEnabled(false);
                mBtnTo.setChecked(true);
                crtToHolder = this;
            } else {
                mBtnFrom.setEnabled(true);
                mBtnTo.setChecked(false);
            }
        }
    }
}
