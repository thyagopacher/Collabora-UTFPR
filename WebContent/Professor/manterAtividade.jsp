<%@ include file="/Professor/headProfessor.jsp" %> 

<div class="row">
  <div class="col-md-12">
  	<div class="page-header">
    	<h4><strong>Elaboração da Atividade</strong></h4>
    </div>
  </div>
</div>

<!-- Painel de informções sobre a Atividade -->
<div class = "row">
<s:form 		
		action="" namespace=""
	   enctype="multipart/form-data" 
	   theme="bootstrap" 
	   cssClass="form-horizontal"
	   method="post">   
<div class = "col-md-12 col-lg-12">
	<div class="panel panel-default">
          <div class="panel-heading">
             	<strong>Informações sobre a Atividade</strong>
          </div>
         <div class="panel-body">
         	<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="form-group ">
				        <div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label">Título:</label>
				        </div>
				        <div class="col-sm-4 col-md-4">
					        <input id="btn-input" class="form-control input-sm" placeholder="Titulo da Atividade" type="text">
				    	</div>
	        		 	<div class="col-sm-1 col-md-1">
				         	<label for="registro " class="control-label ">Entrega:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					         <input type="date" class="form-control" id="entre_ati" placeholder="Data">
				    	</div>
				    	 <div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Horario:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					        <input type="time" class="form-control" id="hota_ati" placeholder="Horário">
				    	</div>
		    		</div>	
         		
         		</div>
		    </div>
		</div>
	</div>
</div>
</s:form>
</div><!-- fecha row -->

<!-- Painel de informções sobre os conteúdos -->
<div class = "row">
<s:form 		
		action="" namespace=""
	   enctype="multipart/form-data" 
	   theme="bootstrap" 
	   cssClass="form-horizontal"
	   method="post">   
<div class = "col-md-12 col-lg-12">
	<div class="panel panel-default">
          <div class="panel-heading">
             	<strong>Conteúdo</strong>
          </div>
         <div class="panel-body">
         	<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="form-group "><!-- Ementa e conteudo -->
				        <div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Ementa:</label>
				        </div>
				        <div class="col-sm-5 col-md-5">
					        <input id="btn-input" class="form-control input-sm" placeholder="Digite a ementa" type="text">
				    	</div>
	        		 	<div class="col-sm-1 col-md-1">
				         	<label for="registro " class="control-label ">Conteúdo:</label>
				        </div>
				        <div class="col-sm-5 col-md-5">
					        <input id="btn-input" class="form-control input-sm" placeholder="Digite o conteúdo" type="text">
				    	</div>
		    		</div>
		    		<div class="form-group ">
		    			<div class="col-sm-12 col-md-12">
		    				<h5><strong>Escolha a quantidade de questões para cada nível de dificuldade</strong></h5>
		    			</div>
		    		</div>	
         			<div class="form-group "><!-- Quantidade -->
				        <div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Díficil:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					        <input id="btn-input" class="form-control input-sm" type="text">
				    	</div>
	        		 	<div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Médio:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					        <input id="btn-input" class="form-control input-sm" type="text">
				    	</div>
				    	<div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Fácil:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					        <input id="btn-input" class="form-control input-sm" type="text">
				    	</div>
				    	<div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Peso:</label>
				        </div>
				        <div class="col-sm-2 col-md-2">
					        <input id="btn-input" class="form-control input-sm" type="text">
				    	</div>
		    		</div>	
         		</div>
		    </div>
		</div><!-- painel body -->
		<div class = "panel-footer">
			<div class = "row">
				<div class = "text-center">
					<button class="btn btn-default btn-sm" id="limpar"  >Limpar <span class="glyphicon glyphicon-erase"></span></button>
					<button class="btn btn-warning btn-sm" id="adiciona" >Adicionar <span class="glyphicon glyphicon-ok"></span></button>
				</div>
			</div>
		</div>
	</div>
</div>
</s:form>
</div>	<!-- fehca row -->

<div class = "row">
<!-- resumo das atividades -->  
<div class = "col-md-12 col-lg-12">
	<div class="panel panel-default">
          <div class="panel-heading">
             	<strong>Conteúdos Adicionados</strong>
          </div>
         <div class="panel-body">
         	<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="panel-group">
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapse1">Conteúdo 1</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapse1" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Difíceis: <span class="badge">10</span></li>
								        <li class="list-group-item">Médias: <span class="badge">5</span></li>
								        <li class="list-group-item">Fáceis: <span class="badge">7</span></li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapse2">Conteúdo 2</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapse2" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Difíceis: <span class="badge">10</span></li>
								        <li class="list-group-item">Médias: <span class="badge">5</span></li>
								        <li class="list-group-item">Fáceis: <span class="badge">7</span></li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapse3">Conteúdo 3</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapse3" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Difíceis: <span class="badge">10</span></li>
								        <li class="list-group-item">Médias: <span class="badge">5</span></li>
								        <li class="list-group-item">Fáceis: <span class="badge">7</span></li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
         			</div>
		    	</div>
		    </div>
		</div>
	</div>
