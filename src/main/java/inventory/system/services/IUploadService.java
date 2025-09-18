package inventory.system.services;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    String uploadImage(MultipartFile file);

}
