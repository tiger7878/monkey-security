package com.monkey.dto;

/**
 * @author: monkey
 * @date: 2018/10/6 17:26
 */
public class FileInfo {

    private String path;

    public FileInfo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
