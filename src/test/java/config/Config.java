package config;

public final class Config {
    private Config() {}

    private static String get(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    public static final String BASE_URL = get("BASE_URL", "https://www.saucedemo.com");
    public static final String STANDARD_USER = get("STANDARD_USER", "standard_user");
    public static final String LOCKED_USER = get("LOCKED_USER", "locked_out_user");
    public static final String PASSWORD = get("PASSWORD", "secret_sauce");
    public static final boolean HEADLESS = Boolean.parseBoolean(get("HEADLESS", "false"));
}
