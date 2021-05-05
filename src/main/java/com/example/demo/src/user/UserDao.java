package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createUser(KakaoProfileRes kakaoProfile){
        String createUserQuery = "insert into user (kakaoId, userId, profileImgUrl,email) VALUES (?,?,?,?)";
//        Object[] createUserParams = new Object[]{kakaoProfile.getId(),kakaoProfile.getId(), kakaoProfile.getProperties().getProfile_image(), kakaoProfile.getKakao_account().getEmail()};
        Object[] createUserParams = new Object[]{kakaoProfile.getId(),kakaoProfile.getId(), kakaoProfile.getProperties().getProfile_image(), ""};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "SELECT MAX(userIdx) FROM user";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
    public int checkKakaoId(Integer kakaoId){
        String checkKakaoQuery = "select exists(select kakaoId from user where kakaoId = ? and isDeleted = 'N')";
        Integer checkKakaoParams = kakaoId;
        return this.jdbcTemplate.queryForObject(checkKakaoQuery,
                int.class,
                checkKakaoParams);

    }
    public boolean checkDuplicatedId(String userId){
        String checkDuplicatedIdQuery = "select exists(select userId from user where userId = ? and isDeleted = 'N')";
        return this.jdbcTemplate.queryForObject(checkDuplicatedIdQuery,
                boolean.class,
                userId);

    }
    public String getUserIdByIdx(int userIdx){
        String getUserIdByIdxQuery = "select userId from user where userIdx = ? and isDeleted = 'N';";
        return this.jdbcTemplate.queryForObject(getUserIdByIdxQuery,
                String.class,
                userIdx);

    }
    public int getUserIdxByKakaoId(Integer kakaoId){
        String getUserIdxQuery = "select userIdx from user where kakaoId = ? and isDeleted = 'N'";
        Integer getUserIdxParams = kakaoId;
        return this.jdbcTemplate.queryForObject(getUserIdxQuery,
                int.class,
                getUserIdxParams);

    }
    public void postUserId(int userIdx, String userId){
        String postUserId = "update user set userId = ? where userIdx = ?;";
        this.jdbcTemplate.update(postUserId,userId, userIdx);
    }
//    public List<GetUserRes> getUsers(){
//        String getUsersQuery = "select * from users";
//        return this.jdbcTemplate.query(getUsersQuery,
//                (rs,rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password"))
//        );
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email){
//        String getUsersByEmailQuery = "select * from users where email =?";
//        String getUsersByEmailParams = email;
//        // query: 여러개 반환하는 경우
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams); // 파라미터 여러개 넘겨줄 때는 배열형식!
//    }
//
//    public GetUserRes getUser(int userIdx){
//        String getUserQuery = "select * from users where userIdx = ?";
//        int getUserParams = userIdx;
//        // queryForObject: 하나만 반환하는 경우
//        return this.jdbcTemplate.queryForObject(getUserQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUserParams);
//    }
//
//
//    public int createUser(PostUserReq postUserReq){
//        String createUserQuery = "insert into users (userName, ID, password, email) VALUES (?,?,?,?)";
//        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getId(), postUserReq.getPassword(), postUserReq.getEmail()};
//        this.jdbcTemplate.update(createUserQuery, createUserParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//    }
//
//    public int checkEmail(String email){
//        String checkEmailQuery = "select exists(select email from users where email = ?)";
//        String checkEmailParams = email;
//        return this.jdbcTemplate.queryForObject(checkEmailQuery,
//                int.class,
//                checkEmailParams);
//
//    }
//
//    public int modifyUserName(PatchUserReq patchUserReq){
//        String modifyUserNameQuery = "update users set userName = ? where userIdx = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
//    }

//    public User getPwd(PostLoginReq postLoginReq){
//        String getPwdQuery = "select userIdx, password,email,userName,ID from users where ID = ?";
//        String getPwdParams = postLoginReq.getId();
//
//        return this.jdbcTemplate.queryForObject(getPwdQuery,
//                (rs,rowNum)-> new User(
//                        rs.getInt("userIdx"),
//                        rs.getString("ID"),
//                        rs.getString("userName"),
//                        rs.getString("password"),
//                        rs.getString("email")
//                ),
//                getPwdParams
//        );
//
//    }



}
