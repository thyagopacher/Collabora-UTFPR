<%@ include file="/Professor/headProfessor.jsp" %> 
<script type="text/javascript">

function tabAluno(alunos, tabela){
	
	if($.fn.dataTable.isDataTable('#'+tabela)) {
		var t = $('#'+tabela).DataTable();
		t.destroy();
	}
		
		//caso seja uma nova turma
		if(alunos== null){
			
			console.log("if do null");
			
			var table1 = $('#tableAlunoGrupo').DataTable( {	
				 
				 language: {
				        url:'//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json',
				    },
			    columns: [
			              { data: 'id' },
			              { data: 'nome' },
			              { data: 'registro' },
			          ]
			});
			
			console.log("depois if do null");
			
			$('tableAlunoGrupo tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            console.log("aquiiiiiiiii");
		        }
		        else {
		            table1.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            console.log("aquiiiiiiii22222i");
		        }
		    } );
			
			
		}else{
			
			var table = $('#'+tabela).DataTable( {	
				 
				 language: {
				        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
				    },
				    
			    "aaData": alunos,
			    "aoColumns": [
			        { "mDataProp": "id" },
			        { "mDataProp": "nome" },
			        { "mDataProp": "registro" }
			    ]
				    
				});
				
				 $('#'+tabela+' tbody').on( 'click', 'tr', function () {
				        if ( $(this).hasClass('selected') ) {
				            $(this).removeClass('selected');
				        }
				        else {
				            table.$('tr.selected').removeClass('selected');
				            $(this).addClass('selected');
				        }
				    } );
		}

}


function criarBoxGruposExistentes(grupos){
	
	if(grupos == null){
		
		document.getElementById("grupoExistente").innerHTML="<h4> N„o h· grupos existentes</h4>"
		
	}	
	
}

