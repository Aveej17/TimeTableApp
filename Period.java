public class Period {
    String subject;
    String teacher;

    public Period(String subject, String teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "[" + subject + " - " + teacher + "]";
    }
}