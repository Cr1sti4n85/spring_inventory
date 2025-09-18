package inventory.system.services.implementation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import inventory.system.exceptions.InvalidImageException;
import inventory.system.services.IUploadService;

@Service
public class UploadService implements IUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) {
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "webp");
        String extensions = null;
        if (file.getOriginalFilename() != null) {
            String[] splitName = file.getOriginalFilename().split("\\.");
            extensions = splitName[splitName.length - 1];
        }
        if (!allowedExtensions.contains(extensions)) {
            throw new InvalidImageException("Formato de imagen no válido.");
        }

        try {
            Map<?, ?> result = cloudinary
                    .uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("folder", "inventory-system"));

            String imageUrl = result.get("secure_url").toString();

            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen: " + e.getMessage());
        }
    }

}
