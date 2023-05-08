package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Notice;
public class NoticeListHandler extends DefaultHandler {
	private List<Notice> noticeList = null;
	private Notice notice;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (notice != null) { 
            String valueString = new String(ch, start, length); 
            if ("noticeId".equals(tempString)) 
            	notice.setNoticeId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	notice.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	notice.setContent(valueString); 
            else if ("publishDate".equals(tempString)) 
            	notice.setPublishDate(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Notice".equals(localName)&&notice!=null){
			noticeList.add(notice);
			notice = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		noticeList = new ArrayList<Notice>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Notice".equals(localName)) {
            notice = new Notice(); 
        }
        tempString = localName; 
	}

	public List<Notice> getNoticeList() {
		return this.noticeList;
	}
}
