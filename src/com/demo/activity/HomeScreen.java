package com.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);
		

	}

	public void onClick(View view) {
		
		final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
		Intent newIntent;
		
		switch (view.getId()) {
		
		
		case R.id.upgradeItem:
			newIntent = new Intent(this, UpgradeActivity.class);
			startActivity(newIntent);
			break;
					case R.id.OptionItem:
			newIntent = new Intent(this, OptionActivity.class);
		// Added
			newIntent.putExtra(Login.USER_NAME_KEY, Login.user);
			
	    	//newIntent.putExtra("USER_NAME_KEY", ((User) this.getApplication()).getUsername());
			//Toast.makeText(this ,((User) this.getApplication()).getUsername() , 5);
			startActivity(newIntent);
			break;
			
		case R.id.friendsItem:
			newIntent = new Intent(this, FriendsActivity.class);
		// Added
			newIntent.putExtra(Login.USER_NAME_KEY, Login.user);
			
	    	//newIntent.putExtra("USER_NAME_KEY", ((User) this.getApplication()).getUsername());
			//Toast.makeText(this ,((User) this.getApplication()).getUsername() , 5);
			startActivity(newIntent);
			break;
		
	/*	case R.id.friendsItem:
			newIntent = new Intent(this, FriendActivity.class);
		// Added
			newIntent.putExtra(Login.USER_NAME_KEY, Login.user);
			
	    	//newIntent.putExtra("USER_NAME_KEY", ((User) this.getApplication()).getUsername());
			//Toast.makeText(this ,((User) this.getApplication()).getUsername() , 5);
			startActivity(newIntent);
			break;*/
		case R.id.setting:
			break;
		case R.id.myArchiveItem:
			newIntent = new Intent(this, ArchiveActivity.class);
			newIntent.putExtra(Login.USER_NAME_KEY, userName);
			startActivity(newIntent);
			break;
		case R.id.logout:

			
			newIntent = new Intent(this, Login.class);
			startActivity(newIntent);
			
			this.finish();
			break;

		
		default:
			break;
		}
	}

}
