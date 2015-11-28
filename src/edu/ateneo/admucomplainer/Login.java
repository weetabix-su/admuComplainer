package edu.ateneo.admucomplainer;

import edu.ateneo.admucomplainer.AboutPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    
public void AboutClicked(View v)
{
	Intent intent = new Intent(getApplicationContext(), AboutPage.class);
	startActivity(intent);

}
public void SignUpClicked(View v)
{
	Intent intent = new Intent(getApplicationContext(), Register.class);
	startActivity(intent);

}
}
