# EarthQuake-Android-App

The EarthQuake android application uses United States Geological Survey API to get information about earthquakes happened around the whole world. 

API Endpoint ---> https://earthquake.usgs.gov/fdsnws/event/1/ 

REQUEST_URL  ---> https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=4&limit=10

To make request to this api first make background service, we have used AsyncTask to request to this API
The async task contains two main methods

1) doInBackground()

this method runs in the background,here we pass api url and try to fetch the data. once the data is fetched completely in json format next step is to parse the json for that purpose JSONArray and JSONObject classes are used. after parsing the json the listview with earthquake information is created and passed to onPostExecute() method.

2) onPostExecute()

this method runs in foreground and present the data in listview format.

once the data is presented in listview you can get more details about earthquake by clicking on list item.
