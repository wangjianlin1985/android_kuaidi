package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CompanyDAO;
import com.mobileserver.domain.Company;

import org.json.JSONStringer;

public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造物流公司业务层对象*/
	private CompanyDAO companyDAO = new CompanyDAO();

	/*默认构造函数*/
	public CompanyServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询物流公司的参数信息*/

			/*调用业务逻辑层执行物流公司查询*/
			List<Company> companyList = companyDAO.QueryCompany();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Companys>").append("\r\n");
			for (int i = 0; i < companyList.size(); i++) {
				sb.append("	<Company>").append("\r\n")
				.append("		<companyId>")
				.append(companyList.get(i).getCompanyId())
				.append("</companyId>").append("\r\n")
				.append("		<companyName>")
				.append(companyList.get(i).getCompanyName())
				.append("</companyName>").append("\r\n")
				.append("	</Company>").append("\r\n");
			}
			sb.append("</Companys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Company company: companyList) {
				  stringer.object();
			  stringer.key("companyId").value(company.getCompanyId());
			  stringer.key("companyName").value(company.getCompanyName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加物流公司：获取物流公司参数，参数保存到新建的物流公司对象 */ 
			Company company = new Company();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			company.setCompanyId(companyId);
			String companyName = new String(request.getParameter("companyName").getBytes("iso-8859-1"), "UTF-8");
			company.setCompanyName(companyName);

			/* 调用业务层执行添加操作 */
			String result = companyDAO.AddCompany(company);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除物流公司：获取物流公司的公司id*/
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			/*调用业务逻辑层执行删除操作*/
			String result = companyDAO.DeleteCompany(companyId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新物流公司之前先根据companyId查询某个物流公司*/
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			Company company = companyDAO.GetCompany(companyId);

			// 客户端查询的物流公司对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("companyId").value(company.getCompanyId());
			  stringer.key("companyName").value(company.getCompanyName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新物流公司：获取物流公司参数，参数保存到新建的物流公司对象 */ 
			Company company = new Company();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			company.setCompanyId(companyId);
			String companyName = new String(request.getParameter("companyName").getBytes("iso-8859-1"), "UTF-8");
			company.setCompanyName(companyName);

			/* 调用业务层执行更新操作 */
			String result = companyDAO.UpdateCompany(company);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
