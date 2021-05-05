package com.example.demo.src.user.model;

import lombok.Data;

@Data
public class KakaoProfileRes {
    private Integer id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public class KakaoAccount {

        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        // 토큰-> 정보 받아올 때 오류 생겨서 없앰
//        private Boolean is_email_valid = false;
//        private Boolean is_email_verified = false;
//        private String email = "";

        @Data
        public class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private Boolean is_default_image;
        }
    }
}