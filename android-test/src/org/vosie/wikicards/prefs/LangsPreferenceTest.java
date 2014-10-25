package org.vosie.wikicards.prefs;

import org.vosie.wikicards.Constants;
import org.vosie.wikicards.MainActivity;
import org.vosie.wikicards.utils.LanguageUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.AttributeSet;

public class LangsPreferenceTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private static class ExLangsPreference extends LangsPreference {

    public boolean preventInitialValue = false;
    public SharedPreferences sharedPrefs;
    public String persistedStringValue = null;

    public ExLangsPreference(Context context, AttributeSet attrs,
            boolean noInitialValue, SharedPreferences sharedPrefs) {
      super(context, attrs);
      this.preventInitialValue = noInitialValue;
      this.sharedPrefs = sharedPrefs;
    }

    public void callOnDialogClosed(boolean positiveResult) {
      this.onDialogClosed(positiveResult);
    }

    public void callOnSetInitialValue(boolean restoreValue,
            Object defaultValue) {
      this.onSetInitialValue(restoreValue, defaultValue);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
      if (!preventInitialValue) {
        super.onSetInitialValue(restoreValue, defaultValue);
      }
    }

    // mock this to make getSharedPreferences work. It depends on
    // PrefereceManager which is created by PreferenceActivity and I don't have
    // it.
    @Override
    public SharedPreferences getSharedPreferences() {
      return sharedPrefs;
    }

    // mock this to make getEditor work. It depends on PrefereceManager which is
    // created by PreferenceActivity and I don't have it.
    @Override
    public Editor getEditor() {
      return sharedPrefs.edit();
    }

    @Override
    protected boolean persistString(String value) {
      persistedStringValue = value;
      return true;
    }
  }

  private SharedPreferences sharedPrefs;

  public LangsPreferenceTest() {
    super(MainActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(
            getActivity());
    // remove all preference before testing.
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.clear().commit();

    super.setUp();
  }

  public void testConstructor() {
    LangsPreference target = new LangsPreference(this.getActivity(), null);
    CharSequence[] entries = target.getEntries();
    assertEquals(Constants.SUPPORTED_LANGUAGES.length, entries.length);
    CharSequence[] entryValues = target.getEntryValues();
    assertEquals(Constants.SUPPORTED_LANGUAGES.length, entryValues.length);
    String defaultValue = target.getValue();
    assertNotNull(defaultValue);
    assertEquals(LanguageUtils.getDefaultLangCode(), defaultValue);
    assertNotNull(target.getSummary());
  }

  public void testDialogClosed() {

    ExLangsPreference target = new ExLangsPreference(getActivity(), null, true,
            sharedPrefs);
    target.setKey("pref_key_native_tongue");
    target.callOnDialogClosed(false);

    assertNull(target.persistedStringValue);

    // set the value to base to force the code in OnDialogClosed to persist the
    // new value.
    target.setValue("base");

    target.callOnDialogClosed(true);

    assertNotSame("base", target.persistedStringValue);
  }

  public void testSetInitialValue() {
    ExLangsPreference target = new ExLangsPreference(getActivity(), null,
            false, sharedPrefs);
    target.setKey("pref_key_native_tongue");
    target.callOnSetInitialValue(true, "zh");
    // We will still have null value here because it doesn't set the value to
    // the shared preferences.
    assertNull(sharedPrefs.getString("pref_key_native_tongue", null));
    target.callOnSetInitialValue(false, "zh");
    // We had intercept the persistString method, just compare here is fine.
    assertEquals("zh", target.persistedStringValue);
  }
}
