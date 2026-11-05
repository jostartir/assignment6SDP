package fintech;

public class TestFile implements ProjectElement {
    private final String fileName;
    private final int linesOfCode;
    private final int testCount;

    public TestFile(String fileName, int linesOfCode, int testCount) {
        this.fileName = fileName;
        this.linesOfCode = linesOfCode;
        this.testCount = testCount;
    }
    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}