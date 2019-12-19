<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment</title>
<spring:url value="/resources/core/css/vendor" var="vendor_css" />
<spring:url value="/resources/core/css/uml" var="uml_css" />
<spring:url value="/resources/core/js/vendor" var="vendor_js" />
<spring:url value="/resources/core/js/uml" var="uml_js" />
<link type="text/css" rel="stylesheet" href="${uml_css}/umlEditor.css">
</head>
<body>
<div id="paypal">
<form action="https://www.paypal.com/cgi-bin/webscr" target="blank" method="post" style="margin: 0;">
	<input type="hidden" name="cmd" value="_s-xclick">
	<input type="hidden" name="hosted_button_id" value="632YE7W64P2NW">
	<input type="image" src="https://www.paypal.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
	<img alt="Paypal donation" border="0" src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" width="1" height="1">
</form>



<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_donations">
<input type="hidden" name="business" value="rmdmohan27@gmail.com">
<input type="hidden" name="lc" value="IN">
<input type="hidden" name="item_name" value="UML WEB">
<input type="hidden" name="amount" value="10.00">
<input type="hidden" name="currency_code" value="USD">
<input type="hidden" name="no_note" value="0">
<input type="hidden" name="bn" value="PP-DonationsBF:btn_donate_LG.gif:NonHostedGuest">
<input type="image" src="https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal – The safer, easier way to pay online.">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>

</div>
</body>
</html>