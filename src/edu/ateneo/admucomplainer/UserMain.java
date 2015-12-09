package edu.ateneo.admucomplainer;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_main);
		
		//Note: I got this from CatPic so I have no idea if it's complaintId I'm supposed to be getting
		String complaintId = (String) getIntent().getStringExtra("complaintId");
		
		ParseQuery<Complaint> query = ParseQuery.getQuery(Complaint.class);
		query.getInBackground(complaintId, new GetCallback<Complaint>()
				{
					@Override
					public void done(Complaint comp, ParseException arg1) {
						// TODO Auto-generated method stub
						if (arg1==null)
						{
							// upon loading, place the ParseFile into the image view
							// then load in background
							ParseImageView imageView = (ParseImageView) findViewById(R.id.imageView1);
							
							imageView.setParseFile(comp.getPic());
							imageView.loadInBackground();
								
						}
						else
						{
							arg1.printStackTrace();
						}
					}
				
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_main, menu);
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
	
	public void SubmitClicked(View v){
		Intent intent = new Intent(getApplicationContext(), Submit.class);
		startActivity(intent);
		finish();
	}
	public void ViewClicked(View v){
		Intent intent = new Intent(getApplicationContext(), View.class);
		startActivity(intent);
		finish();
	}
}
