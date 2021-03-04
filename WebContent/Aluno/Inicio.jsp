<%@ include file="/Aluno/headAluno.jsp"%>

<script type="text/javascript" charset="utf8"
	src="/Ovaces/js/aluno/host.js"></script>

<script type="text/javascript" charset="utf8"
	src="/Ovaces/js/aluno/inicio.js"></script>

<div class="row">
	<div class="col-md-12">
		<div class="page-header">
			<h4>
				<strong>Início</strong>
			</h4>
		</div>
	</div>
</div>
<div class="row">
	<!-- Questão-->
	<div class="questao">
		<div class="col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading">
					<strong>Histórico</strong>
				</div>
				<div class="panel-body">
					<div id="divHistoricoAtividades" class="col-sm-12 col-md-12">
					</div>
				</div>
				<div class="panel-footer"></div>
			</div>
		</div>
	</div>
	<!-- Chat -->
	<div class="col-md-5">
		<div class="panel panel-yellow">
			<div class="panel-heading">
				<strong>Atividades Disponíveis</strong>
			</div>
			<div class="panel-body">
				<div class="row ">
					<div class="col-sm-12 col-md-12">
						<div id="divAtividadesAbertas" class="panel-group"></div>
					</div>
				</div>
			</div>
			<div class="panel-footer"></div>
		</div>
		<!-- mostra as opções relativas ao celular -->
		<div class="opcoesCelular">
			<div class="row">
				<div class="col-sm-12 col-xs-12">
					<div class="text-center">
						<div class="btn-group">
							<button type="button" class="btn btn-warning" data-toggle="modal"
								data-target="#User">Online</button>
							<button type="button" class="btn btn-warning" data-toggle="modal"
								data-target="#historico">Histórico</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br> <br> <br> <br>
	</div>

	<div class="usuario">
		<!-- Painel de usuarios ativos na visualização desktop-->
		<div class="col-md-2">
			<div class="panel panel-default">
				<div class="panel-heading">
					<strong>Usuários Online</strong>
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

	<!-- Modal para ver o histórico no modo celular -->
	<div class="modal fade" id="historico">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<div class="text-center">
						<h4 class="modal-title">
							<strong>Histórico</strong>
						</h4>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="col-sm-12 col-md-12">
										<div class="panel panel-default">
											<div class="panel-heading">
												<div class="text-center">
													<h4 class="panel-title">Grau de confiança</h4>
												</div>
											</div>
											<ul class="list-group">
												<li class="list-group-item">Realizado: <span
													class="badge">15/07</span></li>
												<li class="list-group-item">Conteudos: Grau de
													confiança</li>
												<li class="list-group-item">Quantidade de exercícios: <span
													class="badge">10</span></li>
												<li class="list-group-item">Grupo: <a id="votacao"
													data-toggle="modal" href="#User">Lucas</a>, Eliana
												</li>
												<li class="list-group-item">Score: <span class="badge">70%</span></li>
												<li class="list-group-item">Colaboração: <span
													class="badge">80%</span></li>
											</ul>
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





	<!-- Modal para a apresentação de informações sobre os integrantes do grupo-->
	<div class="modal fade" id="User" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<div class="text-center">
						<h4 class="modal-title">
							<strong>Informações</strong>
						</h4>
					</div>
				</div>
				<div class="modal-body">
					<div class="panel-body">
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
											<h5>Eliana Ishika...</h5>
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
					<div class="modal-footer">
						<div class="text-center">
							<button type="button" class="btn btn-warning"
								data-dismiss="modal">Ir para Chat</button>
						</div>
					</div>
				</div>
				<!-- /.modal-content-->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->


	</div>
</div>
</div>
</div>

</body>
</html>