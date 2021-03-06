package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class SessionListActivity extends BaseActivity{
	
	private ArrayList<Session> total_session_list,filtered_session_list;
	private ListView session_listview;
	private SessionListAdapter sessionlist_adapter;
	private String aranan_tag;
	private int pressed_back_button;
	private TabListener tabListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.sessionlist);
		aranan_tag=this.getIntent().getExtras().getString("tag");
		total_session_list=Util.SessionList;
		sessionListFilter();
		setUpView();
		childItemsActive();
		tabAktif();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			sessionlist_adapter=new SessionListAdapter(this, filtered_session_list, Util.device_height);
			session_listview.setAdapter(sessionlist_adapter);
		}
		
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}

	private void setUpView() {
		
		session_listview=(ListView)findViewById(R.id.sessionlist);
		sessionlist_adapter=new SessionListAdapter(this, filtered_session_list, Util.device_height);
		session_listview.setAdapter(sessionlist_adapter);
	}
	
	private void childItemsActive() {
		session_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSessionPage(arg2);
			}
			
		});
	}
	
	private void startSessionPage(int index){
		Intent intent=new Intent(this,SessionPageActivity.class);
		Bundle b=new Bundle();
		Long id=filtered_session_list.get(index).getId();
		b.putLong("id", id);
		intent.putExtras(b);
		pressed_back_button=1;
		this.startActivity(intent);
	}

	
	private void sessionListFilter() {
		int i,j;
		String[] tags;
		String temp;
		filtered_session_list=new ArrayList<Session>();
		int size=total_session_list.size();
		int size2;
		for(i=0;i<size;i++){
			temp=total_session_list.get(i).getTags();
			if(temp!=null){
				tags=temp.split(",");
				size2=tags.length;
				for(j=0;j<size2;j++){
					if(tags[j].equals(aranan_tag)){
						filtered_session_list.add(total_session_list.get(i));
					}
				}
			}
			
		}
	}
	
	private void setActivityTheme(int height){
		
		
		if(height<=320){
			setTheme(R.style.tagList_low);
		}
		else if(height<=480){
			setTheme(R.style.tagList_Medium);
		}
		else if(height<=800){
			setTheme(R.style.tagList_High);
		}
		else{
			setTheme(R.style.tagList_XHigh);
		}
		
	}
}
