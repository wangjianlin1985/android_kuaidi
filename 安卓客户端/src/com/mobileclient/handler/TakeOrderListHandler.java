package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.TakeOrder;
public class TakeOrderListHandler extends DefaultHandler {
	private List<TakeOrder> takeOrderList = null;
	private TakeOrder takeOrder;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (takeOrder != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderId".equals(tempString)) 
            	takeOrder.setOrderId(new Integer(valueString).intValue());
            else if ("expressTakeObj".equals(tempString)) 
            	takeOrder.setExpressTakeObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	takeOrder.setUserObj(valueString); 
            else if ("takeTime".equals(tempString)) 
            	takeOrder.setTakeTime(valueString); 
            else if ("orderStateObj".equals(tempString)) 
            	takeOrder.setOrderStateObj(new Integer(valueString).intValue());
            else if ("ssdt".equals(tempString)) 
            	takeOrder.setSsdt(valueString); 
            else if ("evaluate".equals(tempString)) 
            	takeOrder.setEvaluate(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TakeOrder".equals(localName)&&takeOrder!=null){
			takeOrderList.add(takeOrder);
			takeOrder = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		takeOrderList = new ArrayList<TakeOrder>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("TakeOrder".equals(localName)) {
            takeOrder = new TakeOrder(); 
        }
        tempString = localName; 
	}

	public List<TakeOrder> getTakeOrderList() {
		return this.takeOrderList;
	}
}
