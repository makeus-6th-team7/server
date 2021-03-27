package com.example.demo.src.feed;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.src.feed.model.*;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FeedDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public int getUserIdxOfFeed (int feedId){
        String getUserIdxOfFeedQuery = "select userIdx from feed where isDeleted = 'N' and id = ?;";
        return this.jdbcTemplate.queryForObject(getUserIdxOfFeedQuery,
                int.class,
                feedId);
    }
    public int getUserIdxOfComment (int commentId){
        String getUserIdxOfCommentQuery = "select userIdx from comment where isDeleted = 'N' and id = ?;";
        return this.jdbcTemplate.queryForObject(getUserIdxOfCommentQuery,
                int.class,
                commentId);
    }
    public int checkAirBnB(int feedId){
        String checkAirBnBQuery = "select exists(select id from feed where isDeleted = 'N' and isAirBnB = 'Y' and id = ?);";
        return this.jdbcTemplate.queryForObject(checkAirBnBQuery,
                int.class,
                feedId);
    }
    public String getAirBnBLink(int feedId){
        String checkAirBnBQuery = "select airBnBLink from feed where isDeleted = 'N' and id = ?;";
        return this.jdbcTemplate.queryForObject(checkAirBnBQuery,
                String.class,
                feedId);
    }
    public int checkFeedId (int feedId){
        String checkFeedIdQuery = "select exists(select id from feed where isDeleted = 'N' and id = ?);";
        return this.jdbcTemplate.queryForObject(checkFeedIdQuery,
                int.class,
                feedId);
    }
    public int checkCommentId (int commentId){
        String checkFeedIdQuery = "select exists(select id from comment where isDeleted = 'N' and id = ?);";
        return this.jdbcTemplate.queryForObject(checkFeedIdQuery,
                int.class,
                commentId);
    }
    public GetHomeFeedRes getPopHomeFeeds(){
        String getPopHomeFeedsQuery = "select popularFeed.id as feedId, title, feedImgUrl,retouchedDegree,temperature\n" +
                "from\n" +
                "    (select feed.id, feed.userIdx, title, retouchedDegree, feedImgUrl,count(feedLike.feedId) as likeNum\n" +
                "    from\n" +
                "         (select feed.id, userIdx,isAirBnB, title, retouchedDegree, feedImgUrl\n" +
                "            from feed join feedImg\n" +
                "            on feed.id = feedImg.feedId\n" +
                "            where feed.isDeleted = 'N'\n" +
                "            group by feed.id) as feed\n" +
                "    left outer join feedLike\n" +
                "    on feed.id = feedLike.feedId and feedLike.isLiked = 'Y'\n" +
                "    group by feed.id) as popularFeed\n" +
                "left outer join user\n" +
                "on popularFeed.userIdx = user.userIdx\n" +
                "order by likeNum desc";
        GetHomeFeedRes getHomeFeedRes;
        GetHomeFeedRes tmp = new GetHomeFeedRes();
        getHomeFeedRes= new GetHomeFeedRes(this.jdbcTemplate.query(getPopHomeFeedsQuery,
                (rs, rowNum) -> tmp.new Feed(
                        rs.getInt("feedId"),
                        rs.getString("title"),
                        rs.getString("feedImgUrl"),
                        rs.getInt("retouchedDegree"),
                        rs.getInt("temperature"))));

        return getHomeFeedRes;
    }
    public List<String> getAccomodationKeywordRecoms(String keyword){
        String getKeywordRecomsQeury = "select title\n" +
                "from feed\n" +
                "where isDeleted = 'N' and title like concat(\"%\",?,\"%\")\n" +
                "group by title;";
        return this.jdbcTemplate.queryForList(getKeywordRecomsQeury, String.class,  keyword);
    }
    public List<String> getTagKeywordRecoms(String keyword){
        String getKeywordRecomsQeury = "select tag.name\n" +
                "from tag join feed\n" +
                "on tag.id = feed.id\n" +
                "where feed.isDeleted = 'N' and name like concat(\"%\",?,\"%\")\n" +
                "group by tag.name;";
        return this.jdbcTemplate.queryForList(getKeywordRecomsQeury, String.class,  keyword);
    }
    public GetHomeFeedRes getNewHomeFeeds(){
        String getNewHomeFeedsQuery = "select newFeed.id as feedId, title, feedImgUrl, retouchedDegree, temperature\n" +
                "from\n" +
                "    (select feed.userIdx, feed.id, title, retouchedDegree, feedImgUrl, feed.createdAt\n" +
                "    from feed join feedImg\n" +
                "    on feed.id = feedImg.feedId\n" +
                "    where feed.isDeleted = 'N') as newFeed\n" +
                "join user\n" +
                "on newFeed.userIdx = user.userIdx\n" +
                "group by newFeed.id\n" +
                "order by newFeed.createdAt DESC;";
        GetHomeFeedRes getHomeFeedRes;
        GetHomeFeedRes tmp = new GetHomeFeedRes();
        getHomeFeedRes= new GetHomeFeedRes(this.jdbcTemplate.query(getNewHomeFeedsQuery,
                (rs, rowNum) -> tmp.new Feed(
                        rs.getInt("feedId"),
                        rs.getString("title"),
                        rs.getString("feedImgUrl"),
                        rs.getInt("retouchedDegree"),
                        rs.getInt("temperature"))));

        return getHomeFeedRes;
    }
    public GetSearchResultRes getSearchResults (String keyword){
        String getSearchResultsQuery = "select resultFeed.id as feedId, feedImgUrl\n" +
                "from\n" +
                "    # 태그 검색 결과\n" +
                "        ((select feed.id, feed.createdAt\n" +
                "        from feed join feedTag\n" +
                "        on feed.id = feedTag.feedId\n" +
                "        join tag\n" +
                "        on feedTag.tagId = tag.id\n" +
                "        where tag.name = ?\n" +
                "        group by feed.id)\n" +
                "    union\n" +
                "    # 숙소 검색 결과\n" +
                "        (select feed.id, feed.createdAt\n" +
                "        from feed join feedImg\n" +
                "        on feed.id = feedImg.feedId\n" +
                "        where feed.isDeleted = 'N' and feed.title like concat(\"%\",?,\"%\")\n" +
                "        group by feed.id)\n" +
                "    order by createdAt DESC) as resultFeed\n" +
                "join feedImg\n" +
                "on resultFeed.id = feedImg.feedId\n" +
                "group by resultFeed.id;\n";
        GetSearchResultRes getSearchResultRes;
        GetSearchResultRes tmp = new GetSearchResultRes();
        getSearchResultRes= new GetSearchResultRes(this.jdbcTemplate.query(getSearchResultsQuery,
                (rs, rowNum) -> tmp.new SearchResult(
                        rs.getInt("feedId"),
                        rs.getString("feedImgUrl")), keyword,keyword));

        return getSearchResultRes;
    }
    public void updateViewTable(int userIdx, int feedId){
        // 자신의 피드를 조회했는지 체크 (자신의 피드조회시 업데이트 X)
        boolean checkMyFeed = false;
        if(getUserIdxOfFeed(feedId) == userIdx) checkMyFeed = true;
        //자신의 피드가 아니라면 viewFeed 테이블 업데이트
        if(!checkMyFeed) {
            String updateViewFeedQuery = "insert into viewFeed (feedId,userIdx) values(?,?)\n" +
                    "on duplicate key update updatedAt = current_timestamp;";
            this.jdbcTemplate.update(updateViewFeedQuery, feedId, userIdx);
        }
    }
    public String getTitleFromFeedId(int feedId){
        String getTitleQuery = "select feed.title from feed where feed.id = ?;";

        return this.jdbcTemplate.queryForObject(getTitleQuery,
                String.class,
                feedId);
    }
    public GetFeedFromDao getFeedDetail(int userIdx, int feedId){
        GetFeedFromDao getFeedFromDao = null;

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
                "    where report.isReported = 'Y' and user.userIdx = ?) as isReported) as isReported #접속중인 사용자 idx\n" +
                "join\n" +
                "    (select count(feed.id) as feedImgNum\n" +
                "    from feed join feedImg\n" +
                "    on feed.id = feedImg.feedId\n" +
                "    where feed.isDeleted = 'N' and feedImg.isDeleted = 'N' and feed.id = ? ) as feedImgNum;";
        Object[] getFeedDetailParams = new Object[]{feedId,feedId,userIdx,feedId,feedId,feedId,userIdx,feedId};
        getFeedFromDao = this.jdbcTemplate.queryForObject(getFeedDetailQuery,
                (rs, rowNum) -> new GetFeedFromDao(
                        rs.getInt("userIdx"),
                        rs.getString("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getInt("temperature"),
                        rs.getInt("retouchedDegree"),
                        rs.getString("review"),
                        rs.getString("title"),
                        rs.getDouble("longitude"),
                        rs.getDouble("latitude"),
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
        getFeedFromDao.setPros(this.jdbcTemplate.queryForList(getFeedProsQuery, String.class,  feedId));

        String getFeedConsQuery ="" +
                "select prosCons.name\n" +
                "from feedProsCons join prosCons\n" +
                "on feedProsCons.prosConsId = prosCons.id\n" +
                "where feedProsCons.isDeleted = 'N' and prosCons.type = 'C' and feedId = ?;";
        getFeedFromDao.setCons(this.jdbcTemplate.queryForList(getFeedConsQuery, String.class,  feedId));

        String getFeedImgUrlQuery ="select feedImg.feedImgUrl as imgUrl\n" +
                "    from feed join feedImg\n" +
                "    on feed.id = feedImg.feedId\n" +
                "    where feed.isDeleted = 'N' and feedImg.isDeleted = 'N' and feed.id = ?;";
        getFeedFromDao.setFeedImgUrls(this.jdbcTemplate.queryForList(getFeedImgUrlQuery, String.class,  feedId));

        String getTagQuery ="select tag.name\n" +
                "from feedTag join tag\n" +
                "on feedTag.tagId = tag.id\n" +
                "where feedTag.isDeleted = 'N' and feedId = ?";
        getFeedFromDao.setTags(this.jdbcTemplate.queryForList(getTagQuery, String.class,  feedId));
        //viewFeed 테이블 업데이트
        updateViewTable(userIdx, feedId);
        return getFeedFromDao;
    }
    public GetCommentRes getComments(int userIdx, int feedId){
        String getCommentsQuery = "" +
                "select comment.id as commentId, user.userIdx, userId, profileImgUrl, content,\n" +
                "       CASE\n" +
                "                WHEN TIMESTAMPDIFF(minute,comment.createdAt,now()) < 60\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(minute,comment.createdAt,now()),'분 전')\n" +
                "                WHEN TIMESTAMPDIFF(hour,comment.createdAt,now()) < 24\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(hour,comment.createdAt,now()),'시간 전')\n" +
                "                WHEN TIMESTAMPDIFF(day,comment.createdAt,now()) < 31\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(day,comment.createdAt,now()),'일 전')\n" +
                "                WHEN TIMESTAMPDIFF(month,comment.createdAt,now()) < 12\n" +
                "                THEN CONCAT(TIMESTAMPDIFF(month,comment.createdAt,now()),'달 전')\n" +
                "                ELSE CONCAT(TIMESTAMPDIFF(year,comment.createdAt,now()),'년 전')\n" +
                "            END AS createdAt\n" +
                "from comment join user\n" +
                "on comment.userIdx = user.userIdx\n" +
                "where comment.isDeleted = 'N' and comment.feedId = ?;";
        GetCommentRes getCommentRes;
        GetCommentRes tmp = new GetCommentRes();
        getCommentRes= new GetCommentRes(this.jdbcTemplate.query(getCommentsQuery,
                (rs, rowNum) -> tmp.new Comment(
                        rs.getInt("commentId"),
                        rs.getInt("userIdx"),
                        rs.getString("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("content"),
                        rs.getString("createdAt")), feedId));


        for(GetCommentRes.Comment comment : getCommentRes.getComments()){
            // likeNum 설정
            String getCommentLikeNumQeury ="" +
                    "select count(commentId) as likeNum\n" +
                    "from commentLike\n" +
                    "where isLiked = 'Y' and commentId = ?;";
            int commentId = comment.getCommentId();
            comment.setLikeNum(this.jdbcTemplate.queryForObject(getCommentLikeNumQeury, int.class, commentId));
            //isLiked 설정
            String getCommentisLikedQuery = "" +
                    "select exists(\n" +
                    "    select user.userIdx\n" +
                    "from user join commentLike\n" +
                    "on user.userIdx = commentLike.userIdx\n" +
                    "where commentLike.isLiked = 'Y' and commentLike.commentId = ? and user.userIdx=?) as isLiked;";
            Object[] getCommentIsLikedParams = new Object[]{commentId, userIdx};
            comment.setCheckLike(this.jdbcTemplate.queryForObject(getCommentisLikedQuery, boolean.class, getCommentIsLikedParams));
        }
        // profileImgUrl 설정
        String getProfileImgUrlQuery = "select profileImgUrl from user where userIdx =?;";
        getCommentRes.setProfileImgUrl(this.jdbcTemplate.queryForObject(getProfileImgUrlQuery, String.class,  userIdx));


        return getCommentRes;
    }
    public void setFeedLike(int userIdx, int feedId){

        String checkFeedLikeRowQuery = "select exists( select feedId from feedLike where feedId = ? and userIdx = ?);";
        boolean checkFeedLikeRow = this.jdbcTemplate.queryForObject(checkFeedLikeRowQuery,boolean.class,feedId,userIdx);

        //FeedLike table에 row가 존재한다면
        if(checkFeedLikeRow) {
            //해당 row 업데이트 (isLiked: N -> Y/ Y -> N)
            String setFeedLikeQuery = "" +
                    "update feedLike\n" +
                    "set isLiked = case when isLiked = 'N' THEN 'Y' ELSE 'N' END\n" +
                    "where feedId = ? and userIdx = ?;";
            this.jdbcTemplate.update(setFeedLikeQuery, feedId, userIdx);
        }
        // 존재하지 않으면
        else{
            //새로운 row 추가
            String insertFeedLikeQuery = "insert into feedLike (feedId, userIdx)values(?,?);";
            this.jdbcTemplate.update(insertFeedLikeQuery, feedId, userIdx);
        }
    }
    public void setCommentLike(int userIdx, int commentId){

        String checkCommentLikeRowQuery = "select exists( select commentId from commentLike where commentId = ? and userIdx = ?);";
        boolean checkCommentLikeRow = this.jdbcTemplate.queryForObject(checkCommentLikeRowQuery,boolean.class,commentId,userIdx);

        //commentLike table에 row가 존재한다면
        if(checkCommentLikeRow) {
            //해당 row 업데이트 (isLiked: N -> Y/ Y -> N)
            String setCommentLikeQuery = "" +
                    "update commentLike\n" +
                    "set isLiked = case when isLiked = 'N' THEN 'Y' ELSE 'N' END\n" +
                    "where commentId = ? and userIdx = ?;";
            this.jdbcTemplate.update(setCommentLikeQuery, commentId, userIdx);
        }
        // 존재하지 않으면
        else{
            //새로운 row 추가
            String insertCommentLikeQuery = "insert into commentLike (commentId, userIdx)values(?,?);";
            this.jdbcTemplate.update(insertCommentLikeQuery, commentId, userIdx);
        }
    }
    public void setFeedReport(int userIdx, int feedId){

        String checkFeedReportRowQuery = "select exists( select feedId from report where feedId = ? and userIdx = ?);";
        boolean checkFeedReportRow = this.jdbcTemplate.queryForObject(checkFeedReportRowQuery,boolean.class,feedId,userIdx);

        //FeedReport table에 row가 존재한다면
        if(checkFeedReportRow) {
            //해당 row 업데이트 (isReported: N -> Y/ Y -> N)
            String setFeedReportQuery = "" +
                    "update report\n" +
                    "set isReported = case when isReported = 'N' THEN 'Y' ELSE 'N' END\n" +
                    "where feedId = ? and userIdx = ?;";
            this.jdbcTemplate.update(setFeedReportQuery, feedId, userIdx);
        }
        // 존재하지 않으면
        else{
            //새로운 row 추가
            String insertFeedReportQuery = "insert into report (feedId, userIdx) values(?,?);";
            this.jdbcTemplate.update(insertFeedReportQuery, feedId, userIdx);
        }
    }

    @Transactional
    public int postNormalFeeds(int userIdx, PostNormalFeedReq postNormalFeedReq){
        // 게시물 업로드
        String postFeedQury = "insert into feed (userIdx, title, retouchedDegree, latitude,\n" +
                "                  longitude,price,startPeriod,endPeriod, review, photoTool)\n" +
                "            value(?,?,?,?,?,?,?,?,?,?);";

        Object[] createFeedParams = new Object[]{userIdx, postNormalFeedReq.getTitle(), postNormalFeedReq.getRetouchedDegree(),
                                                postNormalFeedReq.getLatitude(), postNormalFeedReq.getLongitude(),
                                                postNormalFeedReq.getPrice(), postNormalFeedReq.getStartPeriod(),
                                                postNormalFeedReq.getEndPeriod(), postNormalFeedReq.getReview(),postNormalFeedReq.getPhotoTool()};
        this.jdbcTemplate.update(postFeedQury,createFeedParams);
        // 업로드한 게시물 feedId가져오기
        String lastInsertIdQuery = " SELECT MAX(id) FROM feed;";
        int feedId = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        // 숙소 사진 url 업로드
        List<String> feedImgUrls = postNormalFeedReq.getFeedImgUrls();
        String postFeedImgUrlQuery = "insert into feedImg(feedId,feedImgUrl) values (?,?);";
        for(val feedImgUrl : feedImgUrls){
            this.jdbcTemplate.update(postFeedImgUrlQuery, feedId, feedImgUrl);
        }
        // 장점 리스트 업로드
        List<String> pros = postNormalFeedReq.getPros();
        postProsCons(feedId, pros, "P");
        // 단점 리스트 업로드
        List<String> cons = postNormalFeedReq.getCons();
        postProsCons(feedId, cons,"C");
        // 태그 리스트 업로드
        List<String> tags = postNormalFeedReq.getTags();
        postTags(feedId, tags);

        return feedId;

    }
    @Transactional
    public int postAirBnBFeeds(int userIdx, PostAirBnBFeedReq postAirBnBFeedReq){
        // 게시물 업로드
        String postFeedQury = "insert into feed (userIdx,isAirBnB,retouchedDegree, \n" +
                "                 price,startPeriod,endPeriod,address, review,airBnBLink,additionalLocation, photoTool)\n" +
                "            value(?,?,?,?,?,?,?,?,?,?,?);";

        Object[] createFeedParams = new Object[]{userIdx, "Y" ,postAirBnBFeedReq.getRetouchedDegree(),
                                                postAirBnBFeedReq.getPrice(), postAirBnBFeedReq.getStartPeriod(),
                                                postAirBnBFeedReq.getEndPeriod(), postAirBnBFeedReq.getAddress(),
                                                postAirBnBFeedReq.getReview(), postAirBnBFeedReq.getAirBnBLink(),
                                                postAirBnBFeedReq.getAdditionalLocation(), postAirBnBFeedReq.getPhotoTool()};
        this.jdbcTemplate.update(postFeedQury,createFeedParams);
        // 업로드한 게시물 feedId가져오기
        String lastInsertIdQuery = " SELECT MAX(id) FROM feed;";
        int feedId = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        // 숙소 사진 url 업로드
        List<String> feedImgUrls = postAirBnBFeedReq.getFeedImgUrls();
        String postFeedImgUrlQuery = "insert into feedImg(feedId,feedImgUrl) values (?,?);";
        for(val feedImgUrl : feedImgUrls){
            this.jdbcTemplate.update(postFeedImgUrlQuery, feedId, feedImgUrl);
        }
        // 장점 리스트 업로드
        List<String> pros = postAirBnBFeedReq.getPros();
        postProsCons(feedId, pros, "P");
        // 단점 리스트 업로드
        List<String> cons = postAirBnBFeedReq.getCons();
        postProsCons(feedId, cons,"C");
        // 태그 리스트 업로드
        List<String> tags = postAirBnBFeedReq.getTags();
        postTags(feedId, tags);

        return feedId;

    }
    public void postProsCons(int feedId, List<String> elems, String type){
        for (val elem : elems){
            // prosCons가 DB에 있는지 확인
            String checkProsConsQuery ="select exists(select id from prosCons where name = ?);";
            boolean checkProsCons = this.jdbcTemplate.queryForObject(checkProsConsQuery,boolean.class,elem);

            // prosCons id가져오기
            int prosConsId;
            String getProsConsIdQuery = "select id from prosCons where name = ? and type = ?;";

            // prosCons가 DB에 있으면: prosConsId 찾기
            if(checkProsCons){
                prosConsId = this.jdbcTemplate.queryForObject(getProsConsIdQuery,int.class, elem,type);
            }
            // prosCons가 DB에 없으면: 새로 생성후, prosConsId 찾기
            else{
                String insertProsConsQeury = "insert into prosCons (name, type, isAddedByUser) values (?,?,'Y');";
                this.jdbcTemplate.update(insertProsConsQeury,elem,type);
                prosConsId = this.jdbcTemplate.queryForObject(getProsConsIdQuery,int.class,elem,type);
            }

            // feedProsCons에 업로드
            String postFeedProsConsQuery = "insert into feedProsCons(feedId, prosConsId) values(?,?);\n";
            this.jdbcTemplate.update(postFeedProsConsQuery, feedId, prosConsId);

        }
    }
    public void postTags(int feedId, List<String> tags){
        for (val tag : tags){
            // Tag가 DB에 있는지 확인
            String checkTagQuery ="select exists(select id from tag where name = ?);";
            boolean checkProsCons = this.jdbcTemplate.queryForObject(checkTagQuery,boolean.class,tag);

            // Tag id가져오기
            int tagId;
            String getTagIdQuery = "select id from tag where name = ?;";

            // Tag가 DB에 있으면: tagId 찾기
            if(checkProsCons){
                tagId = this.jdbcTemplate.queryForObject(getTagIdQuery,int.class, tag);
            }
            // Tag가 DB에 없으면: 새로 생성후, tagId 찾기
            else{
                String insertTagQeury = "insert into tag (name) values (?);";
                this.jdbcTemplate.update(insertTagQeury,tag);
                tagId = this.jdbcTemplate.queryForObject(getTagIdQuery,int.class,tag);
            }

            // feedTag에 업로드
            String postFeedTagQuery = "insert into feedTag(feedId, tagId) values(?,?);\n";
            this.jdbcTemplate.update(postFeedTagQuery, feedId, tagId);

        }
    }
    public int postComments(int userIdx, int feedId, String content){
        String postCommentQury = "insert into comment (feedId, userIdx, content) values(?,?,?);";
        this.jdbcTemplate.update(postCommentQury,feedId, userIdx, content);

        String lastInsertCommentQuery = "SELECT MAX(id) FROM comment;";
        return this.jdbcTemplate.queryForObject(lastInsertCommentQuery,int.class);

    }
    public void deleteComments(int commentId){
        String deleteCommentQury = "update comment set isDeleted = 'Y' where id =? ;";
        this.jdbcTemplate.update(deleteCommentQury,commentId);
    }
    public void deleteFeeds(int feedId){
        String deleteFeedQury = "update feed set isDeleted = 'Y' where id =? ;";
        this.jdbcTemplate.update(deleteFeedQury,feedId);
    }

}
