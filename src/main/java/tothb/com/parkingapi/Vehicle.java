package tothb.com.parkingapi;

import jakarta.persistence.*;


@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
    unique = true,
            length  = 16)
    private String plate;

    @Column(length = 100)
    private String owner;

    @Column(nullable = false)
    private boolean allowed = true;

    protected Vehicle() {}

    public Vehicle(String plate, String owner, boolean allowed)
    {
        this.plate = plate;
        this.owner = owner;
        this.allowed = allowed;
    }

    public Long getId() { return id; }
    public String getOwner() { return owner; }
    public String getPlate() { return plate; }
    public boolean isAllowed() {return allowed; }

    public void setOwner(String owner) { this.owner = owner; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }

}
