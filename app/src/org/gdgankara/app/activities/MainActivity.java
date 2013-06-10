package org.gdgankara.app.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.utils.Util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class MainActivity extends BaseActivity implements OnClickListener {

	private ImageView tweetWallButton, programButton, haritaButton, oturumButton,
			sponsorButton, favoriButton, konusmaciButton;
	private ViewFlipper newsFlipper;
	private ImageView araButton, tempImg;
	private String filepath;
	private ArrayList<String> imagePaths;
	private TabListener tabListener;
	private ArrayList<Announcement> AnnouncementList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Util.prepareStaticLists(this);
		setDeviceDimensions();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setUpButtons();
		buttonsActive();
		setUpFlippingImages();
		tabAktif();
		newsFlipper.startFlipping();

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						sleep(1000);
						refreshView();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		thread.start();

	}
	
	private void setUpFlippingImages() {
		AnnouncementList=Util.AnnouncementList;
		int size=AnnouncementList.size();
		for(int i=0;i<size;i++){
			tempImg = new ImageView(this);
			UrlImageViewHelper.setUrlDrawable(tempImg, AnnouncementList.get(i).getImage(),R.drawable.loading);
			newsFlipper.addView(tempImg);
		}
		
	}



	private void setUpButtons() {
		tweetWallButton = (ImageView) findViewById(R.id.tweetWall);
		favoriButton = (ImageView) findViewById(R.id.favori_button);
		haritaButton = (ImageView) findViewById(R.id.harita_button);
		sponsorButton = (ImageView) findViewById(R.id.sponsor_button);
		oturumButton = (ImageView) findViewById(R.id.oturum_button);
		programButton = (ImageView) findViewById(R.id.program_button);
		araButton = (ImageView) findViewById(R.id.search_button);
		konusmaciButton = (ImageView) findViewById(R.id.speakers_button);
		newsFlipper = (ViewFlipper) findViewById(R.id.highlights);
		if(Util.getDefaultLanguage().equals("tr")){
			tweetWallButton.setImageDrawable(getResources().getDrawable(R.drawable.tweet_image_tr));
			favoriButton.setImageDrawable(getResources().getDrawable(R.drawable.favori_image_tr));
			haritaButton.setImageDrawable(getResources().getDrawable(R.drawable.harita_image_tr));
			sponsorButton.setImageDrawable(getResources().getDrawable(R.drawable.sponsor_image));
			oturumButton.setImageDrawable(getResources().getDrawable(R.drawable.oturum_image_tr));
			programButton.setImageDrawable(getResources().getDrawable(R.drawable.program_image_tr));
			konusmaciButton.setImageDrawable(getResources().getDrawable(R.drawable.konusmacilar_image_tr));
		}
		else{
			tweetWallButton.setImageDrawable(getResources().getDrawable(R.drawable.tweet_image_en));
			favoriButton.setImageDrawable(getResources().getDrawable(R.drawable.favori_image_en));
			haritaButton.setImageDrawable(getResources().getDrawable(R.drawable.harita_image_en));
			sponsorButton.setImageDrawable(getResources().getDrawable(R.drawable.sponsor_image));
			oturumButton.setImageDrawable(getResources().getDrawable(R.drawable.oturum_image_en));
			programButton.setImageDrawable(getResources().getDrawable(R.drawable.program_image_en));
			konusmaciButton.setImageDrawable(getResources().getDrawable(R.drawable.konusmacilar_image_en));
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}

	private void setHighligths() {

	}

	private void refreshView() {
		// imagePaths = new ArrayList<String>();
		// imagePaths.add(getImagesFromWeb("https://twitter.com/images/resources/twitter-bird-light-bgs.png","downloadedFile.png"));
		// Log.i("image path", imagePaths.get(0));
	}

	private void buttonsActive() {
		tweetWallButton.setOnClickListener(this);
		favoriButton.setOnClickListener(this);
		haritaButton.setOnClickListener(this);
		sponsorButton.setOnClickListener(this);
		oturumButton.setOnClickListener(this);
		programButton.setOnClickListener(this);
		konusmaciButton.setOnClickListener(this);
		araButton.setOnClickListener(this);
		newsFlipper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startAnnouncementList(newsFlipper.getDisplayedChild());
			}

		});
	}
	
	private void startAnnouncementList(int index){
		Intent intent=new Intent(this,AnnouncementListActivity.class);
		Bundle b=new Bundle();
		b.putInt("index", index);
		intent.putExtras(b);
		this.startActivity(intent);
	}



	@Override
	public void onClick(View arg0) {
		Intent i = null;

		switch (arg0.getId()) {

		case R.id.tweetWall:
			i = new Intent(MainActivity.this, TweetWallActivity.class);
			break;

		case R.id.program_button:
			i = new Intent(MainActivity.this, ProgramActivity.class);
			break;

		case R.id.harita_button:
			//Toast.makeText(this, getResources().getString(R.string.is_map_ready),Toast.LENGTH_SHORT).show();
			i = new Intent(MainActivity.this, MapActivity.class);
			break;

		case R.id.sponsor_button:
			i = new Intent(MainActivity.this, SponsorListActivity.class);
			break;

		case R.id.oturum_button:
			i = new Intent(MainActivity.this, TagListActivity.class);
			break;

		case R.id.favori_button:
			i = new Intent(MainActivity.this, FavoriteListActivity.class);
			break;

		case R.id.speakers_button:
			i = new Intent(MainActivity.this, SpeakerListActivity.class);
			break;
		case R.id.search_button:
			i = new Intent(MainActivity.this, SearchActivity.class);
			break;

		}
		if(i!=null){
			startActivity(i);
		}

	}

	private String getImagesFromWeb(String urlString, String filename) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			File SDCardRoot = Environment.getExternalStorageDirectory()
					.getAbsoluteFile();
			Log.i("Yol", SDCardRoot.getAbsolutePath());
			Log.i("Local filename:", "" + filename);
			File file = new File(SDCardRoot, filename);
			if (file.createNewFile()) {
				file.createNewFile();
			}
			FileOutputStream fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();
			int totalSize = urlConnection.getContentLength();
			int downloadedSize = 0;
			byte[] buffer = new byte[1024];
			int bufferLength = 0;
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, bufferLength);
				downloadedSize += bufferLength;
				Log.i("Progress:", "downloadedSize:" + downloadedSize
						+ "totalSize:" + totalSize);
			}
			fileOutput.close();
			if (downloadedSize == totalSize)
				filepath = file.getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			filepath = null;
			e.printStackTrace();
		}
		Log.i("filepath:", " " + filepath);
		return filepath;
	}

	private void setDeviceDimensions() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Util.setDeviceHeight(metrics.heightPixels);
		Util.setDeviceWidth(metrics.widthPixels);
	}

}
