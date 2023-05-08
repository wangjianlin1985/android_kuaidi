package com.chengxusheji.utils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class CardIdFieldValidator extends FieldValidatorSupport
{

    public void validate(Object object) throws ValidationException
    {
        // ����ֶε�����
        String fieldName = getFieldName();
        // ���������������ֵ
        String value = getFieldValue(fieldName, object).toString();

        if (value == null || value.length() <= 0)
            return;
        
        if(value.length()!=15 && value.length()!=18 )//���֤������15��18λ!
            addFieldError(fieldName, object);
        
        if(value.length()==15)
            validate15CardId(value, object);
        if(value.length()==18)
            validate18CardId(value, object);
    }
    
    /** *//**
     * <p>18λ���֤��֤</p>
     * 
     * ���ݡ��л����񹲺͹����ұ�׼ GB 11643-1999�����йع�����ݺ���Ĺ涨��������ݺ�������������룬��ʮ��λ���ֱ������һλ����У������ɡ�
     * ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬��λ����˳�����һλ����У���롣
     * 
     * ��ʮ��λ����(У����)�ļ��㷽��Ϊ�� 
     * 1.��ǰ������֤����17λ���ֱ���Բ�ͬ��ϵ�����ӵ�һλ����ʮ��λ��ϵ���ֱ�Ϊ��7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 
     * 2.����17λ���ֺ�ϵ����˵Ľ����ӡ� 
     * 3.�üӳ����ͳ���11���������Ƕ��٣� 
     * 4.����ֻ������0 1 2 3 4 5 6 7 8 9 10��11�����֡���ֱ��Ӧ�����һλ���֤�ĺ���Ϊ1 0 X 9 8 7 6 5 4 3 2�� 
     * 5.ͨ�������֪���������2���ͻ������֤�ĵ�18λ�����ϳ����������ֵĢ������������10�����֤�����һλ�������2��
     * 
     * @date 2012-08-26
     * @param value
     * @param object
     */
    public void validate18CardId(String value, Object object)
    {
        // ����ֶε�����
        String fieldName = getFieldName();
        
        String tempStr = value.substring(0,17);
        String sourceCheckCode = value.substring(17,18);
        String checkCode = "";   
        int[] a = new int[17];   
        int i = 0;   
        try
        {
            while(i<17){   
                a[i] = Integer.parseInt(tempStr.substring(i,i+1));   
                i++;   
            }
        } catch (NumberFormatException e)
        {
            addFieldError(fieldName, object);
        }   
        int mod = (a[0]*7+a[1]*9+a[2]*10+a[3]*5+a[4]*8+a[5]*4+a[6]*2+a[7]*1+a[8]*6+a[9]*3+a[10]*7  
            +a[11]*9+a[12]*10+a[13]*5+a[14]*8+a[15]*4+a[16]*2)%11;   
        switch (mod){   
            case 10:    checkCode = "2";    break;   
            case 9:     checkCode = "3";    break;   
            case 8:     checkCode = "4";    break;   
            case 7:     checkCode = "5";    break;   
            case 6:     checkCode = "6";    break;   
            case 5:     checkCode = "7";    break;   
            case 4:     checkCode = "8";    break;   
            case 3:     checkCode = "9";    break;   
            case 2:     checkCode = "x";    break;   
            case 1:     checkCode = "0";    break;   
            case 0:     checkCode = "1";    break;   
        } 
        
        if(!sourceCheckCode.equalsIgnoreCase(checkCode))
            addFieldError(fieldName, object);
    }
    
    /** *//**
     * <p>15λ���֤��֤</p>
     * 
     * ֻ����������֤
     * @date 2012-08-26
     * @param value
     * @param object
     */
    public void validate15CardId(String value, Object object)
    {
        String fieldName = getFieldName();
        int i = 0; 
        try
        {
            while(i<15){   
                if(!Character.isDigit(value.charAt(i)))
                    addFieldError(fieldName, object);
                i++;   
            }
        } catch (NumberFormatException e)
        {
            addFieldError(fieldName, object);
        }
    }
}
