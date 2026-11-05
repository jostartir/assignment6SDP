package fintech;

public class ProjectStatisticsVisitor implements ProjectVisitor{
    private int totalFiles;
    private int linesOfCode;
    private int numberOfTests;
    private int dependencies;
    private int totalComplexity;
    private int sourceFiles;
    private int testFiles;

    @Override
    public void visit(SourceCodeFile file) {
        totalFiles++;
        linesOfCode += file.getLinesOfCode();
        totalComplexity += file.getComplexity();
        sourceFiles++;

    }

    @Override
    public void visit(TestFile file) {
        totalFiles++;
        testFiles++;
        linesOfCode += file.getLinesOfCode();
        numberOfTests += file.getTestCount();

    }

    @Override
    public void visit(ConfigurationFile file) {
        totalFiles++;
    }

    @Override
    public void visit(DependencyFile file) {
        totalFiles++;
        dependencies++;
    }

    @Override
    public void visit(Directory directory) {
        for (int i = 0; i < directory.getElements().size(); i++ ){
            ProjectElement element = directory.getElements().get(i);
            element.accept(this);
        }
    }

    public double getAverageComplexity(){
        if (sourceFiles == 0){
            return 0.0;

        }
        return (double) totalComplexity/sourceFiles;
    }

    public double getTestSourceRatio() {
        if (sourceFiles == 0) {
            return 0.0;
        }
        return (double) testFiles / sourceFiles;
    }

    public void printReport(){
        System.out.println("Total files: " + totalFiles);
        System.out.println("Lines of code: " + linesOfCode);
        System.out.println("Number of test: " + numberOfTests);
        System.out.println("Dependencies: " + dependencies);
        System.out.println("Average complexity: " + getAverageComplexity());
        System.out.println("Test/source ratio: " + getTestSourceRatio());
    }
}
