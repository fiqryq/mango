package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.util.Log;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.mango.autumnleaves.util.EstimoteUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProximityContentManager {
    private Context context;
    private ProximityContentAdapter proximityContentAdapter;
    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;


    public ProximityContentManager(Context context, ProximityContentAdapter proximityContentAdapter, EstimoteCloudCredentials cloudCredentials) {
        this.context = context;
        this.proximityContentAdapter = proximityContentAdapter;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {
        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("kelas")
                .inCustomRange(10)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {
                        List<ProximityContent> nearbyContent = new ArrayList<>(contexts.size());

                        for (ProximityZoneContext proximityContext : contexts) {
                            // Ngodingnya di sini
                            String kelas = proximityContext.getAttachments().get("kelas");
                            String lokasi = proximityContext.getAttachments().get("lokasi");
                            if (kelas == null) {
                                kelas = "unknown";
                            }
                            String idbeacon = EstimoteUtils.getShortIdentifier(proximityContext.getDeviceId());
                            nearbyContent.add(new ProximityContent(kelas, idbeacon,lokasi));
                        }
                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();
                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zone);
    }

    public void stop() {
        proximityObserverHandler.stop();
    }
}

