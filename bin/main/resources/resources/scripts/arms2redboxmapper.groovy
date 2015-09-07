import org.springframework.integration.Message
import org.springframework.integration.annotation.Header
import org.springframework.integration.annotation.Payload
import org.springframework.integration.annotation.Transformer
import org.springframework.integration.file.FileHeaders
import org.springframework.integration.support.MessageBuilder
import groovy.util.ConfigObject
import groovy.json.*
import java.io.File

def inputMessage= data

String payload = new String(inputMessage.getPayload())
def payloadJson = new JsonSlurper().parseText(payload)
def templateJson = new JsonSlurper().parse(new FileReader(new File(config.redbox.templatePath)))

templateJson['data']['data']['datasetId'] = payloadJson['oid']
templateJson['data']['data']['workflow.metadata']['formData']['description'] = payloadJson['collection:description']
templateJson['data']['data']['workflow.metadata']['formData']['title'] = payloadJson['dc:title']

templateJson['data']['data']['tfpackage']['title'] = payloadJson['dc:title']
templateJson['data']['data']['tfpackage']['dc:title'] = payloadJson['dc:title']
templateJson['data']['data']['tfpackage']['dc:description'] = payloadJson['collection:description']
templateJson['data']['data']['tfpackage']['description'] = payloadJson['collection:description']
templateJson['data']['data']['tfpackage']['locrel:dtm.foaf:Agent.foaf:name'] = payloadJson['dataprovider:givenName'] + " " + payloadJson['dataprovider:familyName']

//Set the primary contact
templateJson['data']['data']['tfpackage']['locrel:prc.foaf:Person.foaf:givenName'] = payloadJson['dataprovider:givenName']
templateJson['data']['data']['tfpackage']['locrel:prc.foaf:Person.foaf:familyName'] = payloadJson['dataprovider:familyName']
templateJson['data']['data']['tfpackage']['locrel:prc.foaf:Person.foaf:email'] = payloadJson['dataprovider:email']

templateJson['data']['data']['tfpackage']['bibo:Website.1.dc:identifier'] = payloadJson['discovery-metadata']

templateJson['data']['data']['tfpackage']['armsRequestOid'] = payloadJson['oid']

templateJson['data']['data']['tfpackage']['redbox:formVersion'] = config.redbox.versionNumber


//Set FOR Codes
def forCodePurls = payloadJson["dc:subject.anzsrc:for.0.rdf:resource"]
def forCodeLabels  = payloadJson["dc:subject.anzsrc:for.0.skos:prefLabel"]
for (def i=0; i< forCodePurls.size(); i++) {
	def index= i+1;
	templateJson['data']['data']['tfpackage']['dc:subject.anzsrc:for.'+index+'.rdf:resource'] = forCodePurls.get(i)
	templateJson['data']['data']['tfpackage']['dc:subject.anzsrc:for.'+index+'.skos:prefLabel'] = forCodeLabels.get(i)
} 

def payloadString = JsonOutput.toJson(templateJson)
 
final Message<String> message = MessageBuilder.withPayload(JsonOutput.prettyPrint(payloadString)).setHeader("type","dataset").build()

data =  message
