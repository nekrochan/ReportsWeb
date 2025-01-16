# Reports Web
*Repository for Web App Development discipline*

> ***ToDo:*** *fix docker opensearch, maybe healthcheck.sh is needed*

**Subject area:** Reports
## Explanation of Reports subject area
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

## ERD for Reports subject area
![ReportsERDfromSQL.png](ReportsERDfromSQL.png)
