<%@ include file="/Professor/headProfessor.jsp" %> 

<div class="row">
  <div class="col-md-12">
  	<div class="page-header">
    	<h4 class = "text-uppercase"><strong>�rea do Professor</strong></h4>
    </div>
  </div>
</div>

<section id="fixo" >
<!-- Painel de inform��es sobre as Atividades -->
<div class = "row">
<div class ="questao">
	<div class = "col-md-4 col-sm-4">
		<div class= "panel panel-default">
          <div class="panel-heading text-center text-uppercase">
          		 <strong><span class="glyphicon glyphicon-list-alt"></span> Atividades</strong>
          </div>
	         <div class="panel-body">
			    <div class="col-sm-12 col-md-12">
			    <div class="panel-group">
		     		<div class ="row" >
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
							      <h4 class="panel-title text-center">
							        	Atividade 3  <div class = "pull-right"><span class="badge">Em andamento</span></div>
							      </h4>
					    		</div>
						      	<ul class="list-group">
							        <li class="list-group-item">Fechamento: <span class="badge">15/07</span></li>
							        <li class="list-group-item">Conteudos: Grau de confian�a</li>
							        <li class="list-group-item">Quantidade de exerc�cios: <span class="badge">10</span></li>
							        <li class="list-group-item">
							        	<div class = "text-center">
					    					<button class="btn btn-warning " id="iniciar" >Mais detalhes</button>
					    				</div>
					    			</li>						        
						      	</ul>
					  		</div>
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
		    					<div class="text-center">
								      <h4 class="panel-title">
								        	Atividade 2	<div class = "pull-right"><span class="badge">Finalizada</span></div>
								      </h4>
							      </div>
					    		</div>
						      	<ul class="list-group">
							        <li class="list-group-item">Fechada: <span class="badge">10/06</span></li>
							        <li class="list-group-item">Conteudos: Gr�ficos</li>
							        <li class="list-group-item">Quantidade de exerc�cios: <span class="badge">8</span></li>
							        <li class="list-group-item">
							        	<div class = "text-center">
					    					<button class="btn btn-warning " id="iniciar" >Mais detalhes</button>
					    				</div>
					    			</li>
						      	</ul>
					  		</div>
		          	  		<div class="panel panel-default">
				    			<div class="panel-heading">
		    					<div class="text-center">
								      <h4 class="panel-title">
								        	Atividade 1	<div class = "pull-right"><span class="badge">Finalizada</span></div>
								      </h4>
							      </div>
					    		</div>
						      	<ul class="list-group">
							        <li class="list-group-item">Fechada: <span class="badge">05/06</span></li>
							        <li class="list-group-item">Conteudos: Introdu��o</li>
							        <li class="list-group-item">Quantidade de exerc�cios: <span class="badge">8</span></li>
							        <li class="list-group-item">
							        	<div class = "text-center">
					    					<button class="btn btn-warning " id="iniciar" >Mais detalhes</button>
					    				</div>
					    			</li>
						      	</ul>
				        </div> 
				        </div>
         			</div>
		        </div>
			</div>
			<div class = "panel-footer">
			</div>
		</div>
	</div>
</div>
	<!-- Detalhes -->
    <div class="col-md-8">
    	<div class="panel panel-yellow">
			<div class="panel-heading text-center text-uppercase">
			   <strong><span class="glyphicon glyphicon-plus"></span> Detalhes</strong>
			</div>
			<div class="panel-body">
				<div class="row ">
         		<div class = "col-sm-12 col-md-12">
         			<div class="well">
	         			<h5 class = "text-center text-uppercase"><strong>Progesso dos grupos na atividade</strong></h5>
	         			<div class="progress">
						  <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
						    60%
						  </div>
						</div>
					</div>
		    	</div>
		    	</div>
		    	<section id="secItens">
		    	<div class = "row">
		    		<div class = "col-md-4 col-sm-4">
		    		
           				<div class = "col-md-12 col-sm-12 text-center well">
            				<h4 class="text-uppercase"><strong> Mensagens</strong></h4>
            				<hr>
            				<div class = "huge">340</div>
            				<p> Mensagens postadas nessa atividade</p>
            				<button class = "btn btn-warning" data-toggle="modal" data-target="#classificar">Classificar</button>
           				</div>
           			
		    		</div>
		    		<div class = "col-md-4 col-sm-4">
		    		
           				<div class = "col-md-12 col-sm-12 text-center well">
            				<h4 class="text-uppercase"><strong> Links</strong></h4>
            				<hr>
            				<div class = "huge">35</div>
            				<p> Links compartilhados nesta atividade</p>
            				<button class = "btn btn-warning">Classificar</button>
           				</div>
           			
		    		</div>
		    		<div class = "col-md-4 col-sm-4">
		    		
           				<div class = "col-md-12 col-sm-12 text-center well">
            				<h4 class="text-uppercase"><strong> Imagens</strong></h4>
            				<hr>
            				<div class = "huge">10</div>
            				<p> Imagens enviadas nesta atividade</p>
            				<button class = "btn btn-warning">Classificar</button>
           				</div>
           			
		    		</div>
		    	</div>
		    	</section>
			</div>
			<div class="panel-footer">
			</div>
		</div>
		<!-- mostra as op��es relativas ao celular -->	
		<div class = "opcoesCelular">
			<div class = "row">
				<div class = "col-sm-12 col-xs-12">
					<div class = "text-center">
						<div class="btn-group">
							<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#mensagem">Mensagens</button>
							<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#historico">Hist�rico</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
	<br>
	<br>
	<br>
