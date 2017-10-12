var loja = localStorage.getItem('logista');
$(document).ready(function() {
	if(loja == null || loja == "" || loja == undefined){
		$('#confirm-modal').modal('toggle');	
	}else{
		ajustar();
	}

	$.ajax({
		url: "http://192.168.3.17:8443/preencher-loja",
		type : 'GET',
		success: function(data){
			console.log(data);
			$.each(data, function (index, value) {
				console.log(value);
				$('#selectLoja').append($('<option>', { 
					value: value.id,
					text : value.nome
				}));
			});
		}
	})
})

function lojaSelecionada(){
	$('#confirm-modal').modal('toggle');
	loja = $("#selectLoja").find(":selected").text();
	ajustar();
	
	localStorage.setItem('logista', loja);
}

function sair(){
	localStorage.removeItem("user");
}

function ajustar(){
	$("#lojaEscolhida").text("");
	$(".alterLoja").removeClass("col-xs-6 col-sm-6 col-md-8");
	$(".alterLoja").addClass("col-xs-4 col-sm-4 col-md-2");
	$(".alterTrocar").removeClass("col-xs-3 col-sm-3 col-md-2");
	$(".alterTrocar").addClass("col-xs-5 col-sm-5 col-md-4");
	$("#trocarLoja").text("Trocar loja");
	$("#textoCategoria").text("Olá "+loja+", qual categoria deseja falar hoje?");
	$(".cat").removeClass("hide");
	$("#selectLoja option[value='0']").remove();
}

function categoriaSelecionada(bt){
	localStorage.setItem('categoria', $(bt).text());
	return true;
}

function trocarLojas(){
	$('#confirm-modal').modal('toggle');
}