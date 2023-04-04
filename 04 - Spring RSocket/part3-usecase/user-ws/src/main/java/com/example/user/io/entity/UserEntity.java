package com.example.user.io.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ToString
@Document(collection = "users")
//@Entity
@AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private String name;
    private float balance;

    public UserEntity(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }
}
