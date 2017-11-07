package com.jiangkang.ktools.effect.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiangkang.ktools.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EffectFragment extends Fragment {


    @BindView(R.id.effect_shape)
    Button effectShape;
    Unbinder unbinder;

    public EffectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_effect, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private void handleClick(@NonNull Fragment fragment) {
        String tag = fragment.getClass().toString();
        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.effect_shape)
    public void onEffectShapeClicked() {
        handleClick(new ShapeViewFragment());
    }
}
