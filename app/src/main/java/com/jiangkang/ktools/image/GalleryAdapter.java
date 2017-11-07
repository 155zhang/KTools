package com.jiangkang.ktools.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiangkang.ktools.GlideApp;
import com.jiangkang.ktools.R;

import java.util.ArrayList;

/**
 * Created by jiangkang on 2017/10/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;

    private ArrayList<String> urlList;

    private LruCache<String,Bitmap> cache;

    public GalleryAdapter(Context context) {
        this.context = context;
        int maxMemorySize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemorySize / 6;
        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        loadData();
    }

    private void loadData(){
        urlList = new ArrayList<>();
        for (String url : ImagePool.urls){
            urlList.add(url);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_image,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(urlList.get(position))
                .into(holder.ivContent);
    }

    @Override
    public int getItemCount() {
       if (urlList != null){
           return urlList.size();
       }
       return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ivContent = (ImageView) itemView.findViewById(R.id.iv_content);
        }
    }



}
