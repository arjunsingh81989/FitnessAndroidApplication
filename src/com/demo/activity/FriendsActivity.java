package com.demo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import android.view.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsActivity extends Activity {

	private ListView friendLv;
	private List<String> friends; //= new ArrayList<String>();
	private FriendAdapter adapter = null;

	private List<User1> users = new ArrayList<User1>();
	private User1 me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendlist);
		
		final String MY_NAME = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
		friends = new ArrayList<String>();
		friendLv = (ListView) findViewById(R.id.friendLv);
		adapter = new FriendAdapter(this, R.id.nameTv, friends);
		// friendLv.setAdapter(adapter);

		new Thread(new Runnable() {

			@Override
			public void run() {

				users = Database1.getUserList();
				for (User1 user : users) {
					if (MY_NAME.equalsIgnoreCase(user.getName())) {
						me = user;
						break;
					}
				}
				friends = Database1.getFriendList(me.getId());
				friends.removeAll(Arrays.asList("", null));
				friends = new ArrayList<String>(new LinkedHashSet<String>(friends));

				//	friends.remove(0);
				Log.e("Database", "getFriendList: " + friends);
			
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter = new FriendAdapter(FriendsActivity.this,
								R.id.nameTv, friends);
						if (friends != null && !(friends.isEmpty()))
							friendLv.setAdapter(adapter);
						
					}
				});
			
			}
		}).start();

	}

	public void showUserList() {
		final List<String> userList = new ArrayList<String>();
		for (User1 user : users) {
			if (!friends.contains(user.getName())
					&& (false == user.getName().equalsIgnoreCase(me.getName()))) {
				userList.add(user.getName());

			}
		}
		if (userList.size() < 1) {
			Toast.makeText(this, "No Friend available!", 3).show();
			return;
		}
		final String[] items = (String[]) userList.toArray(new String[userList
				.size()]);

		final ArrayList<String> seletedItems = new ArrayList<String>();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select a user");
		builder.setMultiChoiceItems(items, null,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {
						if (isChecked) {

							seletedItems.add(userList.get(indexSelected));
						} else if (seletedItems.contains(indexSelected)) {

							seletedItems.remove(userList.get(indexSelected));
						}
					}
				})

		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				friends.addAll(seletedItems);
				adapter = new FriendAdapter(FriendsActivity.this, R.id.nameTv,
						friends);
				if (friends != null)
					friendLv.setAdapter(adapter);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Database1.saveFriends(me.getId(), friends);//friends=null;

						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				}).start();

			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_friendlist, menu);
		return true;
	}

	public void onClick(View view) {
		
		final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
		Intent newIntent;
		switch (view.getId()) {
	
		case R.id.backbtn:

		
		newIntent = new Intent(this, HomeScreen.class);
		newIntent.putExtra(Login.USER_NAME_KEY, userName);
		startActivity(newIntent);
		
		this.finish();
		break;
		
		case R.id.addbtn:
			showUserList();
			break;

			
			default:
				break;
	}
}
	static class Holder {
		  TextView userName;
		  
		  Button archiveButton;
		 }
	
	class FriendAdapter extends ArrayAdapter<String> {
		public FriendAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);

			this.context = context;
			this.friends = objects;
		}

		Context context;
		List<String> friends = null;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;
			Holder holder = null;
			
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.friend_item, null);
			
			holder = new Holder();
			
			
			holder.userName=(TextView) row.findViewById(R.id.nameTv);
			
			holder.archiveButton = (Button) row.findViewById(R.id.archiveTv);
			
			row.setTag(holder);
			
			
			}else {
				   holder = (Holder) row.getTag();
			
			}
			final String friendName = friends.get(position);
			holder.userName.setText(friendName);
			
			


			holder.archiveButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
			
					Intent newIntent = new Intent(getApplicationContext(), ArchiveActivity.class);
					newIntent.putExtra(Login.USER_NAME_KEY, friendName);
					startActivity(newIntent);
					
				}});
			
			
			
			
			return row;
		}

	}

}
