package fintech;

public class SourceCodeFile implements ProjectElement{
    private final String fileName;
    private final int linesOfCode;
    private final int complexity;
    private final boolean hasSecurityRisk;

    public SourceCodeFile(String fileName,int linesOfCode, int complexity, boolean hasSecurityRisk){
        this.fileName = fileName;
        this.linesOfCode = linesOfCode;
        this.complexity = complexity;
        this.hasSecurityRisk = hasSecurityRisk;
    }

    public String getFileName(){
        return fileName;
    }
    public int getLinesOfCode(){
        return linesOfCode;
    }
    public int getComplexity(){
        return complexity;
    }
    public boolean isHasSecurityRisk(){
        return hasSecurityRisk;
    }
}
