<%@ page import="shop.Product"%>
<jsp:useBean id='db'
             scope='session'
             class='shop.ShopDB' />

<html>
<head>
    <title>My Shop</title>
    <link href="krt.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="krtheader.jsp" />
<table align="center">
<tr>
<th> Title </th> <th align="right"> Price </th> <th align="right"> Picture </th>
</tr>
<%
    String artist=request.getParameter("artist");
    String title=request.getParameter("title");

    for (Product product : db.getProducts(artist,title))
    {
        // now use HTML literals to create the table
        // with JSP expressions to include the live data
        // but this page is unfinished - the thumbnail
        // needs a hyperlink to the product description,
        // and there should also be a way of selecting
        // pictures from a particular artist
        %>
        <tr>
             <td width="200px"> <%= product.title %> </td>
             <td width="100px" align="right"> <%= Product.formatPrice(product.price) %> </td>
             <td width="100px" align="right"> <a href = '<%="viewProduct.jsp?pid="+product.PID%>'> <img src="<%= product.thumbnail %>"/> </a> </td>
        </tr>
        <%
    }
 %>
 </table>
<hr>
<table align="center">
    <tr>
         <th colspan="2">Search for ... </th>
    </tr>
    <tr>
        <td>
       <form action="products.jsp" method="get">
            <input type="text" name="artist" size="20" />
            <input type="submit" value="Artist" />
        </form>
       </td>
        <td>
        <form action="products.jsp" method="get">
            <input type="text" name="title" size="20" />
            <input type="submit" value="Title" />
        </form>
        </td>
    </tr>
</table>
</body>
</html>
