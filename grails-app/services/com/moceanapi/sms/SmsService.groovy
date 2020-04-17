package com.moceanapi.sms

import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import org.apache.http.HttpEntity
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.CloseableHttpResponse


class SmsService {

    static transactional = false
    def grailsApplication


    /**
     * Send message with Mocean REST API.
     * @param map: list of params
     * @returns Map: the response
     */
    Map send(Map map) {

        map["mocean-api-key"] = grailsApplication.config.moceanapi.api_key
        map["mocean-api-secret"] = grailsApplication.config.moceanapi.api_secret

        return httpRequest(map)
    }

    /**
     * Send message with Mocean REST API.
     * @param from: recipient number
     * @param to: sender number ( from twilio )
     * @param body: message body
     * @returns Map: the response
     */
    Map send(String from, String to, String body) {

        String api_key = grailsApplication.config.moceanapi.api_key
        String api_secret = grailsApplication.config.moceanapi.api_secret
        
        return send(api_key, api_secret, from, to, body)
    }

    /**
     * Send message with Mocean REST API.
     * @param api_key : MoceanAPI API Key
     * @param api_secret : MoceanAPI API secret
     * @param from: sender number
     * @param to: recipent number 
     * @param body: message body
     * @returns :Map
     */
    Map send(String api_key, String api_secret,String from,String to,String body) {
        
        Map moceanPostBody = [:]        
        
        moceanPostBody = ["mocean-api-key": api_key, "mocean-api-secret": api_secret, "mocean-from": from, "mocean-to": to, "mocean-text": body]
        
        return httpRequest(moceanPostBody)
    }
    

    Map httpRequest(Map post_body) {

        HttpClient client = HttpClientBuilder.create().build()

        HttpPost httpPost = new HttpPost("https://rest.moceanapi.com/rest/2/sms")

        List<BasicNameValuePair> body = []

        post_body.each{key,value -> 
            BasicNameValuePair item = new BasicNameValuePair("${key}",value)
            body.add(item)
        }

        BasicNameValuePair item = new BasicNameValuePair("mocean-resp-format","JSON")
        body.add(item)
        
        item = new BasicNameValuePair("mocean-medium","grails")
        body.add(item)

        httpPost.entity = new UrlEncodedFormEntity(body)
        CloseableHttpResponse result = client.execute(httpPost)

        HttpEntity entity = result.getEntity()
        String responseBody = EntityUtils.toString(entity)

        def jsonSlurper = new JsonSlurper() 
        def responseMap = jsonSlurper.parseText(responseBody); 
        result.close()

        return responseMap as Map
    }
}