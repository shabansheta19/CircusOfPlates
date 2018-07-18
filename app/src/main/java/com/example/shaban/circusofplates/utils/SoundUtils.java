package com.example.shaban.circusofplates.utils;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.shaban.circusofplates.R;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by shaban on 7/18/2018.
 */

public class SoundUtils {

    private static SoundUtils instance;
    private SoundPool soundPool;
    private AudioManager audioManager;
    // Maximum sound stream.
    private static final int MAX_STREAMS = 2;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;
    private int soundBounceId;
    private int soundCatchId;
    private int soundGameOverId;
    private float volume;

    private SoundUtils(){}

    public synchronized static SoundUtils getInstance() {
        if (instance == null)
            instance = new SoundUtils();
        return instance;
    }

    public void init(Activity activity) {
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) activity.getSystemService(AUDIO_SERVICE);

        // Current volume Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

        // Volume (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        activity.setVolumeControlStream(streamType);

        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // for Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        // Load sound file (game_bonus_1.wav) into SoundPool.
        soundBounceId = soundPool.load(activity, R.raw.game_bonus_1,1);
        // Load sound file (catch_plate_sound.wav) into SoundPool.
        soundCatchId = soundPool.load(activity, R.raw.catch_plate_sound,1);
        // Load sound file (game_over_sound.wav) into SoundPool.
        soundGameOverId = soundPool.load(activity, R.raw.game_over_sound,1);
    }

    /**
     * play pounce sound.
     */
    public void playSoundBounce()  {
        if(loaded)  {
            float leftVolume = volume;
            float rightVolume = volume;
            // Play sound. Returns the ID of the new stream.
            int streamId = soundPool.play(soundBounceId,leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    /**
     * play catch sound.
     */
    public void playSoundCatch()  {
        if(loaded)  {
            float leftVolume = volume;
            float rightVolume = volume;
            // Play sound. Returns the ID of the new stream.
            int streamId = soundPool.play(soundCatchId,leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    /**
     * play game over sound.
     */
    public void playSoundGameOver()  {
        if(loaded)  {
            float leftVolume = volume;
            float rightVolume = volume;
            // Play sound . Returns the ID of the new stream.
            int streamId = soundPool.play(soundGameOverId,leftVolume, rightVolume, 1, 0, 1f);
        }
    }
}
