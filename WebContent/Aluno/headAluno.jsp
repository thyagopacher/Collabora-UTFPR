<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>COLLABORA</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- jquery libery -->
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- bootstrap libery -->
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<!-- Bootstrap Core CSS -->
<link href="/Ovaces/css/bootstrap.css" rel="stylesheet" type="text/css">

<!-- Custom CSS -->
<link href="/Ovaces/css/sb-admin-chat.css" rel="stylesheet"
	type="text/css">

<!-- Morris Charts CSS -->
<link href="/Ovaces/css/plugins/morris.css" rel="stylesheet"
	type="text/css">

<!-- Custom Fonts -->
<link href="/Ovaces/fonts/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<!-- Data Table js -->
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.11/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/select/1.1.2/js/dataTables.select.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>

<!-- data table css-->
<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.11/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/select/1.1.2/css/select.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css">

</head>


<body>

	<!-- In�cio da parte fixa das p�ginas 
	esta div tbm n�o possui fechamento neste arquivo,
	deve ser fechada nas p�ginas espec�ficas -->

	<div id="wrapper">

		<!-- LOGO DO SITE -->
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.html"><img
				src="/Ovaces/img/logoCollabora.png"
				style="width: 140px; height: 30px;"></a>
		</div>

		<p class="navbar-text text-center">
			<b>COLLABORA</b>
		</p>

		<!-- icone de usuario -->
		<ul class="nav navbar-right top-nav">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><i class="fa fa-user"></i> User <b
					class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#"><i class="fa fa-fw fa-user"></i> Sair</a></li>
				</ul></li>
		</ul>

		<!-- fim dos elementos da barra superior
		inicio dos elementos da barra lateral -->


		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav side-nav">
				<li><a class="text-uppercase" href=# /><span
					class="glyphicon glyphicon-home"></span> Início</a></li>
				<li><a class="text-uppercase" href=# /><span
					class="glyphicon glyphicon-comment"></span> Chat</a></li>
			</ul>
		</div>

		<!-- Fim da �rea colaps�vel superior--> </nav>

		<!-- Barra inferior -->
		<nav class="navbar navbar-default navbar-fixed-bottom">
		<div class="navbar-header">
			<p class="navbar-text text-center">
				<b>COLLABORA 2016</b>
			</p>
		</div>
		</nav>

		<!-- In�cio da �rea principal
	Esta div n�o possui um fechamento, portanto deve ser fechada em cada pagina de conteudo -->
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-md-12 col-sm-12">

						<div class="row">
							<div class="col-md-12 col-sm-12 col-lg-12">
								<s:if test="hasActionMessages()">
									<div class="Messages">
										<s:actionmessage theme="bootstrap" />
									</div>
								</s:if>
								<s:if test="hasActionErrors()">
									<div class="Messages">
										<s:actionerror theme="bootstrap" />
									</div>
								</s:if>
							</div>
						</div>