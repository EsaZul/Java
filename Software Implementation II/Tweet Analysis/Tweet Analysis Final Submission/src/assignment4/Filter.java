package assignment4;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweets> writtenBy(List<Tweets> tweets, String username) {
        List<Tweets> filteredList = new ArrayList<Tweets>(tweets);
        username = username.toLowerCase();

        for (Iterator<Tweets> itr = filteredList.iterator(); itr.hasNext();) {
            Tweets current = itr.next();
            String name = current.getName().toLowerCase();
            if (!(name.equals(username))){
                itr.remove();
            }
        }
        return filteredList;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweets> inTimespan(List<Tweets> tweets, Timespan timespan) {
        List<Tweets> filteredList = new ArrayList<Tweets>(tweets);
        Instant start = timespan.getStart();
        Instant end = timespan.getEnd();
        for (Iterator<Tweets> itr = filteredList.iterator(); itr.hasNext();) {
            Tweets current = itr.next();
            Instant date = Instant.parse(current.getDate());
            int val1 = date.compareTo(start);
            int val2 = date.compareTo(end);
            if ((val1 < 0) | (val2 > 0)){
                itr.remove();
            }

        }
        return filteredList;
    }

    /**
     * Find tweets that contain certain words.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets.
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when
     *         represented as a sequence of nonempty words bounded by space characters
     *         and the ends of the string) includes *at least one* of the words
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweets> containing(List<Tweets> tweets, List<String> words) {
        List<Tweets> filteredList = new ArrayList<Tweets>(tweets);
        int size = words.size();
        for (Iterator<Tweets> itr = filteredList.iterator(); itr.hasNext();) {
            Tweets current = itr.next();
            int count = 0;
            for (int i = 0; i < size; i++) {
                if ((current.getText().toLowerCase().contains(words.get(i).toLowerCase()))){
                    count++;
                }
            }
            if ((count == 0)){
                itr.remove();
            }
        }
        return filteredList;
    }
}