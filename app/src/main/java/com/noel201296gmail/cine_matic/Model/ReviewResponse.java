package com.noel201296gmail.cine_matic.Model;

/**
 * Created by OMOSEFE NOEL OBASEKI on 09/05/2017.
 */
public class ReviewResponse {

    private String id;
    private String author;
    private String content;
    private String url;




    public ReviewResponse(String id,String author,String content,String url){
        this.id=id;
        this.author=author;
        this.content=content;
        this.url=url;


    }
    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }


    public String getContent() {
        return content;
    }


    public String getUrl() {
        return url;
    }

}
