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
import com.chengxusheji.dao.ExpressTakeDAO;
import com.chengxusheji.domain.ExpressTake;
import com.chengxusheji.dao.CompanyDAO;
import com.chengxusheji.domain.Company;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ExpressTakeAction extends BaseAction {

    /*界面层需要查询的属性: 代拿任务*/
    private String taskTitle;
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
    public String getTaskTitle() {
        return this.taskTitle;
    }

    /*界面层需要查询的属性: 物流公司*/
    private Company companyObj;
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }
    public Company getCompanyObj() {
        return this.companyObj;
    }

    /*界面层需要查询的属性: 运单号码*/
    private String waybill;
    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }
    public String getWaybill() {
        return this.waybill;
    }

    /*界面层需要查询的属性: 收货人*/
    private String receiverName;
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getReceiverName() {
        return this.receiverName;
    }

    /*界面层需要查询的属性: 收货电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 送达地址*/
    private String takePlace;
    public void setTakePlace(String takePlace) {
        this.takePlace = takePlace;
    }
    public String getTakePlace() {
        return this.takePlace;
    }

    /*界面层需要查询的属性: 代拿状态*/
    private String takeStateObj;
    public void setTakeStateObj(String takeStateObj) {
        this.takeStateObj = takeStateObj;
    }
    public String getTakeStateObj() {
        return this.takeStateObj;
    }

    /*界面层需要查询的属性: 任务发布人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource ExpressTakeDAO expressTakeDAO;

    /*待操作的ExpressTake对象*/
    private ExpressTake expressTake;
    public void setExpressTake(ExpressTake expressTake) {
        this.expressTake = expressTake;
    }
    public ExpressTake getExpressTake() {
        return this.expressTake;
    }

    /*跳转到添加ExpressTake视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Company信息*/
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加ExpressTake信息*/
    @SuppressWarnings("deprecation")
    public String AddExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(expressTake.getCompanyObj().getCompanyId());
            expressTake.setCompanyObj(companyObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(expressTake.getUserObj().getUser_name());
            expressTake.setUserObj(userObj);
            expressTakeDAO.AddExpressTake(expressTake);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTake添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTake添加失败!"));
            return "error";
        }
    }

    /*查询ExpressTake信息*/
    public String QueryExpressTake() {
        if(currentPage == 0) currentPage = 1;
        if(taskTitle == null) taskTitle = "";
        if(waybill == null) waybill = "";
        if(receiverName == null) receiverName = "";
        if(telephone == null) telephone = "";
        if(takePlace == null) takePlace = "";
        if(takeStateObj == null) takeStateObj = "";
        if(addTime == null) addTime = "";
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryExpressTakeInfo(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        expressTakeDAO.CalculateTotalPageAndRecordNumber(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime);
        /*获取到总的页码数目*/
        totalPage = expressTakeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = expressTakeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("expressTakeList",  expressTakeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("taskTitle", taskTitle);
        ctx.put("companyObj", companyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("waybill", waybill);
        ctx.put("receiverName", receiverName);
        ctx.put("telephone", telephone);
        ctx.put("takePlace", takePlace);
        ctx.put("takeStateObj", takeStateObj);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryExpressTakeOutputToExcel() { 
        if(taskTitle == null) taskTitle = "";
        if(waybill == null) waybill = "";
        if(receiverName == null) receiverName = "";
        if(telephone == null) telephone = "";
        if(takePlace == null) takePlace = "";
        if(takeStateObj == null) takeStateObj = "";
        if(addTime == null) addTime = "";
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryExpressTakeInfo(taskTitle,companyObj,waybill,receiverName,telephone,takePlace,takeStateObj,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ExpressTake信息记录"; 
        String[] headers = { "代拿任务","物流公司","运单号码","收货人","收货电话","收货备注","送达地址","代拿报酬","代拿状态","任务发布人","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<expressTakeList.size();i++) {
        	ExpressTake expressTake = expressTakeList.get(i); 
        	dataset.add(new String[]{expressTake.getTaskTitle(),expressTake.getCompanyObj().getCompanyName(),
expressTake.getWaybill(),expressTake.getReceiverName(),expressTake.getTelephone(),expressTake.getReceiveMemo(),expressTake.getTakePlace(),expressTake.getGiveMoney() + "",expressTake.getTakeStateObj(),expressTake.getUserObj().getName(),
expressTake.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ExpressTake.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ExpressTake信息*/
    public String FrontQueryExpressTake() {
        if(currentPage == 0) currentPage = 1;
        if(taskTitle == null) taskTitle = "";
        if(waybill == null) waybill = "";
        if(receiverName == null) receiverName = "";
        if(telephone == null) telephone = "";
        if(takePlace == null) takePlace = "";
        if(takeStateObj == null) takeStateObj = "";
        if(addTime == null) addTime = "";
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryExpressTakeInfo(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        expressTakeDAO.CalculateTotalPageAndRecordNumber(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime);
        /*获取到总的页码数目*/
        totalPage = expressTakeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = expressTakeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("expressTakeList",  expressTakeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("taskTitle", taskTitle);
        ctx.put("companyObj", companyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("waybill", waybill);
        ctx.put("receiverName", receiverName);
        ctx.put("telephone", telephone);
        ctx.put("takePlace", takePlace);
        ctx.put("takeStateObj", takeStateObj);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*查询要修改的ExpressTake信息*/
    public String ModifyExpressTakeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取ExpressTake对象*/
        ExpressTake expressTake = expressTakeDAO.GetExpressTakeByOrderId(orderId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("expressTake",  expressTake);
        return "modify_view";
    }

    /*查询要修改的ExpressTake信息*/
    public String FrontShowExpressTakeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取ExpressTake对象*/
        ExpressTake expressTake = expressTakeDAO.GetExpressTakeByOrderId(orderId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("expressTake",  expressTake);
        return "front_show_view";
    }

    /*更新修改ExpressTake信息*/
    public String ModifyExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(expressTake.getCompanyObj().getCompanyId());
            expressTake.setCompanyObj(companyObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(expressTake.getUserObj().getUser_name());
            expressTake.setUserObj(userObj);
            expressTakeDAO.UpdateExpressTake(expressTake);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTake信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTake信息更新失败!"));
            return "error";
       }
   }

    /*删除ExpressTake信息*/
    public String DeleteExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            expressTakeDAO.DeleteExpressTake(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTake删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTake删除失败!"));
            return "error";
        }
    }

}
