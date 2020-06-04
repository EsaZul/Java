package assignment4;


import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetReader {
    private static ObjectMapper mapper;
    private final OkHttpClient httpClient = new OkHttpClient();
    private static String json;

    //DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    //String formattedDate = formatter.format(LocalDate.now());


    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    private void sendGet() throws Exception {
        Request request = new Request.Builder()
                .url("http://kevinstwitterclient2.azurewebsites.net/api/products")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            json = response.body().string();
            System.out.println(json);
        }

    }
    private static boolean isThisDateValid(String dateToValidate){
        try {
            Instant.parse(dateToValidate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {
        mapper = new ObjectMapper();
        TweetReader obj = new TweetReader();
        System.out.println("Testing 1 - Send Http GET request");
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        obj.sendGet();

        List<Tweets> tweetList = new ArrayList<>(Arrays.asList(mapper.readValue(json, Tweets[].class)));


        for (Iterator<Tweets> itr = tweetList.iterator(); itr.hasNext();) {
            Tweets current = itr.next();
            boolean b = false;
            Pattern p = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
            if (current.getName() != null) {
                Matcher m = p.matcher(current.getName());
                b = m.find();
            }
            //Removes tweet if a parameter has nothing in the field
            if (current.getName() == null | current.getDate() == null | current.getText() == null) {
                itr.remove();
            }
            //Removes tweet if name has an invalid character
            else if (b){
                itr.remove();
            }
            //Removes a tweet if it exceeds the max of 140 characters
            else if (current.getText().length() > 140){
                itr.remove();
            }
            //Removes a tweet if it is not in the YYYY-MM-DDT00:00:00.000Z format
            else if (!isThisDateValid(current.getDate())){
                itr.remove();
            }
        }
        return tweetList;
    }
}
