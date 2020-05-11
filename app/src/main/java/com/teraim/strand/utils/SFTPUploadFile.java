package com.teraim.strand.utils;

public class SFTPUploadFile {
    public String localFilePath;
    public String fileName;
    public String remoteFolder;
    public SFTPUploadFile(String p,String f, String r){
        this.localFilePath=p;
        this.fileName =f;
        this.remoteFolder=r;
    }

}
