package be.raphcho.sharedpreferencelib;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
	
	private Context	          mContext;
	private String	          SEED;
	private SharedPreferences	preferences;
	
	public SharedPreference(Context context, String preferenceName, String cryptoSeed) {
		mContext = context;
		SEED = cryptoSeed;
		preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
	}
	
	public SharedPreference(Context context, String preferenceName) {
		this(context, preferenceName, null);
	}
	
	public void add(String key, Object obj) {
		if (obj instanceof Boolean)
			preferences.edit().putBoolean(key, (Boolean) obj).commit();
		else if (obj instanceof Integer)
			preferences.edit().putInt(key, (Integer) obj).commit();
		else if (obj instanceof Float)
			preferences.edit().putFloat(key, (Float) obj);
		else if (obj instanceof Long)
			preferences.edit().putLong(key, (Long) obj);
		else if (obj instanceof String)
			preferences.edit().putString(key, (String) obj);
	}
	public Object get(String key) {
		Object obj;
		if ((obj = preferences.getString(key, null)) != null)
			return obj;
		else if ((obj = preferences.getInt(key, -1)) != null)
			return obj;
		else if ((obj = preferences.getBoolean(key, false)) != null)
			return obj;
		else if ((obj = preferences.getFloat(key, -1)) != null)
			return obj;
		else if ((obj = preferences.getLong(key, -1)) != null)
			return obj;
		else
			return null;
	}
	
	public void addEncrypted(String key, Object obj) {
		try {
			preferences.edit().putString(key, CryptoUtils.encrypt(SEED, (String) obj));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object getDecrypted(String key) {
		try {
			return CryptoUtils.decryptString(SEED, preferences.getString(key, null).toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
