play-app-oauth2-to-smartsheet
=============================

The usings Play Framework 2.1 to oauth2 to Smartsheet.


STEPS FOR SETUP:

- Obtain a developer account from Smartsheet : http://www.smartsheet.com/developers
- Create a Smartsheet app, this will create client_id and app secret.
- Install play 2.1, I used brew ```brew install play```
- Git clone the play-app-oauth2-to-smartsheet repro
- Add configurations to application.conf. Specicially the client_id, secret and redirect_url
- Then run play ```play clean compile "run <port>" ```
- Hit localhost:<port> in the browser

