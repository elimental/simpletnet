package com.getjavajob.simplenet.common.entity;

public class Picture extends BaseEntity {
    private byte[] fileData;
    private int userId;

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
