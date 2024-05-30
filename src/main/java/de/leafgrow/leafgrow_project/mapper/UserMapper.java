package de.leafgrow.leafgrow_project.mapper;



import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.domain.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(source = "roles", target = "roles")
    UserDTO toDTO(User user);

    @Mapping(source = "roles", target = "roles")
    User toEntity(UserDTO userDTO);
}