</div>

</div>

<div class = "row">
<!-- Formação dos grupos -->
<s:form 		
		action="" namespace=""
	   enctype="multipart/form-data" 
	   theme="bootstrap" 
	   cssClass="form-horizontal"
	   method="post">   
<div class = "col-md-12 col-lg-12">
	<div class="panel panel-default">
          <div class="panel-heading">
             	<strong>Formar grupos</strong>
          </div>
         <div class="panel-body">
         	<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="form-group ">
				        <div class="col-sm-1 col-md-1">
				          	<label for="registro " class="control-label ">Grupos:</label>
				        </div>
				        <div class="col-sm-4 col-md-4">  
				        	<div class = "row">
	                            <select multiple="" class="form-control">
	                                <option>Grupo 1</option>
	                                <option>Grupo 2</option>
	                                <option>Grupo 3</option>
	                                <option>Grupo 4</option>
	                                <option>Grupo 5</option>
	                            </select>
	                        </div>
	                        <hr>
                            <div class = "row">
	                             <div class="text-center">
	                                   <button class="btn btn-default btn-sm" id="ExGrup" >Excluir <span class="glyphicon glyphicon-trash"></span></button>
	                                   <button class="btn btn-default btn-sm" id="EdGrup" >Editar <span class="glyphicon glyphicon-pencil"></span></button>
	                                   <button class="btn btn-default btn-sm" id="novoGrup" >Novo <span class="glyphicon glyphicon-plus"></span></button>
	                                   <button class="btn btn-warning btn-sm" id="detalhe" >Detalhes <span class="glyphicon glyphicon-zoom-in"></span></button>
	                              </div>
                             </div>
				    	</div>
	        		 	
	        		 	<div class = "col-md-1 col-sm-1">
	        		 		<label class = "control-label">Intgrantes:</label>
	        		 	</div>
	        		 	
	        		 	<div class = "col-md-5 col-sm-5">
                                <div class="list-group">
                                    <div class="list-group-item">
                                        <div class="media">
											<a class="pull-left" href="#">
									    		<img class="media-object img-circle" style="max-height:40px;" src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
											</a>
											<div class="media-body" >
										    	<h5>Lucas Novelli </h5>
											</div>
										</div>
                                    </div>
                                    <div class="list-group-item">
                                        <div class="media">
											<a class="pull-left" href="#">
								    			<img class="media-object img-circle" style="max-height:40px;" src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
											</a>
											<div class="media-body" >
										    	<h5>Eliana Ishikawa</h5>
											</div>
										</div>
                                    </div>
                                     <div class="list-group-item">
                                        <div class="media">
											<a class="pull-left" href="#">
								    			<img class="media-object img-circle" style="max-height:40px;" src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
											</a>
											<div class="media-body" >
										    	<h5>Simone Nasser</h5>
											</div>
										</div>
                                    </div>
                                </div>
                                <div class="text-right">
                                   <button class="btn btn-warning btn-sm" id="adiciona" >Incluir grupo <span class="glyphicon glyphicon-ok"></span></button>
                                </div>
	        		 	</div><!-- fim col-sm-5 -->
	        		 	
		    		</div>	<!-- fim from group -->
         		
         		</div> <!-- fim col 12 -->
		    </div> <!-- fim row do body -->
		</div><!-- fim body do painel -->
	</div> <!-- fim panel -->
</div><!-- fim col-12 da row externa -->
</s:form>
</div> <!-- fim row externa -->



