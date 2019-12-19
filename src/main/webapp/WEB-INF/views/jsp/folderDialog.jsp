<html>
<head>
  <title>Browse Folders</title>
<SCRIPT LANGUAGE="JavaScript">

var currentFolder="";
function GetDriveList(){
  var fso, obj, n, e, item, arr=[];
try { 
	alert('opne Model');
  fso = new ActiveXObject("Scripting.FileSystemObject"); 
  //fso = getDriveList1(); 
} 
catch(er) {
  alert('Could not load Drives. The ActiveX control could not be started.');
  cancelFolder();
} 
  e = new Enumerator(fso.Drives); 
  for(;!e.atEnd();e.moveNext()){ 
    item = e.item();
    obj = {letter:"",description:""};
    obj.letter = item.DriveLetter;
    if (item.DriveType == 3) obj.description = item.ShareName;
    else if (item.IsReady) obj.description = item.VolumeName;
    else obj.description = "[Drive not ready]";
    arr[arr.length]=obj;
  } 
  return(arr);
}


function getDriveList1()
{
	try {
	    // test to see if XMLHttpRequest is defined
	    XMLHttpRequest.DONE;
	}
	catch (e) {
	    XMLHttpRequest = new Object();
	    // define also all the constants
	    XMLHttpRequest.UNSENT = 0;
	    XMLHttpRequest.OPENED = 1;
	    XMLHttpRequest.HEADERS_RECEIVED = 2;
	    XMLHttpRequest.LOADING = 3;
	    XMLHttpRequest.DONE = 4;
	}
	 
	/** Creates new instance of the XMLHttpRequest object */
	XMLHttpRequest.newInstance = function() {
	    var xmlHttp = null;
	    // use the ActiveX control for IE5.x and IE6
	    try {
	        xmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
	    } catch (othermicrosoft){
	        try { 
	            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	        } 
	        catch (native) {
	            // If IE7, Mozilla, Safari, etc: Use native object
	            xmlHttp = new XMLHttpRequest();
	        } 
	    } 
	 
	    return xmlHttp;
	};
	}


function GetSubFolderList(fld){
  var e, arr=[];
  var fso = new ActiveXObject("Scripting.FileSystemObject");
  var f = fso.GetFolder(fld.toString());
  var e = new Enumerator(f.SubFolders);
  for(;!e.atEnd();e.moveNext()){ 
    arr[arr.length]=e.item().Name;
  }
  return(arr);
}
function loadDrives(){
  var drives=GetDriveList(),list="";
  for(var i=0;i<drives.length;i++){
    list+="<div onclick=\"loadList('"+drives[i].letter+':\\\\\')" class="folders" onmouseover="highlight(this)" onmouseout="unhighlight(this)">'+drives[i].letter+':\\ - '+ drives[i].description+'</div>';
  }
  document.getElementById("path").innerHTML='<a href="" onclick="loadDrives();return false" title="My Computer">My Computer</a>\\';
  document.getElementById("list").innerHTML=list;
  currentFolder="";
}
function loadList(fld){
  var path="",list="",paths=fld.split("\\");
  var divPath=document.getElementById("path");
  var divList=document.getElementById("list");
  for(var i=0;i<paths.length-1;i++){
    if(i==paths.length-2){
      path+=paths[i]+' \\';
    }else{
      path+="<a href=\"\" onclick=\"loadList('";
      for(var j=0;j<i+1;j++){
        path+=paths[j]+"\\\\";
      }
      path+='\');return false">'+paths[i]+'</a> \\ ';
    }
  }
  divPath.innerHTML='<a href="" onclick="loadDrives();return false">My Computer</a> \\ '+path;
  divPath.title="My Computer\\"+paths.toString().replace(/,/g,"\\");
  currentFolder=paths.toString().replace(/,/g,"\\");

  var subfolders=GetSubFolderList(fld);
  for(var j=0;j<subfolders.length;j++){
    list+="<div onclick=\"loadList('"+(fld+subfolders[j]).replace(/\\/g,"\\\\")+'\\\\\')" onmouseover="highlight(this)" onmouseout="unhighlight(this)" title="'+subfolders[j]+'" class="folders">'+subfolders[j]+"</div>";
  }
  divList.innerHTML=list;
  resizeList();
  divPath.scrollIntoView();
}
function resizeList(){
  var divList=document.getElementById("list");
  var divPath=document.getElementById("path");
  if(document.body.clientHeight>0 && divPath.offsetHeight>0){
    divList.style.height=document.body.clientHeight-divPath.scrollHeight;
  }
}
function highlight(div){
  div.className="folderButton";
}
function unhighlight(div){
  div.className="folders";
}
function selectFolder(){
  window.returnValue=currentFolder;
  window.close();
}
function cancelFolder(){
  window.returnValue="";
  window.close();
}

</SCRIPT>
<style>
#header{
  background-color: #CCCCCC;
  border-bottom: solid 1px black;
}
 #path{
  position:relative;
  font-size: 8pt;
  font-family: Arial;
  font-weight: bold;
  padding: 2px;
} 
#list{
  font-size: 10pt;
  font-family: Arial;
  overflow:auto;
}
.folders{
  padding: 1px;
  border-top: solid 1px white;
  border-left: solid 1px white;
  border-right: solid 1px white;
  border-bottom: solid 1px black;
  cursor: hand;
  pointer: hand;
  background-color: white;
}
.folderButton{
  padding: 0px;
  border-style: outset;
  border-width: 2px;
  border-color:;
  cursor: hand;
  pointer: hand;
  background-color: #CCCCCC;
}
A{
  color:blue;
  text-decoration:none;
  padding:3px;
}
A:hover{
  background-color: #CCCCCC;
  padding:1px;
  border-style: outset;
  border-width: 2px;
}


.browseMask
{
	display: none;
	position: fixed;
	background: white;
	opacity: 0.7;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
}

#browseContent
{
	display: none;
	position: absolute;
	top: 70%;
	left: 30%;
	width: 550px;
	height: 40px;
	margin-left: -250px;
	margin-top: -200px;
	background: white;
	text-align: center;
	border: 1px solid #333;
}

</style>
</head>
<body onload="loadDrives()" onresize="resizeList()" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" scroll=no>
<form>

<!-- <div class="browseMask"></div>
	<div id="browseContent">
		<div id="path"></div>
		<textarea id="import_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea><br/>
		 <input type="button" value="Select" onclick="selectFolder()"><input type="button" value="Cancel" onclick="cancelFolder()">
	</div> -->

<div id="container">
  <table border="0" cellpadding="0" cellspacing="0" id="header">
    <tr>
      <td><div id="path"></div></td>
      <td align="right" width="1%" nowrap>
        <input type="button" value="Select" onclick="selectFolder()"><input type="button" value="Cancel" onclick="cancelFolder()">
      </td>
    </tr>
  </table>
  <div id="list">You must allow the ActiveX control to run in order to use this dialog.</div>
</div>
</form>
</body>
</html>