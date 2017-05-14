package com.noel201296gmail.cine_matic.Model;

/**
 * Created by OMOSEFE NOEL OBASEKI on 12/05/2017.
 */
public class TrailerResponse {


    public static final String YouTubeBaseUrl ="https://www.youtube.com/watch";
    private String name;
    private String size;
    private String source;
    private String type;


    public TrailerResponse(String name,String size,String source,String type){
        this.name=name;
        this.size=size;
        this.source=source;
        this.type=type;
    }


    public String getName() {
        return name;
    }


    public String getSize() {
        return size;
    }


    public String getSource() {
        return YouTubeBaseUrl+source;
    }

    public String getType() {
        return type;
    }

}
