<%@ include file="/Gerente/headGerente.jsp" %> 

<div>
<script type="text/javascript">
window.onload = TestarEditar;
	
var data = [];
	
     $(document).ready(function() {
    	 
    	 $('#TabelaDisciplina').DataTable( {
    		 
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
    		                   document.getElementById("id").value = dt.rows('.selected').data()[0].idDisicplina;  
    		                   document.getElementById("codigo").value = dt.rows('.selected').data()[0].codigo;  
    		                   
    		                  	pesquisarEmentaDisciplina(dt.rows('.selected').data()[0].idDisicplina);
    		                   
    		               }
    		           }, 
    		           {	
    		               text: 'Novo',
    		               action: function ( e, dt, button, config ) {
    		            	   
    		            	   document.getElementById("nome").value = "";
    		                   document.getElementById("id").value = "";  
    		                   document.getElementById("codigo").value = "";
    		            	   
    		            	 document.getElementById('pesquisar').style.display = "none"; 
    		            	 document.getElementById('editar').style.display = ""; 
    		            	 
    		            	 sessionStorage.setItem("editar", "true");	
    		            	 
    		            	 tabEmenta(null);
    		                   
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
             
     });
     
     function tabEmenta(ementa) {
    	 if(!$.fn.dataTable.isDataTable('#tabelaEmenta')) {
    			
    			//caso seja uma nova disciplina
    			if(ementa == null){
    				
    				$('#tabelaEmenta').DataTable( {	
    					 
    					language: {
    	    		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
    	    		    },
    	    		 
    	   		    columns: [
    	   		              { data: 'idEmenta' },
    	   		              { data: 'nome' },
    	   		          ],
    	   		       "columnDefs": [
    	   		                   	{ "width": "15%", "targets": 0 },
    	   		                	{ "width": "85%", "targets": 1 },
    	   		                   
    	   		                 ]
    				});
    				
    				return
    				
    			}else{
    				
    				var table = $('#tabelaEmenta').DataTable( {	
    					
    	    			 language: {
    	    			        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
    	    			    },
    	    			    
    	    		    "aaData": ementa,
    	    		    "aoColumns": [
    	    		        { "mDataProp": "idEmenta" },
    	    		        { "mDataProp": "nome" },
    	    		    ]
    				});	
    			}
    			
    		$('#tabelaEmenta tbody').on( 'click', 'tr', function () {
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	        }
    	        
    	        var row = table.row($(this));
    	    	 
    	        if ( row.child.isShown() ) {
    	            // This row is already open - close it
    	            $('div.slider', row.child()).slideUp( function () {
    	                row.child.hide();
    	                $(this).removeClass('shown');
    	            } );
    	        }
    	        else {
    	            // Open this row
    	            row.child( format(row.data()), 'no-padding' ).show();
    	            $(this).addClass('shown');
    	 
    	            $('div.slider', row.child()).slideDown();
    	        }
    		});
    		 }
     }
 		 
 		function format ( d ) {
 		   	 
 			pesquisarConteudoEmenta(d.idEmenta);
 		
 			var conteudo = '<div class="slider">'+
			'<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
			var linha;
			var fechar = 	'</table>'+
					'</div>';
					
			console.log(data);
					
			var contador = 0
			
			for(; contador < data.length; contador++){
				
				linha = '<tr>'+
							'<td><Strong>Conteúdo:</Strong></td>'+
				        	'<td>Nome:</td>'+
				        	'<td>'+data[contador].nome+'</td>'+
				            '<td>Descrição:</td>'+
				            '<td>'+data[contador].descricao+'</td>'+
				        '</tr>'
			        
			        conteudo = conteudo.concat(linha);
				
			}
			
			 conteudo = conteudo.concat(fechar);
			
			return conteudo;
 		}
		 		
 		
 		function pesquisarEmentaDisciplina(id) {
			
			$.ajax({
				type : "POST", 
				url : '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarEmentaDisciplina"/>',
				/* contentType: "application/json; charset=utf-8", */
				data : "idDisciplina="+id,
				dataType : "json",
				async: true,
				success : function(result) {
					tabEmenta(result.records);	
				}
			});
			
			
		}
 		
		function pesquisarConteudoEmenta(id) {
			
			$.ajax({
				type : "POST", 
				url : '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarConteudoEmenta"/>',
				data : "idEmenta="+id,
				dataType : "json",
				async: false,
				success : function(result) {
					console.log(result)
					data = result.records;
				}
			});
			
			
		}
     
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
    		
    		 var table = $('#tabelaEmenta').DataTable()
    		 table.destroy();
    	}
