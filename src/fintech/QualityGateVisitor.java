package fintech;

public class QualityGateVisitor implements ProjectVisitor{
    private int sourceFiles;
    private int testFiles;
    private int totalComplexity;
    private int criticalSecurityIssues;

    @Override
    public void visit(SourceCodeFile file) {
        sourceFiles++;
        totalComplexity += file.getComplexity();

        if (file.isHasSecurityRisk()){
            criticalSecurityIssues++;
        }
        if(file.hasHardcodedCredentials()){
            criticalSecurityIssues++;
        }
    }

    @Override
    public void visit(TestFile file) {
        testFiles++;
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.hasExposedCredentials()){
            criticalSecurityIssues++;
        }
        if (file.hasInsecureSettings()){
            criticalSecurityIssues++;
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.isVulnerable()){
            criticalSecurityIssues++;
        }
    }

    @Override
    public void visit(Directory directory) {
        for (int i = 0; i < directory.getElements().size(); i++){
            ProjectElement element = directory.getElements().get(i);
            element.accept(this);
        }
    }

    public void printReport() {
        double averageComplexity = 0.0;
        if (sourceFiles > 0) {
            averageComplexity = (double) totalComplexity / sourceFiles;
        }

        double testSourceRatio = 0.0;
        if (sourceFiles > 0) {
            testSourceRatio = (double) testFiles / sourceFiles;
        }

        boolean securityPass;
        if (criticalSecurityIssues == 0) {
            securityPass = true;
        } else {
            securityPass = false;
        }

        boolean complexityPass;
        if (averageComplexity <= 10) {
            complexityPass = true;
        } else {
            complexityPass = false;
        }

        boolean testsPass;
        if (testSourceRatio >= 0.40) {
            testsPass = true;
        } else {
            testsPass = false;
        }

        if (securityPass) {
            System.out.println("Security: PASS");
        } else {
            System.out.println("Security: FAIL");
        }

        if (complexityPass) {
            System.out.println("Complexity: PASS");
        } else {
            System.out.println("Complexity: FAIL");
        }

        if (testsPass) {
            System.out.println("Tests: PASS");
        } else {
            System.out.println("Tests: FAIL");
        }

        if (securityPass && complexityPass && testsPass) {
            System.out.println("FINAL RESULT: PASSED");
        } else {
            System.out.println("FINAL RESULT: FAILED");
        }
    }
}
