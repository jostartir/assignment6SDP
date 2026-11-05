package fintech;

import java.util.ArrayList;
import java.util.List;

public class Directory implements ProjectElement {
    private final String name;
    private final List<ProjectElement> elements = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addElement(ProjectElement element) {
        elements.add(element);
    }
    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}