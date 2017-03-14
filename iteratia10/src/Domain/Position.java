package Domain;

// Clasa enitate Position
public class Position implements IBaseEntity<Integer>{
    private Integer id;
    private String name;
    private String type;

    public Position() {}
    // Constructor
    public Position(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Get/Set function
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() {
        return this.name;
    }
    public void setName(String name) { this.name = name; }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    // Suprascrierea metodei toString necesara pt afisarea unei entitati
    public String toString() {
        return "Position: " + getId() + " " + name + " " + type;
    }
}
