package com.example.dexin.iot;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Dexin on 4/18/2018.
 */


@DynamoDBTable(tableName = "SensorInfo")
public class UserInfo {

    private String userInfo;
    private String username;
    private String pressure;
    private String proximity;
    private String light;
    private String magnetic;
    private String gravity;
    private String accelerometer;
    private String gyroscope;
    private String happiness;

    @DynamoDBHashKey(attributeName = "userInfo")
    public String getUserInfo() {return userInfo; }
    public void setUserInfo(String userInfo) {this.userInfo = userInfo; }

    @DynamoDBAttribute(attributeName="Username")
    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    @DynamoDBAttribute(attributeName="Pressure")
    public String getPressure() {return pressure; }
    public void setPressure(String pressure) { this.pressure = pressure; }

    @DynamoDBAttribute(attributeName="Proximity")
    public String getProximity() {return proximity; }
    public void setProximity(String proximity) { this.proximity = proximity; }

    @DynamoDBAttribute(attributeName="Light")
    public String getLight() {return light; }
    public void setLight(String light) { this.light = light; }

    @DynamoDBAttribute(attributeName="Magnetic Field")
    public String getMagnetic() {return magnetic; }
    public void setMagnetic(String magnetic) { this.magnetic = magnetic; }

    @DynamoDBAttribute(attributeName="Gravity")
    public String getGravity() {return gravity; }
    public void setGravity(String gravity) { this.gravity = gravity; }

    @DynamoDBAttribute(attributeName="Accelerometer")
    public String getAccelerometer() {return accelerometer; }
    public void setAccelerometer(String accelerometer) { this.accelerometer = accelerometer; }

    @DynamoDBAttribute(attributeName="Gyroscope")
    public String getGyroscope() {return gyroscope; }
    public void setGyroscope(String gyroscope) { this.gyroscope = gyroscope; }

    @DynamoDBAttribute(attributeName="Happiness")
    public String getHappiness() {return happiness; }
    public void setHappiness(String happiness) { this.happiness = happiness; }
}