package com.example.demo.src.feed.model;

import lombok.Data;

import java.util.List;

@Data
public class KakaoAddressRes {

    private Meta meta;
    private List<Documents> documents = null;

    @Data
    public class Meta{
        private Integer total_count;
    }

    public String getAddress(){
        String address = "";
        if(this.meta.total_count >= 1){
            String roadAddress = getDocuments().get(0).getRoad_address().getAddress_name();
            String buildingName = getDocuments().get(0).getRoad_address().getBuilding_name();
            address = roadAddress+" "+buildingName;
        }
        return address;
    }
}
@Data
class Documents{
        private RoadAddress road_address;
        private Address address;

        @Data
        public class RoadAddress{
            private String address_name;
            private String region_1depth_name;
            private String region_2depth_name;
            private String region_3depth_name;
            private String road_name;
            private String underground_yn;
            private String main_building_no;
            private String sub_building_no;
            private String building_name;
            private String zone_no;
        }
        @Data
        public class Address{
            private String address_name;
            private String region_1depth_name;
            private String region_2depth_name;
            private String region_3depth_name;
            private String mountain_yn;
            private String main_address_no;
            private String sub_address_no;
            private String zip_code;
        }

}

