package com.vasanth.Ecommerce_Backend.payload;

import com.vasanth.Ecommerce_Backend.model.*;
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

    CartDTO toCartDTO(Cart cart);
    Cart toCartEntity(CartDTO cartDTO);

    RoleDTO toRoleDTO(Role role);
    Role toRoleEntity(RoleDTO roleDTO);

    AddressDTO toAddressDTO(Address address);
    Address toAddressEntity(AddressDTO addressDTO);

}