</script>
</div>

<div class="row">
       <div class="col-md-12">
       	<div class="page-header">
         	<h4>Gerenciamento de Disciplina</h4>
         </div>
       </div>
     </div>
     
<!--    enviar disciplina
<div class="col-md-6 col-sm-6">	
		<div class="panel panel-default">
			<div class="panel-heading">
	            <div class="row">
	              <div class="col-md-12">
	                <p class = "text-center">Adicionar por arquivo</p>
	              </div>
	            </div>
			</div>
			<div class="panel-body">	
				 <div class="row">           
	            	<div class="col-md-12">
	               <p>Adicionar por arquivo é um meio rápido e fácil de incluir uma grande quantidade
                  de disciplinas.
                  <br>As informações necessárias para o cadastro de uma disciplina são: nome,
                  ementa e conteúdo.
                  <br>
                  <br>Estas informações devem estar dispostas da seguinte maneira:
                  <br>&nbsp;- Primeiramente o nome da disciplina e então a emeneta, sepados
                  por traços
                  <br>&nbsp;- O arquivo deve ser um .txt
                  <br>
                  <br>Exemplo:
                  <br>Disciplina - Ementa1 - Ementa2 - ... - &nbsp; &nbsp;Ementa n;&nbsp;</p>
	              </div>
	            </div>
           </div>
           <div class="panel-footer">
           		<div class="row">
	            	<div class="col-md-12">
	             		<s:form action="UploadDisciplina" 
             			namespace = "/Gerente"
		             	method="post" 
		             	enctype="multipart/form-data"
		             	theme="bootstrap" 
		             	cssClass="form-horizontal">
             				<div class="form-group">
             		
		             			<label for="fileAluno">Escolha o arquivo</label>
		             			
				    			<s:file name="fileUpload" id="fileDisciplina"/>
				    			
				    			<p class="help-block">Escolha um arquivo no formato .txt que atenda as especificações descritas
		                    		ao lado</p>
		                    		
				    			<s:submit value ="Enviar" cssClass= "btn btn-primary"/>
		    				</div>
						</s:form>
	              	</div>
	       		</div>
           </div>
		</div>
	</div>         -->

<div id = "editar">
<s:form action="CRUDDisciplinaInsert" 
	namespace = "/Gerente"
 	method="post" 
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
				<div class = "row">
					<h5><strong>Sobre a disciplina:</strong></h5>
				</div>
				<div class="row ">		             	
	                    <div class="form-group ">
	                    
	                    	<s:textfield 
						               type="hidden" 
						               cssClass="form-control" 
						               name="idDisc" 
						               id="id" />
	                    
		                      <div class="col-sm-2 col-md-2">
		                        	<label for="InNome " class="control-label ">Nome:</label>
		                      </div>
		                      
		                      <div class="col-sm-10 col-md-10">
			                  		<s:textfield 
				                        type="text" 
				                        cssClass="form-control" 
				                        name="nome" 
				                        id="nome" 
				                        placeholder="Nome da disciplina"/>
		                      </div>
		                      
	                    </div>
	                     <div class="form-group ">
	                     	                    
		                      <div class="col-sm-2 col-md-2">
		                        	<label for="InNome " class="control-label ">Código:</label>
		                      </div>
		                      
		                      <div class="col-sm-10 col-md-10">
			                  		<s:textfield 
				                        type="text" 
				                        cssClass="form-control" 
				                        name="codigo" 
				                        id="codigo" 
				                        placeholder="código  da disciplina"/>
		                      </div>
		                      
	                    </div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
	            <div class="row">
            		<div class="col-md-12 col-sm-12">
              			<h4 class = "text-center text-uppercase">
              			<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span> Ementa/Conteúdo</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
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
		</div>
	</div>
</div>
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
  

<!-- Tabela -->

<div id = "pesquisar">
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


<!-- Fechamento do head -->
<br><br><br>

<!-- fechamento das divs do head -->
</div>
</div>
</div>
</div>
              
           
