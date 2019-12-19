<html>
  <head>
    <!--
    
    In this example, I started with the 5-minute example provided by Google 
    on the following page:
    
        https://developers.google.com/drive/
        
    I modified the example code, so that I could write the following 
    Javascript object as a json string into a file called 
    csusbdt-drive-example-app-state.txt.
    
        var appState = {
          number: 12,
          text: 'hello'
        };
    
    -->
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
    <script type="text/javascript">    
      var CLIENT_ID = '441886590368-scl4j3np9n369mm0vmct6pfprq69i1is.apps.googleusercontent.com';
      var SCOPES = 'https://www.googleapis.com/auth/drive';
      /**
       * Called when the client library is loaded to start the auth flow.
       */
      function handleClientLoad() {
        window.setTimeout(checkAuth, 1);
      }
      /**
       * Check if the current user has authorized the application.
       */
      function checkAuth() {
        gapi.auth.authorize(
            {'client_id': CLIENT_ID, 'scope': SCOPES, 'immediate': true},
            handleAuthResult);
      }
      /**
       * Called when authorization server replies.
       *
       * @param {Object} authResult Authorization result.
       */
      function handleAuthResult(authResult) {
        var authButton = document.getElementById('authorizeButton');
        var doitButton = document.getElementById('doitButton');
        authButton.style.display = 'none';
        doitButton.style.display = 'none';
        if (authResult && !authResult.error) {
          // Access token has been successfully retrieved, requests can be sent to the API.
          doitButton.style.display = 'block';
          doitButton.onclick = uploadFile; 
        } else {
          // No access token could be retrieved, show the button to start the authorization flow.
          authButton.style.display = 'block';
          authButton.onclick = function() {
              gapi.auth.authorize(
                  {'client_id': CLIENT_ID, 'scope': SCOPES, 'immediate': false},
                  handleAuthResult);
          };
        }
      }
      /**
       * Start the file upload.
       *
       * @param {Object} evt Arguments from the file selector.
       */
      function uploadFile(evt) {
        gapi.client.load('drive', 'v2', function() {
          insertFile();
        });
      }
      /**
       * Insert new file.
       */
      function insertFile() {
        const boundary = '-------314159265358979323846264';
        const delimiter = "\r\n--" + boundary + "\r\n";
        const close_delim = "\r\n--" + boundary + "--";
        var appState = {
          number: 12,
          text: 'hello'
        };
        var fileName = 'newUML23.txt';
        var contentType = 'application/json';
        var metadata = {
          'title': fileName,
          'mimeType': contentType
        };
        var base64Data = btoa(JSON.stringify(appState));
        var multipartRequestBody =
            delimiter +
            'Content-Type: application/json\r\n\r\n' +
            JSON.stringify(metadata) +
            delimiter +
            'Content-Type: ' + contentType + '\r\n' +
            'Content-Transfer-Encoding: base64\r\n' +
            '\r\n' +
            base64Data +
            close_delim;
        var request = gapi.client.request({
            'path': '/upload/drive/v2/files',
            'method': 'POST',
            'params': {'uploadType': 'multipart'},
            'headers': {
              'Content-Type': 'multipart/mixed; boundary="' + boundary + '"'
            },
            'body': multipartRequestBody});
        request.execute(function(arg) {
          console.log(arg);
        });
      }
    </script>
    <script type="text/javascript" src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
  </head>
  <body>
    <input type="button" id="doitButton"      style="display: none" value="Do it"  onclick="alert('hiiii')" /> <br>
    <input type="button" id="authorizeButton" style="display: none" value="Authorize" />
  </body>
</html>