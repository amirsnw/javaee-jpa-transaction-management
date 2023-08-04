package com.snw.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortDto implements Serializable {

    private String property;
    private Sort direction;

    public enum Sort {
        ASC("asc"),
        DESC("desc");

        private String direction;

        private Sort(String direction) {
            this.direction = direction;
        }

        @JsonCreator
        public static Sort fromString(String value) {
            return getValue(value);
        }

        public static Sort getValue(String value) {
            Sort[] arr$ = values();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                Sort sort = arr$[i$];
                if (sort.direction.equalsIgnoreCase(value)) {
                    return sort;
                }
            }
            return null;
        }

        @JsonValue
        public String fromObject(Sort sort) {
            return sort.direction;
        }
    }

}
