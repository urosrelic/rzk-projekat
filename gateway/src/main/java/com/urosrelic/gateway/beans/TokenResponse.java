package com.urosrelic.gateway.beans;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class TokenResponse {
    String body;
    String message;

}
