package inventory.system.repositories;

import inventory.system.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierCategory extends JpaRepository<Supplier, Long> {
}
