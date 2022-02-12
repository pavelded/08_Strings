import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;

    @Column(name = "status_time", columnDefinition = "date")
    private Date statusTime;

    @Lob
    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    @Lob
    @Column(columnDefinition = "varchar(255)")
    private String url;

    @Lob
    @Column(columnDefinition = "varchar(255)")
    private String name;

    public Site() {
    }

    public Site(String url) {
        this.url = url;
        this.id++;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized Status getStatus() {
        return status;
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    public synchronized Date getStatusTime() {
        return statusTime;
    }

    public synchronized void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public synchronized String getLastError() {
        return lastError;
    }

    public synchronized void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public synchronized String getUrl() {
        return url;
    }

    public synchronized void setUrl(String url) {
        this.url = url;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }
}
