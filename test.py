import tweepy

consumer_key = "in4iaAL5VJdu2CTkeETOMKheg"
consumer_secret = "rC33kyH5EmUsPJr5paq7EPBpTnWVrSYAoPOPuitacB4a7aBC2B"
access_token = "353636024-cZaZNXnZxqxQzuFVHGQra6seKFnHhB7QbCxqhjFk"
access_secret = "b9RJQvrvp1GCaqSWsrNjXEiyXWdDFz19z057RLwqNANjH"

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_secret)

api = tweepy.API(auth)

query = "jual -filter:retweets"

tweets = []

max_id = None

for i in xrange(50):
    results = api.search(q=query, count=100, max_id=max_id)
    max_id = results[-1].id - 1
    tweets.extend(results)

for tweet in tweets:
    print str(tweet.id) + "\t" + tweet.text.encode("utf-8").replace("\n", " ") + "\t" + tweet.created_at.strftime('%d %B %Y, %H:%M:%S')
