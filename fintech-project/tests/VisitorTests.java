import fintech.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTests {

    @Test
    void securityVisitorFindsHardcodedCredentials() {
        Directory project = new Directory("fintech-project");
        project.addElement( new SourceCodeFile("PaymentService.java", 20, 8, false, true));

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        project.accept(visitor);

        assertTrue(visitor.getIssues().toString().contains("Hardcoded credentials"));
    }

    @Test
    void securityVisitorFindsVulnerableDependency() {
        Directory project = new Directory("fintech-project");
        project.addElement(new DependencyFile("old-library", "1.0", true));

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        project.accept(visitor);

        assertTrue(visitor.getIssues().toString().contains("Vulnerable dependency"));
    }

    @Test
    void statisticsVisitorCountsFilesAndLines() {
        Directory project = new Directory("fintech-project");
        project.addElement(new SourceCodeFile("PaymentService.java", 20, 8, false, false));
        project.addElement(new TestFile("PaymentServiceTest.java", 30, 4));

        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        project.accept(visitor);
        String report = captureOutput(visitor::printReport);

        assertTrue(report.contains("Total files: 2"));
        assertTrue(report.contains("Lines of code: 50"));
        assertTrue(report.contains("Test/source ratio: 1.0"));
    }

    @Test
    void statisticsVisitorHandlesEmptyDirectory() {
        Directory emptyProject = new Directory("fintech-project");

        ProjectStatisticsVisitor visitor = new ProjectStatisticsVisitor();
        emptyProject.accept(visitor);
        String report = captureOutput(visitor::printReport);

        assertTrue(report.contains("Total files: 0"));
        assertTrue(report.contains("Lines of code: 0"));
    }

    @Test
    void qualityGatePassesWhenProjectMeetsCriteria() {
        Directory project = new Directory("fintech-project");
        project.addElement(new SourceCodeFile("PaymentService.java", 20, 8, false, false));
        project.addElement(new TestFile("PaymentServiceTest.java", 30, 4));

        QualityGateVisitor visitor = new QualityGateVisitor();
        project.accept(visitor);
        String report = captureOutput(visitor::printReport);

        assertTrue(report.contains("FINAL RESULT: PASSED"));
    }

    @Test
    void qualityGateFailsForRiskyComplexUntestedProject() {
        Directory project = new Directory("fintech-project");
        project.addElement(new SourceCodeFile("RiskyService.java", 20, 15, true, false));

        QualityGateVisitor visitor = new QualityGateVisitor();
        project.accept(visitor);
        String report = captureOutput(visitor::printReport);

        assertTrue(report.contains("Security: FAIL"));
        assertTrue(report.contains("Complexity: FAIL"));
        assertTrue(report.contains("Tests: FAIL"));
        assertTrue(report.contains("FINAL RESULT: FAILED"));
    }

    @Test
    void complianceVisitorFindsExposedCredentialsAndProhibitedDependency() {
        Directory project = new Directory("fintech-project");
        project.addElement(new ConfigurationFile("application.properties", false, true));
        project.addElement(new DependencyFile("example-library", "1.0", false));

        ComplianceVisitor visitor = new ComplianceVisitor();
        project.accept(visitor);

        String violations = visitor.getViolations().toString();
        assertTrue(violations.contains("Exposed credentials"));
        assertTrue(violations.contains("Prohibited dependency"));
    }

    @Test
    void technicalDebtVisitorFindsComplexFileInNestedDirectory() {
        Directory project = new Directory("fintech-project");
        Directory sourceDirectory = new Directory("src");
        Directory servicesDirectory = new Directory("services");

        servicesDirectory.addElement(new SourceCodeFile("ComplexService.java", 100, 14, false, false));
        sourceDirectory.addElement(servicesDirectory);
        project.addElement(sourceDirectory);

        TechnicalDebtVisitor visitor = new TechnicalDebtVisitor();
        project.accept(visitor);

        assertTrue(visitor.getFindings().toString().contains("ComplexService.java"));
        assertEquals(4, visitor.getExcessComplexity());
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            action.run();
            return capturedOutput.toString();
        } finally {
            System.setOut(originalOutput);
        }
    }
}