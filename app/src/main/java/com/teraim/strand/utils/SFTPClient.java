package com.teraim.strand.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.util.Vector;

public class SFTPClient {
    private JSch jsch;
    private Session session;
    private ChannelSftp sftpChannel;
    private boolean connected = false;

    public SFTPClient(){
        jsch = new JSch();
        session = null;
    }
    public void Open(final String SFTPuser) throws JSchException {
        session = jsch.getSession(SFTPuser, Constants.SFTP_HOST, Constants.SFTP_PORT);

        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(Constants.SFTP_PASSWORD);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        sftpChannel = (ChannelSftp) channel;
        connected=true;

    }
    public void Close(){
        connected=false;
        if(sftpChannel!=null)
            sftpChannel.exit();
        if(session!=null)
            session.disconnect();
        sftpChannel = null;
        session = null;
    }
    public int UpploadFile(final String local_file_path, final String filename, final String remote_folder, final boolean overwrite) throws JSchException, SftpException {
        int res = 0;
        if(!connected){
            return 0;
        }
        Boolean fileExits = false;
        //Check if a file with the same name allready exists.
        Vector<ChannelSftp.LsEntry> remoteFiles = sftpChannel.ls(remote_folder);
        for(ChannelSftp.LsEntry l : remoteFiles){
            if(l.getFilename().equals(filename)){
                fileExits =true;
                break;
            }
        }
        if(overwrite || !fileExits) {
            sftpChannel.put(local_file_path + "/" + filename, remote_folder + filename);
            res=1;
        }
        return res;
    }



    public static int UploadFileStatic(final String SFTPuser, final String local_file_path, final String filename, final String remote_folder, final boolean overwrite) throws JSchException, SftpException {
        int res = 0;
        Boolean fileExits = false;
        JSch jsch = new JSch();
        Session session = null;

        session = jsch.getSession(SFTPuser, Constants.SFTP_HOST, Constants.SFTP_PORT);

        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(Constants.SFTP_PASSWORD);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        //Check if a file with the same name allready exists.
        Vector<ChannelSftp.LsEntry> remoteFiles = sftpChannel.ls(remote_folder);
        for(ChannelSftp.LsEntry l : remoteFiles){
            if(l.getFilename().equals(filename)){
                fileExits =true;
                break;
            }
        }
        if(overwrite || !fileExits) {
            sftpChannel.put(local_file_path + "/" + filename, remote_folder + filename);

        }
        sftpChannel.exit();
        session.disconnect();
        res = 1;


        return res;
    }



}

