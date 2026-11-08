package tothb.com.parkingapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController
{
    private final VehicleRepository repo;

    public VehicleController(VehicleRepository repo)
    {
        this.repo = repo;
    }

    private static String normalize(String plate)
    {
        return plate == null ? "" : plate.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    @GetMapping
    public List<Vehicle> all()
    {
        return repo.findAll();
    }

    @GetMapping("/{plate}")
    public ResponseEntity<Vehicle> check(@PathVariable String plate)
    {
        return repo.findByPlate(normalize(plate))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody VehicleRequest req)
    {
        String plate = normalize(req.plate());
        if (plate.length() < 2 || plate.length() > 12)
        {
            return ResponseEntity.badRequest().body("Érvénytelen rendszám");
        }

        if (repo.existsByPlate(plate))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ez a rendszám már létezik");
        }

        Vehicle saved = repo.save(new Vehicle(plate, req.owner(), req.allowed()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Logn id)
    {
        if (!repo.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        repo.deleteAllById(id);
        return ResponseEntity.noContent().build();
    }
}
