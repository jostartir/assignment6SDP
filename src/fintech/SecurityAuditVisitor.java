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
}
