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

    /*�������Ҫ��ѯ������: ��������*/
    private String taskTitle;
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
    public String getTaskTitle() {
        return this.taskTitle;
    }

    /*�������Ҫ��ѯ������: ������˾*/
    private Company companyObj;
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }
    public Company getCompanyObj() {
        return this.companyObj;
    }

    /*�������Ҫ��ѯ������: �˵�����*/
    private String waybill;
    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }
    public String getWaybill() {
        return this.waybill;
    }

    /*�������Ҫ��ѯ������: �ջ���*/
    private String receiverName;
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getReceiverName() {
        return this.receiverName;
    }

    /*�������Ҫ��ѯ������: �ջ��绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: �ʹ��ַ*/
    private String takePlace;
    public void setTakePlace(String takePlace) {
        this.takePlace = takePlace;
    }
    public String getTakePlace() {
        return this.takePlace;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private String takeStateObj;
    public void setTakeStateObj(String takeStateObj) {
        this.takeStateObj = takeStateObj;
    }
    public String getTakeStateObj() {
        return this.takeStateObj;
    }

    /*�������Ҫ��ѯ������: ���񷢲���*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource ExpressTakeDAO expressTakeDAO;

    /*��������ExpressTake����*/
    private ExpressTake expressTake;
    public void setExpressTake(ExpressTake expressTake) {
        this.expressTake = expressTake;
    }
    public ExpressTake getExpressTake() {
        return this.expressTake;
    }

    /*��ת�����ExpressTake��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Company��Ϣ*/
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���ExpressTake��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(expressTake.getCompanyObj().getCompanyId());
            expressTake.setCompanyObj(companyObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(expressTake.getUserObj().getUser_name());
            expressTake.setUserObj(userObj);
            expressTakeDAO.AddExpressTake(expressTake);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTake��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTake���ʧ��!"));
            return "error";
        }
    }

    /*��ѯExpressTake��Ϣ*/
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
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        expressTakeDAO.CalculateTotalPageAndRecordNumber(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = expressTakeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
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
        String title = "ExpressTake��Ϣ��¼"; 
        String[] headers = { "��������","������˾","�˵�����","�ջ���","�ջ��绰","�ջ���ע","�ʹ��ַ","���ñ���","����״̬","���񷢲���","����ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ExpressTake.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯExpressTake��Ϣ*/
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
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        expressTakeDAO.CalculateTotalPageAndRecordNumber(taskTitle, companyObj, waybill, receiverName, telephone, takePlace, takeStateObj, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = expressTakeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�ExpressTake��Ϣ*/
    public String ModifyExpressTakeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡExpressTake����*/
        ExpressTake expressTake = expressTakeDAO.GetExpressTakeByOrderId(orderId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("expressTake",  expressTake);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ExpressTake��Ϣ*/
    public String FrontShowExpressTakeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡExpressTake����*/
        ExpressTake expressTake = expressTakeDAO.GetExpressTakeByOrderId(orderId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("expressTake",  expressTake);
        return "front_show_view";
    }

    /*�����޸�ExpressTake��Ϣ*/
    public String ModifyExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(expressTake.getCompanyObj().getCompanyId());
            expressTake.setCompanyObj(companyObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(expressTake.getUserObj().getUser_name());
            expressTake.setUserObj(userObj);
            expressTakeDAO.UpdateExpressTake(expressTake);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTake��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTake��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ExpressTake��Ϣ*/
    public String DeleteExpressTake() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            expressTakeDAO.DeleteExpressTake(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("ExpressTakeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ExpressTakeɾ��ʧ��!"));
            return "error";
        }
    }

}
