package com.nerdzlab.mysound.Adapters;

import android.content.Context;
import android.media.SoundPool;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nerdzlab.mysound.Models.SoundResource;
import com.nerdzlab.mysound.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orcun on 25.02.2017.
 */
public class SoundsRecyclerAdapter extends RecyclerView.Adapter<SoundsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "SoundsRecyclerAdapter";
    private final Context context;
    private ArrayList<SoundResource> mDataSet;

    public SoundsRecyclerAdapter(ArrayList<SoundResource> data, Context context) {
        this.mDataSet = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SoundResource item = mDataSet.get(position);
        holder.getSoundName().setText(item.getResource_name());

        holder.getVolumebar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.d(TAG, "onProgressChanged: "+i);
                if(context instanceof SoundInterface){
                    Log.d(TAG, "onClick: instance of CI");
                    ((SoundInterface)context).soundLevelChanged(item.getResource_id(),i);
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



        //TODO Fill in your logic for binding the view.
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView soundicon;
        private TextView soundName;
        private SeekBar volumebar;


        public ViewHolder(View itemView) {
            super(itemView);

            soundicon = (ImageView) itemView.findViewById(R.id.soundicon);
            soundName = (TextView) itemView.findViewById(R.id.soundName);
            volumebar = (SeekBar) itemView.findViewById(R.id.volumebar);
        }

        public ImageView getSoundicon() {
            return soundicon;
        }

        public TextView getSoundName() {
            return soundName;
        }

        public SeekBar getVolumebar() {
            return volumebar;
        }
    }
}