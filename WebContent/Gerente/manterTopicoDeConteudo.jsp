<%@ include file="/Gerente/headGerente.jsp" %> 

<div>
<script type="text/javascript">
function TestarEditar(){
	
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


function tabEmenta(ementa) {
	
	if(!$.fn.dataTable.isDataTable('#tabelaEmenta')) {
			
			var table = $('#tabelaEmenta').DataTable( {	
				 
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
		   		            		
		   		            		console.log(dt.rows('.selected').data());
				                   
				                   document.getElementById("Ementaid").value = dt.rows('.selected').data()[0].idEmenta;
				                   document.getElementById("EmentaNome").value = dt.rows('.selected').data()[0].nome;
				                   document.getElementById("Disciplinaid").value = dt.rows('.selected').data()[0].disciplina.idDisicplina;
				                   document.getElementById("DisciplinaNome").value = document.getElementById("discPesq").value;
				                   
				               }
				           }, 
				           {
				               text: 'Novo',
				               action: function ( e, dt, button, config ) {
				            	   
				            	   document.getElementById("Ementaid").value = "";
				                   document.getElementById("EmentaNome").value = "";
				                   document.getElementById("Disciplinaid").value = document.getElementById("discPesqId").value;
				                   document.getElementById("DisciplinaNome").value = document.getElementById("discPesq").value;
				            	   
				            	 document.getElementById('pesquisar').style.display = "none"; 
				            	 document.getElementById('editar').style.display = ""; 
				            	 
				            	 sessionStorage.setItem("editar", "true");	
				                   
				               }
				           }
				   ],
				
				language: {
			        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
			    },
			 
			    "aaData": ementa,
    		    "aoColumns": [
    		        { "mDataProp": "idEmenta" },
    		        { "mDataProp": "nome" },
    		    ]
			},
		
			$('#tabelaEmenta tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
			})
		
		);
	}
}

function tabDisc(){
	
	if(!$.fn.dataTable.isDataTable('#TabelaDisciplina')) {
		
		 var table = $('#TabelaDisciplina').DataTable( {
			 
			 dom: 'Bfrtip',
			 select: true,
			 
			 buttons: [
						
			           {
			               extend: 'selected',
			               text: 'Selecionar',
			               action: function ( e, dt, button, config ) {
			            			
		            			document.getElementById("discPesq").value = dt.rows('.selected').data()[0].nome;
		            			document.getElementById("discPesqId").value = dt.rows('.selected').data()[0].idDisicplina;
		            			
		            			$.ajax({
		            				type : "POST", 
		            				url : '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarEmentaDisciplina"/>',
		            				data : "idDisciplina="+dt.rows('.selected').data()[0].idDisicplina,
		            				dataType : "json",
		            				async: false,
		            				success : function(result) {
		            					tabEmenta(result.records);	
		            				}
		            			});
		            			
		            			 $('#pesqDisciplina').modal('hide');
		            			
		            		}
			                   
			               
			           }
			   ],
			 
			 language: {
			        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
			    },
			 
			 ajax: {
			        url: '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosDisciplina"/>',
			        dataSrc: 'records'
			    },
			    columns: [
			              { data: 'idDisicplina' },
			              { data: 'nome' },
			           	  { data: 'codigo' },
			          ],
			       "columnDefs": [
			                   	{ "width": "15%", "targets": 0 },
			                	{ "width": "60%", "targets": 1 },
			                	{ "width": "15%", "targets": 2 },
			                   
			                 ]
			    
			},
			$('#TabelaDisciplina tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
			})
		);
	}
}

</script>

</div>

<div class="row">
   <div class="col-md-12">
   	<div class="page-header">
     	<h4>Gerenciamento de tópico de conteúdo</h4>
     </div>
   </div>
 </div>



<!-- Painel do editar -->  
<div id = "editar">
<s:form 
	   action="CRUDEmenta"
	   namespace="/Gerente" 
	   enctype="multipart/form-data" 
	   theme="bootstrap" 
	   cssClass="form-horizontal">	
