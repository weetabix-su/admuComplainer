package edu.ateneo.admucomplainer;


import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class TheComplainer extends Application {

	
	private String applicationId = "CMXhLGUGhFKAbLwmEGLA5x2Te31QPySiopCFEaK0";
	private String clientKey = "Fq7mlQyK710zuzh1jxTzomrqZLFD9IXvw4X7a7PQ";
	
	public void onCreate()
	{
		super.onCreate();
		
		// add any of your subclasses here
		//ParseObject.registerSubclass(Picture.class);
		//ParseObject.registerSubclass(MyData.class);
	// initialize Parse here		
		Parse.initialize(this, applicationId, clientKey);
	}
}
