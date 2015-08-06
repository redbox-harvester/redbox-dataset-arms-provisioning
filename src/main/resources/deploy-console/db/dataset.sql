/*
	Creating dataset table and importing dataset records.
*/
\p Dropping existing 'dataset' table...
\c true
DROP TABLE "dataset_source" CASCADE;
\c false

\p Creating table "dataset_source"...
CREATE TABLE "dataset_source" (
	"datasetId" integer identity,
	"owner" varchar(20),
	"last_updated" timestamp,
	"title" varchar(50)	
);
CREATE VIEW "dataset" AS 
	SELECT "datasetId" "datasetId", "owner" "owner", "last_updated" "last_updated", "title" "workflow.metadata.formData.title", "title" "tfpackage.title", "title" "tfpackage.dc:title"
	FROM "dataset_source";  
\p Inserting demo records into 'dataset_source'...
INSERT INTO "dataset_source" ("datasetId","owner","last_updated","title") VALUES (1,'admin','2013-11-11 13:11:53','Title 1');
INSERT INTO "dataset_source" ("datasetId","owner","last_updated","title") VALUES (2,'admin','2013-11-12 10:27:52','Title 2');
INSERT INTO "dataset_source" ("datasetId","owner","last_updated","title") VALUES (3,'admin','2013-11-12 10:27:56','Title 3');
INSERT INTO "dataset_source" ("datasetId","owner","last_updated","title") VALUES (4,'admin','2013-11-12 10:28:01','Title 4');
INSERT INTO "dataset_source" ("datasetId","owner","last_updated","title") VALUES (5,'admin','2013-11-12 10:28:05','Title 5');

COMMIT;