package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Company;
import com.mobileserver.util.DB;

public class CompanyDAO {

	public List<Company> QueryCompany() {
		List<Company> companyList = new ArrayList<Company>();
		DB db = new DB();
		String sql = "select * from Company where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Company company = new Company();
				company.setCompanyId(rs.getInt("companyId"));
				company.setCompanyName(rs.getString("companyName"));
				companyList.add(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return companyList;
	}
	/* ����������˾���󣬽���������˾�����ҵ�� */
	public String AddCompany(Company company) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������˾ */
			String sqlString = "insert into Company(companyName) values (";
			sqlString += "'" + company.getCompanyName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������˾��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������˾���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������˾ */
	public String DeleteCompany(int companyId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Company where companyId=" + companyId;
			db.executeUpdate(sqlString);
			result = "������˾ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������˾ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݹ�˾id��ȡ��������˾ */
	public Company GetCompany(int companyId) {
		Company company = null;
		DB db = new DB();
		String sql = "select * from Company where companyId=" + companyId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				company = new Company();
				company.setCompanyId(rs.getInt("companyId"));
				company.setCompanyName(rs.getString("companyName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return company;
	}
	/* ����������˾ */
	public String UpdateCompany(Company company) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Company set ";
			sql += "companyName='" + company.getCompanyName() + "'";
			sql += " where companyId=" + company.getCompanyId();
			db.executeUpdate(sql);
			result = "������˾���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������˾����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
