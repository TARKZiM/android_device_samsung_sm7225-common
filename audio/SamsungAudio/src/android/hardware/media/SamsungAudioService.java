/*
 * Copyright (c) 2023 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.hardware.media;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.SystemProperties;
import android.os.IBinder;
import android.util.Log;
import android.os.Handler;
import android.media.AudioManager;
import android.media.AudioDeviceInfo;
import android.media.AudioSystem;

public class SamsungAudioService extends Service {
    private static final String TAG = "SamsungAudioService";
    private static final boolean DEBUG = false;

    private Handler mHandler;
    private static Runnable mRunnable;
    private AudioManager mAudioManager;

    @Override
    public void onCreate() {
        if (DEBUG) Log.d(TAG, "SamsungAudioService Started");

        mAudioManager = getSystemService(AudioManager.class);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                if (DEBUG) Log.d(TAG, "onCreate: " + TAG + " is running");

                if (mAudioManager.getMode() == AudioManager.MODE_IN_COMMUNICATION) {

                    if (mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                        || mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
                        || mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BLE_SPEAKER
                        || mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BLE_HEADSET) {
                        if (DEBUG) Log.d(TAG, "Setting VoIP parameter for bluetooth");
                        SystemProperties.set("vendor.audio.voip.device", "bluetooth");
                        AudioSystem.setParameters("voip_call=1");
                    }

                    if (mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BUILTIN_EARPIECE) {
                        if (DEBUG) Log.d(TAG, "Setting VoIP parameter for earpiece");
                        SystemProperties.set("vendor.audio.voip.device", "earpiece");
                        AudioSystem.setParameters("voip_call=1");
                    }

                    if (mAudioManager.getCommunicationDevice().getType() == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER) {
                        if (DEBUG) Log.d(TAG, "Setting VoIP parameter for speaker");
                        SystemProperties.set("vendor.audio.voip.device", "speaker");
                        AudioSystem.setParameters("voip_call=1");
                    }
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };

        mHandler.postDelayed(mRunnable, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
