<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript">
	function su(){
		var dataArray = new Array();
		
		$("input").each(function(i,e){
			dataArray.push($(e).val());
		});
		
		$.ajax({            
		     type: "GET",
		     url: "/demo/Servlet1",
		     traditional: true,
		     data: {
		            array: dataArray
		     },                              
		     success: function(data){
		            alert(data);
		     }
		});
	}
		

</script>
</head>
<body>
	<input name="i1" type="text">
	<input name="i2" type="text">
	<input name="i3" type="text">
	<button onclick="su()">提交</button>
</body>
</html>