function tabTurma(conteudo){
	
	if($.fn.dataTable.isDataTable('#TabelaTurma')) {
		var t = $('#TabelaTurma').DataTable();
		t.destroy();
	}
	
	var table =  $('#TabelaTurma').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		                   
// 		            	   tabAluno(dt.rows('.selected').data()[0].aluno, '#tableAlunoTurma');
// 		            	   var alunosForaDaTurma = filtarAlunos(dt.rows('.selected').data()[0].aluno);
// 		            	   tabAluno(alunosForaDaTurma, '#tableAlunoAll');
							
							document.getElementById("turmaPesq").value = dt.rows('.selected').data()[0].codigo;
							document.getElementById("turmaPesqId").value = dt.rows('.selected').data()[0].id;
							
							$.ajax({
	            				type : "POST", 
	            				url : '<s:url namespace = "/Professor/Pesquisar" action = "grupoTurma"/>',
	            				data : "idTurma="+document.getElementById("turmaPesqId").value,
	            				dataType : "json",
	            				async: false,
	            				success : function(result) {
	            					tabGrupo(result.records);	
	            				}
	            			});
		            			
	            			 $('#pesqTurma').modal('hide');	   
		            			 
		            			 
		                   
		               }
		           }, 
		   		],		 
		    language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		 
		    "aaData": conteudo,
		    "aoColumns": [
		        { "mDataProp": "id" },
		        { "mDataProp": "codigo" },
		        { "mDataProp": "disciplina.nome" },
		    ]
		},
	
		$('#TabelaTurma tbody').on( 'click', 'tr', function () {
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

function tabAtividade(conteudo){
	
	if($.fn.dataTable.isDataTable('#TabelaAtividade')) {
		var t = $('#TabelaAtividade').DataTable();
		t.destroy();
		}
	
	var table =  $('#TabelaAtividade').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
							
							document.getElementById("atividadePesq").value = dt.rows('.selected').data()[0].nome;
							document.getElementById("AtividadePesqId").value = dt.rows('.selected').data()[0].idAtividade;
							
	            			 $('#pesqAtividade').modal('hide');	   
		               }
		           }, 
		   		],		 
		    language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		    ajax: {
		        url: '<s:url namespace = "/Professor/Pesquisar" action = "atividadeTodas"/>',
		        dataSrc: 'records'
		    },
		 
		    columns: [
		              { data: 'nome' },
		              { data: 'inicio' },
		           	  { data: 'fim' },
		          ],
		       "columnDefs": [
		                   	{ "width": "15%", "targets": 0 },
		                	{ "width": "60%", "targets": 1 },
		                	{ "width": "15%", "targets": 2 },
		                   
		                 ]
		},
	
		$('#TabelaAtividade tbody').on( 'click', 'tr', function () {
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


function tabGrupo(conteudo){
	
	console.log(conteudo);
	
	if($.fn.dataTable.isDataTable('#TabelaGrupo')) {
		var t = $('#TabelaGrupo').DataTable();
		t.destroy();
	}
	
	var table = $('#TabelaGrupo').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		                   
		            	   document.getElementById('disciplinaId').value = document.getElementById("discPesqId").value;
	            		   document.getElementById('disciplinaNome').value= document.getElementById("discPesq").value;
		            	   
	            		   document.getElementById('turmaId').value = document.getElementById("turmaPesqId").value;
	            		   document.getElementById('turmaNome').value= document.getElementById("turmaPesq").value;
	            		   
	            		   	document.getElementById('pesquisar').style.display = "none"; 
   		            	 	document.getElementById('editar').style.display = ""; 
	   		            	 
	   		            	 sessionStorage.setItem("editar", "true");	
		                   
		                   
		               } 
		           }, 
		               {	text: 'Gerenciar',
    		               action: function ( e, dt, button, config ) {
	    		            	   
    		            	   document.getElementById('disciplinaId').value = document.getElementById("discPesqId").value;
    	            		   document.getElementById('disciplinaNome').value= document.getElementById("discPesq").value;
    		            	   
    	            		   document.getElementById('turmaId').value = document.getElementById("turmaPesqId").value;
    	            		   document.getElementById('turmaNome').value= document.getElementById("turmaPesq").value;
	    		            	   
    	            		   $.ajax({
	   	            				type : "POST", 
	   	            				url : '<s:url namespace = "/Professor/Pesquisar" action = "alunoPorTurma"/>',
	   	            				data : "idTurma="+document.getElementById("turmaPesqId").value,
	   	            				dataType : "json",
	   	            				async: false,
	   	            				success : function(result) {
	   	            					tabAluno(result.records, "tableAlunoAll");	
	   	            				}
   	            				});
    	            		   
    	            		   tabAluno(null, "tableAlunoGrupo");	
    	            		   criarBoxGruposExistentes(null);
    	            		   
	   		            	 document.getElementById('pesquisar').style.display = "none"; 
	   		            	 document.getElementById('editar').style.display = ""; 
	   		            	 
	   		            	 sessionStorage.setItem("editar", "true");	
	   		                   
    		               }
		           }
		   		],		 
		    language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		 
		    "aaData": conteudo,
		    "aoColumns": [
		        { "mDataProp": "idGrupo" },
		        { "mDataProp": "turma.codigo" },
		    ]
		} );
	
		$('#TabelaGrupo tbody').on( 'click', 'tr', function () {
			
			console.log("aqui");
			
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	        
	     	row = table.row($(this));
	    	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            $('div.slider', row.child()).slideUp( function () {
	                row.child.hide();
	                $(this).removeClass('shown');
	            } );
	        }
	        else {
	            // Open this row
	            row.child( formatTurma(row.data()), 'no-padding' ).show();
	            $(this).addClass('shown');
	 
	            $('div.slider', row.child()).slideDown();
	        }
		})
	
}

