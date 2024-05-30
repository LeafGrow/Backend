package de.leafgrow.leafgrow_project.mapper;



import de.leafgrow.leafgrow_project.domain.entity.Role;
import de.leafgrow.leafgrow_project.domain.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO roleDTO);
}

