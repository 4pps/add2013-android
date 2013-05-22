package org.gdgankara.app.listeners;

import org.gdgankara.app.activities.MainActivity;
import org.gdgankara.app.activities.SearchActivity;
import org.gdgankara.app.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**************************************************************************************
 *      Bu s�n�f her activity i�erisinde tablar�n ne yapaca��n� implement etmek
 *      yerine , sadece burada yaz�p her activity i�erisinde bu s�n�f�n nesnesini
 *      kullanmak i�in olu�turuldu.
 * 
 **************************************************************************************/

public class TabListener implements OnClickListener {

	private Context context;
	
	public TabListener(Context context){
		this.context=context;
	}
	

	public void onClick(View v) {
		
		switch(v.getId()){
		
		case R.id.search_button:
			Intent i = new Intent(context , SearchActivity.class);
			context.startActivity(i);
			break;
			
		case R.id.update_button:
			//Burada listeler update edilecek.
			//Progress dialog e�li�inde olabilir.
			//Listeler yenilenince Intent.FLAG_ACTIVITY_CLEAR_TOP flagi ile MainActivity'nin ba�lat�lmas�nda yarar var
			break;
		}
	
	}
	/*******************************************************************************************/
	
	
}