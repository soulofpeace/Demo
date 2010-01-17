<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>viewPackage</title>
    <script type="text/javascript" language="javascript" src="/viewPackage/viewPackage.nocache.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/paypal/Paypal.css">
  </head>

  <body>
  	<input type="hidden" id="packageKey" value="<c:out value='${packageKey}'/>"/>
  	<h1 class="title">Package Information </h1>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>

  </body>
</html>