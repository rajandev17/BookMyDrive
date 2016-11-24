package com.ggktech.bookmydrive;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateDriveActivity extends AppCompatActivity {

    private EditText driveName;
    private EditText driveDate;
    private EditText driveLocation;
    private EditText driveDesc;
    private Spinner driveTechnology;
    private PredicateLayout drivePanel;
    private Button driveCreate;
    private ImageButton addPanelMember;
    SparseBooleanArray selectedPanelMembers = new SparseBooleanArray();
    List<Panel> panelMembers = new ArrayList<>();
    List<String> panelNames = new ArrayList<>();
    DBHelper dbHelper;
    AlertDialog dialog;
    private TextView noPanel;
    private String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drive);
        getSupportActionBar().setTitle("Schedule Drive");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        driveDesc = (EditText) findViewById(R.id.drive_desc);
        driveDate = (EditText) findViewById(R.id.drive_date);
        driveName = (EditText) findViewById(R.id.drive_name);
        driveLocation = (EditText) findViewById(R.id.drive_location);
        driveTechnology = (Spinner) findViewById(R.id.tech_spinner);
        drivePanel = (PredicateLayout) findViewById(R.id.panel_container);
        driveCreate = (Button) findViewById(R.id.btn_create_drive);
        addPanelMember = (ImageButton) findViewById(R.id.add_Panel_member);
        dbHelper = new DBHelper(CreateDriveActivity.this);
        panelMembers = dbHelper.getAllPanelMembers();
        noPanel = new TextView(this);
        noPanel.setText("Click on edit to modify panel members");
        int i = 0;
        for (Panel panel : panelMembers) {
            panelNames.add(panel.getName());
            selectedPanelMembers.put(i++, false);
        }
        if(drivePanel.getChildCount() == 0){
            drivePanel.addView(noPanel);
        }
        populateInitialData();
        addListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addListeners() {
        driveCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateSuccess()) {
                    if(drivePanel.getChildCount() >= 2) {
                        Drive drive = new Drive();
                        drive.setStatusId(Status.CREATED);
                        drive.setTechId(driveTechnology.getSelectedItemPosition());
                        drive.setName(driveName.getText().toString());
                        drive.setLocation(driveLocation.getText().toString());
                        drive.setDesc(driveDesc.getText().toString());
                        drive.setDriveDate(driveDate.getText().toString());
                        int driverId = (int) dbHelper.addDrive(drive);
                        for (int i = 0; i < selectedPanelMembers.size(); i++) {
                            if (selectedPanelMembers.get(i)) {
                                dbHelper.assignPanelMemberToDrive(panelMembers.get(i).getId(), driverId);
                            }
                        }
                        Toast.makeText(CreateDriveActivity.this, "Successfully Created Drive", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(CreateDriveActivity.this, "Add at least two panel members", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CreateDriveActivity.this, "Please provide all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        driveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar instance= Calendar.getInstance();
                new DatePickerDialog(CreateDriveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date = datePicker.getDayOfMonth() +"-"+datePicker.getMonth()+"-"+datePicker.getYear();
                        driveDate.setText(date);
                    }
                },instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addPanelMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Copy current members
                final SparseBooleanArray cachedPanelMembers = new SparseBooleanArray();
                for (int i = 0; i < selectedPanelMembers.size(); i++) {
                    cachedPanelMembers.put(i,selectedPanelMembers.get(i));
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(CreateDriveActivity.this);
                final ListView panelList = new ListView(CreateDriveActivity.this);
                Button btnDone = new Button(CreateDriveActivity.this);
                btnDone.setTransformationMethod(null);
                btnDone.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                if(panelNames.size() > 0) {
                    btnDone.setText("Update Panel");
                }else {
                    btnDone.setText("No Panel members Found.");
                }

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < cachedPanelMembers.size(); i++) {
                            selectedPanelMembers.put(i,cachedPanelMembers.get(i));
                        }
                        updatePanel();
                        dialog.dismiss();
                    }
                });
                final ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(CreateDriveActivity.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, panelNames);
                panelList.addFooterView(btnDone);
                panelList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                panelList.setAdapter(modeAdapter);
                for (int i = 0; i < selectedPanelMembers.size(); i++) {
                    panelList.setItemChecked(i, selectedPanelMembers.get(i));
                }
                panelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        boolean prevValue = cachedPanelMembers.get(i);
                        cachedPanelMembers.put(i, !prevValue);
                    }
                });
                builder.setView(panelList);
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void updatePanel(){
        drivePanel.removeAllViews();
        for (int i = 0; i < selectedPanelMembers.size(); i++) {
            if (selectedPanelMembers.get(i)) {
                TextView name = (TextView) LayoutInflater.from(CreateDriveActivity.this).inflate(R.layout.panel_item, null);
                name.setText(panelNames.get(i));
                name.setTag(i);
                drivePanel.addView(name);
            }
        }
        if(drivePanel.getChildCount() == 0){
            drivePanel.addView(noPanel);
        }
    }

    private boolean validateSuccess() {
        return !driveName.getText().toString().isEmpty() && !driveLocation.getText().toString().isEmpty() && !driveDesc.getText().toString().isEmpty() && driveTechnology.getSelectedItemPosition() != 0 && date != null;
    }

    private void populateInitialData() {
        Technology technology = new Technology();
        driveTechnology.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, technology.getTechList()));
    }
}
