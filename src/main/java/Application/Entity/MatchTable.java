package Application.Entity;


import jakarta.persistence.*;

@Entity
public class MatchTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchObject;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
// Konstruktory, gettery, settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchObject() {
        return matchObject;
    }

    public void setMatchObject(String matchObject) {
        this.matchObject = matchObject;
    }
}
