import java.io.File;

//def scriptConfig= new ConfigSlurper(config.environment).parse(configFile.toURI().toURL())

println "Running the dependency Injector ${libDir}"
def libDirectory = new File("${libDir}")
def fileNames = libDirectory.list()
def pathList = []
for (fileName in fileNames) {
  pathList.add("resources/lib/"+fileName)
}
def configFile = new File("C:/git/github/redbox-harvester/arms-provisioning-redbox-collection/src/main/resources/deploy-manager/harvester-config.groovy")
def scriptConfig= new ConfigSlurper("production").parse(configFile.toURI().toURL())
println "trying to get environments"

scriptConfig.environments.classPathEntries = pathList

configFile.withWriter { writer ->
				def conf = new ConfigObject()
				conf["environments"]["production"] = scriptConfig
				conf.writeTo(writer)
							 
		}	