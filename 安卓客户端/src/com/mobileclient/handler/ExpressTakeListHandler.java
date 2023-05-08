package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ExpressTake;
public class ExpressTakeListHandler extends DefaultHandler {
	private List<ExpressTake> expressTakeList = null;
	private ExpressTake expressTake;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (expressTake != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderId".equals(tempString)) 
            	expressTake.setOrderId(new Integer(valueString).intValue());
            else if ("taskTitle".equals(tempString)) 
            	expressTake.setTaskTitle(valueString); 
            else if ("companyObj".equals(tempString)) 
            	expressTake.setCompanyObj(new Integer(valueString).intValue());
            else if ("waybill".equals(tempString)) 
            	expressTake.setWaybill(valueString); 
            else if ("receiverName".equals(tempString)) 
            	expressTake.setReceiverName(valueString); 
            else if ("telephone".equals(tempString)) 
            	expressTake.setTelephone(valueString); 
            else if ("receiveMemo".equals(tempString)) 
            	expressTake.setReceiveMemo(valueString); 
            else if ("takePlace".equals(tempString)) 
            	expressTake.setTakePlace(valueString); 
            else if ("giveMoney".equals(tempString)) 
            	expressTake.setGiveMoney(new Float(valueString).floatValue());
            else if ("takeStateObj".equals(tempString)) 
            	expressTake.setTakeStateObj(valueString); 
            else if ("userObj".equals(tempString)) 
            	expressTake.setUserObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	expressTake.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ExpressTake".equals(localName)&&expressTake!=null){
			expressTakeList.add(expressTake);
			expressTake = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		expressTakeList = new ArrayList<ExpressTake>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ExpressTake".equals(localName)) {
            expressTake = new ExpressTake(); 
        }
        tempString = localName; 
	}

	public List<ExpressTake> getExpressTakeList() {
		return this.expressTakeList;
	}
}
