function logar(){
	$("mensagemErro").addClass("hide");
	var user = $("#user").val();
	var password = $("#password").val();

	$.ajax({
		url: "http://192.168.3.17:8443/login/"+user+"/"+password+"/",
		type : 'POST',
		success: function(data){
			if(data){
				console.log("teste");
				var usuario =  {"usuário": [{"user": user, "true": data}]};
				localStorage.setItem('user', usuario);
				window.open('categoria.html', '_self', 'location=no');
			}else{
				$("#mensagemErro").removeClass("hide");
			}
		},
		error: function(){
			$("#mensagemErro").removeClass("hide");
		}
	})
}