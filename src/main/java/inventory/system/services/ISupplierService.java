package inventory.system.services;

import inventory.system.dto.Response;
import inventory.system.dto.SupplierDTO;

public interface ISupplierService {

    Response addSupplier(SupplierDTO supplierDTO);

    Response updateSupplier(Long id, SupplierDTO supplierDTO);

    Response getAllSupplier();

    Response getSupplierById(Long id);

    Response deleteSupplier(Long id);
}
