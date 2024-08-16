package com.vasanth.Ecommerce_Backend.payload;

import com.vasanth.Ecommerce_Backend.model.Category;
import com.vasanth.Ecommerce_Backend.model.Product;
import com.vasanth.Ecommerce_Backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommonMapper {

    CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);

    ProductDTO toProductDTO(Product product);
    Product toProductEntity(ProductDTO productDTO);

    CategoryDTO toCategoryDTO(Category category);
    Category toCategoryEntity(CategoryDTO categoryDTO);

    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);


}
