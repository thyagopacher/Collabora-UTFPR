var Host = obterHost();

var botaoIniciarDesabilitado = false;

var socket = new WebSocket("ws://"+Host.ip+":"+ Host.porta +"/Ovaces/websocket/aluno");
socket.onmessage = receberMensagem;
socket.onopen = iniciarWebsocket;

function iniciarWebsocket() {
	var urlParaIdDeSsessao = "/Ovaces/Aluno/Pesquisar/obterIdSessaoAluno.action";

	$.ajax({
		type : "POST",
		url : urlParaIdDeSsessao,
		context : document.body,
		dataType : "json",
		async : true,
		success : function(result) {
			iniciarSessaoIdentificada(JSON.parse(JSON.stringify(result)));
		}
	});

	setInterval(function() {
		ping();
	}, 1000);
	
	atualizar();
}

function obterIdSessao() {
	var retorno = "";

	var urlParaIdDeSsessao = "/Ovaces/Aluno/Pesquisar/obterIdSessaoAluno.action";

	$.ajax({
		type : "POST",
		url : urlParaIdDeSsessao,
		context : document.body,
		dataType : "json",
		async : false,
		success : function(result) {
			retorno = result;
		}
	});

	return retorno;
}

function iniciarSessaoIdentificada(json) {
	var Mensagem = {
		ato : "iniciar",
		idSessaoAluno : json.record,
	};
	socket.send(JSON.stringify(Mensagem));
}

function receberMensagem(event) {
	var mensagem = JSON.parse(event.data);

	if (mensagem.RespostaAto === "AtualizacaoSessoes") {
		atualizarStatusSessoes(mensagem);
	}

	if (mensagem.RespostaAto === "RedirecionamentoParaChat") {
		redirecionarParaChat(mensagem);
	}
}

function redirecionarParaChat(mensagem) {
	var idSessao = obterIdSessao().record;
	var vaiRedirecionar = mensagem.idSessao !== idSessao;

	if (vaiRedirecionar) {
		var idform = 'f' + mensagem.idAtividade;

		iniciarAtividade(idform);
	}
}

function atualizarStatusSessoes(mensagem) {
	var ulSessoes = '';

	for (i = 0; i < mensagem.registros.length; i++) {
		var ss = mensagem.registros[i];

		var spanStyle = 'success';
		var status = 'Online';

		if (ss.status == 'OFFLINE') {
			spanStyle = 'danger';
			status = 'Offline';
		}

		ulSessoes += '<li class="media">'
				+ '<div class="media-body">'
				+ '<div class="media">'
				+ '<a class="pull-left" href="#">'
				+ '<img class="media-object img-circle" style="max-height: 50px;"src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-128.png" />'
				+ '</a>' + '<div class="media-body">' + '<h5>' + ss.nome
				+ '</h5>' + '<h6><span class="label label-' + spanStyle + '">'
				+ status + '</span></h6>' + '</div>' + '</div>' + '</div>'
				+ '</li>';
	}

	$('#ulStatusSessao').html(ulSessoes);

	botaoIniciarDesabilitado = mensagem.alunosDoGrupo <= 1;

	$('#divAtividadesAbertas').find("button").attr("disabled",
			botaoIniciarDesabilitado);
}

function atualizar() {

	var urlHistoricoAtividades = "/Ovaces/Aluno/Pesquisar/pesquisarHistoricoAtividadesAluno.action";

	$.ajax({
		type : "POST",
		url : urlHistoricoAtividades,
		context : document.body,
		dataType : "json",
		async : true,
		success : function(result) {
			atualizarHistoricoAtividaes(JSON.parse(JSON.stringify(result)));
		}
	});

	var urlAtividadesEmAberto = "/Ovaces/Aluno/Pesquisar/pesquisarAtividadesAbertasAluno.action";

	$.ajax({
		type : "POST",
		url : urlAtividadesEmAberto,
		context : document.body,
		dataType : "json",
		async : true,
		success : function(result) {
			atualizarAtividadesAbertas(JSON.parse(JSON.stringify(result)));
		}
	});
}

function ping() {
	obterIdSessao();
}

function atualizarHistoricoAtividaes(json) {
	var divAtividade = '';
	
	for (i = 0; i < json.records.length; i++) {
		var att = json.records[i];
		divAtividade += '<div class="panel panel-default">'
				+ '<div class="panel-heading">'
				+ '<div class="text-center">'
				+ '<h4 class="panel-title">'
				+ att.nome
				+ '</h4>'
				+ '</div>'
				+ '</div>'
				+ '<ul class="list-group">'
				+ '<li class="list-group-item">Realizado: <span class="badge">'
				+ att.fimExecucao
				+ '</span></li>'
				+ '<li class="list-group-item">Conteudos: '+ att.conteudos+'</li>'
				+ '<li class="list-group-item">Quantidade de exercícios: <spanclass="badge">'+ att.numExercicios +'</span></li>'
				+ '<li class="list-group-item">Score: <span class="badge">'+att.pontuacao+'%</span></li>'
				+ '<li class="list-group-item">Mensagens: <span class="badge">'+att.numLinks+'</span></li>'
				+ '<li class="list-group-item">Arquivos: <span class="badge">'+att.numArquivos+'</span></li>'
				+ '<li class="list-group-item">Total Colaborações: <span class="badge">'+att.numColaboracoes+'</span></li>'
				+ '</ul>' + '</div>';
	}

	$('#divHistoricoAtividades').html(divAtividade);
}

function atualizarAtividadesAbertas(json) {

	var divAtividade = '';
	for (i = 0; i < json.records.length; i++) {

		var att = json.records[i];
		var idform = 'f' + att.idAtividade;

		divAtividade += '' + '<form id="'
				+ idform
				+ '" action="/Ovaces/Aluno/iniciarAtividade/'
				+ att.idAtividade
				+ '.action?" enctype="multipart/form-data">'
				+ '<div class="col-sm-12 col-md-12">'
				+ '<div class="panel panel-default">'
				+ '<div class="panel-heading">'
				+ '<div class="text-center">'
				+ '<h4 class="panel-title">'
				+ att.nome
				+ '</h4>'
				+ '</div>'
				+ '</div>'
				+ '<ul class="list-group">'
				+ '<li class="list-group-item">Data de entrega: <spanclass="badge">'
				+ att.dtfim
				+ '</span></li>'
				+ '<li class="list-group-item">Horário: <span class="badge">'
				+ att.hrfim
				+ '</span></li>'
				+ '<li class="list-group-item">Conteudos: '
				+ att.conteudos
				+ '</li>'
				+ '<li class="list-group-item">Quantidade de exercícios: <spanclass="badge">'
				+ att.numExercicios + '</span></li>'
				+ '<li class="list-group-item">' + '<div class="text-center">'
				+ '<button class="btn btn-warning" onclick="iniciarAtividade('
				+ idform + ');">Iniciar</button>' + '</div>' + '</li>'
				+ '</ul>' + '</div>' + '</div></form>';

	}

	$('#divAtividadesAbertas').html(divAtividade);

	$('#divAtividadesAbertas').find("button").attr("disabled",
			botaoIniciarDesabilitado);
}

function iniciarAtividade(idForm) {
	document.forms[idForm].submit();
}
