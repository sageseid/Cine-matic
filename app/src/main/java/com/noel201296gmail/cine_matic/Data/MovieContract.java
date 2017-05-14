package com.noel201296gmail.cine_matic.Data;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by OMOSEFE NOEL OBASEKI on 14/05/2017.
 */
public class MovieContract {


    public static final String AUTHORITY = "com.noel201296gmail.cine_matic";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "MovieTable";
    public static final class MovieEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_TASKS)
                        .build();

        public static final String TABLE_NAME = "MovieTable";
        public static final String COLUMN_PIC = "picture";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATING = "rating";
    }
}
