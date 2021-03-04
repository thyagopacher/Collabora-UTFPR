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
}

function esconderEditar(){
	
	sessionStorage.setItem("editar", "false");
	document.getElementById('editar').style.display = 'none';
	document.getElementById('pesquisar').style.display = ""; 
	
}

window.onload = TestarEditar;

function tabProposicao(proposicao){
	
	if($.fn.dataTable.isDataTable('#tabelaProposicoes')) {
		var t = $('#tabelaProposicoes').DataTable();
		t.destroy();
	}
		
		//caso seja uma nova turma
		if(proposicao== null) {
			
			$('#tabelaProposicoes').DataTable( {	
				 dom: 'Bfrtip',
				 select: true,
				 
				 buttons: [
							
				           {
				               extend: 'selected',
				               text: 'Selecionar',
				               action: function ( e, dt, button, config ) {			       			
				                	
				            	   document.getElementById('proposicaoEnunciado').value = dt.rows('.selected').data()[0].enunciado;
				            	   document.getElementById('corretaPropo').value = dt.rows('.selected').data()[0].correta;
				            	   document.getElementById('imgProposicao').value = dt.rows('.selected').data()[0].imagem;
				            	   document.getElementById('propID').value = dt.rows('.selected').data()[0].idProposicao;
				            	   $('#detalheProp').modal('show');
				            	   
	    		                  
				               }
				           }, 
	    		           {	
	    		               text: 'Adicionar',
	    		               action: function ( e, dt, button, config ) {
	    		            	       		            	 
	    		            	   document.getElementById('proposicaoEnunciado').value = "";
				            	   document.getElementById('corretaPropo').value = "";
				            	   document.getElementById('imgProposicao').value ="";
				            	   document.getElementById('propID').value = "";
				            	   $('#detalheProp').modal('show');
	    		               }
	    		           }
				   ],
				 language: {
				        url:'//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json',
				    },
			    columns: [
			              { data: 'enunciado' },
			              { data: 'correta' },
			              { data: 'imagem' },
			          ]
			});			
		}else{
			
			var table = $('#tabelaProposicoes').DataTable( {	
				 
				 dom: 'Bfrtip',
				 select: true,
				 
				 buttons: [
							
				           {
				               extend: 'selected',
				               text: 'Selecionar',
				               action: function ( e, dt, button, config ) {			       			
				                	
				            	   document.getElementById('proposicaoEnunciado').value = dt.rows('.selected').data()[0].enunciado;
				            	   document.getElementById('corretaPropo').value = dt.rows('.selected').data()[0].correta;
				            	   document.getElementById('imgProposicao').value = dt.rows('.selected').data()[0].imagem;
				            	   document.getElementById('propID').value = dt.rows('.selected').data()[0].idProposicao;
				            	   $('#detalheProp').modal('show');
				            	   
	    		                  
				               }
				           }, 
	    		           {	
	    		               text: 'Adicionar',
	    		               action: function ( e, dt, button, config ) {
	    		            	       		            	 
	    		            	   document.getElementById('proposicaoEnunciado').value = "";
				            	   document.getElementById('corretaPropo').value = "";
				            	   document.getElementById('imgProposicao').value ="";
				            	   document.getElementById('propID').value = "";
				            	   $('#detalheProp').modal('show');
	    		               }
	    		           }
				   ],
				
				language: {
			        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
			    },
			 
			    "aaData": proposicao,
			    "aoColumns": [
			        { "mDataProp": "enunciado" },
			        { "mDataProp": "correta" },
			        { "mDataProp": "imagem" },
			    ]
			},
		
			$('#tabelaProposicoes tbody').on( 'click', 'tr', function () {
				
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		        
		        var row = table.row($(this));
		    	 
			})
			
			);
			
		}
		
}

