<%@ include file="/Gerente/headGerente.jsp" %> 

        <div class="row">
          <div class="col-md-12">
          	<div class="page-header">
            	<h4>Gerenciamento de Gerentes</h4>
            </div>
          </div>
        </div>
		
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
		
		
<div>
	<script type="text/javascript">
	
     $(document).ready(function() {
    	 
    	 $('#TableGerente').DataTable( {
    		 
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
    		                   
    		               }
    		           },
    		           {
    		               text: 'Novo',
    		               action: function ( e, dt, button, config ) {
    		            	   
  		            	   	document.getElementById("nome").value = "";
   		            		document.getElementById("id").value = "";
    		            	   
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
    		        url: '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosGerente"/>',
    		        dataSrc: 'records'
    		    },
   		    columns: [
   		              { data: 'id' },
   		              { data: 'nome' }
   		          ],
   		       "columnDefs": [
   		                   	{ "width": "15%", "targets": 0 },
   		                	{ "width": "85%", "targets": 1 }
   		                   
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

<div id = "pesquisar">
<div class="row ">
	<div  class="col-md-12 col-sm-12 col-lg-12">
		<table id="TableGerente" class="display" cellspacing="0" width="100%">
			<thead>
			    <tr>
			        <th>ID</th>
			        <th>NOME</th>
			    </tr>
			</thead>
		</table>
	</div>
</div>
</div>


<div id = "editar">
	<s:form action="CRUDGerente" 
		namespace = "/Gerente" 
		enctype="multipart/form-data" 
		theme="bootstrap" 
		cssClass="form-horizontal">
     <div class="col-md-12 col-sm-12">	
		<div class="panel panel-default">
				<div class="panel-heading">
		            <div class="row">
	            		<div class="col-md-12">
		           			<div class="col-md-2 col-sm-5 col-xs-4">	  
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
	            		</div>
	          		</div>
				</div>
				<div class="panel-body">
					<div class="row ">
	                	<div class="form-group ">
		       				<div class="col-sm-2 col-md-2 ">
					          	<label for="InNome " class="control-label ">Nome:</label>
					        </div>
					        <div class="col-sm-10 col-md-10">
					        	<s:textfield 
				               type="hidden" 
				               cssClass="form-control" 
				               name="gerente.id" 
				               id="id" />
				               
					          	<s:textfield 
					               type="text" 
					               cssClass="form-control" 
					               name="gerente.nome" 
					               id="nome" 
					               placeholder="Nome"/>
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
					               name="gerente.senha" 
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
			</div><!-- panel footer -->
		</div><!-- panel default -->
	</div><!-- col md 6 -->
</s:form>
</div><!-- editar -->       	
       
 <div class="row">
     <div class="col-md-12">
         <hr>
     </div>
 </div>
         
</div>
</div>
</div>
</div>

