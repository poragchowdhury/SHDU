
/**
 * Created by nandofioretto on 11/2/16.
 */
public final class Parameters {
	private static int days = 1;
    private static int horizon = 60*24*days;
    private static String deviceDictionaryPath = "inputs/DeviceDictionary.json";
    private static String preferencesPath = "inputs/Preferences_test.json";
    private static String settingsPath = "inputs/Settings.json";
    private static double[] priceSchema =
        {0.198, 0.198, 0.198, 0.198, 0.225, 0.225, 0.249, 0.849, 0.849, 0.225, 0.225, 0.198};

    public static int getHorizon() {
        return horizon;
    }

    public static double[] getPriceSchema() {
        return priceSchema;
    }

    public static String getDeviceDictionaryPath() {
        return deviceDictionaryPath;
    }
    
    public static String getPreferencesPath() {
        return preferencesPath;
    }
    
    public static String getSettingsPath() {
        return settingsPath;
    }
    
}
