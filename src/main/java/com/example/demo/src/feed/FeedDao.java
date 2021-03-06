package com.example.demo.src.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.src.feed.model.GetFeedRes;

import javax.sql.DataSource;

@Repository
public class FeedDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetFeedRes getFeedDetail(int userIdx, int feedId){
        GetFeedRes getFeedRes = null;

        String getFeedDetailQuery = "" +
                "select *\n" +
                "from\n" +
                "    (select user.userIdx, userId, profileImgUrl, temperature, retouchedDegree, review, title, longitude, latitude, price,\n" +
                "            CONCAT(DATE_FORMAT(startPeriod, '%Y.%m.%d'),\" - \",DATE_FORMAT(endPeriod, '%Y.%m.%d')) as period,\n" +
                "            CASE\n" +
                "                WHEN TIMESTAMPDIFF(minute,feed.createdAt,now()) < 60\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(minute,feed.createdAt,now()),'분 전')\n" +
                "                WHEN TIMESTAMPDIFF(hour,feed.createdAt,now()) < 24\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(hour,feed.createdAt,now()),'시간 전')\n" +
                "                WHEN TIMESTAMPDIFF(day,feed.createdAt,now()) < 31\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(day,feed.createdAt,now()),'일 전')\n" +
                "                WHEN TIMESTAMPDIFF(month,feed.createdAt,now()) < 12\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(month,feed.createdAt,now()),'달 전')\n" +
                "                ELSE CONCAT(TIMESTAMPDIFF(year,feed.createdAt,now()),'년 전')\n" +
                "            END AS createdAt\n" +
                "\n" +
                "    from feed join user\n" +
                "    on feed.userIdx = user.userIdx\n" +
                "    where feed.id = ?) as userFeedInfo\n" +
                "join\n" +
                "    (select count(feed.id) as likeNum\n" +
                "    from feed join feedLike\n" +
                "    on feed.id = feedLike.feedId\n" +
                "    where feedLike.isLiked = 'Y' and feed.id = ?) as likeNum\n" +
                "join\n" +
                "    (select exists(select user.userId\n" +
                "    from feedLike join user\n" +
                "    on feedLike.userIdx = user.userIdx\n" +
                "    where user.isDeleted = 'N' and feedLike.isLiked = 'Y' and user.userIdx = ? ) as isLiked) as isLiked #접속중인 사용자 idx\n" +
                "join\n" +
                "    (select count(feed.id) as viewNum\n" +
                "    from feed join viewFeed\n" +
                "    on feed.id = viewFeed.feedId\n" +
                "    where feed.id = ?) as viewNum\n" +
                "join\n" +
                "    (select count(jjim.id) as savedNum\n" +
                "    from feed join jjim\n" +
                "\n" +
                "    on feed.id = jjim.feedId\n" +
                "    where feed.id =?) as savedNum\n" +
                "join\n" +
                "    (select count(comment.id) as commentNum\n" +
                "    from feed join comment\n" +
                "    on feed.id = comment.feedId\n" +
                "    where comment.isDeleted = 'N' and feed.id =?) as commentNum\n" +
                "join\n" +
                "    (select exists(select report.userIdx\n" +
                "    from report join user\n" +
                "    on report.userIdx = user.userIdx\n" +
                "    where report.reported = 'Y' and user.userIdx = ?) as isReported) as isReported #접속중인 사용자 idx\n" +
                "join\n" +
                "    (select count(feed.id) as feedImgNum\n" +
                "    from feed join feedImg\n" +
                "    on feed.id = feedImg.feedId\n" +
                "    where feed.isDeleted = 'N' and feedImg.isDeleted = 'N' and feed.id = ? ) as feedImgNum;";
        Object[] getFeedDetailParams = new Object[]{feedId,feedId,userIdx,feedId,feedId,feedId,userIdx,feedId};
        getFeedRes = this.jdbcTemplate.queryForObject(getFeedDetailQuery,
                (rs, rowNum) -> new GetFeedRes(
                        rs.getInt("userIdx"),
                        rs.getString("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getInt("temperature"),
                        rs.getInt("retouchedDegree"),
                        rs.getString("review"),
                        rs.getString("title"),
                        rs.getString("longitude"),
                        rs.getString("latitude"),
                        rs.getInt("price"),
                        rs.getString("period"),
                        rs.getInt("likeNum"),
                        rs.getBoolean("isLiked"),
                        rs.getString("createdAt"),
                        rs.getInt("viewNum"),
                        rs.getInt("savedNum"),
                        rs.getInt("commentNum"),
                        rs.getBoolean("isReported"),
                        rs.getInt("feedImgNum")),
                getFeedDetailParams);
        String getFeedProsQuery = "" +
                "select prosCons.name\n" +
                "from feedProsCons join prosCons\n" +
                "on feedProsCons.prosConsId = prosCons.id\n" +
                "where feedProsCons.isDeleted = 'N' and prosCons.type = 'P' and feedId = ?;";
        getFeedRes.setPros(this.jdbcTemplate.queryForList(getFeedProsQuery, String.class,  feedId));

        String getFeedConsQuery ="" +
                "select prosCons.name\n" +
                "from feedProsCons join prosCons\n" +
                "on feedProsCons.prosConsId = prosCons.id\n" +
                "where feedProsCons.isDeleted = 'N' and prosCons.type = 'C' and feedId = ?;";
        getFeedRes.setCons(this.jdbcTemplate.queryForList(getFeedConsQuery, String.class,  feedId));

        String getFeedImgUrlQuery ="select feedImg.feedImgUrl as imgUrl\n" +
                "    from feed join feedImg\n" +
                "    on feed.id = feedImg.feedId\n" +
                "    where feed.isDeleted = 'N' and feedImg.isDeleted = 'N' and feed.id = ?;";
        getFeedRes.setFeedImgUrls(this.jdbcTemplate.queryForList(getFeedImgUrlQuery, String.class,  feedId));

        return getFeedRes;
    }

}