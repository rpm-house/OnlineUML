var graph, paper;
// The Browser API key obtained from the Google Developers Console.
var developerKey = 'AIzaSyAxhnzjThT2DPrAvB-_NG3MPk6kv-PuWYo';

// The Client ID obtained from the Google Developers Console. Replace
// with your own Client ID.
var clientId = "441886590368-scl4j3np9n369mm0vmct6pfprq69i1is.apps.googleusercontent.com"

// Scope to use to access user's photos.
var scope = [ 'https://www.googleapis.com/auth/drive.readonly' ];

var pickerApiLoaded = false;
var oauthToken;

// Use the API Loader script to load google.picker and gapi.auth.
function openFiles(x) {
	gapi.load('auth', {
		'callback' : onAuthApiLoad
	});
	if (x == 1) {
		gapi.load('picker', {
			'callback' : onPickerApiLoad
		});
	} else {
		uploadFile();
	}
}

function onAuthApiLoad() {
	window.gapi.auth.authorize({
		'client_id' : clientId,
		'scope' : scope,
		'immediate' : false
	}, handleAuthResult);
}

function onPickerApiLoad() {
	pickerApiLoaded = true;
	createPicker();
}

function handleAuthResult(authResult) {
	if (authResult && !authResult.error) {
		oauthToken = authResult.access_token;
		createPicker();
	}
}

// Create and render a Picker object for picking user Photos.
function createPicker() {
	if (pickerApiLoaded && oauthToken) {
		var picker = new google.picker.PickerBuilder().addViewGroup(
				new google.picker.ViewGroup(google.picker.ViewId.DOCS).addView(
						google.picker.ViewId.DOCUMENTS).addView(
						google.picker.ViewId.PRESENTATIONS)).setOAuthToken(
				oauthToken).setDeveloperKey(developerKey).setCallback(
				pickerCallback).build();
		picker.setVisible(true);
	}
}

// A simple callback implementation.
/*
 * function pickerCallback(data) { var url = 'nothing'; var id; var file; if
 * (data[google.picker.Response.ACTION] == google.picker.Action.PICKED) { var
 * doc = data[google.picker.Response.DOCUMENTS][0]; url =
 * doc[google.picker.Document.URL]; id = doc[google.picker.Document.Id]; file =
 * doc[google.picker.Document]; } var url1= url.split("/d/"); // alert("file:
 * "+file) // alert("download Url :"+file.downloadUrl) alert("url : "+url)
 * alert("url1 : "+url1[1]) var url2 = url1[1].split("/view"); alert("url2 :
 * "+url2[0]) var message = 'You picked URL: ' + url + 'You picked Id: ' + id;
 * //document.getElementById('result').innerHTML = message; //downloadFile(url);
 * downloadFile(url2[0]); }
 */
function pickerCallback(data) {
	if (data.action == google.picker.Action.PICKED) {
		var fileId = data.docs[0].id;
		// var url = doc[google.picker.Document.URL];
		// alert('The user selected: ' + fileId);
		// alert('The url selected: ' + data.docs[0]);
		downloadFile(fileId);
		// alert(data.docs[0].Data);
		// printFile(fileId)
	}
}
function printFile(fileId) {
	// alert("File Id : " + fileId);
	var request = gapi.client.drive.files.get({
		'fileId' : fileId
	});
	request.execute(function(resp) {
		alert(resp.file);
		document.getElementById('result').innerHTML = resp.title;
		console.log('Title: ' + resp.title);
		console.log('Description: ' + resp.description);
		console.log('MIME type: ' + resp.mimeType);
	});
}

/**
 * Download a file's content.
 * 
 * @param {File}
 *            file Drive File instance.
 * @param {Function}
 *            callback Function to call when the request is complete.
 */
function downloadFile(fileId, callback) {
	// var file =
	// "https://www.googleapis.com/drive/v2/files/"+fileId;
	var downloadUrl = "https://drive.google.com/uc?export=download&id="
			+ fileId
	if (downloadUrl) {
		// alert("downloadUrl :" + downloadUrl);
		var accessToken = gapi.auth.getToken().access_token;
		var xhr = new XMLHttpRequest();
		xhr.open('GET', downloadUrl);
		xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
		xhr.onload = function() {
			// alert(xhr.responseText);
			// document.getElementById('result').innerHTML =
			// xhr.responseText;
			// alert("Response :" + xhr.responseText)
			// alert("json :" + JSON.parse(xhr.responseText));
			// alert("graph :" + graph.fromJSON(JSON.parse(xhr.responseText)));
			// callback(xhr.responseText);
			$(".mask").hide();
			$("#import_content").fadeOut();
			return graph.fromJSON(JSON.parse(xhr.responseText));
		};
		xhr.onerror = function() {
			callback(null);
		};
		xhr.send();
	} else {
		callback(null);
	}
}

function uploadFile(evt) {
	// alert("4");
	gapi.client.load('drive', 'v2', function() {
		insertFile();
	});
}
/**
 * Insert new file.
 */
function insertFile() {
	// alert("5");
	const
	boundary = '-------314159265358979323846264';
	const
	delimiter = "\r\n--" + boundary + "\r\n";
	const
	close_delim = "\r\n--" + boundary + "--";
	/*
	 * var appState = { number : 12, text : 'hello' };
	 */
	var fileName = $("#file_Gtext").val() + ".json";
	// var jsonString = JSON.stringify(graph);
	// var fileName = 'newUML111.txt';
	var contentType = 'application/json';
	var metadata = {
		'title' : fileName,
		'mimeType' : contentType
	};
	var base64Data = btoa(JSON.stringify(graph));
	var multipartRequestBody = delimiter
			+ 'Content-Type: application/json\r\n\r\n'
			+ JSON.stringify(metadata) + delimiter + 'Content-Type: '
			+ contentType + '\r\n' + 'Content-Transfer-Encoding: base64\r\n'
			+ '\r\n' + base64Data + close_delim;
	var request = gapi.client.request({
		'path' : '/upload/drive/v2/files',
		'method' : 'POST',
		'params' : {
			'uploadType' : 'multipart'
		},
		'headers' : {
			'Content-Type' : 'multipart/mixed; boundary="' + boundary + '"'
		},
		'body' : multipartRequestBody
	});
	request.execute(function(arg) {
		if (null != arg.id) {
			$(".saveGMask").hide();
			$("#save_Gcontent").fadeOut();
			$(".resultMask").show();
			$("#result_content").fadeIn();
			$("#result_text").html("Saved Successfully");
		}
		console.log(arg);
	});
	// alert("6");
}
