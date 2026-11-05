package fintech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TechnicalDebtVisitor implements ProjectVisitor{
    private final List<String> findings = new ArrayList<>();
    private int excessComplexity;

    @Override
    public void visit(SourceCodeFile file) {
        if (file.getComplexity() > 10){
            int excess = file.getComplexity() - 10;
            excessComplexity += excess;

            findings.add(file.getFileName() + " has complexity " + file.getComplexity() + " (above limit by " + excess + ")");
        }
    }

    @Override
    public void visit(TestFile file) {

    }

    @Override
    public void visit(ConfigurationFile file) {

    }

    @Override
    public void visit(DependencyFile file) {

    }

    @Override
    public void visit(Directory directory) {
        for (int i = 0; i < directory.getElements().size();i++){
            ProjectElement element = directory.getElements().get(i);
            element.accept(this);
        }
    }

    public  List<String> getFindings(){
        return Collections.unmodifiableList(findings);
    }

    public int getExcessComplexity(){
        return excessComplexity;
    }

    public void printReport(){
        System.out.println("Technical debt report:");

        if (findings.isEmpty()){
            System.out.println("No source files exceed complexity 10.");

        }else{
            for (int i = 0;i < findings.size(); i++){
                System.out.println(findings.get(i));
            }


        }

        System.out.println("Total excess complexity: " + excessComplexity);
    }
}
