package com.mobileserver.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.DiskFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;

/**
 * Servlet implementation class UpPhotoServlet
 */
public class UpPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpPhotoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loadpath=this.getServletConfig().getServletContext().getRealPath("/")+"upload";
		String temp = this.getServletConfig().getServletContext().getRealPath("/")+"upload/temp"; // ��ʱĿ¼
		System.out.println("temp=" + temp);
		System.out.println("loadpath=" + loadpath);
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(6 * 1024 * 1024); // ���������û��ϴ��ļ���С,��λ:�ֽ�
		fu.setSizeThreshold(2048); // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
		fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
 
		// ��ʼ��ȡ�ϴ���Ϣ
		int index = 0;
		List fileItems = null;

		try {
			fileItems = fu.parseRequest(request);
			System.out.println("fileItems=" + fileItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String filename = "";

		Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// �������������ļ�������б���Ϣ
			if (!item.isFormField()) {
			    filename = item.getName();// ��ȡ�ϴ��ļ���,����·��
			    System.out.println("@@@@@@@@@@@@@@@@@@@@@ "+filename); 
				filename = filename.substring(filename.lastIndexOf("\\") + 1);// ��ȫ·������ȡ�ļ���
				long size = item.getSize();
				if ((filename == null || filename.equals("")) && size == 0)
					continue;
				int point = filename.indexOf(".");
//				name = (new Date()).getTime()
//						+ name.substring(point, name.length());
				index++; 
				File file = new File(loadpath);
				if(!file.exists()){
					System.out.println("==========================================");
					file.mkdirs();
				}
				Date date = new Date();
				String timeString =  "" + date.getYear() + date.getMonth() + date.getDay() + date.getHours() + date.getMinutes() + date.getSeconds();
				filename = timeString + filename;
				File fNew = new File(loadpath, filename);
				try {
					item.write(fNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} else// ȡ�������ļ�������б���Ϣ
			{
				String fieldvalue = item.getString();
				// �����������ӦдΪ��(תΪUTF-8����)
				// String fieldvalue = new
				// String(item.getString().getBytes(),"UTF-8");
			}
		}
		
		String newUrl = "upload/" + filename;
		 
		PrintWriter out = response.getWriter();
		 
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%  "+ filename);
		
		out.print(newUrl);

	}

}
