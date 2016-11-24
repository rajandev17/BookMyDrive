package com.ggktech.bookmydrive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddPanelActivity extends AppCompatActivity {

    private EditText memberToAdd;
    private Button addBtn;
    private DBHelper dbHelper;
    private List<Panel> panelMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_panel);
        getSupportActionBar().setTitle("Drive History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);
        panelMembers = dbHelper.getAllPanelMembers();
        memberToAdd = (EditText) findViewById(R.id.panel_user_name);
        addBtn = (Button) findViewById(R.id.btn_add_panel_user);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!memberToAdd.getText().toString().isEmpty() && memberToAdd.getText().toString().length() > 6){
                    boolean alreadyExists = false;
                    for(Panel panel : panelMembers){
                        if(panel.getName().toLowerCase().contains(memberToAdd.getText().toString().toLowerCase())){
                           alreadyExists = true;
                        }
                    }
                    if(!alreadyExists){
                        int id = (int) dbHelper.addPanelMember(memberToAdd.getText().toString());
                        panelMembers.add(new Panel(id,memberToAdd.getText().toString()));
                        Toast.makeText(AddPanelActivity.this, "Panel User Added Successfully", Toast.LENGTH_SHORT).show();
                        memberToAdd.setText("");
                    }else {
                        Toast.makeText(AddPanelActivity.this, "Panel User Already exists", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddPanelActivity.this, "Please provide valid name", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
