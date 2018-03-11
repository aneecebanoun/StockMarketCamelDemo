$( document ).ready(function() {
	setTimeout(function(){
		var currentStockMarketURL= $("#currentStockMarketAjax").val(); 
		$.post(currentStockMarketURL,function(result){
			stockMarketDisplay(result);
	    });
	}, 7*100);
	
	setInterval(function(){
		var currentStockMarketURL= $("#currentStockMarketAjax").val(); 
		$.post(currentStockMarketURL,function(result){
			stockMarketDisplay(result);
	    });
	}, 60*1000*7);
});

function stockMarketDisplay(result){
    // display update
    $('#googleId').text(result.Google);
    $("#googleId").attr("style", result.GoogleColor);
    $('#appleId').text(result.Apple);
    $("#appleId").attr("style", result.AppleColor);
    $('#oracleId').text(result.Oracle);
    $("#oracleId").attr("style", result.OracleColor);
    $('#iBMId').text(result.IBM);
    $("#iBMId").attr("style", result.IBMColor);
    $('#barclaysId').text(result.Barclays);
    $("#barclaysId").attr("style", result.BarclaysColor);
    $('#lloydsId').text(result.LloydsUS);
    $("#lloydsId").attr("style", result.LloydsUSColor);
    $('#morganId').text(result.JPMorgan);
    $("#morganId").attr("style", result.JPMorganColor);
    $('#citiId').text(result.Citi);
    $("#citiId").attr("style", result.CitiColor);
    $('#stockUpdateTimeId').text(result.stockUpdateTimeId);
    //chart update
	var googleUrlId = $("#googleUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgGoogleId").attr("src", googleUrlId);
	var appleUrlId = $("#appleUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgAppleId").attr("src", appleUrlId);
	var oracleUrlId = $("#oracleUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgOracleId").attr("src", oracleUrlId);
	var ibmUrlId = $("#ibmUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgIbmId").attr("src", ibmUrlId);
	var barclaysUrlId = $("#barclaysUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgBarclaysId").attr("src", barclaysUrlId);
	var lloydsUrlId = $("#lloydsUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgLloydsId").attr("src", lloydsUrlId);
	var morganUrlId = $("#morganUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgMorganId").attr("src", morganUrlId);
	var citiUrlId = $("#citiUrlId").attr("value")+"&RANDOM="+Math.random(); 
	$("#imgCitiId").attr("src", citiUrlId);
}
