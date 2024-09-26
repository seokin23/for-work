package project.forwork.api.domain.salespost.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import project.forwork.api.domain.resume.infrastructure.enums.FieldType;
import project.forwork.api.domain.resume.infrastructure.enums.LevelType;
import project.forwork.api.domain.salespost.infrastructure.enums.SalesPostSortType;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesPostSearchCond {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private FieldType field;
    private LevelType level;
}
