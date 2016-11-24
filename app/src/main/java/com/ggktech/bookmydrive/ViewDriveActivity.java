package com.ggktech.bookmydrive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ViewDriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drive);
        getSupportActionBar().setTitle("Drive History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        ListView driveListView = (ListView) findViewById(R.id.drive_list_view);
        TextView noDrive = (TextView) findViewById(R.id.drive_not_found_text);
        List<Drive> drives = new DBHelper(this).getAllDrives();
        if(drives.size() > 0) {
            driveListView.setAdapter(new DriveAdapter(this, drives));
            driveListView.setVisibility(View.VISIBLE);
            noDrive.setVisibility(View.GONE);
        }else {
            driveListView.setVisibility(View.GONE);
            noDrive.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
