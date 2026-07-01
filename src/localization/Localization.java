package localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
	
	/**
	 * 
	 * {@code}ResourceBundle lang = Localization.returnBundle();
	 */
	public static ResourceBundle returnBundle() {
		Locale currentLocale = Locale.getDefault();
		String langCode = currentLocale.getLanguage();
		
		
		//Add other languages later maybe switch to switch construction or smth
		if (!langCode.equals("lv")) {
		    langCode = "lv"; 
		}
		
		return ResourceBundle.getBundle("localization." + langCode);
	}
}
