
<%@ include file="/Gerente/headGerente.jsp" %> 
<div>
<script type="text/javascript">
	
function TestarEditar(){
	
	//console.log("estoy aqui");
	
	aux = sessionStorage.getItem("editar");
	if (aux == "true"){
		document.getElementById('pesquisar').style.display = "none"; 
		document.getElementById('editar').style.display = "";
	}		
	else{
		document.getElementById('editar').style.display = 'none'; 
		document.getElementById('pesquisar').style.display = ""; 
	}
		
	
}

function esconderEditar(){
	
	sessionStorage.setItem("editar", "false");
	document.getElementById('editar').style.display = 'none';
	document.getElementById('pesquisar').style.display = ""; 
	
}

window.onload = TestarEditar;

</script>
</div>

<div>
<script type="text/javascript">
     $(document).ready(function() {
    	 
    	 $('#TableAluno').DataTable( {
    		 
    		 dom: 'Bfrtip',
    		 select: true,
    		 
    		 buttons: [
						
    		           {
    		               extend: 'selected',
    		               text: 'Selecionar',
    		               action: function ( e, dt, button, config ) {
    		                   
    		            	   document.getElementById('pesquisar').style.display = "none"; 
       		            	   document.getElementById('editar').style.display = "";
        		            	   
       		            		sessionStorage.setItem("editar", "true");
        		            	  	
       		            		document.getElementById("nome").value = dt.rows('.selected').data()[0].nome;
       		            		document.getElementById("id").value = dt.rows('.selected').data()[0].id;
    		                   
    		                   document.getElementById("nome").value = dt.rows('.selected').data()[0].nome;
    		                   document.getElementById("registro").value = dt.rows('.selected').data()[0].registro;
    		                   document.getElementById("id").value = dt.rows('.selected').data()[0].id;
    		                   document.getElementById("senhaAntiga").value = dt.rows('.selected').data()[0].senha;
    		                   
    		                   
    		               }
    		           }, 
    		           {
    		               text: 'Novo',
    		               action: function ( e, dt, button, config ) {
    		            	   
   		            	   document.getElementById("nome").value ="";
  		            		document.getElementById("id").value = "";
   		                   
   		                   document.getElementById("nome").value = "";
   		                   document.getElementById("registro").value = "";
   		                   document.getElementById("id").value = "";
   		                   document.getElementById("senhaAntiga").value = "";
    		            	   
    		            	 document.getElementById('pesquisar').style.display = "none"; 
    		            	 document.getElementById('editar').style.display = ""; 
    		            	 
    		            	 sessionStorage.setItem("editar", "true");	
    		                   
    		               }
    		           }
    		   ],
    		 
    		 language: {
    		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
    		    },
    		 
    		 ajax: {
    		        url: '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosAluno"/>',
    		        dataSrc: 'records'
    		    },
   		    columns: [
   		              { data: 'id' },
   		              { data: 'nome' },
   		              { data: 'registro' },
   		          ],
   		       "columnDefs": [
   		                   	{ "width": "15%", "targets": 0 },
   		                	{ "width": "60%", "targets": 1 },
   		                	{ "width": "25%", "targets": 2 }
   		                   
   		                 ]
    		    
    		},
    	
    		$('#TableAluno tbody').on( 'click', 'tr', function () {
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	        }
    		})
    		
    	 );
             
     });
</script>
</div>



       <div class="row">
          <div class="col-md-12">
          	<div class="page-header">
            	<h4>Gerenciamento de Aluno</h4>
            </div>
          </div>
        </div>

