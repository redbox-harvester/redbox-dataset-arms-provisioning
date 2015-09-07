client {
    harvesterId = 'genericHarvester'
    description = 'Generic Harvester'
    base = ''
    autoStart = true
    siPath = 'applicationContext-SI-harvester-console.xml'
    classPathEntries = []
}
file {
    runtimePath = 'runtime/harvester-config-console.groovy'
    customPath = 'custom/harvester-config-console.groovy'
    ignoreEntriesOnSave = ['runtime']
}
harvest {
    pollRate = '5000'
    pollTimeout = '30000'
    scripts {
        scriptBase = 'resources/scripts/'
        preBuild = []
        preAssemble = [['arms2redboxmapper.groovy': '']]
        postBuild = []
    }
    rabbitmq {
        url = '127.0.0.1'
//        username = 'arms'
//        password = 'rbadmin'        
//        queuename = 'arms-redbox'
        username = 'demo'
        password = 'demo'        
        queuename = 'livearc-provisioning'
        port = 5672
    }
    activemq {
    	url = 'tcp://localhost:9101'
    }
}
redbox{
	templatePath = 'resources/scripts/template-data/dataset-template.json'
	versionNumber = '1.9-SNAPSHOT'
}
