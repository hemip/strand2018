package com.teraim.strand.utils;

import android.os.Environment;

public class Constants {
    //SFTP Settings
    public final static  String LOCAL_DIR = Environment.getExternalStorageDirectory() + "/" + "exported/";
    public final static  String SFTP_REMOTE_DIR ="export/";
    public final static  String SFTP_PASSWORD ="Landskap2018@Faltnventering";
    public final static  String SFTP_HOST ="10.8.0.1";
    public final static  String SFTP_USER ="nils99";
    public final static  int SFTP_PORT =2211;
}
