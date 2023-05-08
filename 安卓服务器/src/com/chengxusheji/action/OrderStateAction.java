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

    private int orderStateId;
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }
    public int getOrderStateId() {
        return orderStateId;
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
    @Resource OrderStateDAO orderStateDAO;

    /*待操作的OrderState对象*/
    private OrderState orderState;
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
    public OrderState getOrderState() {
        return this.orderState;
    }

    /*跳转到添加OrderState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加OrderState信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.AddOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState添加失败!"));
            return "error";
        }
    }

    /*查询OrderState信息*/
    public String QueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = orderStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryOrderStateOutputToExcel() { 
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderState信息记录"; 
        String[] headers = { "订单状态id","订单状态名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderState.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询OrderState信息*/
    public String FrontQueryOrderState() {
        if(currentPage == 0) currentPage = 1;
        List<OrderState> orderStateList = orderStateDAO.QueryOrderStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        orderStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = orderStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderStateList",  orderStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的OrderState信息*/
    public String ModifyOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderStateId获取OrderState对象*/
        OrderState orderState = orderStateDAO.GetOrderStateByOrderStateId(orderStateId);

        ctx.put("orderState",  orderState);
        return "modify_view";
    }

    /*查询要修改的OrderState信息*/
    public String FrontShowOrderStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderStateId获取OrderState对象*/
        OrderState orderState = orderStateDAO.GetOrderStateByOrderStateId(orderStateId);

        ctx.put("orderState",  orderState);
        return "front_show_view";
    }

    /*更新修改OrderState信息*/
    public String ModifyOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            orderStateDAO.UpdateOrderState(orderState);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderState信息*/
    public String DeleteOrderState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderStateDAO.DeleteOrderState(orderStateId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderState删除失败!"));
            return "error";
        }
    }

}
