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
    public class RisingFeed{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    public class MostLikedFeed{
        private int feedId;
        private String title;
        private String imgUrl;
    }
    @Data
    @AllArgsConstructor
    public class MostReliableFeed{
        private int feedId;
        private String title;
        private String imgUrl;
        private List<String> tags;

        public MostReliableFeed(int feedId, String title, String imgUrl) {
            this.feedId = feedId;
            this.title = title;
            this.imgUrl = imgUrl;
        }
    }

    public GetHomeFeedRes() {
    }

}
