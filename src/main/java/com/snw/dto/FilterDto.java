package com.snw.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private String property;
    private Object value;
    private Set<Object> values = new HashSet<>();
    private Set<FilterDto> orValue = new HashSet<>();
    private SearchMode operator;

    public static FilterDto getInstance(String property, Object value, SearchMode operator) {
        return FilterDto.builder().property(property).value(value).operator(operator).build();
    }

    public static FilterDto getInstance(String property, Set<Object> values, SearchMode operator) {
        return FilterDto.builder().property(property).values(values).operator(operator).build();
    }

    public static FilterDto getInstance(String property, SearchMode operator) {
        return FilterDto.builder().property(property).operator(operator).build();
    }

    public enum SearchMode {
        EQUALS("eq"),
        NOT_EQUALS("neq"),
        BETWEEN("between"),
        CONTAINS("contains"),
        END_WITH("str_with"),
        START_WITH("end_with"),
        GREATER_THAN("gt"),
        GREATER_THAN_OR_EQUALS("ge"),
        LESS_THAN("lt"),
        LESS_THAN_OR_EQUALS("le"),
        IN("in"),
        NOT_IN("not_in"),
        IS_NULL("isn"),
        IS_NOT_NULL("inn"),
        OR("or");

        private String name;

        private SearchMode(String name) {
            this.name = name;
        }

        @JsonCreator
        public static FilterDto.SearchMode fromString(String value) {
            return getValue(value);
        }

        public static SearchMode getValue(String value) {
            SearchMode[] arr$ = values();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                SearchMode operator = arr$[i$];
                if (operator.name.equalsIgnoreCase(value)) {
                    return operator;
                }
            }

            return null;
        }

        @JsonValue
        public String fromObject(SearchMode operator) {
            return operator.name;
        }
    }
}
