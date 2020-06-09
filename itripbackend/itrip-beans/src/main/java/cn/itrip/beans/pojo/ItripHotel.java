package cn.itrip.beans.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.context.annotation.Profile;

import java.io.Serializable;
import java.util.Date;
public class ItripHotel implements Serializable {

            @Field("id")
            private Long id;
            @Field("hotelName")
            private String hotelName;
            @Field("countryId")
            private Long countryId;
             @Field("provinceId")
            private Long provinceId;

            @Field("cityId")
            private Long cityId;
            @Field("address")
            private String address;
            @Field("details")
            private String details;
            @Field("facilities")
            private String facilities;
            @Field("hotelPolicy")
            private String hotelPolicy;
            @Field("hotelType")
            private Integer hotelType;
            @Field("hotelLevel")
            private Integer hotelLevel;
            @Field("isGroupPurchase")
            private Integer isGroupPurchase;
             @Field("redundantCityName")
            private String redundantCityName;
                            @Field("redundantProvinceName")
            private String redundantProvinceName;
                        @Field("redundantCountryName")
            private String redundantCountryName;
                        @Field("redundantHotelStore")
            private Integer redundantHotelStore;
                     @Field("creationDate")
            private Date creationDate;
                        @Field("createdBy")
            private Long createdBy;
                        @Field("modifyDate")
            private Date modifyDate;
                            @Field("modifiedBy")
            private Long modifiedBy;
    @Field("maxPrice")
    private Double maxPrice;

    @Field("minPrice")
    private Double minPrice;

    @Field("extendPropertyNames")
    private String extendPropertyNames;

    @Field("extendPropertyPics")
    private String extendPropertyPics;

    @Field("tradingAreaNames")
    private String tradingAreaNames;

    @Field("featureNames")
    private String featureNames;

    @Field("isOkCount")
    private Integer isOkCount;
    @Field("commentCount")
    private Integer commentCount;
    @Field("avgScore")
    private Double avgScore;

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getExtendPropertyNames() {
        return extendPropertyNames;
    }

    public void setExtendPropertyNames(String extendPropertyNames) {
        this.extendPropertyNames = extendPropertyNames;
    }

    public String getExtendPropertyPics() {
        return extendPropertyPics;
    }

    public void setExtendPropertyPics(String extendPropertyPics) {
        this.extendPropertyPics = extendPropertyPics;
    }

    public String getTradingAreaNames() {
        return tradingAreaNames;
    }

    public void setTradingAreaNames(String tradingAreaNames) {
        this.tradingAreaNames = tradingAreaNames;
    }

    public String getFeatureNames() {
        return featureNames;
    }

    public void setFeatureNames(String featureNames) {
        this.featureNames = featureNames;
    }

    public Integer getIsOkCount() {
        return isOkCount;
    }

    public void setIsOkCount(Integer isOkCount) {
        this.isOkCount = isOkCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    @Field("imgUrl")
            private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setId (Long  id){
                this.id=id;
            }

            public  Long getId(){
                return this.id;
            }
            public void setHotelName (String  hotelName){
                this.hotelName=hotelName;
            }

            public  String getHotelName(){
                return this.hotelName;
            }
            public void setCountryId (Long  countryId){
                this.countryId=countryId;
            }

            public  Long getCountryId(){
                return this.countryId;
            }
            public void setProvinceId (Long  provinceId){
                this.provinceId=provinceId;
            }

            public  Long getProvinceId(){
                return this.provinceId;
            }
            public void setCityId (Long  cityId){
                this.cityId=cityId;
            }

            public  Long getCityId(){
                return this.cityId;
            }
            public void setAddress (String  address){
                this.address=address;
            }

            public  String getAddress(){
                return this.address;
            }
            public void setDetails (String  details){
                this.details=details;
            }

            public  String getDetails(){
                return this.details;
            }
            public void setFacilities (String  facilities){
                this.facilities=facilities;
            }

            public  String getFacilities(){
                return this.facilities;
            }
            public void setHotelPolicy (String  hotelPolicy){
                this.hotelPolicy=hotelPolicy;
            }

            public  String getHotelPolicy(){
                return this.hotelPolicy;
            }
            public void setHotelType (Integer  hotelType){
                this.hotelType=hotelType;
            }

            public  Integer getHotelType(){
                return this.hotelType;
            }
            public void setHotelLevel (Integer  hotelLevel){
                this.hotelLevel=hotelLevel;
            }

            public  Integer getHotelLevel(){
                return this.hotelLevel;
            }
            public void setIsGroupPurchase (Integer  isGroupPurchase){
                this.isGroupPurchase=isGroupPurchase;
            }

            public  Integer getIsGroupPurchase(){
                return this.isGroupPurchase;
            }
            public void setRedundantCityName (String  redundantCityName){
                this.redundantCityName=redundantCityName;
            }

            public  String getRedundantCityName(){
                return this.redundantCityName;
            }
            public void setRedundantProvinceName (String  redundantProvinceName){
                this.redundantProvinceName=redundantProvinceName;
            }

            public  String getRedundantProvinceName(){
                return this.redundantProvinceName;
            }
            public void setRedundantCountryName (String  redundantCountryName){
                this.redundantCountryName=redundantCountryName;
            }

            public  String getRedundantCountryName(){
                return this.redundantCountryName;
            }
            public void setRedundantHotelStore (Integer  redundantHotelStore){
                this.redundantHotelStore=redundantHotelStore;
            }

            public  Integer getRedundantHotelStore(){
                return this.redundantHotelStore;
            }
            public void setCreationDate (Date  creationDate){
                this.creationDate=creationDate;
            }

            public  Date getCreationDate(){
                return this.creationDate;
            }
            public void setCreatedBy (Long  createdBy){
                this.createdBy=createdBy;
            }

            public  Long getCreatedBy(){
                return this.createdBy;
            }
            public void setModifyDate (Date  modifyDate){
                this.modifyDate=modifyDate;
            }

            public  Date getModifyDate(){
                return this.modifyDate;
            }
            public void setModifiedBy (Long  modifiedBy){
                this.modifiedBy=modifiedBy;
            }

            public  Long getModifiedBy(){
                return this.modifiedBy;
            }

}
