package com.example.kishore.accommodate;

/**
 * Created by Kishore on 09-Apr-16.
 */
public class Hosts {
    private String accommodationType;
    private String roomType;
    private String roomFor;
    private String city;
    private String location;
    private String userEmail;
    private String hostedImageUrl;

    public Hosts()
    {
    }

    public Hosts(String userEmail,String accommodationType,String roomType,String roomFor,String city,String location,String hostedImageUrl)
    {
        this.userEmail = userEmail;
        this.accommodationType = accommodationType;
        this.roomType = roomType;
        this.roomFor = roomFor;
        this.city = city;
        this.location = location;
        this.hostedImageUrl = hostedImageUrl;
    }

    public String getAccommodationType()
    {
        return accommodationType;
    }
    public String getRoomType()
    {
        return roomType;
    }
    public String getRoomFor()
    {
        return roomFor;
    }
    public String getCity()
    {
        return city;
    }
    public String getLocation()
    {
        return location;
    }
    public String getHostedImageUrl(){return hostedImageUrl;}

    public void setAccommodationType(String accommodationType)
    {
        this.accommodationType = accommodationType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public void setRoomFor(String roomFor)
    {
        this.roomFor = roomFor;
    }

    public void setCity(String city)
    {
        this.city=city;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setHostedImageUrl(String hostedImageUrl)
    {
        this.hostedImageUrl = hostedImageUrl;
    }

}
