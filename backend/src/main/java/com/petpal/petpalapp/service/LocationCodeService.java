// package com.petpal.petpalapp.service;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import lombok.RequiredArgsConstructor;
// import java.util.List;
// import com.petpal.petpalapp.domain.code.LocationCode;
// import com.petpal.petpalapp.repository.LocationCodeRepository;
// import jakarta.persistence.EntityNotFoundException;

// @Service
// @RequiredArgsConstructor
// public class LocationCodeService {
    
//     private final LocationCodeRepository locationCodeRepository;
    
//     // 전체 조회
//     public List<LocationCode> findAll() {
//         return locationCodeRepository.findAll();
//     }
    
//     // 개별 조회
//     public LocationCode findByCode(String code) {
//         return locationCodeRepository.findById(code)
//             .orElseThrow(() -> new EntityNotFoundException("LocationCode not found with code: " + code));
//     }
    
//     // parent로 조회
//     public List<LocationCode> findByParent(LocationCode parent) {
//         return locationCodeRepository.findByParent(parent);
//     }
    
//     // parent가 null인 최상위 지역 조회
//     public List<LocationCode> findByParentIsNull() {
//         return locationCodeRepository.findByParentIsNull();
//     }
    
//     // 생성
//     public LocationCode create(LocationCode locationCode) {
//         return locationCodeRepository.save(locationCode);
//     }
    
//     // 수정
//     public LocationCode update(String code, LocationCode locationCode) {
//         LocationCode existingCode = findByCode(code);
//         existingCode.setLocationName(locationCode.getLocationName());
//         existingCode.setParent(locationCode.getParent());
//         return locationCodeRepository.save(existingCode);
//     }
    
//     // 삭제
//     public void delete(String code) {
//         locationCodeRepository.deleteById(code);
//     }
// } 