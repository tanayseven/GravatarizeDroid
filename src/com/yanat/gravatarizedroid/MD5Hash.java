package com.yanat.gravatarizedroid;

import java.io.UnsupportedEncodingException;
import java.security.*;

public abstract class MD5Hash 
{
	public static String getHexString(byte[] b)
	{
		  String result = "";
		  for (int i=0; i < b.length; i++) 
		  {
		    result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
	public static String getMD5Hash(String string)
	{
		try 
		{
			byte [] stringBytes = string.getBytes("UTF-8");
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte [] digestedResult = messageDigest.digest(stringBytes);
			return getHexString(digestedResult);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return "";
	}
}
