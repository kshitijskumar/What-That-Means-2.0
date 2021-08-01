# What-That-Means-2.0
## Project Description:
Text recognition based dictionary app.
Uses Firebase ML Kit along with CameraX api for scanning and processing the text. Gives you a list of words scanned and then that word can be clicked to get the meaning and other relevant data about it.
The camera scans the words in every 5 seconds, to avoid continuous scanning and processing of the frames.
## Tech/Libs used:
- CameraX : For camera functionality.
- Firebase ML Kit : For text extraction and recognition.
- Retrofit : For handling network calls to get meaning and other information.
- Coroutines: For handling asynchronous stuff.
- MVVM architecture.
## Demo: 
<img src="https://github.com/kshitijskumar/What-That-Means-2.0/blob/main/demo/foreign%20lang.gif" height=400> &nbsp;&nbsp;

## Cloning and trying the app:
Get your api key from https://rapidapi.com/microsoft-azure-org-microsoft-cognitive-services/api/microsoft-translator-text and then add this {RAPID_API_KEY="x-rapidapi-key: _your_key_"} in local.properties file.
