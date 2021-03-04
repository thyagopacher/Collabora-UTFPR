	
	<%@ include file="/head.jsp" %> 
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-12  col-sm-12">
	 				<s:if test="hasActionErrors()">
						<div class="errors" id = "errorMessage">
							<s:actionerror theme="bootstrap"/>
						</div>
					</s:if>
				</div>
				<div class="row">
					<div class="col-md-12  col-sm-12">
		 				 <div class="col-md-6"> 
		 				 
		 				  <s:form action="Login"
							 	 enctype="multipart/form-data" theme="bootstrap" 
							 	 cssClass="form-horizontal">
		 				 <div class="panel panel-yellow">
		 				 
                            <div class="panel-heading">
                                <div class="row">
                                    <div class = "text-center">Enter com seu login e senha</div>
                                </div>
                            </div>
                            	<div class = "panel-body">
							 		<div class="form-group">
							 			<div class="col-sm-2 col-md-2">
						                    <label for="login " class="control-label ">Login:</label>
						                </div>
						                <div class="col-sm-10 col-md-10">
								 		<s:textfield name = "nome"
												cssClass="form-control" 
												id="login" 
												type="text"/>
								 		</div>
							 		</div>
			 						<div class="form-group">
			 							<div class="col-sm-2 col-md-2">
						                    <label for="senha " class="control-label ">Senha:</label>
						                </div>
						                <div class="col-sm-10 col-md-10">
								 		<s:textfield name= "senha" cssClass="form-control" 
								 			id="senha" type="password"/>
								 		</div>
			 						</div>
			 					</div>
                            <div class="panel-footer">
                                <s:submit id= "acessar" value = "Acessar" cssClass="btn btn-primary" javascriptTooltip = "clique para acessar!"/>
                            </div>
                        </div>
		 			</s:form>
		 				 
		 				 
						 	
						</div>
		  				<div class="col-md-6">
		  					 <h1>COLLABORA</h1> <h3>Você deve possuir um usuário e senha para entrar</h3> 
		  					 <p>Digite seu usuário (número do registro acadêmico) e a senha para realizar as atividades</p>
		  				</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
</div>
