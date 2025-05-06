package org.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String title;
    private List<String> authors;
    private String isbn;
    private String publisher;
    private String datetime;
}
