<%@ include file="/Aluno/headAluno.jsp"%>

<script type="text/javascript" charset="utf8"
	src="/Ovaces/js/aluno/host.js"></script>

<script type="text/javascript" charset="utf8"
	src="/Ovaces/js/aluno/chat.js"></script>

<div class="row">
	<div class="col-md-12">
		<div class="page-header">
			<h4>
				<strong>�rea de Colabora��o</strong>
			</h4>
		</div>
	</div>
</div>
<div class="row">
	<!-- Quest�o-->
	<div class="questao">
		<div class="col-md-5">
			<div class="panel panel-yellow">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-list-alt"></span> <strong>Questão</strong>
				</div>
				<div class="panel-body">
					<ul class="media-list">
						<li class="media">
							<div class="media-body">
								<div class="media">
									<div class="media-body">
										<h5 class="text-center text-uppercase">
											<strong> Enunciado</strong>
										</h5>
										<p id="pEnunciadoQuestao"></p>
										<small class="text-muted">FONTE: adaptado Triola</small>
									</div>
									<div class='media-footer'>
										<h5 class="text-center text-uppercase">
											<strong> Alternativas</strong>
										</h5>
										<ul id="ulAlternativasQuestao" class="list-group">
										</ul>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="text-center">
							<button id="idBtnResponder" class="btn btn-warning btn-sm"
								id="votacao" data-toggle="modal" data-target="#modalVotacao">
								Responder <span class="glyphicon glyphicon-ok-circle"></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- Chat -->
	<section id="chat">
		<div class="col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-comment"></span> <strong>Chat</strong>
					<span class="glyphicon glyphicon-time"></span> <strong
						id="idTempoRestante">Restam: </strong>

					<div class="btn-group pull-right">
						<!-- 						<button type="button" -->
						<!-- 							class="btn btn-warning btn-sm dropdown-toggle" -->
						<!-- 							data-toggle="dropdown"> -->
						<!-- 							Enviar <span class="glyphicon glyphicon-chevron-down"></span> -->
						<!-- 						</button> -->
						<button type="button"
							class="btn btn-warning btn-sm dropdown-toggle"
							data-toggle="modal" data-target="#dialogEscolhaArquivo">Arquivo</button>

						<div id="dialogEscolhaArquivo" class="modal fade" role="dialog">
							<div class="modal-dialog">

								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Arquivo</h4>
									</div>
									<s:form action="uploadArquivo" method="post"
										enctype="multipart/form-data">
										<div class="modal-body">
											<s:file name="upload" label="File" cssClass="btn btn-warning" />
											<s:submit value="Salvar" cssClass="btn btn-warning" />
											<button type="button" class="btn btn-warning"
												data-dismiss="modal">Cancelar</button>
										</div>
									</s:form>
								</div>
							</div>
						</div>
						<div id="dialogAviso" class="modal fade" role="dialog">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 id="headerDialogAviso" class="modal-title"></h4>
									</div>
									<div class="modal-body">
										<p id="msgDialogAviso"></p>
									</div>
									<div class="modal-footer">
										<button id="btnDialogAviso" type="button"
											class="btn btn-warning" data-dismiss="modal">Fechar</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<ol id="olChat" class="chat">
					</ol>
				</div>
				<div class="panel-footer">
					<div class="input-group">
						<input id="idInputColaboracaoTexto" class="form-control input-sm"
							placeholder="Escreva sua mensagem..." type="text"> <span
							class="input-group-btn">
							<button class="btn btn-warning btn-sm" id="btn-chat"
								onclick="enviarColaboracaoTexto();">
								Enviar <span class="glyphicon glyphicon-send"></span>
							</button>
						</span>
					</div>
					<form id="idFormRedirecionamentoInicio"
						action="/Ovaces/Aluno/redirecionarSemMinAlunos.action"
						enctype="multipart/form-data"></form>
				</div>
			</div>
			<!-- mostra as op��es relativas ao celular -->
			<div class="opcoesCelular">
				<div class="row">
					<div class="col-sm-12 col-xs-12">
						<div class="text-center">
							<div class="btn-group">
								<button type="button" class="btn btn-warning"
									data-toggle="modal" data-target="#Online">Online</button>
								<button type="button" class="btn btn-warning"
									data-toggle="modal" data-target="#modalQuestao">Questão</button>
								<button type="button" class="btn btn-warning"
									data-toggle="modal" data-target="#modalVotacao">Responder</button>
								<button type="button" class="btn btn-warning">Pular</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br> <br> <br> <br>
		</div>
	</section>

	<div class="usuario">
		<!-- Painel de usuarios ativos na visualiza��o desktop-->
		<div class="col-md-2">
			<div class="panel panel-default">
				<div class="panel-heading">
					<strong>Usu�rios Online</strong>
				</div>
				<div class="panel-body">
					<ul id="ulStatusSessao" class="media-list">
					</ul>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="text-center"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


