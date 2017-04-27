package com.bn.knowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TxtReader {

	public static String getText(InputStream inputStream)
	{
		InputStreamReader inputStreamReader = null;
		try{
			inputStreamReader = new InputStreamReader(inputStream,"gbk");
			
		}catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try{
			while((line = bufferedReader.readLine())!=null)
			{
				sb.append(line);
				sb.append("\n");
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
}
