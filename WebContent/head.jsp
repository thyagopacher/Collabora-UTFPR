<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
<head>
    
   	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
        
        
    <title>Collabore - Objeto Virtual de Aprendizagem Collabora para a disciplina de Probabilidade e Estatística</title>
    
    <!-- jquery libery -->
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    
    <!-- bootstrap libery -->
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    
    <!-- Bootstrap Core CSS -->
    <link href="./css/bootstrap.css" rel="stylesheet" type="text/css">

    <!-- Custom CSS -->
    <link href="./css/sb-admin.css" rel="stylesheet" type="text/css">

    <!-- Morris Charts CSS -->
    <link href="./css/plugins/morris.css" rel="stylesheet" type="text/css">

    <!-- Custom Fonts -->
    <link href="./fonts/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        
</head>
<body>

<!-- Início da parte fixa das páginas 
	esta div tbm não possui fechamento neste arquivo,
	deve ser fechada nas páginas específicas -->
	
<div id="wrapper">

	<!-- LOGO DO SITE -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	    <div class="navbar-header">
	        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
	            <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	    </button>
	    <a class="navbar-brand" href="index.html"><img src="img/logoCollabora.png" style="width:140px;height:30px;"></a>
	</div>
	
	<p class="navbar-text text-center"><b>OBJETO DE APRENDIZAGEM DE ESTATÍSTICA</b></p>
	
	<!-- icone de usuario -->	
	<ul class="nav navbar-right top-nav">
	    <li class="dropdown">
	        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> User <b class="caret"></b></a>
	        <ul class="dropdown-menu">
	            <li>
	                <a href="#"><i class="fa fa-fw fa-user"></i> Sair</a>
	            </li>
	        </ul>
	    </li>
	</ul>
	
	<!-- fim dos elementos da barra superior
		inicio dos elementos da barra lateral -->

           
	<div class="collapse navbar-collapse navbar-ex1-collapse">
	    <ul class="nav navbar-nav side-nav">
	        <li>
	            <a href="index.html"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Início</a>
	        </li>
	        <li>
	            <a href="<s:url namespace = "/" action = "Contato"/>"><span class="glyphicon glyphicon-education" aria-hidden="true"></span> Contato</a>
	        </li>
	        <li>
	            <a href="<s:url namespace = "/" action = "Descricao"/>"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Descrição</a>
	        </li>
	    </ul>
	</div>
	
	<!-- Fim da área colapsável superior-->
	</nav>

	<!-- Barra inferior -->
	<nav class="navbar navbar-default navbar-fixed-bottom">
	    <div class="navbar-header">
	        <p class="navbar-text">Créditos</p>
	    </div>
	</nav>

<!-- Início da área principal
	Esta div não possui um fechamento, portanto deve ser fechada em cada pagina de conteudo -->
<div id="page-wrapper">