<!-- Paine editar -->
<div id = "editar">
<div class="row">
	<s:form 		
		action="CRUDAluno" namespace="/Gerente"
						   enctype="multipart/form-data" 
						   theme="bootstrap" 
						   cssClass="form-horizontal"
						   method="post">       
	<div class="col-md-12 col-sm-12">	
		<div class="panel panel-default">
			<div class="panel-heading">
           		 <div class="row">
            		<div class="col-md-2 col-sm-3 col-xs-4">	  
            		   	<div class = "pull-left">
            		   		<button onclick = "esconderEditar()" type="button" class="btn btn-warning btn-sm">
              				<span class = "glyphicon glyphicon-arrow-left"></span> Voltar
              				</button>
            		   	</div>       				
            		</div>
            		<div class="col-md-8 col-sm-5 col-xs-4">
              			<h4 class = "text-center text-uppercase">
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados</h4>
              		</div>
              		<div class="col-md-2 col-sm-3 col-xs-4" >
              			<div class = "pull-right">
              				<button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#arquivo">
              				<span class = "glyphicon glyphicon-th-list"></span> Enviar arquivo
              				</button>
           				</div>
            		</div>
          		</div>
			</div>
			<div class="panel-body">
				<div class="row ">
				 	 <!-- <div class="col-md-12 "> -->
						     <div class="form-group ">
						     
						     
						     	<s:textfield 
						               type="hidden" 
						               cssClass="form-control" 
						               name="aluno.id" 
						               id="id" />
						               
						        <s:textfield 
						               type="hidden" 
						               cssClass="form-control" 
						               name="senhaAntiga" 
						               id="senhaAntiga" />
						     
						     
						        <div class="col-sm-2 col-md-2 ">
						          	<label for="InNome " class="control-label ">Nome:</label>
						        </div>
						        <div class="col-sm-10 col-md-10 ">
						          	<s:textfield 
						               type="text" 
						               cssClass="form-control" 
						               name="aluno.nome" 
						               id="nome" 
						               placeholder="Nome"/>
						        </div>
						     </div>
						     <div class="form-group ">
						        <div class="col-sm-2 col-md-2">
						          	<label for="registro " class="control-label ">Registro:</label>
						        </div>
						        <div class="col-sm-10 col-md-10">
						          	<s:textfield 
						               type="text" 
						               cssClass="form-control" 
						               name="aluno.registro" 
						               id="registro" 
						               placeholder="Registro"/>
						        </div>
						     </div>
						     <div class="form-group ">
						        <div class="col-sm-2 col-md-2">
						          	<label for="inSenha " class="control-label ">Senha:</label>
						        </div>
						        <div class="col-sm-10 col-md-10">
						          	<s:textfield 
						               type="password" 
						               cssClass="form-control" 
						               name="aluno.senha" 
						               id="senha" 
						               placeholder="Senha"/>
						        </div>
						     </div>
						     <div class="form-group ">
						        <div class="col-sm-2 col-md-2">
						          	<label for="confirmarSenha " class="control-label ">Confimar Senha:</label>
						        </div>
						       	<div class="col-sm-10 col-md-10">
					            	<s:textfield 
					               type="password" 
					               cssClass="form-control" 
					               name="confirmacao" 
					               id="confirmacao" 
					               placeholder="Repita a senha"/>
						       	</div>
						     </div>
				  <!-- </div> --> 
				</div><!-- row -->
			</div><!-- panel body -->
			<div class="panel-footer">
				<div class="row ">
	  				<div class="col-md-12 ">
	  					<div class = "text-center">
	  						<s:submit  name="button" value = "Salvar" cssClass= "btn btn-warning"/>
						    <s:submit  name="button" value = "Excluir" cssClass= "btn btn-danger"/>
						    <button type= "reset"  class ="btn btn-default">Limpar</button>
	  					</div>
	  				</div>
				</div>			
			</div>
		</div> <!-- panel default -->
		</div> <!-- col md 6 -->
		
	</s:form>
	<div class="row ">
          <div class="col-md-12 col-sm-12 col-lg-12">
              	<div id="DisciplinaTable"></div>
          </div>
    </div>

</div>	<!-- row -->
</div>  <!-- div editar -->

          
          <!-- Tabela -->
          <div id = "pesquisar">
          	 <div class="row ">
            <div  class="col-md-12 col-sm-12 col-lg-12">
              <table id="TableAluno" class="display" cellspacing="0" width="100%">
		        <thead>
		            <tr>
		                <th>ID</th>
		                <th>NOME</th>
		                <th>REGISTRO</th>
		            </tr>
		        </thead>
		       </table>
            </div>
          </div>
          </div>
<br>
<br>
<br>
<!-- modal -->
<div class="modal fade" id="arquivo" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Adicionar via arquivo</h4>
      </div>
      <div class="modal-body">
       		<div class="row">           
	            	<div class="col-md-12">
	                <p>Adicionar por arquivo é um meio rápido e fácil de incluir uma quantidade
	                  grande de alunos.
	                  <br>As informações necessárias para o cadastro de um aluno são: nome, registro
	                  e turma.
	                  <br>
	                  <br>Estas informações devem estar dispostas da seguinte maneira:
	                  <br>&nbsp;- Somente um aluno por linha
	                  <br>&nbsp;- As informações devem ser sepadas por um traço
	                  <br>&nbsp;- Ao final da linha deve ter um ponto e virgula
	                  <br>&nbsp;- O arquivo deve ser um .txt
	                  <br>
	                  <br>Exemplo:
	                  <br>João da Silva - 12345 - aaa; &nbsp;</p>
	              </div>
            </div>
      </div>
      <div class="modal-footer">
       <div class="row">
            	<div class="col-md-12">
            		<s:form action="UploadAluno" 
	             			namespace = "/Gerente"
			             	method="post" 
			             	enctype="multipart/form-data"
			             	theme="bootstrap" 
			             	cssClass="form-horizontal">
	             			<div class="form-group">
	             		
	             				<label for="fileAluno">Escolha o arquivo</label>
	             			
			    				<s:file name="fileUpload" id="fileAluno"/>
			    			
			    				<p class="help-block">Escolha um arquivo no formato .txt que atenda as especificações descritas
	                    			ao lado</p>
	                    		
			    				<s:submit value ="Enviar" cssClass= "btn btn-warning"/>
			    				<button class = "btn btn-default" data-dismiss = "modal">
			    				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button>
			    			</div>
						</s:form>
              	</div>
       		</div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- fim do wrapper -->
</div>
</div>
</div>
</div>
