import tweepy

consumer_key = "in4iaAL5VJdu2CTkeETOMKheg"
consumer_secret = "rC33kyH5EmUsPJr5paq7EPBpTnWVrSYAoPOPuitacB4a7aBC2B"
access_token = "353636024-cZaZNXnZxqxQzuFVHGQra6seKFnHhB7QbCxqhjFk"
access_secret = "b9RJQvrvp1GCaqSWsrNjXEiyXWdDFz19z057RLwqNANjH"

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_secret)

api = tweepy.API(auth)

query = "jual -filter:retweets"
results = api.search(q=query, count=100, )
print len(results)
for tweet in results:
    dicts = { "tweets": tweet.text.replace("\n", " ") , "created_at" : tweet.created_at.strftime('%H:%M %d %B %Y') }
    print dicts
