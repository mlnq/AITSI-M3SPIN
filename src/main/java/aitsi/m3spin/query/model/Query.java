package aitsi.m3spin.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Query {
    private Declaration select;
    private List<SuchThat> suchThatList;
    private List<WithClause> withList;
}