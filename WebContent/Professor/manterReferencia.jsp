<%@ include file="/Professor/headProfessor.jsp" %> 
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
	
	tabRef();
}

function esconderEditar(){
	
	sessionStorage.setItem("editar", "false");
	document.getElementById('editar').style.display = 'none';
	document.getElementById('pesquisar').style.display = ""; 
	
}

window.onload = TestarEditar;

function tabRef(){
	
	if(!$.fn.dataTable.isDataTable('#tabelaReferencia')) {
		
		 var table = $('#tabelaReferencia').DataTable( {
			 
			 dom: 'Bfrtip',
			 select: true,
			 
			 buttons: [
						
			           {
			               extend: 'selected',
			               text: 'Selecionar',
			               action: function ( e, dt, button, config ) {
			            			
			            	   console.log(dt.rows('.selected').data()[0]);
			            	   
		            			document.getElementById("autor").value = dt.rows('.selected').data()[0].autor;
		            			document.getElementById("refId").value = dt.rows('.selected').data()[0].idReferencia;
		            			document.getElementById("editora").value = dt.rows('.selected').data()[0].editora;
		            			document.getElementById("titulo").value = dt.rows('.selected').data()[0].titulo;
		            			
		            			sessionStorage.setItem("editar", "true");
		            			document.getElementById('pesquisar').style.display = "none"; 
		            			document.getElementById('editar').style.display = "";
		            			
		            		}
			                   
			               
			           },{
			               text: 'Nova',
			               action: function ( e, dt, button, config ) {
			            			
		            			document.getElementById("autor").value = "";
		            			document.getElementById("refId").value ="";;
		            			document.getElementById("editora").value = "";
		            			document.getElementById("titulo").value = "";
		            			
		            			sessionStorage.setItem("editar", "true");
		            			document.getElementById('pesquisar').style.display = "none"; 
		            			document.getElementById('editar').style.display = "";
		            			
		            		}
			                   
			               
			           }
			   ],
			 
			 language: {
			        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
			    },
			 
			 ajax: {
			        url: '<s:url namespace = "/Professor/Pesquisar" action = "referenciaTodas"/>',
			        dataSrc: 'records'
			    },
			    columns: [
			              { data: 'autor' },
			              { data: 'titulo' },
			           	  { data: 'editora' },
			          ],
			       "columnDefs": [
			                   	{ "width": "15%", "targets": 0 },
			                	{ "width": "60%", "targets": 1 },
			                	{ "width": "15%", "targets": 2 },
			                   
			                 ]
			    
			},
			$('#tabelaReferencia tbody').on( 'click', 'tr', function () {
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
    	<h4><strong>Referências</strong></h4>
    </div>
  </div>
</div>

<div id="editar">
<s:form 
	   action="CRUDReferencia"
	   namespace="/Professor" 
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
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados da Referência</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
				<div class="row ">
						<div class="form-group ">
	                    	<div class="col-sm-6 col-md-6">
		                    	<s:textfield 
							               type="hidden" 
							               cssClass="form-control" 
							               name="referencia.idReferencia" 
							               id="refId" />
		                    	
			                      <div class="col-sm-2 col-md-2">
			                        	<label  class="control-label ">Autor:</label>
			                      </div>
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
				             	           name="referencia.autor" 
					                        id="autor" 
					                        elementCssClass="col-sm-12 col-md-12"/>
			                      </div>
		                      </div>
		                      <div class="col-sm-6 col-md-6">
		                    	
			                      <div class="col-sm-2 col-md-2">
			                        	<label class="control-label ">Editora:</label>
			                      </div>
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
					                        name="referencia.editora" 
					                        id="editora" 
					                        elementCssClass="col-sm-12 col-md-12"/>
			                      </div>
		                      </div>
	                    	
	                    	<div class="col-sm-6 col-md-6">		                    	
			                      <div class="col-sm-2 col-md-2">
			                        	<label class="control-label ">Título:</label>
			                      </div>
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
					                        name="referencia.titulo" 
					                        id="titulo" 
					                        elementCssClass="col-sm-12 col-md-12"/>
			                      </div>
		                      </div>
		                      <div class="col-sm-6 col-md-6">		                    	
			                      <div class="col-sm-2 col-md-2">
			                        	<label class="control-label ">Ano da publicão:</label>
			                      </div>
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
					                        name="ano" 
					                        id="ano" 
					                        elementCssClass="col-sm-12 col-md-12"/>
			                      </div>
		                      </div>
	                    </div>
                    </div>
                 </div>	
			</div>
		</div> <!-- panel default -->
	</div> <!-- row -->
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
</div>

<div id="pesquisar">
	<div class="row ">
		<div  class="col-md-12 col-sm-12 col-lg-12">
	     	<table id="tabelaReferencia" class="display" cellspacing="0" width="100%">
	 			<thead>
	     			<tr>
				         <th>AUTOR</th>
				         <th>TITULO</th>
				         <th>EDITORA</th>
	     			</tr>
	 			</thead>
			</table>
	   	</div>
	</div>
</div>


</div>
</div>
</div>
</div>

</body>
</html>