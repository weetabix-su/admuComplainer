package edu.ateneo.admucomplainer;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import edu.ateneo.admucomplainer.ImageUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;


import edu.ateneo.admucomplainer.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Submit extends Activity {
	Spinner sp1;
	private TextView textTargetUri;
	ArrayAdapter<CharSequence> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		adapter = ArrayAdapter.createFromResource(this, R.array.inconvenience_rating, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter);
	}
	private File outputFile;
	private File thumbNailFile;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void save(View v)
	{
				
		//pic 
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File (sdCard.getAbsolutePath() + "/CameraTest/");
		directory.mkdirs();
		String name = String.valueOf(System.currentTimeMillis());
		outputFile = new File(directory, name+".jpg");
		thumbNailFile = new File(directory, name+"_tn.jpg");
		
		 Uri outputFileUri = Uri.fromFile(outputFile);
	     Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	     intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	     
	     startActivityForResult(intent, 0);
		//

		//ParseObject review = new ParseObject("MyData");
		
		

		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) 
		{
			// rescale the picture from the full size output file which is assumed to now be
			// in outputFile
			try
			{
				// create rescale 640
				// create thumbnail rescale 50

				ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 100, thumbNailFile.getAbsolutePath());
				ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 640, outputFile.getAbsolutePath());
				sendToParse();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				textTargetUri.setText(e.getClass().getName());				
			}
		}
	}
	public void sendToParse() throws IOException
	{
	    String filename = outputFile.getAbsolutePath();
	    String thumbnailFilename = thumbNailFile.getAbsolutePath();
	    Date dateTaken = new Date();
	
		
	    // send Images via Parse    
        ParseFile thumbnail = new ParseFile(ImageUtils.getFileByte(thumbnailFilename), thumbnailFilename);
        thumbnail.saveInBackground();

        ParseFile fullSize = new ParseFile(ImageUtils.getFileByte(filename), filename);
        fullSize.saveInBackground();
        
        ParseObject review = new ParseObject("MyData");
   
        review.put("fullSize", fullSize);
        review.put("thumbnail", thumbnail);
        review.put("dateTaken", dateTaken);
		disableButtonsShowProgress();
		review.saveInBackground(new SaveCallback() {
			
			public void done(ParseException e) 
			  {
			    if (e == null) 
			    {
			    	Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_LONG).show();
			    	
			    }
			    else
			    {
			    	e.printStackTrace();
			    	Toast.makeText(getApplicationContext(), "Unable to Send Review: "+e.getMessage(), Toast.LENGTH_LONG).show();
			    }
			    enableButtonDisableProgress();
			}
		}); 
	}
	public void cancel(View v)
	{
		finish();
	}
	private void disableButtonsShowProgress()
	{
		View button1 = findViewById(R.id.button1);
		
		View progress = findViewById(R.id.progressBar1);
		
		button1.setEnabled(false);
		
		progress.setVisibility(View.VISIBLE);
	}
	private void enableButtonDisableProgress()
	{
		View button1 = findViewById(R.id.button1);
		
		View progress = findViewById(R.id.progressBar1);
		
		button1.setEnabled(true);
		
		progress.setVisibility(View.INVISIBLE);		
	}	
}
