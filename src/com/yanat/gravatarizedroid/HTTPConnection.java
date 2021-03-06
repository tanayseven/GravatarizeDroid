package com.yanat.gravatarizedroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class HTTPConnection extends AsyncTask<Object, Integer, Boolean>
{
	protected final String baseURL = "http://www.gravatar.com/avatar/";
	protected String mainURL;
	protected String hashcode;
	protected final String extension = ".jpg";
	protected boolean busy;
	protected Integer downloadProgress;
	protected MainGravatar callersObject;
	
	protected Drawable drawable;
	
	public HTTPConnection()
	{
		mainURL = hashcode = "";
		drawable = null;
		busy = false;
		downloadProgress = 0;
	}
	
	public void createURL()
	{
		busy = true;
		mainURL = baseURL + hashcode + extension;
	}
	
	public void setHashCode(String hashcode)
	{
		this.hashcode = hashcode;
	}

	public void setCallersObject(MainGravatar object)
	{
		callersObject = object;
	}
	public Drawable getDrawable()
	{
		return drawable;
	}
	
	public Integer getProgress()
	{
		return downloadProgress;
	}
	
	public boolean isBusy()
	{
		return busy;
	}
	
	@Override
	protected Boolean doInBackground(Object... params) 
	{
		try 
		{
			URL url;
			int responseCode;
			url = new URL(mainURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			responseCode = urlConnection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK)
			{
				InputStream in = (InputStream)url.getContent();
				drawable = Drawable.createFromStream(in, "src");
            	in.close();
			}
			if(responseCode == HttpURLConnection.HTTP_NOT_FOUND)
			{
				return false;
			}
		    urlConnection.disconnect();
			busy = false;
	        return true;
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	@Override
	protected void onPostExecute(Boolean status) 
	{
		callersObject.downloadingOver();
	}
}