function gerarProposicao(){
	
	var proposicao = [];
	var e = document.getElementById('corretaPropo');
	
	proposicao["correta"] = e.options[e.selectedIndex].text;
	proposicao["enunciado"] = document.getElementById('proposicaoEnunciado').value ;
	proposicao["imagem"] = document.getElementById('imgProposicao').value;
	proposicao["idProposicao"] = document.getElementById('propID').value;
	
	var table = $('#tabelaProposicoes').DataTable();
	
	table.row.add(proposicao).draw();
}

function armazenarProp(){
	
	var table = $('#tabelaProposicoes').DataTable();
	
	console.log(table.data());
	
// 	document.getElementById('proposicoes').value = table.data();
}

function tabQuestao(questao){
	
	var row;
	
	if($.fn.dataTable.isDataTable('#tabelaQuestoes')) {
		var t = $('#tabelaQuestoes').DataTable();
		t.destroy();
	}
		
		var table = $('#tabelaQuestoes').DataTable( {	
			 
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
	    		            	
	    		            	setarDadosEditar(false);
	    		            	
	    		            	console.log(row.data());
	    		            	tabProposicao(row.data().proposicoes);
	    		            	document.getElementById('questaoEnunciado').value = row.data().enunciado;
	    		            	document.getElementById('dificuldade').value = row.data().dificuldade;
	    		            	document.getElementById('peso').value = row.data().peso;
	    		            	document.getElementById('imgQuestao').value = row.data().imagem;
	    		            	document.getElementById('questaoId').value = row.data().idQuestao;
	    		            	document.getElementById('referencia').value = row.data().referencia.autor;
	    		            	document.getElementById('idRef').value = row.data().referencia.idReferencia;
	    		            	
			               }
			           }, 
    		           {	
    		               text: 'Novo',
    		               action: function ( e, dt, button, config ) {
    		            	       		            	 
				                document.getElementById('pesquisar').style.display = "none"; 
		    		            document.getElementById('editar').style.display = ""; 
	    		            	sessionStorage.setItem("editar", "true");	
    		            		
	    		            	tabProposicao(null);
	    		            	
	    		            	setarDadosEditar(true);
    		               }
    		           }
			   ],
			
			language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		 
		    "aaData": questao,
		    "aoColumns": [
		        { "mDataProp": "enunciado" },
		        { "mDataProp": "dificuldade" },
		        { "mDataProp": "referencia.autor" },
		    ]
		});
	
		$('#tabelaQuestoes tbody').on( 'click', 'tr', function () {
			
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
	            row.child( formatQuestao(row.data()), 'no-padding' ).show();
	            $(this).addClass('shown');
	 
	            $('div.slider', row.child()).slideDown();
	        }
		});
}

function setarDadosEditar(limpar){
	
	document.getElementById("conteudoId").value = document.getElementById("conteudoPesqId").value;
    document.getElementById("conteudoNome").value = document.getElementById("ContPesq").value;
    
    document.getElementById("Ementaid").value = document.getElementById("ementPesqId").value;
   	document.getElementById("EmentaNome").value = document.getElementById("ementPesq").value;
       
    document.getElementById("Disciplinaid").value = document.getElementById("discPesqId").value;
    document.getElementById("DisciplinaNome").value = document.getElementById("discPesq").value;
    
    if(limpar){
    	
    	document.getElementById('questaoEnunciado').value = "";
    	document.getElementById('dificuldade').value = "";
    	document.getElementById('peso').value = "";
    	document.getElementById('imgQuestao').value = "";
    	document.getElementById('questaoId').value = "";
    	document.getElementById('referencia').value = "";
    	document.getElementById('idRef').value = "";
    }
}

