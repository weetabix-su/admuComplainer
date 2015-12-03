package edu.ateneo.admucomplainer;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import edu.ateneo.admucomplainer.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {
	
	
Spinner sp1;
ArrayAdapter<CharSequence> adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		adapter = ArrayAdapter.createFromResource(this, R.array.schools_list, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter);
		
	}

	public void save(View v)
	{
		EditText idNumber = (EditText) findViewById(R.id.idNumber);
		EditText email = (EditText) findViewById(R.id.email);
		EditText password = (EditText) findViewById(R.id.password);
		EditText confirm = (EditText) findViewById(R.id.confirm);
		Spinner school = (Spinner) findViewById(R.id.spinner1);

		
		String idNumberStr  = idNumber.getText().toString();
		String passwordStr = password.getText().toString();		
		String emailStr = email.getText().toString();
		String confirmStr = confirm.getText().toString();
		String schoolStr = school.getSelectedItem().toString();
		
		if (!passwordStr.equals(confirmStr))
		{
			Toast toast = Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG);
			toast.show();
		}
		
		else{
			ParseUser user = new ParseUser();
			user.setUsername(idNumberStr);
			user.setPassword(passwordStr);
			user.setEmail(emailStr);
			
			disableButtonsShowProgress();
			user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) 
			  {
			    if (e == null) 
			    {
			     //ayyyyy lmaaoooo
			    	Toast.makeText(getApplicationContext(), "Registration successful.  You can now log in", Toast.LENGTH_LONG).show();
			    	finish();
			    } 
			    else 
			    {
			     //app  dies Rip in peace Pupper
			    	e.printStackTrace();
			    	Toast.makeText(getApplicationContext(), "Unable to register: "+e.getMessage(), Toast.LENGTH_LONG).show();
			    }
			    
			    enableButtonDisableProgress();
			  }
			});
		}
	}
	
	
	
	public void cancel(View v)
	{
		finish();
	}
	
	private void disableButtonsShowProgress()
	{
		View button1 = findViewById(R.id.save);
		View button2 = findViewById(R.id.cancel);
		View progress = findViewById(R.id.progressBar1);
		
		button1.setEnabled(false);
		button2.setEnabled(false);
		progress.setVisibility(View.VISIBLE);

	}
	
	private void enableButtonDisableProgress()
	{
		View button1 = findViewById(R.id.save);
		View button2 = findViewById(R.id.cancel);
		View progress = findViewById(R.id.progressBar1);
		
		button1.setEnabled(true);
		button2.setEnabled(true);
		progress.setVisibility(View.INVISIBLE);		
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
}
