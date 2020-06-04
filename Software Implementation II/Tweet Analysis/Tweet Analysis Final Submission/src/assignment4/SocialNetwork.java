package assignment4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    public static Set<String> mentions(List<Tweets> tweets, String s1, Set<String> set) {
        set.add(s1);
        List<Tweets> filteredUser = Filter.writtenBy(tweets, s1);
        for (Iterator<Tweets> itr = filteredUser.iterator(); itr.hasNext(); ) {
            String mention = null;
            Tweets current = itr.next();
            String text = current.getText().toLowerCase();
            StringBuilder edit = new StringBuilder(text);


            while (text.contains("@")) {
                int z = text.indexOf("@");
                int end = z + 1;
                int size = text.length();
                String valid = String.valueOf(text.charAt(z));
                if (z != 0) {
                    valid = String.valueOf(text.charAt(z - 1));
                }
                boolean b = false;
                Pattern p = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(valid);
                b = m.find();
                if (b) {
                    boolean d = false;
                    while (!d & (end < size)) {
                        String fin = String.valueOf(text.charAt(end));
                        m = p.matcher(fin);
                        d = m.find();
                        if (!d) {
                            end++;
                        }
                    }
                    mention = text.substring(z + 1, end);
                }

                edit.deleteCharAt(z);
                text = String.valueOf(edit);
                if ((!(mention == null))) {
                    set.add(mention);
                }
            }
        }
        return set;
    }



    /**
     * Get K most followed Users.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @param k      integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     * A username-mention is "@" followed by a Twitter username (as
     * defined by Tweet.getName()'s spec).
     * The username-mention cannot be immediately preceded or followed by any
     * character valid in a Twitter username.
     * For this reason, an email address like ethomaz@utexas.edu does NOT
     * contain a mention of the username.
     * Twitter usernames are case-insensitive, and the returned set may
     * include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<>();
        List<String> all = new ArrayList<>();
        Set<String> tweeter = new HashSet<String>();
        List<Tweets> copy = new ArrayList<Tweets>(tweets);
        for (Iterator<Tweets> itr = copy.iterator(); itr.hasNext(); ) {
            Tweets current = itr.next();
            String n = current.getName().toLowerCase();
            tweeter.add(n);
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Iterator<String> itr2 = tweeter.iterator(); itr2.hasNext(); ) {
            String current = itr2.next();
            Set<String> empty = new HashSet<>();
            empty = mentions(copy, current, empty);
            if (empty.size() > 1) {
                for (Iterator<String> itr3 = empty.iterator(); itr3.hasNext(); ) {
                    String next = itr3.next();
                    if (!(next.equals(current))) {
                        if (map.containsKey(next)) {
                            Integer i = map.get(next);
                            Integer j = i;
                            j++;
                            map.replace(next, i, j);
                        } else {
                            map.putIfAbsent(next, 1);
                        }
                    }
                }
            }

        }
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
            all.add(aa.getKey());
        }
        for (int i = 0; i < k; i++) {
            mostFollowers.add(all.get(i));
        }
        return mostFollowers;
    }

    /**
     * Find all cliques in the social network.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return list of set of all cliques in the graph
     */
    public static List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        Set<String> tweeter= new HashSet<String>();
        List<Tweets> copy = new ArrayList<Tweets>(tweets);

        for(Iterator<Tweets> itr = copy.iterator(); itr.hasNext();){
            Tweets current = itr.next();
            String n = current.getName().toLowerCase();
            tweeter.add(n);
        }
        for (Iterator<String> itr2 = tweeter.iterator(); itr2.hasNext(); ) {
            HashMap<String, HashMap> map = new HashMap<String, HashMap>();
            HashMap<String, Integer> clique = new HashMap<>();
            String current = itr2.next();
            Set<String> empty = new HashSet<>();
            empty = mentions(copy, current, empty);
            if ((empty.size() > 1)) {
                for (Iterator<String> itr3 = empty.iterator(); itr3.hasNext(); ) {
                    //ArrayList<String> keys = new ArrayList<>();
                    String next = itr3.next();
                    if (!(next.equals(current))) {
                        Set<String> empty2 = new HashSet<>();
                        empty2 = mentions(copy, next, empty2);
                        if ((empty2.size() > 1) & (empty2.contains(current))) {
                            clique.put(next, 1);
                            int size = map.size();
                            int count = 0;
                            if (size > 0) {
                                HashMap check = map.get(current);
                                Set check2 = check.keySet();
                                for (Iterator<String> it5 = check2.iterator(); it5.hasNext(); ) {
                                    String verify = it5.next();
                                    if (empty2.contains(verify)) {
                                        count++;
                                    }
                                }
                            }
                            if (size == count) {
                                map.put(current, clique);
                                clique.put(current, 1);
                                map.putIfAbsent(current, clique);
                            }
                        }
                    }

                }
            }
            if(map.size() > 0) {
                Set finalSet = map.get(current).keySet();
                Set ugh = new HashSet();
                for(Iterator convert = finalSet.iterator(); convert.hasNext();) {
                    String help = String.valueOf(convert.next());
                    ugh.add(help);

                }
                if(!(result.contains(ugh))) {
                    result.add(ugh);
                }
            }
        }
        //for(Iterator<Set> iterator)
        return result;
    }
}
