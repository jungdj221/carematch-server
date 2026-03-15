# CareMatch Code Architecture Guidelines

## 1. Directory Structure (Domain-Driven Design)
- **Domain별 패키징**: `src/main/java/com/corp/carematch_server/domain/{domain_name}` (예: auth, match, user 등)
- **Global**: 전역 설정 및 공통 예외 처리는 `global` 폴더에 위치합니다 (`config`, `error`, `util` 등).

## 2. Entity 설계 컨벤션
- **기본 어노테이션 규칙**: 
  - `@Entity`, `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@DynamicInsert` 를 조합하여 사용.
  - 테이블 명시는 `@Table(name = "복수형_테이블명")` 권장 (예: `users`, `profiles`).
- **PK 정의**: `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`. 자료형은 `Long` 권장.
- **연관 관계 매핑**: 
  - 양방향 연관관계 시 편의 메서드를 반드시 작성하여 객체 상태를 동기화합니다 (예: `addPatientProfile()`).
  - 초기화가 필요한 컬렉션(List 등)은 NPE 방지를 위해 `@Builder.Default`와 함께 `new ArrayList<>()`로 명시적 초기화합니다.

## 3. 예외 및 응답 처리
- **Controller 응답**: 상태 코드 제어를 위해 `ResponseEntity<T>` 를 사용합니다.
- **Global Exception**: `global.error.GlobalExceptionHandler` 에서 `@RestControllerAdvice`를 통해 전역 예외를 처리합니다. (현재 `ResponseEntity<String>` 형태로 에러 메시지 반환).

## 4. 보안 체계
- `global.config.SecurityConfig` 를 통해 보안 설정을 관리하며 JWT 토큰 인증 체계를 사용합니다.
- Security 컨텍스트 정보는 `global.util.SecurityUtil` 등을 활용해 접근합니다.
