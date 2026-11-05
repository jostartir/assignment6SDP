import fintech.*;

public class Main {
    public static void main(String[] args) {
        Directory project = new Directory("fintech-project");

        Directory sourceDirectory = new Directory("src");
        sourceDirectory.addElement(new SourceCodeFile("PaymentService.java", 20, 8, false, false));
        sourceDirectory.addElement(new SourceCodeFile("UserService.java", 10, 4, false, false));

        Directory testsDirectory = new Directory("tests");
        testsDirectory.addElement(new TestFile("PaymentServiceTest.java", 30, 4));

        Directory configDirectory = new Directory("config");
        configDirectory.addElement(new ConfigurationFile("application.properties", false, false));

        project.addElement(sourceDirectory);
        project.addElement(testsDirectory);
        project.addElement(configDirectory);

        project.addElement(new ConfigurationFile("pom.xml", false, false));

        project.addElement(new DependencyFile("junit-jupiter", "5.11.0", false));

        SecurityAuditVisitor securityVisitor = new SecurityAuditVisitor();
        project.accept(securityVisitor);
        securityVisitor.printReport();

        ProjectStatisticsVisitor statisticsVisitor = new ProjectStatisticsVisitor();
        project.accept(statisticsVisitor);
        statisticsVisitor.printReport();

        QualityGateVisitor qualityVisitor = new QualityGateVisitor();
        project.accept(qualityVisitor);
        qualityVisitor.printReport();

        ComplianceVisitor complianceVisitor = new ComplianceVisitor();
        project.accept(complianceVisitor);
        complianceVisitor.printReport();

        TechnicalDebtVisitor debtVisitor = new TechnicalDebtVisitor();
        project.accept(debtVisitor);
        debtVisitor.printReport();
    }
}