</div>
</div>
</div>
</div>

<!-- Modal para a apresenta��o da quest�o no modo celular -->
<div class="modal fade" id="modalQuestao">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Questao</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-yellow">
							<div class="panel-body">
								<ul class="media-list">
									<li class="media">
										<div class="media-body">
											<div class="media">
												<div class="media-body">
													<h5 class="text-center text-uppercase">
														<strong> Enunciado</strong>
													</h5>
													<p>(6.1) A confeitaria Hudson Valley fabrica sonhos que
														s�o ebalados em pacotes com a indica��o de que h� 12
														sonhos pesando um total de 42oz. Se a varia�ao entre os
														sonhos � muito grande, algumas caixas ter�o peso a mais e
														outras a menos, o que desagrada o consumidor. O supervisor
														de controle de qualidade constatou que esses problemas
														podem ser evitados se os sonhos tiverem um peso m�dio de
														3,5oz e um desvio-padr�o de 0,06oz ou menos.
														Selecionaram-se aleatoriamente, na linha de produ��o, doze
														sonhos, que s�o pesados, dando os resultados abaixo. Com
														respeito a estimativa da vari�ncia e do desvio-padr�o
														populacional e ao n�vel de confian�a de 95% assinale as
														alternativas verdadeiras no modelo somat�rio: DADOS: 3,58
														3,50 3,68 3,61 3,42 3,52 3,66 3,50 3,36 3,42</p>
													<small class="text-muted">FONTE: adaptado Triola</small>
												</div>
												<div class='media-footer'>
													<h5 class="text-center text-uppercase">
														<strong> Alternativas</strong>
													</h5>
													<ul class="list-group">
														<li class="list-group-item">X�r=3,816</li>
														<li class="list-group-item">X�r=21,920</li>
														<li class="list-group-item">O intervalo de confian�a
															para a vari�ncia esta entre 0,006 e 0,034</li>
														<li class="list-group-item">O intervalo de confian�a
															para a vari�ncia esta entre 0,77 e 0,185</li>
														<li class="list-group-item">O intervalo de confian�a
															para o desvio-padr�o esta entre 0,77 e 0,185</li>
													</ul>
												</div>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="text-center">
										<button class="btn btn-warning btn-sm" id="votacaoCEl"
											data-toggle="modal" data-dismiss="modal"
											data-target="#modalVotacao">Responder</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="modalVotacao">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button id="bFecharDialog" type="button" class="close"
					data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Votação</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-yellow">
							<div class="panel-body">
								<ul class="media-list">
									<li class="media">
										<div class="media-body">
											<div class="media">
												<div class="media-body">
													<h5 class="text-center text-uppercase">
														<strong> Enunciado</strong>
													</h5>
													<p id="pEnunciadoDialog"></p>
													<small class="text-muted">FONTE: adaptado Triola</small>
												</div>
												<div class='media-footer'>
