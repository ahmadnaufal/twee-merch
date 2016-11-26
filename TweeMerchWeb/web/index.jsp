<%@ page import="information_extraction.InformationExtraction" %>
<%@ page import="model.InformationSell" %>

<jsp:include page="template/header.jsp" />

    <div class="container">
        <div class="header">
            <h1>Twee Merch</h1>
            <p>Twitter Merchant Aggregator</p>
        </div>
        
        <%
            String tweet = "Di jual parfum oriflame giordani man notte ..... Harga 385000 area tembilahan https://t.co/2k4GwNywhu";
            InformationExtraction ie = new InformationExtraction(tweet.toLowerCase());
            ie.ieAll();
            
            InformationSell infoSell = ie.getInformationSell();
        %>
        
        <div class="items">
            <a href="https://twitter.com" class="item clearfix">
                <div class="seller-img">
                    <img src="https://pbs.twimg.com/profile_images/793261462043045888/3RwOQuE8_bigger.jpg" alt="">
                </div>

                <div class="item-info">
                    <div class="seller-name">Boby</div>
                    <div class="item-name"><span class="item-attr">Barang</span> <%= infoSell.getItemName() %></div>
                    <div class="price"><span class="item-attr">Harga</span> <%= infoSell.getPrice().toString() %></div>
                    <div class="contact"><span class="item-attr">Kontak</span> <%= infoSell.getPhone() %></div>
                </div>
            </a>

            <a href="https://twitter.com" class="item clearfix">
                <div class="seller-img">
                    <img src="https://pbs.twimg.com/profile_images/793261462043045888/3RwOQuE8_bigger.jpg" alt="">
                </div>

                <div class="item-info">
                    <div class="seller-name">Boby</div>
                    <div class="item-name"><span class="item-attr">Barang</span> <%= infoSell.getItemName() %></div>
                    <div class="price"><span class="item-attr">Harga</span> <%= infoSell.getPrice().toString() %></div>
                    <div class="contact"><span class="item-attr">Kontak</span> <%= infoSell.getPhone() %></div>
                </div>
            </a>
        </div>
    </div>
    
<jsp:include page="template/footer.jsp" />

