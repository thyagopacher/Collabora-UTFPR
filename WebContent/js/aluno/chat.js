var Host = obterHost();

var socket = new WebSocket("ws://" + Host.ip + ":" + Host.porta + "/Ovaces/websocket/aluno");
socket.onmessage = receberMensagem;
socket.onopen = iniciarWebsocket;

var questaoEmExec = null;
var finalizou = false;

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

	setTimeout(requisitarInformacoesIniciais, 1000);
}

function requisitarInformacoesIniciais() {
	var Mensagem = {
		ato : "solicitar_info_iniciais",
		idSessaoAluno : obterIdSessao().record
	};

	console.log(Mensagem);

	socket.send(JSON.stringify(Mensagem));
}

function enviarColaboracaoTexto() {
	var colaboracaoTexto = document.getElementById('idInputColaboracaoTexto').value;

	document.getElementById('idInputColaboracaoTexto').value = "";

	var Mensagem = {
		ato : "enviar_colaboracao_texto",
		idSessaoAluno : obterIdSessao().record,
		msg : colaboracaoTexto
	};

	socket.send(JSON.stringify(Mensagem));
}

function finalizarQuestao() {
	var Mensagem = {
		ato : "finalizar_questao",
		idSessaoAluno : obterIdSessao().record
	};

	socket.send(JSON.stringify(Mensagem));
}

