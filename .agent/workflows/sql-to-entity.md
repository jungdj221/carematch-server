---
description: 사용자가 SQL DDL(CREATE TABLE 등)을 제공했을 때 Java Entity 클래스를 생성하는 프로세스
---
# SQL 변환 워크플로우 (SQL to Entity)

사용자가 `CREATE TABLE` 등 SQL을 입력하며 Entity 클래스 생성을 요청할 경우 다음 단계에 따라 코드를 작성합니다.

1. **테이블명 및 컬럼명 추출**: SQL에서 테이블명과 컬럼명을 분석합니다.
2. **Java Entity 클래스 작성**:
   - `CamelCase`로 필드명 변환
   - 날짜 타입은 `LocalDateTime`으로 단일화 고정.
   - 클래스 상단에 반드시 다음 어노테이션 적용: `@Data`, `@Entity`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@DynamicInsert`, `@Builder`, `@Table(name = "sql상의_테이블명")`
   - PK 매핑: `@Id`와 함께 `@GeneratedValue(strategy = GenerationType.IDENTITY)` 적용.
   - 컬럼 매핑: 필드 위에 `@Column(name = "sql상의_컬럼명")` 적용.
3. **연관관계 매핑 (FK)**:
   - 외래 키가 있는 경우 객체 참조 형태로 매핑합니다.
   - `// TODO: 1:1 혹은 N:1 혹은 N:M 관계 설정 필요 및 @JoinColumn 주석 필수 포함.` 메시지를 반드시 남깁니다.
4. **예외 처리**:
   - JPA 단일 처리가 모호한 필드(List 등 JSON 매핑 등)가 있을 경우, 해당 필드는 전체 주석 처리 후 `// 사용자 검토 필요` 메시지를 남깁니다.
5. **결과 제공**: 사용자에게 작성된 Entity 코드를 제공하고, 컴파일 오류 확인을 제안합니다.
