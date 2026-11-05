package fintech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SecurityAuditVisitor implements ProjectVisitor {

    private final List<String> issues = new ArrayList<>();

    @Override
    public void visit(SourceCodeFile file){
        if (file.isHasSecurityRisk()){
            issues.add("High-risk source file: " + file.getFileName());
        }
        if (file.hasHardcodedCredentials()) {
            issues.add("Hardcoded credentials in: " + file.getFileName());
        }
    }

    @Override
    public void visit(TestFile file){

    }

    @Override
    public void visit(ConfigurationFile file){
        if (file.hasExposedCredentials()){
            issues.add("Exposed credentials in: " + file.getFileName());

        }

        if (file.hasInsecureSettings()){
            issues.add("Insecure settings in: " + file.getFileName());
        }

    }

    @Override
    public void visit(DependencyFile file) {
        if (file.isVulnerable()){
            issues.add("Vulnerable dependency: " + file.getPackageName() + " " + file.getVersion());
        }
    }

    @Override
    public void visit(Directory directory) {
        for (int i = 0; i < directory.getElements().size(); i++) {
            ProjectElement element = directory.getElements().get(i);
            element.accept(this);
        }
    }

    public List<String> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public int getSecurityScore() {
        return Math.max(0, 100 - issues.size() * 20);
    }

    public void printReport() {
        System.out.println("Security score: " + getSecurityScore() + "/100");

        if (issues.isEmpty()) {
            System.out.println("No security issues found.");
            return;
        }

        for (String issue : issues) {
            System.out.println(issue);
        }
    }
}
