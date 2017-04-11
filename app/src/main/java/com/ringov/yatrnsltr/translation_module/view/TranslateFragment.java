package com.ringov.yatrnsltr.translation_module.view;

import android.widget.TextView;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.base.implementations.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends BaseFragment implements TranslationView {

    @BindView(R.id.tv_translation)
    TextView mTvTranslation;

    @OnClick(R.id.btn_translate)
    void onTranslateClick(){

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.translate_fragment;
    }

}
