package com.ringov.yatrnsltr.translation_module.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ringov.yatrnsltr.R;
import com.ringov.yatrnsltr.custom_views.FavoriteButton;
import com.ringov.yatrnsltr.custom_views.ToggleImageButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sergey Koltsov on 10.04.2017.
 */

public class TranslateFragment extends Fragment {

    @BindView(R.id.tv_translation)
    TextView mTvTranslation;

    @OnClick(R.id.btn_translate)
    void onTranslateClick(){
        
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translate_fragment, container, false);
        return v;
    }
}
