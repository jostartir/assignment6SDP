package fintech;

public class DependencyFile implements ProjectElement {
    private final String packageName;
    private final String version;
    private final boolean vulnerable;

    public DependencyFile(String packageName, String version, boolean vulnerable) {
        this.packageName = packageName;
        this.version = version;
        this.vulnerable = vulnerable;
    }
}