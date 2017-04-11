package com.teraim.strand;

import java.util.Timer;
import java.util.TimerTask;

import com.teraim.strand.M_Activity.CheckSaveStatusTask;
import com.teraim.strand.dataobjekt.ArtListaProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

/**
 * 
 * @author Terje
 *
 * Class saving constants etc needed for this App globally.
 */
public class Strand {

	//The root folder for the SD card is in the global Environment.
	private final static String path = Environment.getExternalStorageDirectory().getPath();
	//Remember to always add system root path before any app specific path!

	//Root for Strand
	public final static String STRAND_ROOT_DIR = path+"/strand/";


	//Root for the data objects storing data per provyta.
	public final static String DATA_ROOT_DIR = path+"/strand/data/";

	//Root for pictures.
	public static final String PIC_ROOT_DIR = path+"/strand/bilder/";


	public static final String KEY_PY_PARCEL = "com.teraim.strand.py_object";

	public static final String KEY_RUTA_ID= "ruta_id";

	public static final String KEY_PROVYTA_ID = "provyta_id";

	public static final String KEY_LAG_ID = "lag_id";

	public static final String KEY_INVENTERARE = "inventerare";

	public static final String KEY_CURRENT_PY = "py_id";

	public static final String KEY_PIC_NAME = "pic_name";

	public static final String KEY_CURRENT_TABLE = "curr_table";

	public static final String KEY_STATE = "key_state";

	public static final String KEY_CHAR = "key_char";

	public static final String KEY_ZONE_DISPLAY_STATE = "zone_display_state";

	public static final String KEY_HABITAT_DISPLAY_STATE = "habitat_display_state";

	public static final String KEY_HABITAT_DISPLAY_STATE_DYN = "habitat_display_table_dyn";

	public static final String KEY_PREV_ROW = "prev_row";

	
	//Configuration
	
	//30 seconds between saves.
	public static final int SAVE_INTERVAL = 30;

	public static final int ARTER = 1;
	public static final int TRÄD = 2;
	public static final int BUSKAR = 3;
	public static final int GRAMINIDER = 4;

	public static final int LAVAR = 5;

	public static final int MOSSOR = 6;

	public static final int ORTER = 7;

	public static final int RIS = 8;

	protected static final int ORMBUNKAR = 9;



	
	private static Timer timer = null;

	//Class for saving data regularly
	public static	class ContinousSave extends TimerTask {
		public void run() {
			if (currentProvyta!=null ) {
				if(!currentProvyta.isSaved())
					//Spara värden
					Persistent.onSave(currentProvyta);
			}
		}
	}



	public static class PersistenceHelper {
		public static final String UNDEFINED = "";
		SharedPreferences sp;
		public PersistenceHelper(Context ctx) {
			sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			if (ctx == null)
				Log.e("Strand","Context null in getdefaultsharedpreferences!");
		}

		public String get(String key) {
			return sp.getString(key,UNDEFINED);
		}

		public void put(String key, String value) {
			sp.edit().putString(key,value).commit();
		}

	}

	private static Provyta currentProvyta=null;

	public static void setCurrentProvyta(Provyta py) {
		currentProvyta = py;
		//start save timer.
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new ContinousSave(),30, 1000*SAVE_INTERVAL);
		}
	}

	public static Provyta getCurrentProvyta(Context c) {
		if(currentProvyta==null) {
			if (c==null)
				Log.e("Strand","CONTEXT was null in Strand!!");
			PersistenceHelper ph = new PersistenceHelper(c);
			Log.e("Strand","Provyta null in Stran.getCurrent..");
			//Try to load from saved PY_ID.
			String pycID = ph.get(Strand.KEY_CURRENT_PY);
			//load if exists..
			if (pycID!=PersistenceHelper.UNDEFINED)
				currentProvyta = Persistent.onLoad(pycID);

		} 
		return currentProvyta;
	}

	private static ArtListaProvider ap=null;

	public static ArtListaProvider getArtListaProvider() {
		return ap;
	}

	public static void setCurrentArtListaProvider(ArtListaProvider ap) {
		Strand.ap=ap;
	}

	public static int getInt(String s) {
		return s!=null&&s.length()>0?Integer.parseInt(s):0;				
	}

	public static float getFloat(String s) {
		return s!=null&&s.length()>0?Float.parseFloat(s):0;				

	}



}
