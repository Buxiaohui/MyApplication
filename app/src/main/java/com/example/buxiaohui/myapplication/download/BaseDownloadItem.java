package com.example.buxiaohui.myapplication.download;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public class BaseDownloadItem {
    private int operationType;
    private String fileName;
    private long fileLength;
    private long currentProgress;
    private long breakProgress;
    private String filePath;
    private String fileSourceUrl;
    private int state;

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
    }

    public long getBreakProgress() {
        return breakProgress;
    }

    public void setBreakProgress(long breakProgress) {
        this.breakProgress = breakProgress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSourceUrl() {
        return fileSourceUrl;
    }

    public void setFileSourceUrl(String fileSourceUrl) {
        this.fileSourceUrl = fileSourceUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