<div class="row">        
	<div class="col-md-12 col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading">
	            <div class="row">
            		<div class="col-md-2 col-sm-5 col-xs-4">	  
            		   	<div class = "pull-left">
            		   		<button onclick = "esconderEditar()" type="button" class="btn btn-warning btn-sm">
              				<span class = "glyphicon glyphicon-arrow-left"></span> Voltar
              				</button>
            		   	</div>       				
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-4">
              			<h4 class = "text-center text-uppercase">
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
				<div class="row ">		             	
	                    <div class="form-group ">
	                    
	                    	<s:textfield 
						               type="hidden" 
						               cssClass="form-control" 
						               name="ementa.idEmenta" 
						               id="Ementaid" />
	                    	
		                      <div class="col-sm-2 col-md-2">
		                        	<label for="InNome " class="control-label ">Nome:</label>
		                      </div>
		                      
		                      <div class="col-sm-10 col-md-10">
			                  		<s:textfield 
				                        type="text" 
				                        cssClass="form-control" 
				                        name="ementa.nome" 
				                        id="EmentaNome" 
				                        placeholder="Nome da Ementa"/>
		                      </div>
		                      
	                    </div>
	                    <div class="form-group ">
	                    	
	                    	<s:textfield 
						               type="hidden" 
						               cssClass="form-control" 
						               name="ementa.disciplina.idDisicplina" 
						               id="Disciplinaid" />
	                    	
		                      <div class="col-sm-2 col-md-2">
		                        	<label for="InNome " class="control-label ">Disciplina:</label>
		                      </div>
		                      
		                      <div class="col-sm-10 col-md-10">
			                  		<s:textfield 
				                        type="text" 
				                        cssClass="form-control" 
				                        name="ementa.disciplina.nome" 
				                        id="DisciplinaNome" 
				                        readonly = "true"/>
				                        
		                         <!-- <button id="pesqDis" class="btn btn-default" type="button" 
						         onclick ="tabDisc()" data-toggle="modal" data-target="#pesqDisciplina">
						        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button> -->
		                      </div>
		                      
	                    </div>
                    </div>
                 </div>	
			</div>
		</div> <!-- panel default -->
	</div> <!-- col -->
	
<div class = "row">
<div class = "col-md-12 col-sm-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row ">
				<div class="col-md-12">
					<div class = "text-center">
						<s:submit  name="button" value = "Salvar" cssClass= "btn btn-warning"/>
					    <s:submit  name="button" value = "Excluir" cssClass= "btn btn-danger"/>
					    <button type= "reset"  class ="btn btn-default">Limpar</button>
					</div>
				</div>
			</div>	
		</div>
	</div>
</div> <!-- col -->
</div> <!-- row -->
	
	
	</s:form>
</div><!-- row -->

<!-- modal Disciplina --> 
<div class="modal fade" id="pesqDisciplina" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Disciplina</h4>
      </div>
      <div class="modal-body">
		<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="TabelaDisciplina" class="display" cellspacing="0" width="100%">
		 			<thead>
		     			<tr>
					         <th>ID</th>
					         <th>NOME</th>
					         <th>CÓDIGO</th>
		     			</tr>
		 			</thead>
				</table>
		   	</div>
		</div>
      </div>
      <div class="modal-footer">
 			<button type="button" class="btn btn-default" data-dismiss="modal">
 			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button> 			
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal --> 

<div id = "pesquisar">
<div class = "row">
	<div class = "col-md-8 col-md-offset-2 col-sm-12 col-xs-12">
		<div class = "text-center">
		<form class = "form-horizontal">
			<div class="well">
				<div class="form-group">
					<div class = "col-md-12 col-sm-8 col-xs-12">
						<h5><strong>Para modificar ou inserir um tópico de conteúdo é necessário selecionar sua disciplina</strong></h5>
					</div>						
				</div>
				<div class="form-group">
					<div class = "col-md-12 col-sm-8 col-xs-12">
						<input  type="hidden"  class="form-control" id="discPesqId" />
						<input readonly class ="form-control" type="text" id="discPesq">
					</div>
				</div>
				
				<div class="form-group">
					<div class = "col-md-12 col-sm-8 col-xs-12">
						<button id="btnDiscPesq" class="btn btn-default" type="button" 
				        onclick ="tabDisc()" data-toggle="modal" data-target="#pesqDisciplina">
				        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
			        </div>
		        </div>
	        </div>
		</form>
		</div>	
	</div>
</div>
<div class = "row">
	<hr>
</div>
<div class="row ">
	<div  class="col-md-12 col-sm-12 col-lg-12">
     	<table id="tabelaEmenta" class="display" cellspacing="0" width="100%">
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



<!-- fechando o as divs do wraper -->
</div>
</div>
</div>
</div>