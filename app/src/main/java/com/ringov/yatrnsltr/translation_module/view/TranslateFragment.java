package com.ringov.yatrnsltr.translation_module.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;
import com.ringov.yatrnsltr.translation_module.interactor.TranslationInteractorImpl;
import com.ringov.yatrnsltr.translation_module.presenter.TranslationPresenter;
import com.ringov.yatrnsltr.translation_module.router.TranslationRouter;
import com.ringov.yatrnsltr.translation_module.view.ui_entity.UITranslation;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment<TranslationPresenter>
        implements TranslationView {

    @BindView(R.id.et_input)
    EditText mEtOriginalText;
    @BindView(R.id.rv_output)
    RecyclerView mRvOutput;
    @BindView(R.id.cv_output_field)
    CardView mCvOutputCard;

    @OnClick(R.id.ll_change_lang)
    void onChangeLangClick() {
        Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();
    }

    TranslationAdapter mAdapter;

    @OnClick(R.id.btn_translate)
    void onTranslateClick() {
        mPresenter.translateClicked(mEtOriginalText.getText().toString());
    }

    @Override
    protected TranslationPresenter providePresenter() {
        return new TranslationPresenter(new TranslationRouter() {
        }, new TranslationInteractorImpl());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecycler();
    }

    private void initializeRecycler() {
        mRvOutput.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TranslationAdapter();
        mRvOutput.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.translate_fragment;
    }

    @Override
    public void showTranslation(UITranslation translation) {
        if(!translation.isEmpty()) {
            mCvOutputCard.setVisibility(View.VISIBLE);
            mAdapter.setTranslation(translation);
        }
    }
}
