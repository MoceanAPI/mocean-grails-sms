Mocean SMS Grails Plugin
=


## Description
This plugin provide sending SMS capability to grails framework for user using [MoceanAPI](https://moceanapi.com) service

Try for FREE now. 20 trial SMS credits will be given upon [registration] (https://dashboard.moceanapi.com/register?fr=grails). Additional SMS credits can be requested and is subject to approval by MoceanAPI

## Configuration
Add your credentials to your application.yml like sample below.

```yaml
moceanapi:
    api_key: YOUR_MOCEAN_API_KEY
    api_secret: YOUR_MOCEAN_API_SECRET

```

Add dependencies
~~~
dependencies {
    ...
    compile "org.grails.plugins:mocean-grails-sms:0.0.1"
}
~~~


Add repo
~~~
repositories {
    ...
	maven { url "http://dl.bintray.com/moceanapi/plugins" }
}

~~~


## Usage

#### Normal:
If you only need send SMS can follow the sample below.
~~~java

import com.moceanapi.sms.SmsService

class Testing {

    def SmsService
    
    def index() { 
       
        def from = "SENDER_NUMBER"
        def to = "RECEIVER_NUMBER"
        def text = "MESSAGE_BODY"
        Map response = SmsService.send(from, to, text)
    }
}

~~~

#### Advance:
If you wish to have more configuration options to your SMS. Please visit our [API docs](https://moceanapi.com/docs/#send-sms) to find out more parameters 

~~~java

import com.moceanapi.sms.SmsService

class Testing {

    def SmsService
    
    def index() { 
       
        Map map = ["mocean-from": "SENDER", "mocean-to": "RECEIVER", "mocean-text": "MESSAGE_BODY"]
        Map response = SmsService.send(map)
    }
}

~~~
