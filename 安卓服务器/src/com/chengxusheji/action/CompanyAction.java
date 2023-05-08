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

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource CompanyDAO companyDAO;

    /*待操作的Company对象*/
    private Company company;
    public void setCompany(Company company) {
        this.company = company;
    }
    public Company getCompany() {
        return this.company;
    }

    /*跳转到添加Company视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Company信息*/
    @SuppressWarnings("deprecation")
    public String AddCompany() {
        ActionContext ctx = ActionContext.getContext();
        try {
            companyDAO.AddCompany(company);
            ctx.put("message",  java.net.URLEncoder.encode("Company添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Company添加失败!"));
            return "error";
        }
    }

    /*查询Company信息*/
    public String QueryCompany() {
        if(currentPage == 0) currentPage = 1;
        List<Company> companyList = companyDAO.QueryCompanyInfo(currentPage);
        /*计算总的页数和总的记录数*/
        companyDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = companyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = companyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("companyList",  companyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCompanyOutputToExcel() { 
        List<Company> companyList = companyDAO.QueryCompanyInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Company信息记录"; 
        String[] headers = { "公司id","公司名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Company.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Company信息*/
    public String FrontQueryCompany() {
        if(currentPage == 0) currentPage = 1;
        List<Company> companyList = companyDAO.QueryCompanyInfo(currentPage);
        /*计算总的页数和总的记录数*/
        companyDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = companyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = companyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("companyList",  companyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的Company信息*/
    public String ModifyCompanyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键companyId获取Company对象*/
        Company company = companyDAO.GetCompanyByCompanyId(companyId);

        ctx.put("company",  company);
        return "modify_view";
    }

    /*查询要修改的Company信息*/
    public String FrontShowCompanyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键companyId获取Company对象*/
        Company company = companyDAO.GetCompanyByCompanyId(companyId);

        ctx.put("company",  company);
        return "front_show_view";
    }

    /*更新修改Company信息*/
    public String ModifyCompany() {
        ActionContext ctx = ActionContext.getContext();
        try {
            companyDAO.UpdateCompany(company);
            ctx.put("message",  java.net.URLEncoder.encode("Company信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Company信息更新失败!"));
            return "error";
       }
   }

    /*删除Company信息*/
    public String DeleteCompany() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            companyDAO.DeleteCompany(companyId);
            ctx.put("message",  java.net.URLEncoder.encode("Company删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Company删除失败!"));
            return "error";
        }
    }

}
