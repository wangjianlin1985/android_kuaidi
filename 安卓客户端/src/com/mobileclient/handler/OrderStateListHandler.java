package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.OrderState;
public class OrderStateListHandler extends DefaultHandler {
	private List<OrderState> orderStateList = null;
	private OrderState orderState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (orderState != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderStateId".equals(tempString)) 
            	orderState.setOrderStateId(new Integer(valueString).intValue());
            else if ("orderStateName".equals(tempString)) 
            	orderState.setOrderStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("OrderState".equals(localName)&&orderState!=null){
			orderStateList.add(orderState);
			orderState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		orderStateList = new ArrayList<OrderState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("OrderState".equals(localName)) {
            orderState = new OrderState(); 
        }
        tempString = localName; 
	}

	public List<OrderState> getOrderStateList() {
		return this.orderStateList;
	}
}
