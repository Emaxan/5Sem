<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet" />
<title>Insert title here</title>
<style type="text/css">
tbody tr:hover {
	background: #f6ffe9;
}
</style>
<script type="text/javascript" src="js/test.js"></script>
</head>
<body>
	<div class="container" style="margin-top: 35px">
		<h4>Select Number of Rows</h4>
		<div class="form-group">
			<select name="state" id="maxRows" class="form-controll"	style="width: 150px;">
				<option value="5000">Show all</option>
				<option value="3">3</option>
				<option value="5">5</option>
				<option value="10">10</option>
			</select>
		</div>
		
		<table id="mytable" class="table table-bordered table-striped">
				<thead>
				<tr>
					<th>user</th>
					<th>password</th>
					<th>role</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${users}" var="user">
				<tr data-href="UserForm?id=${user.id}">
					<td><a href="UserForm?id=${user.id}"> ${user.id }</a></td>
					<td>${user.password }</td>
					<td>${user.role}</td>
				</tr>
				</c:forEach>
				</tbody>
		</table> 
	  <div class="pagination-container">
            <nav>
                <ul class="pagination"></ul>
            </nav>
        </div>
	</div>
	<form action="UserForm"><input type="submit" value="NEW" style="margin-left: 35px"></form>
	<form action="#"><input type="submit" value="RECALCULATE" style="margin-left: 35px"></form>
	<a href="Welcome" style="margin-left: 35px">BACK</a>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>

 <script>
    var table = '#mytable'
    var idtable='table table-bordered table-striped'
    $('#maxRows').on('change', function(){
        $('.pagination').html('')
        var trnum = 0
        var maxRows = parseInt($(this).val())
        var totalRows = $(table+' tbody tr').length
        $(table+' tr:gt(0)').each(function(){
            trnum++
            if(trnum > maxRows){
                $(this).hide()
            }
            if(trnum <= maxRows){
                $(this).show()
            }
        })
        if(totalRows > maxRows){
            var pagenum = Math.ceil(totalRows/maxRows)
            for(var i=1;i<=pagenum;){
                $('.pagination').append('<li data-page="'+i+'">\<span>'+ i++ +'<span class="sr-only">(current)</span></span>\</li>').show()
            }
        }
        $('.pagination li:first-child').addClass('active')
        $('.pagination li').on('click',function(){
            var pageNum = $(this).attr('data-page')
            var trIndex = 0;
            $('.pagination li').removeClass('active')
            $(this).addClass('active')
            $(table+' tr:gt(0)').each(function(){
                trIndex++
                if(trIndex > (maxRows*pageNum) || trIndex <= ((maxRows*pageNum)-maxRows)){
                    $(this).hide()
                } else{
                    $(this).show()
                }
            })
        })
    })
	
    jQuery( function($) {
$('tbody tr[data-href]').addClass('clickable').click( function() {
window.location = $(this).attr('data-href');
}).find('a').hover( function() {
$(this).parents('tr').unbind('click');
}, function() {
$(this).parents('tr').click( function() {
window.location = $(this).attr('data-href');
});
});
});

    </script>
<script>buildUserTable();</script>
</body>
</html>
