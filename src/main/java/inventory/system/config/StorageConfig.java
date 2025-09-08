package inventory.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {

    @Value("${app.image-directory}")
    private String imageDirectory;

    public Path getImagePath() {
        System.out.println(Paths.get(imageDirectory).toAbsolutePath().normalize());
        return Paths.get(imageDirectory).toAbsolutePath().normalize();
    }
}
