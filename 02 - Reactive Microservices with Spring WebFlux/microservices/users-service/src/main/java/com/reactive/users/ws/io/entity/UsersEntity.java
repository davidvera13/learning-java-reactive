package com.reactive.users.ws.io.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@ToString
@Builder
// not using "entity" -> not using hibernate annotations
// table is spring data annotation
@Table(name="users")
public class UsersEntity {

    // spring data annotation
    @Id
    private Long id;
    private String name;
    private Double balance;
}
