package com.terapanth.abtmm.utils;

import android.content.Context;
import android.content.Intent;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.commons.Constants;

/**
 * Created by MindstixSoftware on 01/01/18.
 */

public class shareUtils {

    public static void shareMe(Context context, String share) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share);
        context.startActivity(Intent.createChooser(sharingIntent,"Share using"));
    }

    public static String getSharingStringForNews(String newsTitle, String description, String author, String date) {
        return newsTitle + "\n" + description + "\nPosted by: " + author + " on: " + date + "\n\nDownload ABTMM App - " + Constants.APP_STORE_LINK;
    }
}
