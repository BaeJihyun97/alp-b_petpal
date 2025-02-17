package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.petpal.petpalapp.domain.PetSitterService;
import com.petpal.petpalapp.dto.request.PetSitterServiceRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterServiceResponseDTO;
import com.petpal.petpalapp.repository.CodeDetailRepository;

@Mapper(componentModel = "spring")
public abstract class PetSitterServiceMapper {
    @Autowired
    protected CodeDetailRepository codeDetailRepository;
    
    public PetSitterServiceResponseDTO toResponseDTO(PetSitterService service) {
        System.out.println("service : " + service);
        PetSitterServiceResponseDTO responseDTO = new PetSitterServiceResponseDTO();
        
        responseDTO.setServiceId(service.getServiceId());
        responseDTO.setPetSitterId(service.getPetSitter().getPetSitterId());
        
        // 펫 타입 코드 설정
        responseDTO.setPetTypeCodeId(service.getPetTypeCodeId());
        responseDTO.setPetTypeCodeGroupId(service.getPetTypeCodeGroupId());
        responseDTO.setPetTypeCodeName(getCodeName(service.getPetTypeCodeId(), service.getPetTypeCodeGroupId()));
        
        // 위치 코드 설정
        responseDTO.setLocationCodeId(service.getLocationCodeId());
        responseDTO.setLocationCodeGroupId(service.getLocationCodeGroupId());
        responseDTO.setLocationCodeName(getCodeName(service.getLocationCodeId(), service.getLocationCodeGroupId()));
        
        // 펫 사이즈 코드 설정
        responseDTO.setPetSizeCodeId(service.getPetSizeCodeId());
        responseDTO.setPetSizeCodeGroupId(service.getPetSizeCodeGroupId());
        responseDTO.setPetSizeCodeName(getCodeName(service.getPetSizeCodeId(), service.getPetSizeCodeGroupId()));
        
        // 서비스 요금 설정
        responseDTO.setServiceFee(service.getServiceFee());
        
        return responseDTO;
    }
    
    @Mapping(target = "serviceId", ignore = true)
    @Mapping(target = "petSitter", ignore = true)
    public abstract PetSitterService toEntity(PetSitterServiceRequestDTO requestDTO);

    protected String getCodeName(String codeId, String groupId) {
        if (codeId == null || groupId == null) {
            return "";
        }
        String codeName = codeDetailRepository.findCodeNameByCodeIdAndGroupId(codeId, groupId);
        return codeName != null ? codeName : "";
    }
}