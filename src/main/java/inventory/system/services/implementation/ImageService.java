package inventory.system.services.implementation;

import inventory.system.config.StorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final Path root;

    public ImageService(StorageConfig config) throws IOException {
        this.root = config.getImagePath();
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    public String save(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destination = this.root.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
