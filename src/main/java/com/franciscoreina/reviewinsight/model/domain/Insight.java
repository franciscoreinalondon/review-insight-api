package com.franciscoreina.reviewinsight.model.domain;

import lombok.Data;

import java.util.List;

@Data
public class Insight {
    private String summary;
    private List<Problem> topProblems;
}
