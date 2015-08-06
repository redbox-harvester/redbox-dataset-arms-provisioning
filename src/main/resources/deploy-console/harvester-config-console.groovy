	client {
	harvesterId='redboxDatasetJdbcAdapterConsole'
	description='Sample ReDBox Dataset JDBC Harvester-Console'
	base=''
	autoStart=true
	siPath='applicationContext-SI-harvester-console.xml'
	classPathEntries=['resources/lib/hsqldb-2.3.1.jar']
	inboundAdapter='inboundJdbcAdapter'
}
file {
	runtimePath='runtime/harvester-config-console.groovy'
	customPath='custom/harvester-config-console.groovy'
	ignoreEntriesOnSave=['runtime']
}
harvest {
	rabbitmq {
		url='127.0.0.1'
		username='demo'
		password='demo'
		queuename='arms-redbox'
		port=5672
	}
	jdbc {
		user='SA'
		pw=''
		driver='org.hsqldb.jdbcDriver'
		url='jdbc:hsqldb:file:db/data/local'
		Dataset {
			query='SELECT * FROM "dataset" WHERE "last_updated" >= TIMESTAMP(:last_harvest_ts)'
			sqlParam.last_harvest_ts='2015-06-05 13:08:25'
		}
	}
	pollRate='120000'
	pollTimeout='60000'
	scripts {
		scriptBase='resources/scripts/'
		preBuild=[]
		preAssemble=[['merge.groovy':'merge-config.groovy']]
		postBuild=[['update_last_harvest_ts.groovy':''], ['saveconfig.groovy':'']]
	}
}
activemq.url='tcp://localhost:9101'
lastSaved='2015-06-05 01:08:25'
redboxTemplatePath='resources/scripts/template-data/dataset-template.json'
