package com.urosrelic.auth.model;

import com.urosrelic.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private Role role;
    private String password;
    private String[] orders;
}
