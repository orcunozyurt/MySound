package com.nerdzlab.mysound;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.nerdzlab.mysound.Adapters.MySection;
import com.nerdzlab.mysound.Adapters.SoundInterface;
import com.nerdzlab.mysound.Adapters.SoundsRecyclerAdapter;
import com.nerdzlab.mysound.Models.SoundResource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SoundInterface {

    private static final String TAG = "MainActivity";
    private HashMap<String, ArrayList<SoundResource>> data;
    private RelativeLayout contentMain;
    private RecyclerView soundrecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected SoundsRecyclerAdapter mAdapter;
    private SoundPool soundPool;
    public static HashMap< Long , Float > volumeMap = new HashMap<>();

    private AudioManager audioManager;

    // Maximumn sound stream.
    private static final int MAX_STREAMS = 10;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private Map playingMap;
    private int Volume;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        playingMap = new HashMap();

        contentMain = (RelativeLayout) findViewById(R.id.content_main);
        soundrecycler = (RecyclerView) findViewById(R.id.soundrecycler);

        data = new HashMap<String, ArrayList<SoundResource>>();
        data = loadDrawables(R.raw.class);

        Log.d(TAG, "onCreate: "+data);

        mLayoutManager = new LinearLayoutManager(this);
        soundrecycler.setLayoutManager(mLayoutManager);

        //mAdapter = new SoundsRecyclerAdapter(data, this);

        //soundrecycler.setAdapter(mAdapter);

        // Create an instance of SectionedRecyclerViewAdapter
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        // Add your Sections
        for (String key : data.keySet()) {
            sectionAdapter.addSection(new MySection(key,data.get(key),this,sectionAdapter));
        }


        // Set up your RecyclerView with the SectionedRecyclerViewAdapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.soundrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);

        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

        this.soundPool = builder.build();

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                Log.i("OnLoadCompleteListener","Sound "+sampleId+" loaded.");


                //Log.d(TAG, "onLoadComplete: VOLUME:"+Volume);
                int streamId = soundPool.play(sampleId,0.5f, 0.5f, 1, -1, 1.0f);

                Log.d(TAG, "onLoadComplete: STREAM ID:"+streamId);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fb) {
            /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));*/
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(getResources().getString(R.string.google_play_url)))
                    .build();
            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        } else if (id == R.id.nav_tw) {
            /*Intent tweet = new Intent(Intent.ACTION_VIEW);
            tweet.setData(Uri.parse("http://twitter.com/?status=" +
                    Uri.encode(getResources().getString(R.string.google_play_url))));
            startActivity(tweet);*/
            Intent tweet = new Intent(android.content.Intent.ACTION_SEND);
            tweet.setType("text/plain");
            tweet.putExtra(Intent.EXTRA_TEXT, Uri.parse(getResources().getString(R.string.google_play_url)).toString());
            startActivity(Intent.createChooser(tweet, "Share this via"));

        } else if (id == R.id.nav_github) {

            Intent tweet = new Intent(Intent.ACTION_VIEW);
            tweet.setData(Uri.parse("https://github.com/orcunozyurt/MySound"));//where message is your string message
            startActivity(tweet);

        }else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse(getResources().getString(R.string.google_play_url)));
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static HashMap<String,ArrayList<SoundResource>> loadDrawables(Class<?> clz){
        final Field[] fields = clz.getDeclaredFields();
        //ArrayList<SoundResource> resources = new ArrayList<SoundResource>();
        HashMap<String, ArrayList<SoundResource>> resources = new HashMap<>();
        for (Field field : fields) {
            SoundResource sr = new SoundResource();
            try {
                Log.d(TAG, "loadDrawables: "+field.getLong(clz) + " "+ field.getName());
                sr.setResource_id(field.getInt(clz));
                sr.setResource_name(field.getName());
                sr.setResource_volume(0);


                volumeMap.put(sr.getResource_id(),0f);
                //resources.add(sr);
                if(resources.containsKey(sr.getSectionName()))
                {
                    resources.get(sr.getSectionName()).add(sr);
                }
                else
                {
                    ArrayList<SoundResource> arr = new ArrayList<>();
                    arr.add(sr);
                    resources.put(sr.getSectionName(),arr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadDrawables: EXCEPTION on:"+ field.getName());
            }
        /* make use of drawableId for accessing Drawables here */
        }

        return resources;
    }

    @Override
    public void soundLevelChanged(long res_id, int percent) {

        //Log.d(TAG, "soundLevelChanged: "+" RES ID: "+res_id + " percent: "+ percent);

        if(playingMap.containsKey(res_id))
        {
            Log.d(TAG, "soundLevelChanged: playingMAp contains:"+ res_id +" in "+ playingMap);
            if(percent == 0)
            {
                try {
                    soundPool.stop((Integer) playingMap.get(res_id));
                    playingMap.remove(res_id);
                    volumeMap.put(res_id,0f);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }else{

                try{
                    //Log.d(TAG, "soundLevelChanged: SET SOUND LEVEL TO:"+percent/100f);
                    soundPool.setVolume((Integer) playingMap.get(res_id),percent/100f,percent/100f);
                    volumeMap.put(res_id,percent/100f);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }else
        {
            Log.d(TAG, "soundLevelChanged: playingMAp NOT contains:"+ res_id +" in "+ playingMap);
            int sample = this.soundPool.load(this, (int)res_id, 1 );
            Volume = percent/100;
            playingMap.put(res_id,sample);
            volumeMap.put(res_id, percent/100f);

        }


    }
}
