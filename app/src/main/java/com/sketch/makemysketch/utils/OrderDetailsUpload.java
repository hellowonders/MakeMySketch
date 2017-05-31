package com.sketch.makemysketch.utils;

import android.os.StrictMode;

import com.sketch.makemysketch.model.OrderDetail;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OrderDetailsUpload {
    public static boolean ftpUpload(OrderDetail orderDetail) {

        String imageUri = (String) orderDetail.getImageUri();
        String orderId = (String) orderDetail.getOrderId();
        String imageName = (String) orderDetail.getImageName();

        String server = "files.000webhost.com";
        String user = "makemysketch";
        String pass = "pallaviartistry";

        FTPClient ftpClient = new FTPClient();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ftpClient.connect(server);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(imageUri);
            String remoteFile = orderId +"_"+ imageName ;
            InputStream inputStream = new FileInputStream(localFile);

            boolean done = ftpClient.storeFile("/public_html/sketch/" + remoteFile, inputStream);
            inputStream.close();
            if (done) {
               return true;
            }

        } catch (IOException ioException) {
            return false;
        }
        return false;
    }
}
