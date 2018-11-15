package com.example.android.popular_movies_stage_1;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

//public class NetworkUtils {

//   private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/";
//   private static final String API_VERSION = "3";
//   private static final String MOVIE = "movie";
//   private static final String VIDEOS = "videos";
//   private static final String REVIEWS = "reviews";
//    private static final String API_KEY_PARAM = "api_key=b1d3802dbb5542cb1aaaab6d85d9d5ae";

//   public static URL buildMovieURL (String sortType){
//       Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
//               .appendPath(API_VERSION)
//              .appendPath(MOVIE)
                        //              .appendPath(sortType)
//              .appendPath(API_KEY_PARAM)
                        //             .build();
//      URL url = null;
//      try {
//           url = new URL(uri.toString());
//       } catch (MalformedURLException e){
//          e.printStackTrace();
//      }
//      return url;
//   }

//   public static URL buildReviewUrl (String id){
//       Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
//               .appendPath(API_VERSION)
//               .appendPath(MOVIE)
//               .appendPath(id)
//              .appendPath(REVIEWS)
//              .appendPath(API_KEY_PARAM)
//              .build();
//      URL url = null;
//       try {
//           url = new URL(uri.toString());
//       } catch (MalformedURLException e){
//           e.printStackTrace();
//      }
//      return url;
//   }

//}


