 
<style type="text/css">
#colorBox{
	border: 1px solid #33FF99;
	background-color: #FFFFCC;
	position: relative;
	visibility: hidden;
	z-index: 2;
	height: 75px;
	width: 342px;
	left: 5px;
	top: -10px;
}

</style>
<script language="JavaScript1.2">
var katmangizlimi=false;
function katmanGizle()
{
	if(document.all)
	{
		document.all.colorBox.style.visibility="hidden";
		document.all.colorBox.style.height="21px";
		katmangizlimi=true;
	}
	else if(document.layers)
	{
		document.layers.colorBox.style.visibility="hidden";
		document.layers.colorBox.style.height="21px";
		katmangizlimi=true;
	}
	
}
function katmanGoster()
{
	if(document.all)
	{
		document.all.colorBox.style.visibility="visible";
		document.all.colorBox.style.height="100px";
		katmangizlimi=false;
	}
	else if(document.layers)
	{
		document.layers.colorBox.style.visibility="visible";
		document.layers.colorBox.style.height="100px";
		katmangizlimi=false;
	}
	
}
	
</script>
<P><a href="#" onMouseOver="katmanGoster()" >Sorular</a></P>
<div id="colorBox" onMouseOver="katmanGoster()" onMouseOut="katmanGizle()"> <a href="<%=response.encodeURL("cikissicakligi.jsp")%>">Çýkýþ 
  Sýcaklýðý</a><br>
  <a href="<%=response.encodeURL("civalitermometre.jsp")%>">Civalý 
  Termometre</a><br>
  <a href="<%=response.encodeURL("suyundengesicakligi.jsp")%>">Suyun 
  Denge Sýcaklýðý</a><br>
  <a href="javascript:katmanGizle()">MENÜYÜ KAPAT</a> </div>
