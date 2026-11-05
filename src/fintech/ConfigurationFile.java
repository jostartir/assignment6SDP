package fintech;

public class ConfigurationFile implements ProjectElement {
    private final String fileName;
    private final boolean hasInsecureSettings;
    private final boolean hasExposedCredentials;

    public ConfigurationFile(String fileName, boolean hasInsecureSettings,
                             boolean hasExposedCredentials) {
        this.fileName = fileName;
        this.hasInsecureSettings = hasInsecureSettings;
        this.hasExposedCredentials = hasExposedCredentials;
    }


    public String getFileName() { return fileName; }
    public boolean hasInsecureSettings() { return hasInsecureSettings; }
    public boolean hasExposedCredentials() { return hasExposedCredentials; }


    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}