package com.example.demo.src.feed.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverShoppingRes {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item implements Comparable<Item>{
        private String title;
        private String link;
        private String image;
        private String lprice;
        private String hprice;
        private String mallName;
        private String productId;
        private String productType;
        private String brand;
        private String maker;
        private String category1;
        private String category2;
        private String category3;
        private String category4;

        // 최저가 순으로 정렬시 필요
        @Override
        public int compareTo(@NotNull Item o) {
            int targetPrice = Integer.parseInt(o.getLprice());
            int price = Integer.parseInt(lprice);
            if(price == targetPrice) return 0;
            else if (price > targetPrice) return 1;
            else return -1;
        }
    }
}

