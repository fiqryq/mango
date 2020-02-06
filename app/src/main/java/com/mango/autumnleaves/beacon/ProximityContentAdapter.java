package com.mango.autumnleaves.beacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.util.EstimoteUtils;

import java.util.ArrayList;
import java.util.List;

public class ProximityContentAdapter extends BaseAdapter {

    private Context context;
    public ProximityContentAdapter(Context context) {
        this.context = context;
    }

    private List<ProximityContent> nearbyContent = new ArrayList<>();

    public void setNearbyContent(List<ProximityContent> nearbyContent) {
        this.nearbyContent = nearbyContent;
    }

    @Override
    public int getCount() {
        return nearbyContent.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;

            convertView = inflater.inflate(R.layout.presensi_content_beacon, parent, false);
        }

        ProximityContent content = nearbyContent.get(position);
        TextView kelas = convertView.findViewById(R.id.beacon_kelas);
        TextView matakuliah = convertView.findViewById(R.id.beacon_matakuliah);
        kelas.setText(content.getTitle());
        matakuliah.setText(content.getSubtitle());


        return convertView;
    }

}
