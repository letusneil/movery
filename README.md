# movery

An example of very basic multi-pane layout Android app.

### Features

* Display list of deliveries
* Display the details for each delivery

### Handset screenshots

<img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202318.png" width="200" height="415"> <img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202425.png" width="200" height="415"> <img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202507.png" width="200" height="415"> 

### Tablet screenshots

<img src="https://github.com/neilvinas/movery/blob/master/screenshots/tablet/device-2018-01-10-202959.png" width="559" height="415">

### Getting started

The app uses [JsonStub](http://jsonstub.com/) as data source and Google maps for location. The following keys are needed to compile the app.
* [Google Map API](https://developers.google.com/maps/documentation/android-api/signup) 
* JsonStub-User-Key & JsonStub-Project-Key - set-up your own json stub using the following data format
```
{
	"status": "ok",
	"deliveries": [{
		"id": 12,
		"description": "Deliver steam moneys to Gabe",
		"imageUrl": "https://cdn-images-1.medium.com/max/1200/1*tOkpfLYZ1XK-UlIFtz5HmA.jpeg",
		"receiverName": "Gabe Newell",
		"availability": "12 noon onwards",
		"contactNo": "+65123912389",
		"fees": 10,
		"location": {
			"lat": 47.610378,
			"lng": -122.200676,
			"address": "Bellevue, WA"
		}
	}...
}
```
Once these data are obtained, replace the API keys inside res/strings.xml.

### Approach
To support multi-pane layout Fragments were used. On a tablet, multiple Fragments were placed in one Activity while on a mobile, separate Activities were used to host each Fragment. 


# License

	Copyright 2017 Neil Vinas
	
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