function btnAdd(){
 	
	 var tabTodos = $('#tableAlunoAll').DataTable();
	 var tabGrupo = $('#tableAlunoGrupo').DataTable();
	 

	 var data = tabTodos.row('.selected').data();
	 
	  
	 tabGrupo.row.add(data).draw();
	 
	 /*
	 if(verifExis(data.id)){
		 alert("Este aluno jÔøΩ estÔøΩ inserido nesta turma!");
	 }else{*/
		 
		 tabTodos.rows('.selected').remove().draw( false ); 
	 //}
	
}

function excluir(){
	
	 var tabTodos = $('#tableAlunoAll').DataTable();
	 var tabGrupo = $('#tableAlunoGrupo').DataTable();
	 

	 var data = tabGrupo.row('.selected').data();
	  
	 tabTodos.row.add(data).draw();

	 tabGrupo.rows('.selected').remove().draw( false ); 
	
}


function btnAddAll(){
	
		var tabTodos = $('#tableAlunoAll').DataTable();
		var tabGrupo = $('#tableAlunoGrupo').DataTable();
	
	 	var data = tabTodos.rows().data();
	 	
	 	for (var i = 0; i < data.length; i++) { 
	 		tabGrupo.row.add(data[i]).draw();
	    }
	 	
		tabTodos.clear();
		tabTodos.draw();
	
}

function btnExcluirAll(){
	
	var tabTodos = $('#tableAlunoAll').DataTable();
	var tabGrupo = $('#tableAlunoGrupo').DataTable();

 	var data = tabGrupo.rows().data();
 	
 	for (var i = 0; i < data.length; i++) { 
 		tabTodos.row.add(data[i]).draw();
    }
 	
 	tabGrupo.clear();
 	tabGrupo.draw();
	
}

function formatTurma ( d ) {
	
	console.log(d);
 	console.log("formate");
	
	var conteudo = '<div class="slider">'+
	'<ul class="list-group">'
	var linha;
	var fechar = '</ul>'+
			'</div>';
			
	var contador = 0
	var data = d.alunos;
	
	for(; contador < data.length; contador++){
		
		linha = '<li class="list-group-item">'
		        	+data[contador].nome+' - '+
		            data[contador].registro+'</li>'
		            
        conteudo = conteudo.concat(linha);
		
	}
	
	 conteudo = conteudo.concat(fechar);
	
	return conteudo;
	}

