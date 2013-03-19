package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.Locale;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteListActivity extends Activity{
	
	private ArrayList<Session> total_session_list,favorite_session_list;
	private ListView session_listview;
	private SessionListAdapter sessionlist_adapter;
	private LinearLayout sessionlist_layout;
	private LayoutInflater inflater ;
	private View view;
	private int height,lang,pressed_back_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=getDimensionsOfScreen();
		setActivityTheme(height);
		setContentView(R.layout.sessionlist);
		setLang();
		total_session_list=Util.SessionList;
		findFavorites();
		setUpView();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			findFavorites();
			if(favorite_session_list.size()!=0){
				sessionlist_adapter=new SessionListAdapter(this, favorite_session_list, height);
				session_listview.setAdapter(sessionlist_adapter);
			}
			else{
				inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				session_listview.setVisibility(View.GONE);
				view=inflater.inflate(R.layout.blank_list, null, false);
				((TextView)view.findViewById(R.id.blanklist_text)).setText(lang==1?"Favori oturumunuz bulunmamaktad�r":"There is no favorite session");
				sessionlist_layout.addView(view);
			}
		}
		
	}
	
	private void setLang() {
		if(Locale.getDefault().getLanguage().equals("tr")){
			lang=1;
		}
		else{
			lang=0;
		}
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
		Long id=favorite_session_list.get(index).getId();
		b.putLong("id", id);
		intent.putExtras(b);
		pressed_back_button=1;
		this.startActivity(intent);
	}

	private void setUpView() {
		session_listview=(ListView)findViewById(R.id.sessionlist);
		sessionlist_layout=(LinearLayout)findViewById(R.id.sessionlist_layout);
		if(favorite_session_list.size()==0){		
			inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			session_listview.setVisibility(View.GONE);
			view=inflater.inflate(R.layout.blank_list, null, false);
			((TextView)view.findViewById(R.id.blanklist_text)).setText(lang==1?"Favori oturumunuz bulunmamaktad�r":"There is no favorite session");
			sessionlist_layout.addView(view);
		}
		else{
			sessionlist_adapter=new SessionListAdapter(this, favorite_session_list, height);
			session_listview.setAdapter(sessionlist_adapter);
			childItemsActive();
		}
		
	}

	private void findFavorites() {
		favorite_session_list=new ArrayList<Session>();
			for(Session session:total_session_list){
				if(session.isFavorite()){
					favorite_session_list.add(session);
				}
			}
	}

	private int getDimensionsOfScreen(){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;

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