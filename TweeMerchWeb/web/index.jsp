<%@page import="twitter.TweetGet"%>
<%@page import="twitter4j.Status"%>
<%@page import="java.util.List"%>
<%@page import="information_extraction.InformationExtraction" %>
<%@page import="model.InformationSell" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="template/header.jsp" />

    <div class="container">
        <div class="header">
            <h1>Twee Merch</h1>
            <p>Twitter Merchant Aggregator</p>
        </div>
        
        <%
            
            TweetGet tweetGet = new TweetGet();
            tweetGet.query("jual");
            
            List<Status> tweets = tweetGet.getTweet();
            for (Status tweet : tweets) {
                System.out.println(tweet);
            }
            // InformationExtraction ie = new InformationExtraction();
            // ie.ieAll();  
            // InformationSell infoSell = ie.getInformationSell();
        %>
        
        <div class="items">
            <% for (Status tweet: tweets) { %>
                <%
                    InformationExtraction ie = new InformationExtraction(tweet.getText());
                    ie.ieAll();  
                    InformationSell infoSell = ie.getInformationSell();
                %>
                
                <a href="https://twitter.com/<%= tweet.getUser().getScreenName() %>/status/<%= tweet.getId() %>" class="item clearfix">
                    <div class="seller-img">
                        <img src="<%= tweet.getUser().getProfileImageURLHttps() %>" alt="">
                    </div>

                    <div class="item-info">
                        <div class="seller-name"><%= tweet.getUser().getName() %></div>
                        <div class="item-name"><span class="item-attr">Barang</span><%= infoSell.getItemName() %></div>
                        <div class="price"><span class="item-attr">Harga</span><%= infoSell.getPrice() %></div>
                        <div class="contact"><span class="item-attr">Kontak</span><%= infoSell.getPhone() %></div>
                        <div class="original">
                            <div class="original-tweet">
                                <strong>Original Tweet</strong>
                                <br>
                                <%= tweet.getText() %>
                            </div>
                        </div>
                    </div>
                </a>
                
            <% } %>
        </div>
    </div>
    
<jsp:include page="template/footer.jsp" />