function tabDisc(){
	
	if($.fn.dataTable.isDataTable('#TabelaDisciplina')) {
		var t = $('#TabelaDisciplina').DataTable();
		t.destroy();
	}
		
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
		            				url : '<s:url namespace = "/Professor/Pesquisar" action = "turmaDisciplina"/>',
		            				data : "idDisciplina="+document.getElementById('turmaId').value,
		            				dataType : "json",
		            				async: false,
		            				success : function(result) {
		            					console.log(result);
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



function getAlunoIds(){
	var tabgrupo = $('#tableAlunoGrupo').DataTable();
	var ids = "ids:";
	var virgula = ","
	var id;
	
	var data = tabgrupo.rows().data();
	
	for (var i = 0; i < data.length; i++) { 
		id = data[i].id;
		
		ids = ids.concat(id,virgula);
    }
	
	document.getElementById("idAlunos").value = ids;
	
	
	$.ajax({
		type : "POST", 
		url : '<s:url namespace = "/Professor" action = "manterGrupo"/>',
		data : "idTurma="+dt.rows('.selected').data()[0].idDisicplina,
		dataType : "json",
		async: false,
		success : function(result) {
			tabTurma(result.records);	
		}
	});
	
	localStorage.setItem("alunos", ids);
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
	var tabTodos = $('#tableAlunoAll').DataTable();
	 var tabTurma = $('#tableAlunoTurma').DataTable();
	 
	 tabTodos.destroy();
	 tabTurma.destroy();
	
}

window.onload = TestarEditar;
</script>

<div class="row">
  <div class="col-md-12">
  	<div class="page-header">
    	<h4><strong>Grupos</strong></h4>
    </div>
  </div>
</div>

<div id="editar">
<s:form 
	   action="CRUDGrupos"
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
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados dos grupos</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
			
			<s:textfield
               type="hidden" 
               cssClass="form-control" 
               name="idAlunos" 
               id="idAlunos" />  
			
				<div class="form-group">
					<s:textfield type="hidden" cssClass="form-control" name="" id="disciplinaId" />	
                      <div class="col-sm-11 col-md-11">
	                  		<s:textfield type="text" cssClass="form-control" name="" id="disciplinaNome" 
		                        label="Disciplina" disabled="true"/>
                      </div>
				</div>
		        <div class="form-group">
					<s:textfield type="hidden" cssClass="form-control" name="" id="turmaId" />	
                      <div class="col-sm-11 col-md-11">
	                  		<s:textfield type="text" cssClass="form-control" name="" id="turmaNome" 
		                        label="Turma" disabled="true"/>
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
			<div class = "row">
				<div class = "col-md-12 col-sm-12 col-lg-12">
					<h4 class = "text-center text-uppercase">
					<span class="glyphicon glyphicon-star" aria-hidden="true"></span> Grupos existentes
					</h4>
				</div>
			</div>
		</div>
		<div class = "panel-body">
			<div class="panel-group" id= "grupoExistente">
		       
   			</div>			
		</div><!-- panel body -->
	</div><!-- panel default -->
</div> <!-- col -->
</div> <!-- row -->

<div class = "row">
<div class = "col-md-12 col-sm-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class = "row">
				<div class = "col-md-12 col-sm-12 col-lg-12">
					<h4 class = "text-center text-uppercase">
					<span class="glyphicon glyphicon-education" aria-hidden="true"></span> Montar Grupos
					</h4>
				</div>
			</div>
		</div>
		<div class = "panel-body">
			<div class="row">
				<div  class="col-md-5 col-sm-5 col-lg-5">
					<div class = "text-center text-uppercase">
						<h5><div class="well well-sm"><Strong>Alunos sem grupo</Strong></div></h5>
					</div>
			     	<table id="tableAlunoAll" class="display" cellspacing="0" width="100%">
			 			<thead>
			     			<tr>
						         <th>ID</th>
						         <th>NOME</th>
						         <th>REGISTRO</th>
			     			</tr>
			 			</thead>
					</table>
			   	</div><!-- col -->
			   	<div class ="col-md-2 col-sm-2">
			   		<div class = "text-center text-uppercase">
						<h5><div class="well well-sm"><Strong>AÁıes</Strong></div></h5>
					</div>
			   		<div class = "row">
			   			<div class = "col-md-12 col-sm-12">
			   				<div class = "form-group">
			   					<button onclick="btnAdd()" type ="button" id="btn-add" class = "btn btn-default btn-block">Incluir</button>
			   				</div>
			   			</div>
			   		</div>
			   		<div class = "row">
			   			<div class = "col-md-12 col-sm-12">
			   				<div class = "form-group">
			   					<button onclick="btnAddAll()" type ="button" id="btn-addAll" class = "btn btn-default btn-block">Incluir Todos</button>
			   				</div>
			   			</div>
			   		</div>
			   		<div class = "row">
			   			<div class = "col-md-12 col-sm-12">
			   				<div class = "form-group">
			   					<button onclick= "excluir()" type ="button" id="btn-remove" class = "btn btn-default btn-block">Exluir</button>
			   				</div>
			   			</div>
			   		</div>
			   		<div class = "row">
			   			<div class = "col-md-12 col-sm-12">
			   				<div class = "form-group">
			   					<button onclick="btnExcluirAll()" type ="button" id="btn-removeAll" class = "btn btn-default btn-block">Excluir Todos</button>
			   				</div>
			   			</div>
			   		</div>
			   	</div>
			   	<div  class="col-md-5 col-sm-5 col-lg-5">
			   		<div class = "text-center text-uppercase">
						<h5><div class="well well-sm"><Strong>Grupo atual</Strong></div></h5>
					</div>
			     	<table id="tableAlunoGrupo" class="display" cellspacing="0" width="100%">
			 			<thead>
			     			<tr>
						         <th>ID</th>
						         <th>NOME</th>
						         <th>REGISTRO</th>
			     			</tr>
			 			</thead>
					</table>
			   	</div><!-- col -->
			</div><!-- row -->
			<div class = "row">
				<div class = "form-group">
					<div class = "text-right">
						<div class = "col-md-9 col-md-offset-3">
							<button class = "btn btn-primary" onclick="getAlunoIds()">Adicionar Grupo</button>
						</div>
					</div>
				</div>
			</div>
		</div><!-- panel body -->
	</div><!-- panel default -->
</div> <!-- col -->
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

<div class = "row">
	<div class = "col-md-8 col-md-offset-2 col-sm-12 col-xs-12">
		<div class = "text-center">
		<form class = "form-horizontal">
			<div class="well">
				<div class="form-group">
					<div class = "col-md-12 col-sm-8 col-xs-12">
						<h5><strong>Para modificar ou inserir um grupo È necess·rio uma Disciplina, Turma e atividade</strong></h5>
					</div>						
				</div>
				<div class="form-group">
					<div class = "col-md-2 col-sm-3 col-xs-4">
						<label for="discPesq" class="control-label">Disciplina:</label>
					</div>
					<div class = "col-md-7 col-sm-6 col-xs-5">
						<input  type="hidden"  class="form-control" id="discPesqId" />
						<input readonly class ="form-control" type="text" id="discPesq">
					</div>
					<div class = "col-md-3 col-sm-3 col-xs-3">
						<div class = "text-center">
							<button id="btnDiscPesq" class="btn btn-default" type="button" 
					        onclick ="tabDisc()" data-toggle="modal" data-target="#pesqDisciplina">
					        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
						</div>
			        </div>
				</div>
		        <div class="form-group">
					<div class = "col-md-2 col-sm-3 col-xs-4">
						<label for="turmaPesq" class="control-label">Turma:</label>
					</div>
					<div class = "col-md-7 col-sm-6 col-xs-5">
						<input  type="hidden"  class="form-control" id="turmaPesqId" />
						<input readonly class ="form-control" type="text" id="turmaPesq">
					</div>
					<div class = "col-md-3 col-sm-3 col-xs-3">
						<div class = "text-center">
							<button id="btnTurmaPesq" class="btn btn-default" type="button" 
					         data-toggle="modal" data-target="#pesqTurma" >
					        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
						</div>
					</div>
				</div>
	        </div>
		</form>
		</div>	
	</div>
</div>



	<div class="row ">
		<div  class="col-md-12 col-sm-12 col-lg-12">
	     	<table id="TabelaGrupo" class="display" cellspacing="0" width="100%">
	 			<thead>
	     			<tr>
	     				<th>ID</th>	
				         <th>TURMA</th>
	     			</tr>
	 			</thead>
			</table>
	   	</div>
	</div>
</div>


<!-- modal disciplina --> 
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
					         <th>C”DIGO</th>
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

<!-- modal Atividade --> 
<div class="modal fade" id="pesqAtividade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Atividades</h4>
      </div>
      <div class="modal-body">
		<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="TabelaAtividade" class="display" cellspacing="0" width="100%">
		 			<thead>
		     			<tr>
					         <th>NOME</th>
					         <th>INÕçCIO</th>
					         <th>FIM</th>
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

<!-- modal turma --> 
<div class="modal fade" id="pesqTurma" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Turma</h4>
      </div>
      <div class="modal-body">
		<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="TabelaTurma" class="display" cellspacing="0" width="100%">
		 			<thead>
		     			<tr>
					         <th>ID</th>
					         <th>C”DIGO</th>
					         <th>DISCIPLINA</th>
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

</div>
</div>
</div>
</div>

</body>
</html>