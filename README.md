web-communicator
=======================

Java web communication API that currently supports:
- Sending emails via TLS-enabled connection
- Sending HTTP requests (GET, POST, PUT)
- Posting Facebook updates to your public wall using Graph API
- Sending Twitter updates using Twitter OAuth

Usage
=====

```java
WebCommunicator c = new WebCommunicator();
```

Email
-----
```java
// smtp server doesn't require authentication
String recipient = "repicient@example.com";
String subject = "My subject";
String message = "My message body";
String from = "sender@example.com";
String smptHost = "smtp.mycompany.com";
c.sendEmail(recipient, subject, message, from, smptHost);

// smtp server requires authentication
smptHost = "smtp.google.com";
String username = "me@google.com";
String pswd = "myGooglePswd";
c.sendEmail(recipient, subject, message, from, smptHost, username, pswd);
```

HTTP Requests
-------------
```java
String url = "http://myservice.com/";
HashMap<String, String> params = new HashMap<String, String>() {
    {
        put("param1", "val1");
        put("param2", "val2");
        put("param3", "val3");
    }
};
String getResponse = c.httpGET(url, params);
String postResponse = c.httpPOST(url, params);
String putResponse = c.httpPUT(url, params);
```

Facebook Status Update
----------------------
Instructions on how to obtain token: http://goo.gl/NUyc2
```java
String fbMessage = "OMG it was so hilarious i literally died laughing #LOL #SWAG";
String fbAccessToken = "yourfbappaccesstoken";
c.postFacebookUpdate(fbMessage, fbAccessToken);
```

Twitter Status Update
--------------------
Instructions on how to obtain credentials: https://dev.twitter.com/docs/auth/oauth/faq
```java
// sending tweet, no pin required
String twMessage = "Eating breakfast! #yummy (@ IHOP) [pic]: http://4sq.com/rcgmnH ";
String twConsumerKey = "consumerkeyfromyourapp";
String twConsumerSecret = "consumersecretfromyourapp";
c.tweet(twMessage, twConsumerKey, twConsumerSecret);

// sending tweet, pin required
String twPin = "yourapppin";
c.tweet(twMessage, twConsumerKey, twConsumerSecret, twPin);
```

Comming soon
============
1. OAuth 2.0 support for sending emails via smtp.google.com
2. Posting images and videos in Facebook status update