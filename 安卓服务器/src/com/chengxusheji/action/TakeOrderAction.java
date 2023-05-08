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

    /*界面层需要查询的属性: 代拿的快递*/
    private ExpressTake expressTakeObj;
    public void setExpressTakeObj(ExpressTake expressTakeObj) {
        this.expressTakeObj = expressTakeObj;
    }
    public ExpressTake getExpressTakeObj() {
        return this.expressTakeObj;
    }

    /*界面层需要查询的属性: 接任务人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 接单时间*/
    private String takeTime;
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }
    public String getTakeTime() {
        return this.takeTime;
    }

    /*界面层需要查询的属性: 订单状态*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
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
    @Resource ExpressTakeDAO expressTakeDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource OrderStateDAO orderStateDAO;
    @Resource TakeOrderDAO takeOrderDAO;

    /*待操作的TakeOrder对象*/
    private TakeOrder takeOrder;
    public void setTakeOrder(TakeOrder takeOrder) {
        this.takeOrder = takeOrder;
    }
    public TakeOrder getTakeOrder() {
        return this.takeOrder;
    }

    /*跳转到添加TakeOrder视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ExpressTake信息*/
        List<ExpressTake> expressTakeList = expressTakeDAO.QueryAllExpressTakeInfo();
        ctx.put("expressTakeList", expressTakeList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的OrderState信息*/
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*添加TakeOrder信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrder添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrder添加失败!"));
            return "error";
        }
    }

    /*查询TakeOrder信息*/
    public String QueryTakeOrder() {
        if(currentPage == 0) currentPage = 1;
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj, userObj, takeTime, orderStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        takeOrderDAO.CalculateTotalPageAndRecordNumber(expressTakeObj, userObj, takeTime, orderStateObj);
        /*获取到总的页码数目*/
        totalPage = takeOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryTakeOrderOutputToExcel() { 
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj,userObj,takeTime,orderStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TakeOrder信息记录"; 
        String[] headers = { "订单id","代拿的快递","接任务人","接单时间","订单状态","实时动态","用户评价"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TakeOrder.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询TakeOrder信息*/
    public String FrontQueryTakeOrder() {
        if(currentPage == 0) currentPage = 1;
        if(takeTime == null) takeTime = "";
        List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrderInfo(expressTakeObj, userObj, takeTime, orderStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        takeOrderDAO.CalculateTotalPageAndRecordNumber(expressTakeObj, userObj, takeTime, orderStateObj);
        /*获取到总的页码数目*/
        totalPage = takeOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的TakeOrder信息*/
    public String ModifyTakeOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取TakeOrder对象*/
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

    /*查询要修改的TakeOrder信息*/
    public String FrontShowTakeOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取TakeOrder对象*/
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

    /*更新修改TakeOrder信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrder信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrder信息更新失败!"));
            return "error";
       }
   }

    /*删除TakeOrder信息*/
    public String DeleteTakeOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            takeOrderDAO.DeleteTakeOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("TakeOrder删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TakeOrder删除失败!"));
            return "error";
        }
    }

}
