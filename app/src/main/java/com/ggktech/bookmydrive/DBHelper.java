package com.ggktech.bookmydrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajan.kali on 11/15/2016.
 * Sqlite Helper
 */

class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AppStorage";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDriveTable(sqLiteDatabase);
        createPanelTable(sqLiteDatabase);
        createDrivePanelTable(sqLiteDatabase);
        createCommentTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropDriveTable(sqLiteDatabase);
        dropDrivePanelTable(sqLiteDatabase);
        dropPanelTable(sqLiteDatabase);
        dropCommentTable(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private void createDriveTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Drive(id integer primary key autoincrement,name text,location text,tech_id int,status_id int,drive_date text,desc text)");
    }

    private void createPanelTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Panel(id integer primary key autoincrement,name text)");
    }

    private void createDrivePanelTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DrivePanel(drive_id int,panel_id int)");
    }

    private void createCommentTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Comment(drive_id int,name text,comment text)");
    }

    private void dropDriveTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE Drive");
    }
    private void dropCommentTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE Comment");
    }
    private void dropDrivePanelTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE DrivePanel");
    }
    private void dropPanelTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE Panel");
    }

    long addDrive(Drive drive) {
        SQLiteDatabase writeDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", drive.getName());
        contentValues.put("location", drive.getLocation());
        contentValues.put("tech_id", drive.getTechId());
        contentValues.put("status_id", drive.getStatusId());
        contentValues.put("drive_date", drive.getDriveDate());
        contentValues.put("desc", drive.getDesc());
        return writeDb.insert("Drive", null, contentValues);
    }

    public List<Drive> getAllDrives() {
        List<Drive> drives = new ArrayList<>();
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM Drive", null);
        if (c.moveToFirst()) {
            do {
                Drive drive = new Drive();
                drive.setId(c.getInt(c.getColumnIndex("id")));
                drive.setDesc(c.getString(c.getColumnIndex("desc")));
                drive.setDriveDate(c.getString(c.getColumnIndex("drive_date")));
                drive.setLocation(c.getString(c.getColumnIndex("location")));
                drive.setName(c.getString(c.getColumnIndex("name")));
                drive.setStatusId(c.getInt(c.getColumnIndex("status_id")));
                drive.setTechId(c.getInt(c.getColumnIndex("tech_id")));
                drives.add(drive);
            } while (c.moveToNext());
        }
        c.close();
        return drives;
    }

    long addPanelMember(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        return db.insert("Panel",null,values);
    }

    List<Panel> getAllPanelMembers(){
        List<Panel> panelList = new ArrayList<>();
        Cursor panelCursor = this.getReadableDatabase().rawQuery("SELECT * FROM Panel",null);
        if(panelCursor.moveToFirst()){
            do{
                Panel panel = new Panel(panelCursor.getString(panelCursor.getColumnIndex("name")));
                panel.setId(panelCursor.getInt(panelCursor.getColumnIndex("id")));
                panelList.add(panel);
            }while (panelCursor.moveToNext());
        }
        panelCursor.close();
        return panelList;
    }

    public List<Comment> getCommentsForDrive(int driveId){
        List<Comment> commentList = new ArrayList<>();
        String query = "SELECT * FROM Comment WHERE drive_id = "+ driveId;
        Cursor c = this.getReadableDatabase().rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                Comment comment = new Comment();
                comment.setName(c.getString(c.getColumnIndex("name")));
                comment.setDriverId(c.getInt(c.getColumnIndex("drive_id")));
                comment.setComment(c.getString(c.getColumnIndex("comment")));
                commentList.add(comment);
            }while (c.moveToNext());
        }
        c.close();
        return commentList;
    }

    public void AddCommentToDrive(int drive_id,String name, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("comment",comment);
        values.put("drive_id",drive_id);
        db.insert("Comment",null,values);
    }

    public List<Panel> getPanelMembersForDrive(int driveId){
        List<Panel> drivePanelList = new ArrayList<>();
        String query = "SELECT * FROM Panel p INNER JOIN DrivePanel dp ON p.id == dp.panel_id WHERE dp.drive_id = "+ driveId;
        Cursor c = this.getReadableDatabase().rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                Panel panel = new Panel(c.getString(c.getColumnIndex("name")));
                panel.setId(c.getInt(c.getColumnIndex("id")));
                drivePanelList.add(panel);
            }while (c.moveToNext());
        }
        c.close();
        return drivePanelList;
    }

    public void assignPanelMemberToDrive(int pId,int dId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("panel_id",pId);
        values.put("drive_id",dId);
        db.insert("DrivePanel",null,values);
    }
}
