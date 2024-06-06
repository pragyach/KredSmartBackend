package com.kredsmart.dataaggregator.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonIgnore
    private Long id;
//    @CreationTimestamp
//    @JsonIgnore
    private LocalDateTime createdAt;
//    @CreationTimestamp
//    @JsonIgnore
    private LocalDateTime updatedAt;
//    @Version
//    @JsonIgnore
    private Integer version;

}
