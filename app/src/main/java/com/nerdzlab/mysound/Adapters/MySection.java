package com.nerdzlab.mysound.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nerdzlab.mysound.Models.SoundResource;
import com.nerdzlab.mysound.R;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by orcunozyurt on 5/1/17.
 */

public class MySection extends StatelessSection {

    private static final String TAG = "SoundsRecyclerAdapter";

    ArrayList<SoundResource> myList ;
    String title;
    Context mContext;
    boolean expanded = true;
    SectionedRecyclerViewAdapter mAdapter;

    public MySection(String title, ArrayList<SoundResource> soundResource, Context context, SectionedRecyclerViewAdapter adapter) {
        // call constructor with layout resources for this Section header and items
        super(R.layout.header, R.layout.sound_item);
        this.myList = soundResource;
        this.title = title;
        this.mContext = context;
        this.mAdapter = adapter;

    }
    @Override
    public int getContentItemsTotal() {
        return expanded? myList.size() : 0;

    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        final SoundResource item = myList.get(position);

        // bind your view here
        itemHolder.soundName.setText(item.getCleanName());


        itemHolder.volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.d(TAG, "onProgressChanged: "+i);
                if(mContext instanceof SoundInterface){
                    Log.d(TAG, "onClick: instance of CI");
                    ((SoundInterface)mContext).soundLevelChanged(item.getResource_id(),i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Log.d(TAG, "onStopTrackingTouch: ");

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.titletw.setText(title);

        headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                headerHolder.titleiw.setImageResource(
                        expanded ? R.drawable.ic_arrow_upwards : R.drawable.ic_arrow_downwards
                );
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {


        private ImageView soundicon;
        private TextView soundName;
        private SeekBar volumebar;



        public MyItemViewHolder(View itemView) {
            super(itemView);



            soundicon = (ImageView) itemView.findViewById(R.id.soundicon);
            soundName = (TextView) itemView.findViewById(R.id.soundName);
            volumebar = (SeekBar) itemView.findViewById(R.id.volumebar);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {


        private ImageView titleiw;
        private TextView titletw;
        private View rootView;



        public HeaderViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            titleiw = (ImageView) itemView.findViewById(R.id.caret);
            titletw = (TextView) itemView.findViewById(R.id.title);

        }
    }
}
