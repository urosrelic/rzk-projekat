package com.urosrelic.category.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GetCategoriesByIdsRequest {
    private List<String> ids;
}
