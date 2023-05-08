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
import com.chengxusheji.dao.CompanyDAO;
import com.chengxusheji.domain.Company;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CompanyAction extends BaseAction {

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

    private int companyId;
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public int getCompanyId() {
        return companyId;
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
    @Resource CompanyDAO companyDAO;

    /*��������Company����*/
    private Company company;
    public void setCompany(Company company) {
        this.company = company;
    }
    public Company getCompany() {
        return this.company;
    }

    /*��ת�����Company��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Company��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCompany() {
        ActionContext ctx = ActionContext.getContext();
        try {
            companyDAO.AddCompany(company);
            ctx.put("message",  java.net.URLEncoder.encode("Company��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Company���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCompany��Ϣ*/
    public String QueryCompany() {
        if(currentPage == 0) currentPage = 1;
        List<Company> companyList = companyDAO.QueryCompanyInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        companyDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = companyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = companyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("companyList",  companyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCompanyOutputToExcel() { 
        List<Company> companyList = companyDAO.QueryCompanyInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Company��Ϣ��¼"; 
        String[] headers = { "��˾id","��˾����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<companyList.size();i++) {
        	Company company = companyList.get(i); 
        	dataset.add(new String[]{company.getCompanyId() + "",company.getCompanyName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Company.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCompany��Ϣ*/
    public String FrontQueryCompany() {
        if(currentPage == 0) currentPage = 1;
        List<Company> companyList = companyDAO.QueryCompanyInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        companyDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = companyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = companyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("companyList",  companyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Company��Ϣ*/
    public String ModifyCompanyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������companyId��ȡCompany����*/
        Company company = companyDAO.GetCompanyByCompanyId(companyId);

        ctx.put("company",  company);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Company��Ϣ*/
    public String FrontShowCompanyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������companyId��ȡCompany����*/
        Company company = companyDAO.GetCompanyByCompanyId(companyId);

        ctx.put("company",  company);
        return "front_show_view";
    }

    /*�����޸�Company��Ϣ*/
    public String ModifyCompany() {
        ActionContext ctx = ActionContext.getContext();
        try {
            companyDAO.UpdateCompany(company);
            ctx.put("message",  java.net.URLEncoder.encode("Company��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Company��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Company��Ϣ*/
    public String DeleteCompany() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            companyDAO.DeleteCompany(companyId);
            ctx.put("message",  java.net.URLEncoder.encode("Companyɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Companyɾ��ʧ��!"));
            return "error";
        }
    }

}
