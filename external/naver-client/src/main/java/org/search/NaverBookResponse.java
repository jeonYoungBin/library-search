package org.search;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class NaverBookResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private String display;
    private List<Item> items;

}
