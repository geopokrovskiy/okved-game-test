package com.geopokrovskiy.okved_game_test.entity;

import lombok.Data;

import java.util.List;

@Data
public class OkvedNode {
    private String code;
    private String name;
    private List<OkvedNode> items;
}
