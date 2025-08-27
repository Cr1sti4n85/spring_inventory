package inventory.system.services;

import inventory.system.dto.CategoryDTO;
import inventory.system.dto.Response;

public interface ICategoryService {
    Response createCategory(CategoryDTO categoryDTO);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDTO categoryDTO);

    Response deleteCategory(Long id);
}
