public class PetTypeResponseDto {
    private String petTypeCode;
    private String petTypeName;
    private String parentTypeCode;  // 부모의 전체 객체 대신 코드만 포함
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 