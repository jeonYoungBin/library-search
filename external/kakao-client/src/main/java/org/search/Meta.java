package org.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
    @JsonProperty("is_end")
    private boolean isEnd;
    @JsonProperty("pageable_count")
    private Integer pageableCount;
    @JsonProperty("total_count")
    private Integer totalCount;
}
