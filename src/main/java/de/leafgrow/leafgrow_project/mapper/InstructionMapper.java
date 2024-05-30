package de.leafgrow.leafgrow_project.mapper;



import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.dto.InstructionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstructionMapper {

    InstructionDTO toDTO(Instruction instruction);

    Instruction toEntity(InstructionDTO instructionDTO);
}
