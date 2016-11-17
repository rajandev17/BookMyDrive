package com.ggktech.bookmydrive;

/**
 * Created by rajan.kali on 11/16/2016.
 */

public class DrivePanel {
    private int panelId;
    private int driveId;

    public DrivePanel(int driveId, int panelId) {
        this.driveId = driveId;
        this.panelId = panelId;
    }

    public int getDriveId() {
        return driveId;
    }

    public int getPanelId() {
        return panelId;
    }
}
