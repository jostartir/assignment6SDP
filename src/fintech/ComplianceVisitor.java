package fintech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComplianceVisitor implements ProjectVisitor{
    private final List<String> violations = new ArrayList<>();
    private final List<String> prohibitedDependencies = List.of("example-library");
    private int sourceFiles;
    private int testFiles;

    @Override
    public void visit(SourceCodeFile file) {
        sourceFiles++;

        if (file.hasHardcodedCredentials()){
            violations.add("Hardcoded credentials in: " + file.getFileName());

        }

    }

    @Override
    public void visit(TestFile file) {
        testFiles++;
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.hasExposedCredentials()){
            violations.add("Exposed credentials in: " + file.getFileName());
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.getVersion() == null ||  file.getVersion().isBlank()){
            violations.add("Dependency version is missing: " + file.getPackageName());

        }
        if (prohibitedDependencies.contains(file.getPackageName())){
            violations.add("Prohibited dependency: " + file.getPackageName());
        }

    }

    @Override
    public void visit(Directory directory) {
        for (int i = 0; i < directory.getElements().size(); i++){
            ProjectElement element = directory.getElements().get(i);
            element.accept(this);
        }

        if (directory.getName().equals("fintech-project") && sourceFiles > 0 && testFiles == 0){
            violations.add("Production source files have no test files.");

        }

    }

    public boolean isCompliant(){
        return violations.isEmpty();
    }

    public List<String> getViolations(){
        return Collections.unmodifiableList(violations);
    }

    public void printReport(){
        if (violations.isEmpty()){
            System.out.println("Compliance: PASS");
        }else{
            System.out.println("Compliance: FAIL");

            for (int i = 0; i < violations.size(); i++){
                System.out.println(violations.get(i));
            }
        }


    }
}