<div class = "row">
<!-- resumo dos grupos -->  
<div class = "col-md-12 col-lg-12">
	<div class="panel panel-default">
          <div class="panel-heading">
             	<strong>Grupos Adicionados</strong>
          </div>
         <div class="panel-body">
         	<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="panel-group">
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapseGrup1">Grupo 1</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapseGrup1" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Lucas Novelli</li>
								        <li class="list-group-item">Eliana Ishikawa</li>
								        <li class="list-group-item">Simone Nasser </li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapseGrup2">Grupo 2</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapseGrup2" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Lucas Novelli</li>
								        <li class="list-group-item">Eliana Ishikawa</li>
								        <li class="list-group-item">Simone Nasser</li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
				        <div class="col-sm-3 col-md-3">
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
								      <h4 class="panel-title">
								        	<a data-toggle="collapse" href="#collapseGrup3">Grupo 3</a>
								        	<div class = "pull-right">
								        		<a href="#"><span class="glyphicon glyphicon-remove" aria-hidden="true" ></span></a>
								        	</div>
								      </h4>
					    		</div>
							    <div id="collapseGrup3" class="panel-collapse collapse">
							      	<ul class="list-group">
								        <li class="list-group-item">Lucas Novelli</li>
								        <li class="list-group-item">Eliana Ishikawa</li>
								        <li class="list-group-item">Simone Nasser</li>
							      	</ul>
						    	</div>
					  		</div>
				        </div>
         			</div>
		    	</div>
		    </div>
		</div>
	</div>
</div>

</div>
<div class = row>
	<div class = "col-md-12 col-sm-12">
		<div class="panel panel-default">
			<div class="panel-body">
			     <div class = "text-center">
			     	 <button class="btn btn-default" id="cancelar" >Cancelar <span class="glyphicon glyphicon-remove"></button>
			     	 <button class="btn btn-default" id="limpar" >Limpar <span class="glyphicon glyphicon-erase"></span></button>
			     	 <button class="btn btn-warning" id="gerar" data-toggle="modal" data-target="#resumo">Gerar <span class="glyphicon glyphicon-ok"></span></button>
			     </div>
			</div>
		</div>
	</div>
</div>


<!-- Modal para apresentação do resumo da atividade -->
<div class="modal fade" id ="resumo" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title"><strong>Detalhes da Atividade</strong></h4>
      		</div>
      		<div class="modal-body">
			<section id="secExer">
			<div class = "row">
				<div class = "col-md-12 col-sm-12">
					<div class="panel panel-default">
				          <div class="panel-heading">
				             	<strong>Exercícios</strong>
				          </div>
			      		<div class= "panel-body">
			      			<div class = "row">
			      				<div class = "col-md-4 col-sm-4"><!-- listagem dos conteudos -->
	      							<select multiple="" class="form-control">
		                                <option>Conteúdo 1</option>
		                                <option>Conteúdo 2</option>
		                                <option>Conteúdo 3</option>
	                        		</select>
			      				</div>
			      				<div class = "col-md-8 col-sm-8"><!-- mostrar os exercicios -->
			      					<div class = "row col-md-12 col-sm-12"><!-- exercicio -->
				      					<div class="panel panel-default">
				      						<div class = "panel-heading">
				      							<div class = "row">
					      							<strong>Exercicio 1</strong> <div class = "pull-right">
					      								<span class="glyphicon glyphicon-asterisk"></span>Dificuldade : Alta
					      							</div>
				      							</div>
				      						</div>
				      						<div class= "panel-body">
					      						<p>&nbsp;&nbsp;(2.2)Um levantamento realizado em uma amostra de pessoas normais, 
					      						segundo a quantidade de hemoglobina ( g/ 100 ml) existente no sangue 
					      						forneceu os seguintes resultados: 13,5 12,5 10,6 15,1 11,7 12,9 12,8 9,4 14,9 12,0.<br> 
					      						&nbsp;&nbsp;De acordo com os dados, assinale as alternativas verdadeiras no estilo somatório</p>
			      							</div>
			      							<div class = "panel-footer">
			      								<div class = "text-center">
			      									<div class="btn-group" role="group" aria-label="Basic example" id="groupExer">
													  <button type="button" id="exerAnt" class="btn btn-default"><span class="glyphicon glyphicon-arrow-left"></span> Anterior</button>
													  <button type="button" id="exerProx" class="btn btn-default">Próximo <span class="glyphicon glyphicon-arrow-right"></span></button>
													</div>
			      								</div>
			      							</div>
				      					</div>
			      					</div>
			      				</div>
			      			</div>
			      		</div>    
		          	</div>					
				</div>
			</div>
			</section>
      		</div><!-- modal body -->
      		<div class = "modal-footer">
      			<div class = "row text-center">
      				<button class="btn btn-default" id="voltar" ><span class="glyphicon glyphicon-arrow-left"></span> Voltar</button>
			     	 <button class="btn btn-warning" id="finaliza" >Finalizar <span class="glyphicon glyphicon-ok"></span></button>
      			</div>
      		</div>
    	</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- fechamento das Divs do head -->
</div>
</div>
</div>
</div>

</body>
</html>