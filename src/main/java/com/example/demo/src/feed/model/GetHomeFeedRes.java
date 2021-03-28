package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetHomeFeedRes {
    List<Banner> banners;
    List<SeoulTop5Feed> seoulTop5Feeds;
    List<RisingFeed> risingFeeds;
    List<MostLikedFeed> mostLikedFeeds;
    List<MostReliableFeed> mostReliableFeeds;

    @Data
    @AllArgsConstructor
    public class Banner{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    public class SeoulTop5Feed{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    private class RisingFeed{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    private class MostLikedFeed{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    private class MostReliableFeed{
        private int feedId;
        private String title;
        private String imgUrl;
        private List<String> tags;
    }

    //나중에 지우기

    public GetHomeFeedRes() {
    }
}
