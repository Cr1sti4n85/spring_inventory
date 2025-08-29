package inventory.system.services;

import inventory.system.dto.ProductDTO;
import inventory.system.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);

    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);

    Response getAllProducts();

    Response getProductById(Long id);

    Response deleteProduct(Long id);

    Response searchProduct(String input);
}
