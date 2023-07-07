<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var msgType = "<c:out value='${msgType}'/>";
	var msg = "<c:out value='${msg}'/>";
	if(msg != "") alert(msg);
	switch(msgType){
		case "msgAndRedirect" : 
			location.href = "<c:out value='${redirectUrl}'/>"; break;
	}
</script>