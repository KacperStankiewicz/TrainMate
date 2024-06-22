package pl.edu.pja.trainmate.core.config;

import java.util.Arrays;
import java.util.List;

public class Profiles {

    public static final String LOCAL = "local";
    public static final String INTEGRATION = "integration";
    public static final String DEV = "dev";
    public static final String PROD = "prod";
    protected static final List<String> AVAILABLE_PROFILES = Arrays.asList("local", "integration", "dev", "prod");

    private Profiles() {
    }
}
