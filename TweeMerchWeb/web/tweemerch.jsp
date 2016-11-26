<%@page import="java.text.DecimalFormat"%>
<%@page import="twitter.MyStatus"%>
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
            tweetGet.getTweetFromUser("tweemerch");
            
            List<MyStatus> tweets = tweetGet.getTweet();
        %>
        
        <div class="items">
            <% for (MyStatus tweet : tweets) { %>
                <%
                    InformationExtraction ie = new InformationExtraction(tweet.getTweet());
                    ie.ieAll();
                    InformationSell infoSell = ie.getInformationSell();
                    DecimalFormat df = new DecimalFormat("#");
                %>
                <div class="item clearfix">
                    <div class="seller-img">
                        <img src="<%= tweet.getImageUrl()%>" alt="">
                    </div>

                    <div class="item-info">
                        <div class="seller-name">
                            <a href="https://twitter.com/<%= tweet.getUserScreenName()%>/status/<%= tweet.getStatusId()%>">
                                <%= tweet.getUsername()%>
                            </a>
                        </div>
                        <div class="item-name"><span class="item-attr">Barang</span> <%= infoSell.getItemName()%></div>
                        <div class="price"><span class="item-attr">Harga</span>
                            <%= infoSell.getPrice() == 0 ? "-" : df.format(infoSell.getPrice()) %>
                        </div>
                        <div class="contact"><span class="item-attr">Kontak</span> <%= infoSell.getPhone()%></div>
                        <% if (infoSell.getLink().contains("http")) {%>
                        <div><a href="<%= infoSell.getLink()%>">Laman Jual</a></div>
                        <% }%>
                        <div class="original">
                            <div class="original-tweet">
                                <strong>Original Tweet</strong>
                                <br>
                                <%= tweet.getTweet()%>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
    
<jsp:include page="template/footer.jsp" />

