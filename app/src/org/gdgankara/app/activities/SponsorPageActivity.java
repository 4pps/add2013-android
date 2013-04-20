package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.Locale;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class SponsorPageActivity  extends Activity{

	private Sponsor sponsor;
	private ArrayList<Sponsor> total_sponsor_list;
	private TextView text;
	private Long sponsor_id;
	private TabListener tabListener;
	private int height,lang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setContentView(R.layout.sponsorpage);
		sponsor_id=this.getIntent().getExtras().getLong("id");
		setLang();
		getSponsorList();
		findSponsor();
		setUpView();
		tabAktif();
		
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private void setUpView() {
		
		
		/*Sponsor logosu : view de�i�keni ile ula� ve logoyu set et */
		
		/*Sponsor ad�*/
		text=(TextView)findViewById(R.id.session_title);
		text.setText(sponsor.getName());
		setSponsorNameTextSize(text);
		
		/*Sponsor �zet*/
		text=(TextView)findViewById(R.id.sponsor_description);
		text.setText(sponsor.getDescription());
		
		/*Ba�l�k Dili*/
		text=(TextView)findViewById(R.id.sponsor_link_title);
		text.setText(lang==1?"Ayr�nt�l� bilgi i�in":"Fore more details");
		
	}
	
	private void setLang() {
		if(Locale.getDefault().getLanguage().equals("tr")){
			lang=1;
		}
		else{
			lang=0;
		}
	}
	
	private void findSponsor(){
		for(Sponsor temp:total_sponsor_list){
			if(temp.getId()==sponsor_id){
				sponsor=temp;
				break;
			}
		}
	}
	
	private void getSponsorList(){
		total_sponsor_list=Util.SponsorList;
	}
	
	
	private void setSponsorNameTextSize(TextView text) {		
		
		if(height<=320){
			text.setTextSize(15);
		}
		else if(height<=480){
			text.setTextSize(16);
		}
		else if(height<=800){
			text.setTextSize(17);
		}
		else{
			text.setTextSize(18);
		}
	
	}
	
	private void setActivityTheme(){
		
		
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
