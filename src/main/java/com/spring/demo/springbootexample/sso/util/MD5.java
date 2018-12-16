package com.spring.demo.springbootexample.sso.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5 {

    private static Logger logger = LoggerFactory.getLogger(MD5.class);
	public static String GetMD5Code(String source,int length){
		 String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            if(length != 32){
            	result =  buf.toString().substring(8, 24);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5加密异常",e);
        }
        return result;
	}
}
