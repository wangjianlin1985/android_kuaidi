package com.chengxusheji.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.chengxusheji.utils.FileTypeException;
import com.mobileserver.util.MD5Util;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends  ActionSupport{
	/*当前第几页*/
    protected int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    protected int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    protected int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }
    
    
    public String photoUpload(File inputFile,String fileContentType) throws Exception {
    	String fileName = "";
    	InputStream is = new FileInputStream(inputFile);
		if(fileContentType.equals("image/jpeg")  || fileContentType.equals("image/pjpeg"))
			fileName = MD5Util.getMD5String(UUID.randomUUID().toString()) +  ".jpg";
		else if(fileContentType.equals("image/gif"))
			fileName = MD5Util.getMD5String(UUID.randomUUID().toString()) +  ".gif";
		else {
			throw new FileTypeException("文件上传格式不对！");
		}
		String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
		File file = new File(path, fileName);
		OutputStream os = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int bs = 0;
		while ((bs = is.read(b)) > 0) {
			os.write(b, 0, bs);
		}
		is.close();
		os.close();
		return "upload/" + fileName;  
    }
    
    
    public String fileUpload(File inputFile,String orginalFileName) throws Exception {
    	String extName = orginalFileName.substring(orginalFileName.lastIndexOf("."));
    	InputStream is = new FileInputStream(inputFile);
		String fileName = MD5Util.getMD5String(UUID.randomUUID().toString()) + extName;
		String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
		File file = new File(path, fileName);
		OutputStream os = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int bs = 0;
		while ((bs = is.read(b)) > 0) {
			os.write(b, 0, bs);
		}
		is.close();
		os.close();
		return "upload/" + fileName;  
    }
}


