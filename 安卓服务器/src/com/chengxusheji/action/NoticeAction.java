package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.NoticeDAO;
import com.chengxusheji.domain.Notice;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NoticeAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int noticeId;
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
    public int getNoticeId() {
        return noticeId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource NoticeDAO noticeDAO;

    /*��������Notice����*/
    private Notice notice;
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
    public Notice getNotice() {
        return this.notice;
    }

    /*��ת�����Notice��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Notice��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.AddNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNotice��Ϣ*/
    public String QueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noticeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = noticeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noticeList",  noticeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNoticeOutputToExcel() { 
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Notice��Ϣ��¼"; 
        String[] headers = { "����id","����","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<noticeList.size();i++) {
        	Notice notice = noticeList.get(i); 
        	dataset.add(new String[]{notice.getNoticeId() + "",notice.getTitle(),notice.getPublishDate()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Notice.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯNotice��Ϣ*/
    public String FrontQueryNotice() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Notice> noticeList = noticeDAO.QueryNoticeInfo(title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        noticeDAO.CalculateTotalPageAndRecordNumber(title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = noticeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = noticeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("noticeList",  noticeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Notice��Ϣ*/
    public String ModifyNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noticeId��ȡNotice����*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Notice��Ϣ*/
    public String FrontShowNoticeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������noticeId��ȡNotice����*/
        Notice notice = noticeDAO.GetNoticeByNoticeId(noticeId);

        ctx.put("notice",  notice);
        return "front_show_view";
    }

    /*�����޸�Notice��Ϣ*/
    public String ModifyNotice() {
        ActionContext ctx = ActionContext.getContext();
        try {
            noticeDAO.UpdateNotice(notice);
            ctx.put("message",  java.net.URLEncoder.encode("Notice��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Notice��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Notice��Ϣ*/
    public String DeleteNotice() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            noticeDAO.DeleteNotice(noticeId);
            ctx.put("message",  java.net.URLEncoder.encode("Noticeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Noticeɾ��ʧ��!"));
            return "error";
        }
    }

}
