package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFeedLinksRes {
    List<Item> items;

    public GetFeedLinksRes() {
        items = new ArrayList<>();
    }
    public void addItem(String mallName, String link){
        items.add(new Item(mallName,link));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Item{
        private String mallName;
        private String link;
    }
}
