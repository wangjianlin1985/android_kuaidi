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
import com.chengxusheji.dao.TakeOrderDAO;
import com.chengxusheji.domain.TakeOrder;
import com.chengxusheji.dao.ExpressTakeDAO;
import com.chengxusheji.domain.ExpressTake;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.OrderStateDAO;
import com.chengxusheji.domain.OrderState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TakeOrderAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���õĿ��*/
    private ExpressTake expressTakeObj;
    public void setExpressTakeObj(ExpressTake expressTakeObj) {
        this.expressTakeObj = expressTakeObj;
    }
    public ExpressTake getExpressTakeObj() {
        return this.expressTakeObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ӵ�ʱ��*/
    private String takeTime;
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }
    public String getTakeTime() {
        return this.takeTime;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
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
    @Resource ExpressTakeDAO expressTakeDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource OrderStateDAO orderStateDAO;
    @Resource TakeOrderDAO takeOrderDAO;

    /*��������TakeOrder����*/
    private TakeOrder takeOrder;
    public void setTakeOrder(TakeOrder takeOrder) {
        this.takeOrder = takeOrder;
    }
    public TakeOrder getTakeOrder() {
        return this.takeOrder;
    }

    /*��ת�����TakeOrder��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ExpressTake��Ϣ*/
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�OrderState��Ϣ*/
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*���TakeOrder��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTakeOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ExpressTake expressTakeObj = expressTakeDAO.GetExpressTakeByOrderId(takeOrder.getExpressTakeObj().getOrderId());
            takeOrder.setExpressTakeObj(expressTakeObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(takeOrder.getUserObj().getUser_name());
            takeOrder.setUserObj(userObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByOrderStateId(takeOrder.getOrderStateObj().getOrderStateId());
            takeOrder.setOrderStateObj(orderStateObj);
            takeOrderDAO.AddTakeOrder(takeOrder);
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrder��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrder���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTakeOrder��Ϣ*/
    public String QueryTakeOrder() {
        if(currentPage == 0) currentPage = 1;
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj, userObj, takeTime, orderStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        takeOrderDAO.CalculateTotalPageAndRecordNumber(expressTakeObj, userObj, takeTime, orderStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = takeOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = takeOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("takeOrderList",  takeOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("expressTakeObj", expressTakeObj);
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("takeTime", takeTime);
        ctx.put("orderStateObj", orderStateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTakeOrderOutputToExcel() { 
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj,userObj,takeTime,orderStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TakeOrder��Ϣ��¼"; 
        String[] headers = { "����id","���õĿ��","��������","�ӵ�ʱ��","����״̬","ʵʱ��̬","�û�����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<takeOrderList.size();i++) {
        	TakeOrder takeOrder = takeOrderList.get(i); 
        	dataset.add(new String[]{takeOrder.getOrderId() + "",takeOrder.getExpressTakeObj().getTaskTitle(),
takeOrder.getUserObj().getName(),
takeOrder.getTakeTime(),takeOrder.getOrderStateObj().getOrderStateName(),
takeOrder.getSsdt(),takeOrder.getEvaluate()});
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
			response.setHeader("Content-disposition","attachment; filename="+"TakeOrder.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTakeOrder��Ϣ*/
    public String FrontQueryTakeOrder() {
        if(currentPage == 0) currentPage = 1;
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj, userObj, takeTime, orderStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        takeOrderDAO.CalculateTotalPageAndRecordNumber(expressTakeObj, userObj, takeTime, orderStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = takeOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = takeOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("takeOrderList",  takeOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("expressTakeObj", expressTakeObj);
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("takeTime", takeTime);
        ctx.put("orderStateObj", orderStateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�TakeOrder��Ϣ*/
    public String ModifyTakeOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡTakeOrder����*/
        TakeOrder takeOrder = takeOrderDAO.GetTakeOrderByOrderId(orderId);

        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("takeOrder",  takeOrder);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�TakeOrder��Ϣ*/
    public String FrontShowTakeOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡTakeOrder����*/
        TakeOrder takeOrder = takeOrderDAO.GetTakeOrderByOrderId(orderId);

        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("takeOrder",  takeOrder);
        return "front_show_view";
    }

    /*�����޸�TakeOrder��Ϣ*/
    public String ModifyTakeOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ExpressTake expressTakeObj = expressTakeDAO.GetExpressTakeByOrderId(takeOrder.getExpressTakeObj().getOrderId());
            takeOrder.setExpressTakeObj(expressTakeObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(takeOrder.getUserObj().getUser_name());
            takeOrder.setUserObj(userObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByOrderStateId(takeOrder.getOrderStateObj().getOrderStateId());
            takeOrder.setOrderStateObj(orderStateObj);
            takeOrderDAO.UpdateTakeOrder(takeOrder);
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrder��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrder��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��TakeOrder��Ϣ*/
    public String DeleteTakeOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            takeOrderDAO.DeleteTakeOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrderɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrderɾ��ʧ��!"));
            return "error";
        }
    }

}
