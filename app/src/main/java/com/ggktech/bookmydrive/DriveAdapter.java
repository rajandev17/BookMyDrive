package com.ggktech.bookmydrive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rajan.kali on 11/23/2016.
 * Drive Adapter
 */

public class DriveAdapter extends ArrayAdapter<Drive> {

    private List<Drive> drives;
    private Context context;

    public DriveAdapter(Context context, List<Drive> drives) {
        super(context, R.layout.drive_item, drives);
        this.drives = drives;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.drive_item,null);

        Drive drive = drives.get(position);

        TextView driveDate = (TextView) convertView.findViewById(R.id.drive_item_date);
        PredicateLayout drivePanel = (PredicateLayout) convertView.findViewById(R.id.drive_panel_container);
        TextView driveDesc = (TextView) convertView.findViewById(R.id.drive_item_desc);
        TextView driveName = (TextView) convertView.findViewById(R.id.drive_item_name);
        TextView driveLocation = (TextView) convertView.findViewById(R.id.drive_item_location);

        driveName.setText(drive.getName() +" ("+new Technology().getTechnology(drive.getTechId())+")");
        driveDate.setText(drive.getDriveDate());
        driveDesc.setText(drive.getDesc());
        driveLocation.setText(drive.getLocation());
        List<Panel> panelList = new DBHelper(context).getPanelMembersForDrive(drive.getId());
        drivePanel.removeAllViews();
        for (Panel panel : panelList) {
                TextView name = (TextView) LayoutInflater.from(context).inflate(R.layout.panel_item, null);
                name.setText(panel.getName());
                drivePanel.addView(name);
        }
        return convertView;
    }
}
