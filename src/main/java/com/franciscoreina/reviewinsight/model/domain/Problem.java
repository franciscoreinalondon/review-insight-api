package com.franciscoreina.reviewinsight.model.domain;

import lombok.Data;

import java.util.List;

@Data
public class Problem {
    private String name;
    private int count;
    private List<String> examples;
}