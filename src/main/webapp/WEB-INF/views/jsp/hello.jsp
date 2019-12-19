<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/core/css/vendor" var="vendor_css" />
<spring:url value="/resources/core/css/uml" var="uml_css" />
<spring:url value="/resources/core/js/vendor" var="vendor_js" />
<spring:url value="/resources/core/js/uml" var="uml_js" />
    <link rel="stylesheet" href="${vendor_css}/joint.css" />
    <link rel="stylesheet" href="${vendor_css}/joint.ui.stencil.css" />
     <link rel="stylesheet" href="${uml_css}/stencil.css" />
    <script src="${vendor_js}/jquery.min.js"></script>
    <script src="${vendor_js}/lodash.min.js"></script>
    <script src="${vendor_js}/backbone-min.js"></script>
    <script src="${vendor_js}/joint_uml.js"></script>
    <script src="${vendor_js}/uml_joint.ui.stencil.js"></script>
</head>
<body>
  <div id="paper-holder-loading"></div> 
  <script type="text/javascript" src="${uml_js}/uml_stencil.js"></script>
</body>
</html>
        