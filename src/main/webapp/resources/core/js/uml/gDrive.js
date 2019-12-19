var graph, paper;

var CLIENT_ID = '441886590368-scl4j3np9n369mm0vmct6pfprq69i1is.apps.googleusercontent.com';
var SCOPES = 'https://www.googleapis.com/auth/drive.readonly';
/**
 * Called when the client library is loaded to start the auth flow.
 */

function changeFileLocation() {
	var fileLocation = $("#fileLocation").val();
	var authButton = document.getElementById('authorizeButton');
	var doitButton = document.getElementById('doitButton');
	var submitSave = document.getElementById('submit_save');
	var cancelSave = document.getElementById('cancel_save');
	if (fileLocation == "GDrive") {
		alert("21");
		submitSave.style.display = 'none';
		cancelSave.style.display = 'block';
		saveFiles1();
	} else {
		alert("1");
		authButton.style.display = 'none';
		doitButton.style.display = 'none';
		submitSave.style.display = 'block';
		cancelSave.style.display = 'block';
	}
	// alert(fileLocation);
}

function saveFile() {
	alert("2");
	window.setTimeout(checkAuth, 5);
}

/**
 * Check if the current user has authorized the application.
 */

function saveFiles1() {
	alert("test");
	gapi.load('auth', {
		'callback' : checkAuth
	});

}

function checkAuth() {
	window.gapi.auth.authorize({
		'client_id' : clientId,
		'scope' : scope,
		'immediate' : false
	}, handleAuthResult);
}

function handleAuthResult(authResult) {
	alert("authResult " + authResult)
	if (authResult && !authResult.error) {
		oauthToken = authResult.access_token;
		alert("oauthToken " + oauthToken)
		saveFiles();
	}
}
/**
 * Called when authorization server replies.
 * 
 * @param {Object}
 *            authResult Authorization result.
 */

function saveFiles(authResult) {
	alert("3");
	var authButton = document.getElementById('authorizeButton');
	var doitButton = document.getElementById('doitButton');
	authButton.style.display = 'none';
	doitButton.style.display = 'none';
	if (authResult && !authResult.error) {
		// Access token has been successfully retrieved, requests can be sent to
		// the API.
		doitButton.style.display = 'block';
		doitButton.onclick = uploadFile;
	} else {
		// No access token could be retrieved, show the button to start the
		// authorization flow.
		authButton.style.display = 'block';
		authButton.onclick = function() {
			gapi.auth.authorize({
				'client_id' : CLIENT_ID,
				'scope' : SCOPES,
				'immediate' : false
			}, handleAuthResult);
		};
	}
}
/**
 * Start the file upload.
 * 
 * @param {Object}
 *            evt Arguments from the file selector.
 */
function uploadFile(evt) {
	alert("4");
	gapi.client.load('drive', 'v2', function() {
		insertFile();
	});
}
/**
 * Insert new file.
 */
function insertFile() {
	const
	boundary = '-------314159265358979323846264';
	const
	delimiter = "\r\n--" + boundary + "\r\n";
	const
	close_delim = "\r\n--" + boundary + "--";
	/*
	 * var appState = { number : 12, text : 'hello' };
	 */
	var fileName = $("#save_text").val();
	// var jsonString = JSON.stringify(graph);
	// var fileName = 'newUML11.txt';
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
			$(".saveMask").hide();
			$("#save_content").fadeOut();
			$(".resultMask").show();
			$("#result_content").fadeIn();
			$("#result_text").html("Saved Successfully");
		}
		console.log(arg);
	});
}

function retrieveAllFiles(callback) {
	var retrievePageOfFiles = function(request, result) {
		request.execute(function(resp) {
			result = result.concat(resp.items);
			var nextPageToken = resp.nextPageToken;
			if (nextPageToken) {
				request = gapi.client.drive.files.list({
					'pageToken' : nextPageToken
				});
				retrievePageOfFiles(request, result);
			} else {
				callback(result);
			}
		});
	}
	var initialRequest = gapi.client.drive.files.list();
	retrievePageOfFiles(initialRequest, []);
}