# Reports Web
*Repository for Web App Development discipline*

## Инфа для запуска

### Postgresql
- поднимается **_локально_**
- порт: `5432`
- имя базы: `ReportsWeb`
- username: `postgres`
- password: `admin`
- 
### OpenSearch
- поднимается **_в докере_**
- версия: `3.8`
- порты: `9200` и `9600`
- container_name: `opensearch-reportsweb`
- порт: `4560`

### Logstash
- поднимается **_в докере_**
- порт: `4560`
- container_name: `logstash-reportsweb`

### Kibana
- поднимается **_в докере_**
- container_name: `kibana-reportsweb`
- порт: `5601`

## Describtion
> ***ToDo:*** *fix docker opensearch, maybe healthcheck.sh is needed; pages ...-by-name look ugly*

**Subject area:** Reports
### Explanation of Reports subject area
- Report:
	- id - not null, unique, string;
	- theme - not null, string;
	- value - string;
	- reporterName - string;;
	- conferenceName - string.


- Reporter:
	- id - not null, unique, string;
	- reporterName - not null, string;
	- competence - string;;
	- organization - string.


- Conference:
	- id - not null, unique, string;
	- confName - not null, string;
	- year - number;
	- hostName - string;
	- founderName - string.


- Host:
	- id - not null, unique, string;
	- hostName - not null, string.


- Founder:
	- id - not null, unique, string;
	- founderName - not null, string.

|Report|Reporter|Conference|Host|Founder|
|-|-|-|-|-|
|id - not null, unique, string|id - not null, unique, string|id - not null, unique, string|id - not null, unique, string|id - not null, unique, string|
|theme - not null, string|reporterName - not null, string|confName - not null, string|hostName - not null, string|founderName - not null, string|
|value - string|competence - string|year - number|||
|reporterName - string|organization - string|hostName - string|||
|conferenceName - string||founderName - string|||

### ERD for Reports subject area
![ReportsERDfromSQL.png](ReportsERDfromSQL.png)
