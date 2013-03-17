package com.yanat.gravatarizedroid;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainGravatar extends Activity implements OnClickListener {

	protected EditText edit_email;
	protected Button button_search;
	protected volatile ProgressDialog downloadProgress;
	protected ImageView image_gravatar;
	protected String MD5hash;
	protected HTTPConnection connection;
	protected boolean finished;
	
	Bitmap btmp;
	
	protected void init()
	{
		edit_email = (EditText) findViewById(R.id.edittxt_email);
		button_search = (Button) findViewById(R.id.btn_search);
		button_search.setOnClickListener(this);
		image_gravatar = (ImageView) findViewById(R.id.imageView_gravatar);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_gravatar);
		init();
	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btn_search:
			   downloadGravatar();
		}
	}
	
	private void downloadGravatar()
	{
		connection = new HTTPConnection();
		connection.setCallersObject(this);
		MD5hash = MD5Hash.getMD5Hash(edit_email.getText().toString());
		edit_email.selectAll();
		connection.setHashCode(MD5hash);
		connection.createURL();
		connection.execute();
		downloadProgress = ProgressDialog.show(this, "Please wait", "Downloading please wait..", true);
		downloadProgress.setCancelable(false);
	}
	
	void downloadingOver()
	{
		image_gravatar.setImageDrawable(connection.getDrawable());
		downloadProgress.dismiss();
	}
}