<!-- 													<h5 class="text-center text-uppercase"> -->
<!-- 														<strong> Alternativas</strong> -->
<!-- 													</h5> -->
<!-- 													<div class="text-center"> -->
<!-- 														<div class="row"> -->
<!-- 															Op��es -->
<!-- 															<div class="col-sm-12 col-md-12 col-lg-12" -->
<!-- 																id="divRespostasDialog"></div> -->
<!-- 															col -->
<!-- 														</div> -->
<!-- 														/.row -->
<!-- 													</div> -->
																							<h5 class="text-center text-uppercase">
											<strong> Alternativas</strong>
										</h5>
										<ul id="ulRespostasDialog" class="list-group">
										</ul>
												</div>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="text-center">
										<button class="btn btn-warning" id="confirmar"
											data-toggle="modal" data-dismiss="modal"
											onclick="responderQuestao()">Confirmar</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="correcao" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-center text-uppercase">
					<strong>Correçãoo <span
						class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span></strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 col-sm-12 text-center">
								<h4 class="text-uppercase">
									<strong>Resolução</strong>
								</h4>
								<p>Resolução para o exercício proposto.</p>
							</div>
						</div>
						<div class="col-md-12 col-sm-12">
							<div class="row">
								<hr>
							</div>
						</div>
						<div class="row" id="idDivRespostas">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="text-center">
					<button type="button" class="btn btn-warning" data-dismiss="modal" onclick="finalizarQuestao()" >Continuar</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="feedbackAtividade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-center text-uppercase">
					<strong>Feedback Atividade <span
						aria-hidden="true"></span></strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row" id="idDivHeaderFeedbackAtividade">
							<div class="col-md-12 col-sm-12 text-center">
								<h4 class="text-uppercase">
									<strong>Resolução</strong>
								</h4>
								<p>Resolução para o exercício proposto.</p>
							</div>
						</div>
						<div class="col-md-12 col-sm-12">
							<div class="row">
								<hr>
							</div>
						</div>
						<div class="row" id="idDivFeedbackColabs">
						</div>
						<div class="row" id="idDivPontuacoes">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="text-center">
					<button type="button" class="btn btn-warning" data-dismiss="modal" onclick="finalizarAtividade()" >Finalizar</button>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Modal para apresenta��o da resposta correta -->
<div class="modal fade" id="Online" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-center">Usu�rios Online</h4>
			</div>
			<div class="modal-body">
				<ul class="media-list">
					<li class="media">
						<div class="media-body">
							<div class="media">
								<a class="pull-left" href="#"> <img
									class="media-object img-circle" style="max-height: 50px;"
									src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
								</a>
								<div class="media-body">
									<h5>Lucas Novelli</h5>
									<h6>
										<span class="label label-success">Online</span>
									</h6>
								</div>
							</div>
						</div>
					</li>
					<li class="media">
						<div class="media-body">
							<div class="media">
								<a class="pull-left" href="#"> <img
									class="media-object img-circle" style="max-height: 50px;"
									src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
								</a>
								<div class="media-body">
									<h5>Eliana Ishikawa</h5>
									<h6>
										<span class="label label-success">Online</span>
									</h6>
								</div>
							</div>
						</div>
					</li>
					<li class="media">
						<div class="media-body">
							<div class="media">
								<a class="pull-left" href="#"> <img
									class="media-object img-circle" style="max-height: 50px;"
									src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />
								</a>
								<div class="media-body">
									<h5>Simone Nasser</h5>
									<h6>
										<span class="label label-danger">Offline</span>
									</h6>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Modal para apresenta��o de empate da escolhe da alternativa correta -->
<div class="modal fade" id="empate" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title text-center text-uppercase">
					<strong>Atenção <span
						class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span></strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="panel panel-default">
					<div class="panel-body">
						<section id="panelEmpate">
							<div class="row">
								<div class="col-md-12 col-sm-12 text-center">
									<h4 class="text-uppercase">
										<strong>Não há consenso</strong>
									</h4>
									<p>Houveram respostas distintas para a exercício! Continuem
										a colaborar!</p>
								</div>
							</div>
							<div class="col-md-12 col-sm-12">
								<div class="row"></div>
								<hr>
							</div>
							<div class="row" id="idDivVotosEmpate"></div>
						</section>
					</div>
					<!-- panel body -->
				</div>
				<!-- panel -->
			</div>
			<!-- modal body -->
			<!-- 			<div class="modal-footer"> -->
			<!-- 				<div class="text-center"> -->
			<!-- 					<button type="button" class="btn btn-warning" id="OKEmpate" -->
			<!-- 						data-toggle="modal" data-dismiss="modal" data-target="#correcao">Escolher -->
			<!-- 						correta</button> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

</body>
</html>