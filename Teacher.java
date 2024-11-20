
public class Teacher {
    String name;
    boolean isPrimary;
    String subject;
    int periodsHandled; // For attendance tracking

    public Teacher(String name, boolean isPrimary, String subject) {
        this.name = name;
        this.isPrimary = isPrimary;
        this.subject = subject;
        this.periodsHandled = 0;
    }

    public String getName() {
        return this.name;
    }
}