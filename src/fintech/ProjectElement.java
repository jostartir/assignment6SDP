package fintech;

public interface ProjectElement {
    void accept(ProjectVisitor visitor);
}

