// package com.petpal.petpalapp.controller;

// import java.util.List;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.petpal.petpalapp.domain.PetSitter;
// import com.petpal.petpalapp.service.PetSitterService;
// import com.petpal.petpalapp.service.PetSitterServiceService;
// import com.petpal.petpalapp.service.PetSitterService;
// // import com.petpal.petpalapp.dto.PetSitterRequestDTO;
// // import com.petpal.petpalapp.dto.PetSitterServiceRequestDTO;
// // import com.petpal.petpalapp.dto.PetSitterResponseDTO;

// import org.springframework.http.ResponseEntity;
// import com.petpal.petpalapp.dto.ApiResponse;

// import lombok.Getter;
// import lombok.Setter;
// import lombok.RequiredArgsConstructor;

// // DTO
// @Getter
// @Setter
// public class PetSitterRegistrationRequest {
//     private PetSitterRequestDTO petSitterInfo;
//     private List<PetSitterServiceRequestDTO> services;
// }

// // Controller
// @RestController
// @RequestMapping("/api/v1/pet-sitters")
// @RequiredArgsConstructor
// public class PetSitterRegistrationRequestController {
//     private final PetSitterService petSitterService;
//     private final PetSitterServiceService petSitterServiceService;

//     @PostMapping("/register")
//     public ResponseEntity<ApiResponse> registerPetSitterWithServices(@RequestBody PetSitterRegistrationRequest request) {
//         try {
//             PetSitterResponseDTO registeredPetSitter = petSitterService.registerPetSitterWithServices(request);
//             return ResponseEntity.ok(new ApiResponse(200, "펫시터가 성공적으로 등록되었습니다.", registeredPetSitter));
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(new ApiResponse(400, e.getMessage()));
//         }
//     }
// }

// // Service
// @Service
// @Transactional
// @RequiredArgsConstructor
// public class PetSitterService {
//     private final PetSitterRepository petSitterRepository;
//     private final PetSitterServiceService petSitterServiceService;

//     @Transactional
//     public PetSitterResponseDTO registerPetSitterWithServices(PetSitterRegistrationRequest request) {
//         // 1. 펫시터 등록
//         PetSitter petSitter = new PetSitter();
//         petSitter.setUserId(request.getPetSitterInfo().getUserId());
//         petSitter.setName(request.getPetSitterInfo().getName());
//         petSitter.setPhoneNumber(request.getPetSitterInfo().getPhoneNumber());
//         petSitter.setIntroduction(request.getPetSitterInfo().getIntroduction());
//         petSitter.setLocationCodeId(request.getPetSitterInfo().getLocationCodeId());
//         petSitter.setLocationCodeGroupId(request.getPetSitterInfo().getLocationCodeGroupId());

//         PetSitter savedPetSitter = petSitterRepository.save(petSitter);

//         // 2. 서비스 등록
//         for (PetSitterServiceRequestDTO serviceRequest : request.getServices()) {
//             serviceRequest.setPetSitterId(savedPetSitter.getPetSitterId());
//             petSitterServiceService.save(serviceRequest);
//         }

//         // 3. 응답 데이터 생성
//         PetSitterResponseDTO response = new PetSitterResponseDTO();
//         response.setPetSitterId(savedPetSitter.getPetSitterId());
//         response.setName(savedPetSitter.getName());
//         response.setPhoneNumber(savedPetSitter.getPhoneNumber());
//         response.setIntroduction(savedPetSitter.getIntroduction());
//         response.setLocationCodeId(savedPetSitter.getLocationCodeId());
//         response.setLocationCodeGroupId(savedPetSitter.getLocationCodeGroupId());

//         return response;
//     }
// }