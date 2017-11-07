package com.jiangkang.ktools.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jiangkang.ktools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, GalleryActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }



    @BindView(R.id.rc_gallery)
    RecyclerView rcGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        rcGallery.setLayoutManager(new LinearLayoutManager(this));
        rcGallery.setAdapter(new GalleryAdapter(this));
    }
}
