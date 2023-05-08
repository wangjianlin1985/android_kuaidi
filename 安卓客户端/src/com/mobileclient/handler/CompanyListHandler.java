package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Company;
public class CompanyListHandler extends DefaultHandler {
	private List<Company> companyList = null;
	private Company company;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (company != null) { 
            String valueString = new String(ch, start, length); 
            if ("companyId".equals(tempString)) 
            	company.setCompanyId(new Integer(valueString).intValue());
            else if ("companyName".equals(tempString)) 
            	company.setCompanyName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Company".equals(localName)&&company!=null){
			companyList.add(company);
			company = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		companyList = new ArrayList<Company>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Company".equals(localName)) {
            company = new Company(); 
        }
        tempString = localName; 
	}

	public List<Company> getCompanyList() {
		return this.companyList;
	}
}
