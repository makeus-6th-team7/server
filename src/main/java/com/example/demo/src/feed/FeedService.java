package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.src.feed.model.GetFeedRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INVALID_FEED_ID;

@Service
public class FeedService {
    private final FeedDao feedDao;

    @Autowired
    public FeedService(FeedDao feedDao) {
        this.feedDao = feedDao;
    }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void setFeedLike(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            feedDao.setFeedLike(userIdx,feedId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void setFeedReport(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            feedDao.setFeedReport(userIdx,feedId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