function formatQuestao ( d ) {
	
// 	console.log("formate");
	
		var conteudo = '<div class="slider">'+
	'<table class = "table table-bordered" >'+
	'<thead class="thead-inverse" ><tr><th colspan= "4"><strong>Proposições</strong><th></tr></thead>'+
	'<tbody>';
	var linha;
	var fechar = 	'</tbody></table>'+
			'</div>';
			
	var contador = 0
	var data = d.proposicoes;
	
	for(; contador < data.length; contador++){
		
		linha = '<tr>'+
					'<td><Strong>Enundiado:</Strong></td>'+
		        	'<td>'+data[contador].enunciado+'</td>'+
		            '<td>Correta:</td>'+
		            '<td>'+data[contador].correta+'</td>'+
		        '</tr>'
	        
	        conteudo = conteudo.concat(linha);
		
	}
	
	 conteudo = conteudo.concat(fechar);
	
	return conteudo;
	}

function tabConteudo(conteudo) {
	
	if($.fn.dataTable.isDataTable('#tabelaConteudo')) {
		var t = $('#tabelaConteudo').DataTable();
		t.destroy();
	}
			
		var table = $('#tabelaConteudo').DataTable( {	
			 
			 dom: 'Bfrtip',
			 select: true,
			 
			 buttons: [
						
			           {
			               extend: 'selected',
			               text: 'Selecionar',
			               action: function ( e, dt, button, config ) {		
			            	   
			            	   aux = sessionStorage.getItem("editar");
			            	   
			            		if (aux == "true"){
			            			document.getElementById("conteudoId").value =dt.rows('.selected').data()[0].idConteudo;
			            		    document.getElementById("conteudoNome").value =  dt.rows('.selected').data()[0].nome;
			            			
			            		}else{
			            			document.getElementById("conteudoPesqId").value = dt.rows('.selected').data()[0].idConteudo;
				                   	document.getElementById("ContPesq").value = dt.rows('.selected').data()[0].nome;
			            		}
			                   
			                   $.ajax({
				       				type : "POST", 
				       				url : '<s:url namespace = "/Professor/Pesquisar" action = "questaoConteudo"/>',
				       				data : "idEmenta="+document.getElementById("ementPesqId").value+
				       				"&idConteudo="+document.getElementById("conteudoPesqId").value+
				       				"&idDisciplina="+document.getElementById("discPesqId").value,
				       				dataType : "json",
				       				async: false,
				       				success : function(result) {
				       					tabQuestao(result.records);
				       				}
				       			});
			                   
			                   $('#pesqConteudo').modal('hide');
			               }
			           }
			   ],
			
			language: {
		        url: '//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json'
		    },
		 
		    "aaData": conteudo,
   		    "aoColumns": [
   		        { "mDataProp": "idConteudo" },
   		        { "mDataProp": "nome" },
   		        { "mDataProp": "descricao" },
   		    ]
		},
	
		$('#tabelaConteudo tbody').on( 'click', 'tr', function () {
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


function tabEmenta(ementa) {
	
	if($.fn.dataTable.isDataTable('#tabelaEmenta')) {
		var t = $('#tabelaEmenta').DataTable();
		t.destroy();
	}
			
			var table = $('#tabelaEmenta').DataTable( {	
				 
				 dom: 'Bfrtip',
				 select: true,
				 
				 buttons: [
							
				           {
				               extend: 'selected',
				               text: 'Selecionar',
				               action: function ( e, dt, button, config ) {
				            	   
				            	   aux = sessionStorage.getItem("editar");
				            	   
				            		if (aux == "true"){
				            			
				            			document.getElementById("conteudoId").value ="";
				            		    document.getElementById("conteudoNome").value =  "";
				            		    
				            		    document.getElementById("Ementaid").value =dt.rows('.selected').data()[0].idEmenta;
				            		   	document.getElementById("EmentaNome").value =dt.rows('.selected').data()[0].nome;
				            			
				            			
				            		}else{
			            				document.getElementById("ementPesqId").value = dt.rows('.selected').data()[0].idEmenta;
					                   	document.getElementById("ementPesq").value = dt.rows('.selected').data()[0].nome;
				            		}
				                   
				                   
				                   
				                   $.ajax({
				       				type : "POST", 
				       				url : '<s:url namespace = "/Gerente/Pesquisar" action = "pesquisarConteudoEmenta"/>',
				       				data : "idEmenta="+dt.rows('.selected').data()[0].idEmenta,
				       				dataType : "json",
				       				async: false,
				       				success : function(result) {
				       					tabConteudo(result.records);
				       				}
				       			});
				       			
				                   $('#pesqEmenta').modal('hide');
				                   
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

function tabRef(){
	
	if($.fn.dataTable.isDataTable('#tabelaReferencia')) {
		var t = $('#tabelaReferencia').DataTable();
		t.destroy();
	}
	
	 var table = $('#tabelaReferencia').DataTable( {
		 
		 dom: 'Bfrtip',
		 select: true,
		 
		 buttons: [
					
		           {
		               extend: 'selected',
		               text: 'Selecionar',
		               action: function ( e, dt, button, config ) {
		            			
	            			document.getElementById("referencia").value = dt.rows('.selected').data()[0].autor;
	            			document.getElementById("idRef").value = dt.rows('.selected').data()[0].idReferencia;
	            			
	            			 $('#pesqReferencia').modal('hide');
	            			
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
		            	   
		            	   aux = sessionStorage.getItem("editar");
		            	   
		            		if (aux == "true"){
		            			document.getElementById("DisciplinaNome").value = dt.rows('.selected').data()[0].nome;
		            			document.getElementById("Disciplinaid").value = dt.rows('.selected').data()[0].idDisicplina;
		            			
		            			document.getElementById("conteudoId").value ="";
		            		    document.getElementById("conteudoNome").value = "";
		            		    
		            		    document.getElementById("Ementaid").value ="";
		            		   	document.getElementById("EmentaNome").value = "";
		            			
		            			
		            		}else{
		            			document.getElementById("discPesq").value = dt.rows('.selected').data()[0].nome;
		            			document.getElementById("discPesqId").value = dt.rows('.selected').data()[0].idDisicplina;
		            		}
		            			
	            			
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

</script>
</div>

<div class="row">
  <div class="col-md-12">
  	<div class="page-header">
    	<h4 class = "text-uppercase"><strong>Questões</strong></h4>
    </div>
  </div>
</div>

<div id="editar">
<s:form 
	   action="CRUDQuestao"
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
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados do Conteúdo</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
						<div class="form-group ">
						<div class="col-sm-12 col-md-12">
		                    	<s:textfield 
							               type="hidden" 
							               cssClass="form-control" 
							               name="questao.conteudo.ementa.disciplina.idDisicplina" 
							               id="Disciplinaid" />			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
					                        name="questao.conteudo.ementa.disciplina.nome" 
					                        id="DisciplinaNome" 
					                        readonly = "true"
					                        label="Disciplina:"/>
			                      </div>
			                      <div class = "col-md-3 col-sm-3 col-xs-3">
										<div class = "text-center">
											<button id="btnDiscPesq" class="btn btn-default" type="button" 
									        onclick ="tabDisc()" data-toggle="modal" data-target="#pesqDisciplina">
									        <span class = "glyphicon glyphicon-search"></span> Editar</button>
										</div>
							        </div>
		                      </div>
		                       <div class="col-sm-12 col-md-12">
			                      <s:textfield 
							               type="hidden" 
							               cssClass="form-control" 
							               name="questao.conteudo.ementa.idEmenta" 
							               id="Ementaid" />
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
					                        name="questao.conteudo.ementa.nome" 
					                        id="EmentaNome" 
					                        readonly = "true"
					                        label="Ementa: "/>
			                      </div>
			                      <div class = "col-md-3 col-sm-3 col-xs-3">
									<div class = "text-center">
										<button id="btnEmentPesq" class="btn btn-default" type="button" 
								         data-toggle="modal" data-target="#pesqEmenta">
								        <span class = "glyphicon glyphicon-search"></span> Editar</button>
									</div>
								  </div>
		                      </div>
	                    	<div class="col-sm-12 col-md-12">
		                    	<s:textfield 
							               type="hidden" 
							               cssClass="form-control" 
							               name="questao.conteudo.idConteudo" 
							               id="conteudoId" />
			                      
			                      <div class="col-sm-9 col-md-9">
				                  		<s:textfield 
					                        type="text" 
					                        cssClass="form-control" 
				             	           name="questao.conteudo.nome" 
					                        id="conteudoNome" 
					                        label="Conteúdo:"
					                        readonly = "true"/>
			                      </div>
			                      <div class = "col-md-3 col-sm-3 col-xs-3">
										<div class = "text-center">
											<button id="btnContPesq" class="btn btn-default" type="button" 
									         data-toggle="modal" data-target="#pesqConteudo">
									        <span class = "glyphicon glyphicon-search"></span> Editar</button>
										</div>
									</div>
		                      </div>
	                    </div>
                 </div>	
			</div>
		</div> <!-- panel default -->
	</div> <!-- row -->
	
<div class="row">        
	<div class="col-md-12 col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading">
	            <div class="row">
            		<div class="col-md-12 col-sm-12 col-xs-12">
              			<h4 class = "text-center text-uppercase">
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados da Questão</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
					<div class="form-group ">
                    	
                    	<s:textfield 
					               type="hidden" 
					               cssClass="form-control" 
					               name="questao.idQuestao" 
					               id="questaoId" />
                    	
	                      <div class="col-sm-2 col-md-2">
	                        	<label class="control-label ">Enúnciado:</label>
	                      </div>
	                      
	                      <div class="col-sm-10 col-md-10">
		                  		<s:textarea 
			                        type="text" 
			                        cssClass="form-control" 
			                        name="questao.enunciado" 
			                        id="questaoEnunciado" 
			                        placeholder = "Digite aqui o enï¿½nciado da questï¿½o."
			                        rows="4"
			                        elementCssClass="col-sm-11 col-md-11"/>
	                      </div>
	                      
                    
                    	<div class="col-sm-2 col-md-2">
	                        <label class="control-label ">Dificuldade:</label>
                      	</div>
						<div class="col-sm-4 col-md-4">
							    <s:select 
							       name="questao.dificuldade"
							       headerKey="-1" headerValue="--- Selecione ---"
       							   list="#{'01':'Alto', '02':'Médio', '03':'Baixo'}"
							       multiple="false"
							       id="dificuldade"
							       required="true"/>	
						</div>
						<div class="col-sm-2 col-md-2">
	                        <label class="control-label ">Peso:</label>
                      	</div>
						<div class="col-sm-4 col-md-4">
							    <s:textfield 
					               type="text" 
					               cssClass="form-control" 
					               name="questao.peso" 
					               id="peso" />	  
                    	</div>
               		
               			<div class="col-sm-2 col-md-2">
	                        <label class="control-label ">Imagem:</label>
                      	</div>
                      	<div class="col-sm-4 col-md-4">
                      			<label for="imagemQuestao">Escolha o arquivo</label>
	             			
			    				<s:file name="userImage" id="imgQuestao"/>
			    			
			    				<p class="text-center">
			    					<button class="btn btn-sm btn-warning">Visualizar <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
			    					</button>
			    					<button class="btn btn-sm btn-danger">Remover <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			    					</button>
			    				</p>
                      	</div>
                      	<div class="col-sm-2 col-md-1">
	                        <label class="control-label ">Referência:</label>
                      	</div>
                      	<div class="col-sm-4 col-md-4">
				      		<s:textfield id="referencia" type="text" name="questao.referencia.autor"
				      		  readonly="true" elementCssClass="col-sm-12 col-md-12" cssClass = "form-control"/>
				      		  <div class = "text-center">
				      		  <button class = "btn btn-default" onclick="tabRef()" 
				      		  data-toggle="modal" data-target="#pesqReferencia">Pesquisar</button>
				      		  </div>
                      	</div>
               		</div>
               		<s:textfield id="idRef" type="hidden" name="questao.referencia.idReferencia"/>	
			</div>
	</div> <!-- panel default -->
</div> <!-- col -->
</div>
<div class="row">        
	<div class="col-md-12 col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading">
	            <div class="row">
            		<div class="col-md-12 col-sm-12 col-xs-12">
              			<h4 class = "text-center text-uppercase">
              			<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Proposições</h4>
              		</div>
          		</div>
			</div>
			<div class="panel-body">
				<div class="row ">
					<div  class="col-md-12 col-sm-12 col-lg-12">
				     	<table id="tabelaProposicoes" class="display" cellspacing="0" width="100%">
				 			<thead>
				     			<tr>
							         <th>ENUNCIADO</th>
							         <th>CORRETA</th>
							         <th>IMAGEM</th>
				     			</tr>
				 			</thead>
						</table>
				   	</div>
				</div>
			</div>
		</div>
	</div>
	<s:textfield 
      type="hidden" 
      cssClass="form-control" 
      name="proposicoes" 
      id="proposicoes" />
</div>
	
<div class="row ">
	<div  class="col-md-12 col-sm-12 col-lg-12">
	 <hr>
	 	</div>
</div>
<div class = "row">
<div class = "col-md-12 col-sm-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="row ">
				<div class="col-md-12">
					<div class = "text-center">
						<s:submit onclick ="armazenarProp()" name="button" value = "Salvar" cssClass= "btn btn-warning"/>
					    <s:submit  name="button" value = "Excluir" cssClass= "btn btn-danger"/>
					    <button type= "reset"  class ="btn btn-default">Limpar</button>
					</div>
				</div>
			</div>	
		</div>
	</div>
</div> <!-- col -->
</div> <!-- row -->
<br>
<br>
</s:form>
</div>

<div id = "pesquisar">
<div class = "row">
	<div class = "col-md-8 col-md-offset-2 col-sm-12 col-xs-12">
		<div class = "text-center">
		<form class = "form-horizontal">
			<div class="well">
				<div class="form-group">
					<div class = "col-md-12 col-sm-8 col-xs-12">
						<h5><strong>Para modificar ou inserir uma questão é necessário selecionar sua disciplina, ementa e conteúdo</strong></h5>
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
						<label for="ementPesq" class="control-label">Ementa:</label>
					</div>
					<div class = "col-md-7 col-sm-6 col-xs-5">
						<input  type="hidden"  class="form-control" id="ementPesqId" />
						<input readonly class ="form-control" type="text" id="ementPesq">
					</div>
					<div class = "col-md-3 col-sm-3 col-xs-3">
						<div class = "text-center">
							<button id="btnEmentPesq" class="btn btn-default" type="button" 
					         data-toggle="modal" data-target="#pesqEmenta">
					        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
						</div>
					</div>
				</div>
		        <div class="form-group">
					<div class = "col-md-2 col-sm-3 col-xs-4">
						<label for="ContPesq" class="control-label">Conteúdo:</label>
					</div>
					<div class = "col-md-7 col-sm-6 col-xs-5">
						<input  type="hidden"  class="form-control" id="conteudoPesqId" />
						<input readonly class ="form-control" type="text" id="ContPesq">
					</div>
					<div class = "col-md-3 col-sm-3 col-xs-3">
						<div class = "text-center">
							<button id="btnContPesq" class="btn btn-default" type="button" 
					         data-toggle="modal" data-target="#pesqConteudo">
					        <span class = "glyphicon glyphicon-search"></span> Pesquisar</button>
						</div>
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
     	<table id="tabelaQuestoes" class="display" cellspacing="0" width="100%">
 			<thead>
     			<tr>
			         <th>ENUNCIADO</th>
			         <th>DIFICULDADE</th>
			         <th>AUTOR</th>
     			</tr>
 			</thead>
		</table>
   	</div>
</div>
</div>



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

<!-- modal Ementa --> 
<div class="modal fade" id="pesqEmenta" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Ementa</h4>
      </div>
      <div class="modal-body">
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
      <div class="modal-footer">
 			<button type="button" class="btn btn-default" data-dismiss="modal">
 			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button> 			
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal --> 

<!-- modal Referencia --> 
<div class="modal fade" id="pesqReferencia" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Referência</h4>
      </div>
      <div class="modal-body">
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
      <div class="modal-footer">
 			<button type="button" class="btn btn-default" data-dismiss="modal">
 			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar</button> 			
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal --> 

<!-- modal Conteudo --> 
<div class="modal fade" id="pesqConteudo" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Pesquisar Conteúdo</h4>
      </div>
      <div class="modal-body">
		<div class="row ">
			<div  class="col-md-12 col-sm-12 col-lg-12">
		     	<table id="tabelaConteudo" class="display" cellspacing="0" width="100%">
		 			<thead>
		     			<tr>
					         <th>ID</th>
					         <th>NOME</th>
					         <th>DESCRIÇÃO</th>
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

<!-- modal DEtalhes proposicoes --> 
<div class="modal fade" id="detalheProp" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    <s:form 
	   action="CRUDQuestao"
	   namespace="/Professor" 
	   enctype="multipart/form-data" 
	   theme="bootstrap" 
	   cssClass="form-horizontal">	
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Proposições</h4>
      </div>
      <div class="modal-body">
      	<div class="row ">
				<div class = "col-md-12 col-sm-12">
				<div class = "form-group">
					 <div class="col-sm-2 col-md-2">
                        	<label class="control-label ">Enúnciado:</label>
                      </div>
                      
                      <div class="col-sm-10 col-md-10">
	                  		<s:textarea 
		                        type="text" 
		                        cssClass="form-control" 
		                        id="proposicaoEnunciado"
		                        name="proposicao.enunciado" 
		                        placeholder = "Digite aqui o enúnciado da proposição."
		                        rows="4"
		                        elementCssClass="col-sm-11 col-md-11"/>
                      </div>
                </div>
                <div class = "form-group">
                    <div class="col-sm-2 col-md-2">
                        <label class="control-label ">Correta:</label>
                   	</div>
					<div class="col-sm-4 col-md-3">
						    <s:select 
						       headerKey="-1" headerValue="--- Selecione ---"
      							   list="#{'01':'falso', '02':'verdadeiro'}"
						       multiple="false"
						       id="corretaPropo"
						       name="proposicao.correta"
						       required="true"/>	
					</div>
					<div class="col-sm-2 col-md-1">
                        <label class="control-label ">Imagem:</label>
                   	</div>
                   	<div class="col-sm-4 col-md-5">
                     			<label for="imagemProp">Escolha o arquivo</label>
             			
		    				<s:file name="imagemProp" id="imgProposicao"/>
		    			
		    				<p class="text-center">
		    					<button class="btn btn-sm btn-warning">Visualizar <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
		    					</button>
		    					<button class="btn btn-sm btn-danger">Remover <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			    				</button>
		    				</p>
                   	</div>
                   		<s:textfield 
		                        type="hidden" 
		                        cssClass="form-control" 
		                        id="propID" />
				</div>
				</div>	
			 
          </div>
      </div>
      <div class="modal-footer">
			<div class = "row">
           		<div class = "text-center">
           			<s:submit onclick = "gerarProposicao(); data-dismiss= 'modal';" name="button" value="Adicionar Proposição" 
           			class = "btn btn-warning"/>
           			<button type="reset" class = "btn btn-default">Limpar <span class="glyphicon glyphicon-erase" aria-hidden="true"></span></button>
           		</div>
           	</div>			
      </div>
      </s:form>
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