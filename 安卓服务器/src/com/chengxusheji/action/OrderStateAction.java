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
import com.chengxusheji.dao.OrderStateDAO;
import com.chengxusheji.domain.OrderState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderStateAction extends BaseAction {

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

    private int orderStateId;
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }
    public int getOrderStateId() {
        return orderStateId;
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
    @Resource OrderStateDAO orderStateDAO;

    /*��������OrderState����*/
    private OrderState orderState;
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
    public OrderState getOrderState() {
        return this.orderState;
    }

    /*��ת�����OrderState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���OrderState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.AddOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderState��Ϣ*/
    public String QueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderStateOutputToExcel() { 
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderState��Ϣ��¼"; 
        String[] headers = { "����״̬id","����״̬����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderStateList.size();i++) {
        	OrderState orderState = orderStateList.get(i); 
        	dataset.add(new String[]{orderState.getOrderStateId() + "",orderState.getOrderStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯOrderState��Ϣ*/
    public String FrontQueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderState��Ϣ*/
    public String ModifyOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderStateId��ȡOrderState����*/
        OrderState orderState = orderStateDAO.GetOrderStateByOrderStateId(orderStateId);

        ctx.put("orderState",  orderState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderState��Ϣ*/
    public String FrontShowOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderStateId��ȡOrderState����*/
        OrderState orderState = orderStateDAO.GetOrderStateByOrderStateId(orderStateId);

        ctx.put("orderState",  orderState);
        return "front_show_view";
    }

    /*�����޸�OrderState��Ϣ*/
    public String ModifyOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.UpdateOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderState��Ϣ*/
    public String DeleteOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderStateDAO.DeleteOrderState(orderStateId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderStateɾ��ʧ��!"));
            return "error";
        }
    }

}
