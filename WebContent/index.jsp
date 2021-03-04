<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
       	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">

        <title>COLLABORA</title>
    </head>
    <body>
 <div class="cover"> 
   <div class="navbar navbar-default">
      <div class="container">
         <div class="navbar-header"> 
	         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
		         <span class="sr-only">Toggle navigation</span>
		         <span class="icon-bar"></span>
		         <span class="icon-bar"></span>
		         <span class="icon-bar"></span>
	         </button> 
	         <a class="navbar-brand" href="#">
	         	<span>Brand</span>
	         </a> 
         </div>
         <div class="collapse navbar-collapse" id="navbar-ex-collapse"> 
         	<ul class="nav navbar-nav navbar-right"> 
         		<li class="active">
					<a href="#">Home</a> 
				</li>
				<li> 
					<a href="#">Contacts</a> 
				</li>
			</ul> 
		</div>
	</div>
	</div>
		<div class="cover-image" style="background-image : url('http://pingendo.github.io/pingendo-bootstrap/assets/blurry/800x600/10.jpg')"></div>
		<div class="container"> 
			<div class="row">
				<div class="col-md-12 text-center"> 
					<h1>COLLABORA - Objeto Virtual Colaborativa de Probabilidade e Estatística</h1> 
					<p>Meio rápido e prático para realização de atividades colaboraticas por meio de chat.</p>
					<br><br>
					<s:form action = "acessar" theme="bootstrap" cssClass="form-horizontal">
						<s:submit value="Acessar" cssClass = "btn btn-default"/>
					</s:form>
				</div>
			</div>
		</div>
</div>
<div class="section">
	<div class="container">
		<div class="row">
			<div class="col-md-12"><hr></div>
		</div>
	</div>
</div>
	<div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    
                    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="index.jsp">Início</a>
                        </div>
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li class="active">
                                    <a href="<s:url namespace = "/" action = "Descricao"/>">Descrição</a>
                                </li>
                                <li>
                                    <a href="<s:url namespace = "/" action = "Contato"/>">Contato</a>
                                </li>
                            </ul>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Usuário<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="<s:url namespace = "/" action = "acessar"/>">Entrar</a>
                                        </li>
                                        <li>
                                            <a href="<s:url namespace = "/" action = "Sair"/>">Sair</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 text-center">
                    <h2>Contato</h2>
                    <p>Fale conosco.</p>
                    <p>
                        <a class="btn" href="#">Mostrar detalhes »</a>
                    </p>
                </div>
                <div class="col-md-4 text-center">
                    <h2>Descrição</h2>
                    <p>Saiba mais sobre o projeto.</p>
                    <p>
                        <a class="btn" href="#">Mostrar detalhes »</a>
                    </p>
                </div>
                <div class="col-md-4 text-center">
                    <h2>Créditos</h2>
                    <p>Equipe envolvida no projeto.</p>
                    <p>
                        <a class="btn" href="#">Mostrar detalhes »</a>
                    </p>
                </div>
            </div>
        </div>
     
     	<jsp:include page="/foot.jsp"/> 
  