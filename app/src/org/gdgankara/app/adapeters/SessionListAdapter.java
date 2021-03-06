package org.gdgankara.app.adapeters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.gdgankara.app.R;
import org.gdgankara.app.model.Session;
import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SessionListAdapter extends ArrayAdapter<Session>{

	private List<Session> sessionlist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private int textSize,lang;
	private Session session;
	private String temp;
	
	public SessionListAdapter(Context context, List<Session> objects,int height) {
		super(context,R.layout.child_of_sessionlist,objects);
		sessionlist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
		setLang();
	}
	

	private void setLang() {
		if(Locale.getDefault().getLanguage().equals("tr")){
			lang=1;
		}
		else{
			lang=0;
		}
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		session=sessionlist.get(position);
		view=inflater.inflate(R.layout.child_of_sessionlist, null, false);
		text=(TextView)view.findViewById(R.id.session_name);
		text.setText(session.getTitle());
		text=(TextView)view.findViewById(R.id.session_features);
		temp=session.getDay()==14?lang==1?"Cuma":"Friday":lang==1?"Cumartesi":"Saturday";
		temp+=" "+session.getStart_hour()+" - "+session.getEnd_hour()+" | ";
		temp+=lang==1?session.getHall()+" Salonu":"Hall "+ session.getHall();
		text.setText(temp);
		text.setTextSize(textSize);
		if(session.isFavorite()){
			view.findViewById(R.id.isfavorite).setVisibility(View.VISIBLE);
		}
		return view;
	}
	
	public void setList(ArrayList<Session> list){
		sessionlist=list;
	}
	
	private void setFeaturesTextSize(int height){
		
		
		if(height<=320){
			textSize=9;
		}
		else if(height<=480){
			textSize=10;
		}
		else if(height<=800){
			textSize=11;
		}
		else{
			textSize=12;
		}
		
	}
}
