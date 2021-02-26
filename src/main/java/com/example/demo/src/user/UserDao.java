package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        return this.jdbcTemplate.query("select * from users",
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")));
    }

    public List<GetUserRes> getUsers(String email){
        // query: 여러개 반환하는 경우
        return this.jdbcTemplate.query("select * from users where email =?",
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                email); // 파라미터 여러개 넘겨줄 때는 배열형식!
    }

    public GetUserRes getUser(int userIdx){
        // queryForObject: 하나만 반환하는 경우
        return this.jdbcTemplate.queryForObject("select * from users where userIdx = ?",
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                userIdx);
    }


    public int createUser(PostUserReq postUserReq){
        this.jdbcTemplate.update("insert into users (userName, ID, password, email) VALUES (?,?,?,?)",
                new Object[]{postUserReq.getUserName(), postUserReq.getId(), postUserReq.getPassword(), postUserReq.getEmail(),}
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public int checkEmail(String email){
        return this.jdbcTemplate.queryForObject("select exists(select email from users where email = ?)",
                int.class,
                email);

    }



}
