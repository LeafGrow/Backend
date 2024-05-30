package de.leafgrow.leafgrow_project.mapper;



import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.dto.PotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, InstructionMapper.class})
public interface PotMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "instruction.id", target = "instructionId")
    PotDTO toDTO(Pot pot);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "instructionId", target = "instruction.id")
    Pot toEntity(PotDTO potDTO);
}
