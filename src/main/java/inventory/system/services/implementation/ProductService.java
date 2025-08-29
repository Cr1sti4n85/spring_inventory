package inventory.system.services.implementation;

import inventory.system.dto.ProductDTO;
import inventory.system.dto.Response;
import inventory.system.exceptions.NotFoundException;
import inventory.system.models.Category;
import inventory.system.models.Product;
import inventory.system.repositories.CategoryRepository;
import inventory.system.repositories.ProductRepository;
import inventory.system.services.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-images/";


    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) {

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        //map our dto to product entity
        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        if (imageFile != null && !imageFile.isEmpty()) {
            log.info("La imagen ya existe");
            String imagePath = saveImage(imageFile);

            System.out.println("La URL de la imagen es: " + imagePath);
            productToSave.setImageUrl(imagePath);
        }

        //save the product entity
        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Producto guardado correctamente")
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {

        //check if product exisit
        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        //check if image is associated with the product to update and upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);

            System.out.println("La URL de la imagen es: " + imagePath);
            existingProduct.setImageUrl(imagePath);
        }

        //check if category is to be chanegd for the products
        if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
            existingProduct.setCategory(category);
        }

        //check if product fields is to be changed and update
        if (productDTO.getName() != null && !productDTO.getName().isBlank()) {
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getSku() != null && !productDTO.getSku().isBlank()) {
            existingProduct.setSku(productDTO.getSku());
        }

        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStock() != null && productDTO.getStock() >= 0) {
            existingProduct.setStock(productDTO.getStock());
        }
        //update the product
        productRepository.save(existingProduct);

        //Build our response
        return Response.builder()
                .status(200)
                .message("Producto actualizado correctamente")
                .build();


    }

    @Override
    public Response getAllProducts() {

        List<Product> productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProductDTO> productDTOList = modelMapper
                .map(productList, new TypeToken<List<ProductDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();
    }

    @Override
    public Response getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        return Response.builder()
                .status(200)
                .message("success")
                .product(modelMapper.map(product, ProductDTO.class))
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {

        productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        productRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Producto eliminado correctamente")
                .build();
    }

    @Override
    public Response searchProduct(String input) {

        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(input, input);

        if (products.isEmpty()) {
            throw new NotFoundException("Producto no encontrado");
        }

        List<ProductDTO> productDTOList = modelMapper
                .map(products, new TypeToken<List<ProductDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();
    }


    //saving to the root of the project
    private String saveImage(MultipartFile imageFile) {
        //validate image and check if it is greater than 1GB
        if (!Objects.requireNonNull(imageFile.getContentType()).startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 1024) {
            throw new IllegalArgumentException("Hay un problema con la imagen, verifica que sea menor a 1GB");
        }

        //create the directory if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);

        if (!directory.exists()) {
            if (directory.mkdir()) {
                log.info("Se creó el directorio");
            } else {
                log.warn("Hubo un error al crear el directorio");
            }
        }
        //unique UUID for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //Get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //we are writing the image to this folder
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar imagen: " + e.getMessage());
        }
        return imagePath;

    }

}