function finalizarAtividade() {
	var Mensagem = {
		ato : "finalizar_atividade",
		idSessaoAluno : obterIdSessao().record
	};

	socket.send(JSON.stringify(Mensagem));
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

	if (mensagem.RespostaAto === "TempoDuracaoAtividade") {
		atualizarTempoRestante(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "AtualizacaoSessoes") {
		atualizarStatusSessoes(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "Colaboracao") {
		atualizarChat(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "TrocarQuestao") {
		trocarQuestao(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "NotificarEmpate") {
		notificarEmpate(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "ApresentarFeedbackQuestao") {
		apresentarFeedbackQuestao(mensagem);
		return;
	}

	if (mensagem.RespostaAto === "ApresentarFeedbackAtividade") {
		apresentarFeedbackAtividade(mensagem);
	}

	if (mensagem.RespostaAto === "RedirecionarAposAtividade") {
		redirecionarAoFinalizarAtividade();
	}
}

function atualizarTempoRestante(mensagem) {

	var negativo = false;

	if (mensagem.minutos < 0) {
		mensagem.minutos = mensagem.minutos * -1;
		negativo = true;
	}

	if (mensagem.segundos < 0) {
		mensagem.segundos = mensagem.segundos * -1;
		negativo = true;
	}

	var mins = mensagem.minutos < 10 ? '0' + mensagem.minutos
			: mensagem.minutos;
	var segs = mensagem.segundos < 10 ? '0' + mensagem.segundos
			: mensagem.segundos;

	document.getElementById("idTempoRestante").innerText = 'Restam ' + mins
			+ ':' + segs;

	if (negativo) {
		$("#idTempoRestante").css("color", "red");

		// if (document.getElementById("modalVotacao").style.display == "none"
		// || document.getElementById("modalVotacao").style.display == ""
		// || document.getElementById("dialogAviso").style.display == "none"
		// || document.getElementById("dialogAviso").style.display == "") {
		// $('#modalVotacao').modal('show');
		// document.getElementById("bFecharDialog").style.visibility = "hidden";
		// }
		// document.getElementById("bFecharDialog").style.visibility = "hidden";
	} else {
		$("#idTempoRestante").css("color", "black");

	}
}

function atualizarStatusSessoes(mensagem) {
	var ulSessoes = '';

	if (mensagem.alunosDoGrupo > 1) {
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
					+ '</h5>' + '<h6><span class="label label-' + spanStyle
					+ '">' + status + '</span></h6>' + '</div>' + '</div>'
					+ '</div>' + '</li>';
		}

		$('#ulStatusSessao').html(ulSessoes);
	} else {
		document.forms["idFormRedirecionamentoInicio"].submit();
	}

}

function selecionarSomenteUm(check) {
	var checkboxSelecionados = $("#divRespostasDialog input:checkbox");

	for (var i = 0; i < checkboxSelecionados.size(); i++) {
		checkboxSelecionados[i].checked = checkboxSelecionados[i].id == check.id;
	}
}

function responderQuestao() {

	var checkboxProposicoes = $("#ulRespostasDialog input:checkbox");

	var totalSelecionadas = 0;
	for (var i = 0; i < checkboxProposicoes.size(); i++) {
		if (checkboxProposicoes[i].checked) {
			totalSelecionadas = totalSelecionadas + 1;
		}
	}

	if (totalSelecionadas > 0) {
		var selecionadas = new Array(totalSelecionadas);

		for (var i = 0; i < checkboxProposicoes.size(); i++) {
			if (checkboxProposicoes[i].checked) {
				selecionadas.push(checkboxProposicoes[i].id);
			}
		}

		var Mensagem = {
			ato : "responder_questao",
			idSessaoAluno : obterIdSessao().record,
			proposicoes : selecionadas
		};

		socket.send(JSON.stringify(Mensagem));
	}
}

function trocarQuestao(mensagem) {
	questaoEmExec = mensagem.questao;

	var paragraphEnunciado = document.getElementById("pEnunciadoQuestao");
	paragraphEnunciado.innerText = questaoEmExec.enunciado;

	var ulAlternativasQ = document.getElementById("ulAlternativasQuestao");

	while (ulAlternativasQ.firstChild) {
		ulAlternativasQ.removeChild(ulAlternativasQ.firstChild);
	}

	var ulRespostasDialog = document.getElementById("ulRespostasDialog");

	while (ulRespostasDialog.firstChild) {
		ulRespostasDialog.removeChild(ulRespostasDialog.firstChild);
	}

	var pEnunciadoDialog = document.getElementById("pEnunciadoDialog");
	pEnunciadoDialog.innerText = questaoEmExec.enunciado;

	// var divRespostasDialog = document.getElementById("divRespostasDialog");
	//
	// while (divRespostasDialog.firstChild) {
	// divRespostasDialog.removeChild(divRespostasDialog.firstChild);
	// }

	for (i = 0; i < questaoEmExec.proposicoes.length; i++) {

		// CHAT

		var proposicao = questaoEmExec.proposicoes[i].proposicao + ") "
				+ questaoEmExec.proposicoes[i].enunciado;

		var liProp = document.createElement("li");
		liProp.setAttribute("class", "list-group-item");
		liProp.innerText = proposicao;

		ulAlternativasQ.appendChild(liProp);

		// DIALOG

		var inputCheckboxAlternativa = document.createElement("input");
		inputCheckboxAlternativa.setAttribute("id", "prop"
				+ questaoEmExec.proposicoes[i].id);
		// inputCheckboxAlternativa.setAttribute("onclick",
		// "selecionarSomenteUm(prop" + questaoEmExec.proposicoes[i].id
		// + ")");
		inputCheckboxAlternativa.setAttribute("style", "width: 5%;");
		inputCheckboxAlternativa.setAttribute("type", "checkbox");

		// var spanCheckAlternativa = document.createElement("span");
		// spanCheckAlternativa.setAttribute("class", "input-group-addon");
		// spanCheckAlternativa.appendChild(inputCheckboxAlternativa);
		//		
		// var spanTextoAlternativa = document.createElement("span");
		// spanCheckAlternativa.setAttribute("class", "input-group-addon");
		// spanCheckAlternativa.innerText = proposicao;

		var inputTextoAlternativa = document.createElement("label");
		// inputTextoAlternativa.setAttribute("type", "text");
		inputTextoAlternativa.setAttribute("style",
				"width: 95%;display: inline-table;");
		inputTextoAlternativa.setAttribute("class", "form-control");
		inputTextoAlternativa.innerText = proposicao;
		// inputTextoAlternativa.readonly = true;

		var liAlternativa = document.createElement("li");
		liAlternativa.setAttribute("class", "list-group-item");
		liAlternativa.appendChild(inputCheckboxAlternativa);
		liAlternativa.appendChild(inputTextoAlternativa);

		// var divAlternativaDialog = document.createElement("div");
		// divAlternativaDialog.setAttribute("class", "input-group");
		// divAlternativaDialog.appendChild(spanCheckAlternativa);
		// divAlternativaDialog.appendChild(spanTextoAlternativa);

		ulRespostasDialog.appendChild(liAlternativa);
	}

}

function atualizarChat(mensagem) {
	var srcImgMinhaMsg = 'http://placehold.it/50/FA6F57/fff&amp;text=ME';
	var srcImgMsgOutro = 'http://placehold.it/50/55C1E7/fff&amp;text=U';

	var ulSessoes = '';
	var idSessao = obterIdSessao().record;

	var myNode = document.getElementById("olChat");
	while (myNode.firstChild) {
		myNode.removeChild(myNode.firstChild);
	}

	for (i = 0; i < mensagem.registros.length; i++) {
		var colaboracao = mensagem.registros[i];
		var mensagemAutoral = colaboracao.idSessaoOrigem === idSessao;

		var strongAutorMensagem = document.createElement("strong");
		strongAutorMensagem.setAttribute("class",
				mensagemAutoral ? "pull-right primary-font"
						: "pull-left primary-font");
		strongAutorMensagem.innerText = colaboracao.autor;

		var spanTempo = document.createElement("span");
		spanTempo.setAttribute("class", "glyphicon glyphicon-time");

		var smallTempo = document.createElement("small");
		smallTempo.setAttribute("class",
				mensagemAutoral ? "pull-left text-muted"
						: "pull-right text-muted");
		smallTempo.innerHTML = '<span class="glyphicon glyphicon-time"></span><span>13 mins ago</span>';

		var divMensagemHeader = document.createElement("div");
		divMensagemHeader.setAttribute("class", "header clearfix");
		divMensagemHeader.setAttribute("style", "height: 2em;");
		divMensagemHeader.appendChild(smallTempo);
		divMensagemHeader.appendChild(strongAutorMensagem);

		var pMensagem = document.createElement("p");
		if (colaboracao.anexo) {
			var aAnexo = document.createElement("a");
			aAnexo.setAttribute("href", colaboracao.link);
			aAnexo.setAttribute("download", colaboracao.nomeArquivo);
			aAnexo.innerText = colaboracao.mensagem;
			pMensagem.appendChild(aAnexo);
		} else {
			pMensagem.innerText = colaboracao.mensagem;
		}

		var divMensagemFooter = document.createElement("div");
		divMensagemFooter.setAttribute("class",
				mensagemAutoral ? "footer pull-right" : "footer pull-left");
		divMensagemFooter.appendChild(pMensagem);

		var divMensagemMaster = document.createElement("div");
		divMensagemMaster.appendChild(divMensagemHeader);
		divMensagemMaster.setAttribute("class", "chat-body clearfix");
		divMensagemMaster.appendChild(divMensagemFooter);

		var imgIdentificacao = document.createElement("img");
		imgIdentificacao.setAttribute("class", "img-circle");
		imgIdentificacao.setAttribute("src", mensagemAutoral ? srcImgMinhaMsg
				: srcImgMsgOutro);
		imgIdentificacao.setAttribute("alt", "User Avatar");

		var spanIdentificacao = document.createElement("span");
		spanIdentificacao.setAttribute("class",
				mensagemAutoral ? "chat-img pull-right" : "chat-img pull-left");
		spanIdentificacao.appendChild(imgIdentificacao);

		var liMaster = document.createElement("li");
		liMaster.setAttribute("class", mensagemAutoral ? "right clearfix"
				: "left clearfix");
		liMaster.appendChild(spanIdentificacao);
		liMaster.appendChild(divMensagemMaster);

		document.getElementById("olChat").appendChild(liMaster);
	}
}

function notificarEmpate(mensagem) {

	var divVotosEmpate = document.getElementById("idDivVotosEmpate");

	while (divVotosEmpate.firstChild) {
		divVotosEmpate.removeChild(divVotosEmpate.firstChild);
	}

	for (i = 0; i < mensagem.alunos.length; i++) {
		var aluno = mensagem.alunos[i];

		var ulVotos = document.createElement("ul");
		ulVotos.setAttribute("class", "list-group");

		for (j = 0; j < aluno.votos.length; j++) {
			var voto = aluno.votos[j];

			var liVoto = document.createElement("li");
			liVoto.setAttribute("class", "list-group-item");
			liVoto.innerText = voto;

			ulVotos.appendChild(liVoto);
		}

		var strongAluno = document.createElement("strong");
		strongAluno.innerText = "Aluno: " + aluno.aluno;

		var h5Aluno = document.createElement("h5");
		h5Aluno.setAttribute("class", "text-center text-uppercase");
		h5Aluno.appendChild(strongAluno);

		var divAluno = document.createElement("div");
		divAluno.setAttribute("class", "col-sm-12 col-md-12");
		divAluno.appendChild(h5Aluno);
		divAluno.appendChild(ulVotos);

		divVotosEmpate.appendChild(divAluno);
	}

	$('#empate').modal('show');

}

function apresentarFeedbackAtividade(mensagem) {

	if ((document.getElementById("feedbackAtividade").style.display == "none" || document
			.getElementById("feedbackAtividade").style.display == "")) {

		var divHeaderFeedbackAtividade = document
				.getElementById("idDivHeaderFeedbackAtividade");

		while (divHeaderFeedbackAtividade.firstChild) {
			divHeaderFeedbackAtividade
					.removeChild(divHeaderFeedbackAtividade.firstChild);
		}

		var strongCabecalho = document.createElement("strong");
		strongCabecalho.innerText = mensagem.nomeAtividade;

		var h4Cabecalho = document.createElement("h4");
		h4Cabecalho.setAttribute("class", "text-uppercase");
		h4Cabecalho.appendChild(strongCabecalho);

		var pCabecalho = document.createElement("p");
		pCabecalho.innerText = "Total Colaborações: "
				+ mensagem.totalColaboracoes;

		var divCabecalho = document.createElement("div");
		divCabecalho.setAttribute("class", "col-md-12 col-sm-12 text-center");
		divCabecalho.appendChild(h4Cabecalho);
		divCabecalho.appendChild(pCabecalho);

		divHeaderFeedbackAtividade.appendChild(divCabecalho);

		var divFeedbackColabs = document.getElementById("idDivFeedbackColabs");

		while (divFeedbackColabs.firstChild) {
			divFeedbackColabs.removeChild(divFeedbackColabs.firstChild);
		}

		if (mensagem.colaboracoes != null) {
			for (i = 0; i < mensagem.colaboracoes.length; i++) {
				var colaboracao = mensagem.colaboracoes[i];

				var liNumColabs = document.createElement("li");
				liNumColabs.setAttribute("class", "list-group-item");
				liNumColabs.innerText = "Nº Colaborações: "
						+ colaboracao.numColaboracoes;

				var liPercentColabs = document.createElement("li");
				liPercentColabs.setAttribute("class", "list-group-item");
				liPercentColabs.innerText = "Colaboração: "
						+ colaboracao.porcentagemColaboracoes + "%";

				var olDetalhesColab = document.createElement("ol");
				olDetalhesColab.setAttribute("class", "list-group");
				olDetalhesColab.appendChild(liNumColabs);
				olDetalhesColab.appendChild(liPercentColabs);

				var strongAluno = document.createElement("strong");
				strongAluno.innerText = colaboracao.aluno;

				var h5Aluno = document.createElement("h5");
				h5Aluno.setAttribute("class", "text-center text-uppercase");
				h5Aluno.appendChild(strongAluno);

				var divColaboracao = document.createElement("div");
				divColaboracao.setAttribute("class", "col-sm-12 col-md-12");
				divColaboracao.appendChild(h5Aluno);
				divColaboracao.appendChild(olDetalhesColab);

				divFeedbackColabs.appendChild(divColaboracao);
			}

		}

		var divPontuacoes = document.getElementById("idDivPontuacoes");

		var strongPontuacoes = document.createElement("strong");
		strongPontuacoes.innerText = "Pontuações";

		var h5Pontuacoes = document.createElement("h5");
		h5Pontuacoes.setAttribute("class", "text-center text-uppercase");
		h5Pontuacoes.appendChild(strongPontuacoes);

		var olPontuacoesQuestoes = document.createElement("ol");
		olPontuacoesQuestoes.setAttribute("class", "list-group");

		if (mensagem.pontuacoes != null) {
			for (i = 0; i < mensagem.pontuacoes.length; i++) {
				var pontuacao = mensagem.pontuacoes[i];

				var liPontuacaoQuestao = document.createElement("li");
				liPontuacaoQuestao.setAttribute("class", "list-group-item");
				liPontuacaoQuestao.innerText = "Questão " + pontuacao.questao
						+ ": " + pontuacao.pontuacao + "%";

				olPontuacoesQuestoes.appendChild(liPontuacaoQuestao);
			}
		}

		var divPrincipalPontuacoes = document.createElement("div");
		divPrincipalPontuacoes.setAttribute("class", "col-sm-12 col-md-12");
		divPrincipalPontuacoes.appendChild(h5Pontuacoes);
		divPrincipalPontuacoes.appendChild(olPontuacoesQuestoes);

		while (divPontuacoes.firstChild) {
			divPontuacoes.removeChild(divPontuacoes.firstChild);
		}

		divPontuacoes.appendChild(divPrincipalPontuacoes);

		$('#feedbackAtividade').modal('show');
	}

}

function apresentarFeedbackQuestao(mensagem) {

	if ((document.getElementById("correcao").style.display == "none" || document
			.getElementById("correcao").style.display == "")
			&& !finalizou) {

		var ulDetalhesRespostaCorreta = document.createElement("ul");
		ulDetalhesRespostaCorreta.setAttribute("class", "list-group");

		for (var i = 0; i < mensagem.corretas.length; i++) {
			var correta = mensagem.corretas[i];

			var liDetalheRespostaCorreta = document.createElement("li");
			liDetalheRespostaCorreta.setAttribute("class", "list-group-item");
			liDetalheRespostaCorreta.innerText = correta.identificacao + ") "
					+ correta.enunciado;

			ulDetalhesRespostaCorreta.appendChild(liDetalheRespostaCorreta);
		}

		var spanRespostaCorreta = document.createElement("span");
		spanRespostaCorreta.setAttribute("class", "label label-success");
		spanRespostaCorreta.innerText = "Resposta(s) Correta(s)";

		var strongRespostaCorreta = document.createElement("strong");
		strongRespostaCorreta.appendChild(spanRespostaCorreta);

		var h5RespostaCorreta = document.createElement("h5");
		h5RespostaCorreta.setAttribute("class", "text-center text-uppercase");
		h5RespostaCorreta.appendChild(strongRespostaCorreta);

		var divRespostaCorreta = document.createElement("div");
		divRespostaCorreta.setAttribute("class", "col-sm-6 col-md-6");
		divRespostaCorreta.appendChild(h5RespostaCorreta);
		divRespostaCorreta.appendChild(ulDetalhesRespostaCorreta);

		var ulDetalhesRespostaSelecionada = document.createElement("ul");
		ulDetalhesRespostaSelecionada.setAttribute("class", "list-group");

		for (var i = 0; i < mensagem.selecionadas.length; i++) {
			var selecionada = mensagem.selecionadas[i];

			var liDetalheRespostaSelecionada = document.createElement("li");
			liDetalheRespostaSelecionada.setAttribute("class",
					"list-group-item");
			liDetalheRespostaSelecionada.innerText = selecionada.identificacao
					+ ") " + selecionada.enunciado;

			ulDetalhesRespostaSelecionada
					.appendChild(liDetalheRespostaSelecionada);
		}

		var spanRespostaSelecionada = document.createElement("span");
		spanRespostaSelecionada.setAttribute("class", "label label-success");
		spanRespostaSelecionada.innerText = "Resposta(s) Selecionada(s)";

		var strongRespostaSelecionada = document.createElement("strong");
		strongRespostaSelecionada.appendChild(spanRespostaSelecionada);

		var h5RespostaSelecionada = document.createElement("h5");
		h5RespostaSelecionada.setAttribute("class",
				"text-center text-uppercase");
		h5RespostaSelecionada.appendChild(strongRespostaSelecionada);

		var divRespostaSelecionada = document.createElement("div");
		divRespostaSelecionada.setAttribute("class", "col-sm-6 col-md-6");
		divRespostaSelecionada.appendChild(h5RespostaSelecionada);
		divRespostaSelecionada.appendChild(ulDetalhesRespostaSelecionada);

		var divRespostas = document.getElementById("idDivRespostas");
		while (divRespostas.firstChild) {
			divRespostas.removeChild(divRespostas.firstChild);
		}
		divRespostas.appendChild(divRespostaCorreta);
		divRespostas.appendChild(divRespostaSelecionada);

		$("#modalVotacao").modal("hide");
		$('#correcao').modal('show');
	}

}

function redirecionarAoFinalizarAtividade() {
	window.location = "http://" + Host.ip + ":" + Host.porta + "/Ovaces/Aluno/Inicio.jsp";
}

function ping() {
	obterIdSessao();
}
