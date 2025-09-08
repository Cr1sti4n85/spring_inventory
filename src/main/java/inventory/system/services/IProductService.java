package inventory.system.services;

import inventory.system.dto.ProductDTO;
import inventory.system.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException;

    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException;

    Response getAllProducts();

    Response getProductById(Long id);

    Response deleteProduct(Long id);

    Response searchProduct(String input);
}
