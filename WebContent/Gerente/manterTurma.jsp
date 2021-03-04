<%@ include file="/Gerente/headGerente.jsp" %> 

<div>
<script type="text/javascript">

$(document).ready(function() {
	
	tabturma();
});

function tabturma(){
	
 $('#TabelaTurma').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		                   
		            	   tabAluno(dt.rows('.selected').data()[0].aluno, '#tableAlunoTurma');
		            	   var alunosForaDaTurma = filtarAlunos(dt.rows('.selected').data()[0].aluno);
		            	   tabAluno(alunosForaDaTurma, '#tableAlunoAll');
		            	   //tabTodosAluno();
		            	   
		            	   alunosForaDaTurma = [];
		            	   
		            	   document.getElementById('pesquisar').style.display = "none"; 
   		            	   document.getElementById('editar').style.display = "";
    		            	   
   		            		sessionStorage.setItem("editar", "true");
		                   
		                   document.getElementById("codigo").value = dt.rows('.selected').data()[0].codigo;
		                   document.getElementById("id").value = dt.rows('.selected').data()[0].id;
		                   
		                   document.getElementById("idProfessor").value = dt.rows('.selected').data()[0].professor.id;
		                   document.getElementById("professor").value = dt.rows('.selected').data()[0].professor.nome;
		                   
		                   console.log(dt.rows('.selected').data()[0].id);
		                   
		                   document.getElementById("idDisciplina").value = dt.rows('.selected').data()[0].disciplina.idDisicplina;
		                   document.getElementById("disciplina").value = dt.rows('.selected').data()[0].disciplina.nome;
		                   
		               }
		           }, 
		           {
		               text: 'Novo',
		               action: function ( e, dt, button, config ) {
		            	   
		            	   tabAluno(null, '#tableAlunoTurma');
		            	   var alunosForaDaTurma = filtarAlunos(null);
		            	   tabAluno(alunosForaDaTurma, '#tableAlunoAll');
		            	   alunosForaDaTurma = [];
		            	   /* tabTodosAluno();
		            	   tabAluno(null); */
		            	   
		            	   document.getElementById("codigo").value = "";
		                   document.getElementById("id").value = "";
		                   
		                   document.getElementById("idProfessor").value = "";
		                   document.getElementById("professor").value = "";
		                   
		                   document.getElementById("idDisciplina").value = "";
		                   document.getElementById("disciplina").value = "";
		            	   
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
		        url: '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosTurma"/>',
		        dataSrc: 'records'
		    },
		    columns: [
		              { data: 'id' },
		              { data: 'codigo' },
		              { data: 'disciplina.nome' },
		              { data: 'professor.nome' },
		          ],
		       "columnDefs": [
		                   	{ "width": "15%", "targets": 0 },
		                	{ "width": "30%", "targets": 1 },
		                	{ "width": "25%", "targets": 2 },
		                	{ "width": "30%", "targets": 3 }
		                   
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

function filtarAlunos(alunosDaTurma){
	var data = [];
	
	$.ajax({
		type : "POST", 
		url : '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosAluno"/>',
		dataType : "json",
		async: false,
		success : function(result) {
			//console.log(result)
			data = result.records;
		}
	});
	
	//se nï¿½o existem alunos, nï¿½o precisa ser filtrado
	if(alunosDaTurma == null)
		return data;
	
	var contData = 0;
	var contAlunos = 0;
	
	for(; contAlunos < alunosDaTurma.length; contAlunos++){
		
		for(contData = 0; contData<data.length; contData++){
			
			if(alunosDaTurma[contAlunos].id == data[contData].id){
				
				var index = data.indexOf(data[contData]);
				data.splice(index, 1);
			}
			
		}
	}
	
	//console.log(data);
	
	return data;
	
}

function tabAluno(alunos, tabela){
	
	if($.fn.dataTable.isDataTable(tabela)) {
		var t = $(tabela).DataTable();
		t.destroy();
	}
		
	//caso seja uma nova turma
	if(alunos== null){
		
		$('#tableAlunoTurma').DataTable( {	
			 
			 language: {
			        url: 			'//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json',
			    },
		    columns: [
		              { data: 'id' },
		              { data: 'nome' },
		              { data: 'registro' },
		          ]
		});
		
		 $('#tableAlunoTurma tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		    } );
		
		return
		
	}
		
	var table = $(tabela).DataTable( {	
		 
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
		
		 $(tabela+' tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		    } );
	 
}


function btnAdd(){
	 	
	var tabTodos = $('#tableAlunoAll').DataTable();
	var tabTurma = $('#tableAlunoTurma').DataTable();
	 
 	var data = tabTodos.row('.selected').data();
	 
 	if(data == null){
		$('#rowMessage').show();
		return;
	}else{
		$('#rowMessage').hide();
	}	 
  
	tabTurma.row.add(data).draw();
	 
 	tabTodos.rows('.selected').remove().draw( false ); 
	
}

function excluir(){
 	
	 	var tabTodos = $('#tableAlunoAll').DataTable();
		var tabTurma = $('#tableAlunoTurma').DataTable();
	 	
		var data = tabTurma.row('.selected').data();
		
	 	if(data == null){
			$('#rowMessage').show();
			return;
		}else{
			$('#rowMessage').hide();
		}
	  
	 	tabTodos.row.add(data).draw();
 
		tabTurma.rows('.selected').remove().draw( false ); 
	
}


function btnAddAll(){
	
		var tabTodos = $('#tableAlunoAll').DataTable();
		var tabTurma = $('#tableAlunoTurma').DataTable();
		var data = tabTodos.rows().data();
		
		if(data == null){
			$('#rowMessage').show();
			return;
			
		}else{
			$('#rowMessage').hide();
		}
	 	
	 	for (var i = 0; i < data.length; i++) { 
		 	tabTurma.row.add(data[i]).draw();
	    }
	 	
		tabTodos.clear();
		tabTodos.draw();
	
}

function getAlunoIds(){
	var tabTurma = $('#tableAlunoTurma').DataTable();
	var ids = "ids:";
	var virgula = ","
	var id;
	
	var data = tabTurma.rows().data();
	
	for (var i = 0; i < data.length; i++) { 
		id = data[i].id;
		
		ids = ids.concat(id,virgula);
    }
	
	document.getElementById("idAlunos").value = ids;
	
	localStorage.setItem("alunos", ids);
}

function btnExcluirAll(){
	
	var tabTodos = $('#tableAlunoAll').DataTable();
	var tabTurma = $('#tableAlunoTurma').DataTable();
	var data = tabTurma.rows().data();
	
	if(data == null){
		$('#rowMessage').show();
		return;
		
	}else{
		$('#rowMessage').hide();
	}
 	
 	for (var i = 0; i < data.length; i++) { 
 		tabTodos.row.add(data[i]).draw();
    }
 	
 	tabTurma.clear();
 	tabTurma.draw();
	
}

/* function tabTodosAluno(){
	
	if(!$.fn.dataTable.isDataTable('#tableAlunoAll')){
	var table = $('#tableAlunoAll').DataTable( {
		 
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
		    
		});
	
		
		$('#tableAlunoAll tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}
}
 */
function tabProfessor() {
	 
	 if($.fn.dataTable.isDataTable('#TabelaProfessor')) {
			var t = $('#TabelaProfessor').DataTable();
			t.destroy();
	}
	 
	
	 $('#TabelaProfessor').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		                   
		                   document.getElementById("professor").value = dt.rows('.selected').data()[0].nome;
		                   document.getElementById("idProfessor").value = dt.rows('.selected').data()[0].id;
		                   $('#pesqProfessor').modal('hide');
		                   
		               }
		           }
		   ],
		 
		 language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		 
		 ajax: {
		        url: '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarTodosProfessor"/>',
		        dataSrc: 'records'
		    },
		    columns: [
					{ data: 'id' },
				   { data: 'nome' },
				   { data: 'registro' }
		          ],
		       "columnDefs": [
		                   	{ "width": "15%", "targets": 0 },
		                	{ "width": "60%", "targets": 1 },
		                	{ "width": "25%", "targets": 2 }
		                   
		                 ]
		    
		},
	
		$('#TabelaProfessor tbody').on( 'click', 'tr', function () {
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

function tabDisciplina() {
	 
	 if($.fn.dataTable.isDataTable('#TabelaDisciplina')) {
			var t = $('#TabelaDisciplina').DataTable();
			t.destroy();
	}
	
	
	 $('#TabelaDisciplina').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		                   
		            	   console.log(dt.rows('.selected').data());
		            	   
		            	   document.getElementById("disciplina").value = dt.rows('.selected').data()[0].nome;
		                   document.getElementById("idDisciplina").value = dt.rows('.selected').data()[0].idDisicplina;
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


/*var test = [1, 2, 3, 4, 5];
$.ajax({
    method: "POST",
    url: '<s:url namespace = "/Gerente" action = "CRUDTurma"/>',
    data: { test : test },
    traditional: true,
    success:
        function()
        {
           	console.log("estoy aqui!");
        }
});*/

window.onload = TestarEditar;
		
	function camposComErro(){
	tabAluno(localStorage.getItem("alunos"), '#tableAlunoTurma');
	   var alunosForaDaTurma = filtarAlunos(localStorage.getItem("alunos"));
	   tabAluno(alunosForaDaTurma, '#tableAlunoAll');
	   alunosForaDaTurma = [];
}
	
	function camposSemErro(){
		console.log("aqui");
		localStorage.removeItem("alunos");
	}

</script>
</div>

<s:if test="hasFieldErrors()">
	<script type="text/javascript">
		camposComErro();
	</script>
</s:if>
<s:else>
	<script type="text/javascript">
		camposSemErro();
	</script>
</s:else>
    
<div class="row">
  <div class="col-md-12">
 	<div class="page-header">
    	<h4>Gerenciamento de Turmas</h4>
    </div>
  </div>
</div>
        
        
<!-- Painel do arquivo -->  
<div id = "editar">
<s:form 
	   action="CRUDTurma"
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
					               name="turma.id" 
					               id="id" />
						
					        <div class="col-sm-2 col-md-2">
					          	<label for="registro " class="control-label ">Código:</label>
					        </div>
					        <div class="col-sm-10 col-md-10">
					          	<s:textfield 
					               type="text" 
					               cssClass="form-control" 
					               name="turma.codigo" 
					               id="codigo" 
					               placeholder="Código da turma"/>
					        </div>
				     </div>
				     
					<div class="form-group ">	
							<s:textfield 
					               type="hidden" 
					               cssClass="form-control" 
					               name="idProf" 
					               id="idProfessor" />
											
					        <div class="col-sm-2 col-md-2">
					          	<label for="registro " class="control-label ">Professor:</label>
					        </div>
					        <div class="col-sm-10 col-md-10">
					          	<s:textfield 
					               type="text" 
					               cssClass="form-control" 
					               id="professor" 
					               name = "nomeProfessor"
					               readonly = "true"
					               placeholder="Atribua um professor a esta Turma"/>
						        <button id="pesqPro" class="btn btn-default" type="button" 
						         onclick ="tabProfessor()" data-toggle="modal" data-target="#pesqProfessor">
						        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
					        </div>
				     </div>
				     <div class="form-group ">	
				     		<s:textfield 
					               type="hidden" 
					               cssClass="form-control" 
					               name="idDisc" 
					               id="idDisciplina" />
					        <s:textfield
					               type="hidden" 
					               cssClass="form-control" 
					               name="idAlunos" 
					               id="idAlunos" />     						
					        <div class="col-sm-2 col-md-2">
					          	<label for="registro " class="control-label ">Disciplina:</label>
					        </div>
					        <div class="col-sm-10 col-md-10">
					          	<s:textfield 
					               type="text" 
					               cssClass="form-control" 
					               id="disciplina" 
					               name = "nomeDisciplina"
					               readonly = "true"
					               placeholder="Atribua uma disciplina a esta Turma"/>
						        <button class="btn btn-default" type="button" 
						        onclick ="tabDisciplina()" data-toggle="modal" data-target="#pesqDisciplina">
						        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
					        </div>
				     </div>	
				</div>
			</div>
			<div class="panel-footer">		
			</div>
		</div> <!-- panel default -->
	</div> <!-- col -->
</div><!-- row -->


<div class = "row">
<div class = "col-md-12 col-sm-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class = "row">
				<div class = "col-md-12 col-sm-12 col-lg-12">
					<h4 class = "text-center text-uppercase">
					<span class="glyphicon glyphicon-education" aria-hidden="true"></span> alunos
					</h4>
				</div>
			</div>
		</div>
		<div class = "panel-body">
			<div class="row" style="display: none;" id="rowMessage">
				<div class="alert alert-danger" id="erroMessage">
				<strong>Você deve selecionar um registro antes de executar a ação</strong>
				</div>
			</div>
			<div class="row">
				<div  class="col-md-5 col-sm-5 col-lg-5">
					<div class = "text-center text-uppercase">
						<h5><div class="well well-sm"><Strong>Todos os Alunos</Strong></div></h5>
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
						<h5><div class="well well-sm"><Strong>Ações</Strong></div></h5>
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
						<h5><div class="well well-sm"><Strong>Alunos desta turma</Strong></div></h5>
					</div>
			     	<table id="tableAlunoTurma" class="display" cellspacing="0" width="100%">
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
						<s:submit onclick= "getAlunoIds()" name="button" value = "Salvar" cssClass= "btn btn-warning"/>
					    <s:submit onclick= "getAlunoIds()" name="button" value = "Excluir" cssClass= "btn btn-danger"/>
					    <button type= "reset"  class ="btn btn-default">Limpar</button>
					</div>
				</div>
			</div>	
		</div>
	</div>
</div> <!-- col -->
</div> <!-- row -->


</s:form>
</div><!-- editar -->


<div id = "pesquisar">
	<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="TabelaTurma" class="display" cellspacing="0" width="100%">
		 			<thead>
		     			<tr>
					         <th>ID</th>
					         <th>CÓDIGO</th>
					         <th>DISCIPLINA</th>
					         <th>PROFESSOR</th>
		     			</tr>
		 			</thead>
				</table>
		   	</div>
		</div>
</div> <!-- pesquisar -->

<br>
<br>
<br>

<!-- modal arquivo --> 
<div class="modal fade" id="pesqProfessor" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Professor</h4>
      </div>
      <div class="modal-body">
		<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="TabelaProfessor" class="display" cellspacing="0" width="100%">
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
      <div class="modal-footer">
      		<button type="button" class="btn btn-default" data-dismiss="modal">
 			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button> 			
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->    

<!-- modal arquivo --> 
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
					         <th>Cï¿½DIGO</th>
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


    
<!-- modal arquivo 
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
	                <p>Adicionar por arquivo ï¿½ um meio rï¿½pido e fï¿½cil de incluir uma quantidade
                  grande de professores.
                  <br>As informaï¿½ï¿½es necessï¿½rias para o cadastro de um professor sï¿½o: nome e
                  senha.
                  <br>
                  <br>Estas informaï¿½ï¿½es devem estar dispostas da seguinte maneira:
                  <br>&nbsp;- Somente um professor por linha
                  <br>&nbsp;- As informaï¿½ï¿½es devem ser sepadas por um traï¿½o
                  <br>&nbsp;- Ao final da linha deve ter um ponto e virgula
                  <br>&nbsp;- O arquivo deve ser um .txt
                  <br>
                  <br>Exemplo:
                  <br>Joï¿½o da Silva - 12345; &nbsp;</p>
	              </div>
            </div>
      </div>
      <div class="modal-footer">
       <div class="row">
            	<div class="col-md-12">
             		<s:form action="UploadProfessor" 
            			namespace = "/Gerente"
	             	method="post" 
	             	enctype="multipart/form-data"
	             	theme="bootstrap" 
	             	cssClass="form-horizontal">
            				<div class="form-group">
             		
             				<s:file name="fileUpload" id="fileProfessor"/>
	    			
			    			<p class="help-block">Escolha um arquivo no formato .txt que atenda as especificaï¿½ï¿½es descritas
	                    		acima</p>
	                    		
			    			<s:submit value ="Enviar" cssClass= "btn btn-warning"/>
			    			<button class = "btn btn-default" data-dismiss = "modal">
			    			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button>
		    			</div>
					</s:form>
              	</div>
       		</div>
      </div>
    </div><!-- /.modal-content
  </div><!-- /.modal-dialog 
</div><!-- /.modal -->

</div>
</div>
</div>
</div>
    