</div>
</div><!-- fecha row -->
</section><!-- fecha fixo -->

<!-- Modal para apresenta��o da resposta correta -->
<div class="modal fade" id = "classificar" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title text-center text-uppercase">Classificar <span class = "glyphicon glyphicon-th"></span></h4>
      		</div>
      		<div class="modal-body">
        		<div class = "row">
        			<div class="col-sm-4 col-md-4">
	          	  		<div class="panel panel-default">
			    			<div class="panel-heading">
							      <h4 class="panel-title text-center">
							        	<button class = "btn btn-default btn-sm"> <span class = "glyphicon glyphicon-arrow-left"></span></button> 
							        	Grupo 1
							        	<button class = "btn btn-default btn-sm"> <span class = "glyphicon glyphicon-arrow-right"></span></button>
							      </h4>
				    		</div>
						    <div class = "panel-body">
						      	<ul class="list-group">
							        <li class="list-group-item">Lucas Novelli</li>
							        <li class="list-group-item">Eliana Ishikawa</li>
							        <li class="list-group-item">Simone Nasser </li>
						      	</ul>
					    	</div>
				  		</div>
			        </div>
			        <div class="col-sm-8 col-md-8">
		        		<div class = "col-md-4 col-sm-4">
			    		
	           				<div class = "col-md-12 col-sm-12 text-center well">
	            				<h4 class="text-uppercase"><strong> Mensagens</strong></h4>
	            				<hr>
	            				<div class = "huge">40</div>
	            				<p> Mensagens do grupo postadas nessa atividade</p>
	           				</div>
	           			
			    		</div>
			    		<div class = "col-md-4 col-sm-4">
			    		
	           				<div class = "col-md-12 col-sm-12 text-center well">
	            				<h4 class="text-uppercase"><strong> Links</strong></h4>
	            				<hr>
	            				<div class = "huge">5</div>
	            				<p> Links do grupo compartilhados nesta atividade</p>
	           				</div>
	           			
			    		</div>
			    		<div class = "col-md-4 col-sm-4">
			    		
	           				<div class = "col-md-12 col-sm-12 text-center well">
	            				<h4 class="text-uppercase"><strong> Imagens</strong></h4>
	            				<hr>
	            				<div class = "huge">1</div>
	            				<p> Imagens do grupo enviadas nesta atividade</p>
	           				</div>
	           			
		    			</div>
			        </div>
        		</div><!-- /row -->
        		<div class = "row">
        			<section id = "fixoClassificar">
        			<div class = "col-md-12 col-sm-12">
        				<div class="panel panel-default">
        					<div class ="panel-body">
        						<h5 class="text-uppercase text-center"><strong> Mensagens</strong></h5>
        						<div class="input-group">
							        <input id="mensagem1" class="form-control"	 value="Oi pessoal, tudo bom?" type="text" readonly>
							        <span class="input-group-btn">
							            <button class="btn btn-success" id="btn-relevante">
							                Relevante  <span class="glyphicon glyphicon-ok"></span></button>
						                 <button class="btn btn-danger" id="btn-relevante">
						                	N�o Relevante  <span class="glyphicon glyphicon-remove"></span></button>
							        </span>
							     </div>
							     <div class="input-group">
							        <input id="mensagem1" class="form-control" value="Tudo sim e você?" type="text" readonly>
							        <span class="input-group-btn">
							            <button class="btn btn-success" id="btn-relevante">
							                Relevante  <span class="glyphicon glyphicon-ok"></span></button>
						                 <button class="btn btn-danger" id="btn-relevante">
						                	N�o Relevante  <span class="glyphicon glyphicon-remove"></span></button>
							        </span>
							     </div>
							     <div class="input-group">
							        <input id="mensagem1" class="form-control" value="Vamos começar?" type="text" readonly>
							        <span class="input-group-btn">
							            <button class="btn btn-success" id="btn-relevante">
							                Relevante  <span class="glyphicon glyphicon-ok"></span></button>
						                 <button class="btn btn-danger" id="btn-relevante">
						                	N�o Relevante  <span class="glyphicon glyphicon-remove"></span></button>
							        </span>
							     </div>
							     <hr>
        					</div>
        				</div>
       				</div>
        			</section>
        		</div><!-- /row -->
      		</div><!-- /.modal-body -->
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