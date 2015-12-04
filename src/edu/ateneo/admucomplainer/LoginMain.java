package edu.ateneo.admucomplainer;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_main, menu);
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
	public void SignUpClicked(View v)
	{
		Intent intent = new Intent(getApplicationContext(), Register.class);
		startActivity(intent);
	}
	public void signIn(View v)
	{
		EditText uN = (EditText) findViewById(R.id.userName);
		EditText pW = (EditText) findViewById(R.id.password);
		String unStr = uN.getText().toString();
		String pwStr = pW.getText().toString();
		barOnButts(true);
		ParseUser.logInInBackground(unStr, pwStr, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
			if (user != null) 
				{
				// Hooray! The user is logged in.
				// NOTE: Intent below should lead us to complainer home screen depending on access permissions (isAdmin)
				Intent intent;
				if (user.getBoolean("isAdmin"))
				{
					intent = new Intent(getApplicationContext(), AdminMain.class);
				}
				else
				{
					intent = new Intent(getApplicationContext(), UserMain.class);
				}
				startActivity(intent);
				//Toast.makeText(getApplicationContext(), "Sign in successful!\nNow fix me!", Toast.LENGTH_LONG).show();
				} 
			else 
				{
				// Signup failed. Look at the ParseException to see what happened.
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Unable to sign in: "+e.getMessage(), Toast.LENGTH_LONG).show();
				
				}
			
			
			// enable the buttons and disable the spinner
			barOnButts(false);
			
			}
		});	
	}
	private void barOnButts(Boolean chk)
	{
		View butt1 = findViewById(R.id.signIn);
		View butt2 = findViewById(R.id.register);
		View spin = findViewById(R.id.progressBar1);
		
		if (chk == true){
			butt1.setEnabled(false);
			butt2.setEnabled(false);
			spin.setVisibility(View.VISIBLE);
		}
		else if (chk == false){
			butt1.setEnabled(true);
			butt2.setEnabled(true);
			spin.setVisibility(View.INVISIBLE);
		}

	}
}
