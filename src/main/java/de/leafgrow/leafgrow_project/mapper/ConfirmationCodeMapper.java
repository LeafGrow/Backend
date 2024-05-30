package de.leafgrow.leafgrow_project.mapper;


import de.leafgrow.leafgrow_project.domain.dto.ConfirmationCodeDTO;
import de.leafgrow.leafgrow_project.domain.entity.ConfirmationCode;
import de.leafgrow.leafgrow_project.domain.dto.ConfirmationCodeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ConfirmationCodeMapper {

    @Mapping(source = "user.id", target = "userId")
    ConfirmationCodeDTO toDTO(ConfirmationCode confirmationCode);

    @Mapping(source = "userId", target = "user.id")
    ConfirmationCode toEntity(ConfirmationCodeDTO confirmationCodeDTO);
}
