package com.ringov.yatrnsltr.common_module.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.common_module.entities.UILanguage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 18.04.2017.
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private List<UILanguage> items;

    private ViewHolder crtFromHolder;
    private ViewHolder crtToHolder;

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

    public void setLanguages(List<UILanguage> languages) {
        this.items = languages;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_language_name)
        TextView mTvLanguageName;
        @BindView(R.id.btn_from)
        ToggleButton mBtnFrom;
        @BindView(R.id.btn_to)
        ToggleButton mBtnTo;

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
        }

        public void bindView(int position) {
            mTvLanguageName.setText(items.get(position).getFullName());

            if (this == crtFromHolder) {
                mBtnTo.setEnabled(false);
            }
            if (this == crtToHolder) {
                mBtnFrom.setEnabled(false);
            }
        }
    }
}
