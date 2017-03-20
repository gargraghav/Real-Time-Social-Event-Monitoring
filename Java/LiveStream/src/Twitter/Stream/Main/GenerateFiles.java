package Twitter.Stream.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.conf.ConfigurationBuilder;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.TwitterException;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.StatusDeletionNotice;
import twitter4j.HashtagEntity;
import twitter4j.User;
import twitter4j.StallWarning;
import twitter4j.FilterQuery;



public class GenerateFiles {
    public static void main(String[] args) throws IOException, TwitterException {
        final BufferedWriter bwTweets = new BufferedWriter(new FileWriter(new File("tweets.txt")));
        final BufferedWriter bwHashtags = new BufferedWriter(new FileWriter(new File("hashtags.txt")));
        //System.setProperty("http.proxyHost", "proxy1.ddn.upes.ac.in");
        //System.setProperty("http.proxyPort", "8080");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("sm3MOo19xPv0nwVPwprPZ0nnD")
                .setOAuthConsumerSecret("AZMquMbDOsUt5Pm4bckNjUakIvRF7PmErcNqc726nDzWeXmvCs")
                .setOAuthAccessToken("778918275514179586-Yn5JilmY924JQaXnM4s9VZZgotRMSIi")
                .setOAuthAccessTokenSecret("mHA1c60PRbtvrqs93Uh36dZF3DvB2URGmFx25FmMhvDf7");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                String tweet = "";
                String hashtag = "";
                HashtagEntity[] entity = status.getHashtagEntities(); //returns an array of hashtags mentioned in the tweet
                for(int i = 0; i < entity.length; i++) {
                    tweet += "#" + entity[i].getText() + " "; //appends # to the hashtag name
                    hashtag += hashtag + "#" + entity[i].getText() + " ";
                }
                String content = status.getText(); //gets tweet content
                tweet += content;
                //System.out.println(tweet + "\n");
                try {
                    bwTweets.write(tweet + "\n");
                    bwHashtags.write(hashtag + "\n");
                    bwTweets.flush();
                    bwHashtags.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                User user = status.getUser();
                String username = status.getUser().getScreenName();
                String profileLocation = user.getLocation();
                long tweetId = status.getId();
                */
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

        };

        FilterQuery fq = new FilterQuery();
        String keywords[] = {""};
        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
}
