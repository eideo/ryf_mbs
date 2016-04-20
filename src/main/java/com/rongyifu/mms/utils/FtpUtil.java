package com.rongyifu.mms.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.directwebremoting.io.FileTransfer;

public class FtpUtil {
	public static class Ftp {  
	    private FTPClient ftpClient;  
	    private String strIp;  
	    private int intPort;  
	    private String user;  
	    private String password;  
	  
	   // private static Logger logger = Logger.getLogger(Ftp.class.getName());  
	  
	    /* * 
	     * Ftp构造函数 
	     */  
	    public Ftp(String strIp, int intPort, String user, String Password) {  
	        this.strIp = strIp;  
	        this.intPort = intPort;  
	        this.user = user;  
	        this.password = Password;  
	        this.ftpClient = new FTPClient();  
	    }  
	    /** 
	     * @return 判断是否登入成功 
	     * */  
	    public boolean ftpLogin() {  
	        boolean isLogin = false;  
	        FTPClientConfig ftpClientConfig = new FTPClientConfig();  
	        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());  
	        this.ftpClient.setControlEncoding("GBK");  
	        this.ftpClient.configure(ftpClientConfig);  
	        try {  
	            if (this.intPort > 0) {  
	                this.ftpClient.connect(this.strIp, this.intPort);  
	            } else {  
	                this.ftpClient.connect(this.strIp);  
	            }  
	            // FTP服务器连接回答  
	            int reply = this.ftpClient.getReplyCode();  
	            if (!FTPReply.isPositiveCompletion(reply)) {  
	                this.ftpClient.disconnect();  
	               // logger.error("登录FTP服务失败！");  
	                return isLogin;  
	            }  
	            this.ftpClient.login(this.user, this.password);  
	            // 设置传输协议  
	            this.ftpClient.enterLocalPassiveMode();  
	            this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	           // logger.info("恭喜" + this.user + "成功登陆FTP服务器");  
	            isLogin = true;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            //logger.error(this.user + "登录FTP服务失败！" + e.getMessage());  
	        }  
	        this.ftpClient.setBufferSize(1024 * 2);  
	        this.ftpClient.setDataTimeout(30 * 1000);  
	        return isLogin;  
	    } 
	    
		public FileTransfer downloadFile(String downFileName, String fileName,Ftp ftp) { 
			
	        ByteArrayOutputStream bytebuffer = new ByteArrayOutputStream();
	        try {  
	        	boolean bb= this.ftpClient.changeWorkingDirectory(downFileName);  
	        	InputStream is =this.ftpClient.retrieveFileStream(fileName);
	        	   int buff=0;
				   byte[] b = new byte[1024]; 
					while((buff=is.read(b))!=-1){
						  bytebuffer.write(b, 0, buff);
					}
				   is.close();
				   ftp.ftpLogOut();
				   return new FileTransfer(fileName,"application/vnd.ms-excel",bytebuffer.toByteArray());

	        } catch (Exception e) {  
	            e.printStackTrace();  
	        
	        }  
	        
	        return null;  
	    } 
	  
	    /** 
	     * @退出关闭服务器链接 
	     * */  
	    public void ftpLogOut() {  
	        if (null != this.ftpClient && this.ftpClient.isConnected()) {  
	            try {  
	                boolean reuslt = this.ftpClient.logout();// 退出FTP服务器  
	                if (reuslt) {  
	                   // logger.info("成功退出服务器");  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	                //logger.warn("退出FTP服务器异常！" + e.getMessage());  
	            } finally {  
	                try {  
	                    this.ftpClient.disconnect();// 关闭FTP服务器的连接  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                    //logger.warn("关闭FTP服务器的连接异常！");  
	                }  
	            }  
	        }  
	    }  
	  
	   
	  
	    // FtpClient的Set 和 Get 函数  
	    public FTPClient getFtpClient() {  
	        return ftpClient;  
	    }  
	    public void setFtpClient(FTPClient ftpClient) {  
	        this.ftpClient = ftpClient;  
	    }  
	      
	    public static void main(String[] args)  {  
	        Ftp ftp=new Ftp("192.168.30.229",21,"pos","pos123");  
	        ftp.ftpLogin();  
	        
	        //上传文件夹  
	       // ftp.uploadDirectory("d://DataProtemp", "/home/data/");  
	        //下载文件夹  
	       // ftp.downLoadDirectory("d://tmp//", "/home/data/DataProtemp");  
	        //ftp.downloadFile("20160314164050_TradeData.xls", "d://test/", "/shht/764/20160314/");
	        ftp.ftpLogOut();          
	    }  
	} 
	


}
