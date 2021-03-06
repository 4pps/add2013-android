package org.gdgankara.app.utils;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.gdgankara.app.io.FavoritesHandler;
import org.gdgankara.app.io.ParticipantHandler;
import org.gdgankara.app.io.ProgramHandler;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Util {
	public static final String TAG = Util.class.getSimpleName();

	public static ArrayList<Session> SessionList = new ArrayList<Session>();
	public static ArrayList<Speaker> SpeakerList = new ArrayList<Speaker>();
	public static ArrayList<Announcement> AnnouncementList = new ArrayList<Announcement>();
	public static ArrayList<Sponsor> SponsorList = new ArrayList<Sponsor>();
	public static ArrayList<String> TagList = new ArrayList<String>();
	public static ArrayList<Long> FavoritesList = new ArrayList<Long>();
	public static ArrayList<String> ParticipantList = new ArrayList<String>();
	public static int device_height;
	public static int device_width;
	public static int qr_state = 1;

	/**
	 * Shared Preferences'ta tutulan versiyon numarasını verilen numara ile
	 * karşılaştırır.
	 * 
	 * @param context
	 * @param number
	 * @return Boolean
	 * @throws JSONException
	 */
	public static Boolean isVersionUpdated(Context context,
			JSONObject jsonObject) {
		long number;
		Boolean state = false;
		try {
			number = jsonObject.getJSONObject("version").getLong("number");
			SharedPreferences settings = context.getSharedPreferences(TAG, 0);
			long mNumber = settings.getLong("version", 0);
			if (mNumber == number) {
				state = false;
			} else if (mNumber != number || mNumber == 0) {
				Editor editor = settings.edit();
				editor.putLong("version", number);
				editor.commit();
				state = true;
			}
		} catch (JSONException e) {
			state = false;
			e.printStackTrace();
		}
		return state;
	}

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}else {
			return false;
		}
	}

	public static void prepareStaticLists(Context context) {
		String lang = getDefaultLanguage();
		ProgramHandler programHandler = new ProgramHandler(context);
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		ParticipantHandler participantHandler = new ParticipantHandler(context);

		ParticipantList = participantHandler.getParticipantList();
		FavoritesList = favoritesHandler.getFavoritesList(lang);
		programHandler.initializeLists(lang);
	}

	public static void prepareStaticListsFromCache(Context context) {
		String lang = getDefaultLanguage();
		ProgramHandler programHandler = new ProgramHandler(context);
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		ParticipantHandler participantHandler = new ParticipantHandler(context);

		ParticipantList = participantHandler.getParticipantList();
		FavoritesList = favoritesHandler.getFavoritesList(lang);
		programHandler.initializeListsFromCache(lang);
	}

	public static void setDeviceHeight(int height) {
		device_height = height;
	}

	public static void setDeviceWidth(int width) {
		device_width = width;
	}

	public static void addSessionFavorites(Context context, Long sessionID) {
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		if (FavoritesList == null) {
			FavoritesList = new ArrayList<Long>();
		}
		if (!FavoritesList.contains(sessionID)) {
			FavoritesList.add(sessionID);
		}
		favoritesHandler.updateFavoritesList(FavoritesList,
				getDefaultLanguage());
	}
	
	public static void removeSessionFavorites(Context context, Long sessionID) {
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		if (FavoritesList.contains(sessionID)) {
			FavoritesList.remove(sessionID);
		}
		favoritesHandler.updateFavoritesList(FavoritesList,
				getDefaultLanguage());
	}
		
	public static void addParticipantToList(Context context, String profile){
		ParticipantHandler participantHandler = new ParticipantHandler(context);
		if (ParticipantList == null) {
			ParticipantList = new ArrayList<String>();
		}
		if (!ParticipantList.contains(profile)) {
			ParticipantList.add(profile);
		}
		participantHandler.updateParticipantList(ParticipantList);
	}

	public static String getDefaultLanguage() {
		if (Locale.getDefault().getLanguage().equals("tr")) {
			return "tr";
		} else {
			return "en";
		}
	}

	/**
	 * Verilen InputStream'i String'e çevirir.
	 * 
	 * @param inputStream
	 * @return String
	 */
	public static String convertInputStreamToString(InputStream inputStream) {
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			result = stringBuilder.toString();

		} catch (Exception e) {
			Log.w(TAG, e.toString());
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	public static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	/*
	 * public static void setMaxLinesWithEllipsize(TextView text,int max_line){
	 * 
	 * ViewTreeObserver vto = text.getViewTreeObserver();
	 * vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	 * 
	 * @Override public void onGlobalLayout() { ViewTreeObserver obs =
	 * snippet.getViewTreeObserver(); obs.removeGlobalOnLayoutListener(this);
	 * if(snippet.getLineCount() > 3) { int lineEndIndex =
	 * snippet.getLayout().getLineEnd(2); String text =
	 * snippet.getText().subSequence(0, lineEndIndex-3) +"...";
	 * snippet.setText(text); } } });
	 * 
	 * }
	 */

}
