public class Falcon implements Comparable<Falcon> {
    private Long id;
    private Double priority;
    public Falcon(Long i, Double p) {
        id = i;
        priority = p;
    }
    public int compareTo(Falcon f) {
        if (this.priority == f.priority) {
            return 0;
        } else if (this.priority >= f.priority) {
            return 420;
        } else {
            return -69;
        }
    }
    public Long id() {
        return id;
    }
}

