# movery

An example of multi-pane layout Android app using XML based Fragments. 

### Features

* Display list of deliveries
* Display the details for each delivery
* Open SMS messaging app to contact the receiver name

### Handset screenshots

<img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202318.png" width="200" height="415"> <img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202425.png" width="200" height="415"> <img src="https://github.com/neilvinas/movery/blob/master/screenshots/handset/device-2018-01-10-202507.png" width="200" height="415"> 

### Tablet screenshots

<img src="https://github.com/neilvinas/movery/blob/master/screenshots/tablet/device-2018-01-10-202959.png" width="559" height="415">

### Getting started

The app uses [JsonStub](http://jsonstub.com/) as data source and Google maps for location. The following keys are needed to compile the app.
* [Google Map API](https://developers.google.com/maps/documentation/android-api/signup) 
* JsonStub-User-Key & JsonStub-Project-Key - contact me for this details or you can set-up your own json stub using the following data format
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

### Things to note
* The project priority is to achieve the basic requirements and create a "production-ready" solution
* No particular architectural pattern were used to avoid over engineering as the requirement only has two screens for a handset and one for a tablet

### Technical approach
To support multi-pane layout Fragments were used. On a tablet, multiple Fragments were placed in one Activity while on a handset, separate Activities were used to host each Fragment. 

### Libraries used
Third party libraries were used as to not reinvent the wheel in solving common problems such as performing network calls and offline handling.

* [Retrofit](http://square.github.io/retrofit/) - to perform network calls and retrieve data from the API
* [Realm](https://realm.io/) - used as the persistence layer to cache data for offline handling
* [Dagger](https://github.com/google/dagger) - for dependency injection

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
