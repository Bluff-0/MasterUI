# MasterUI [Ext.js]

#### Projects Included

* Java_Training_2021
* Struts_WebApp
* Spring_WebApp

### Java_Training_2021

> The main UI and Servelets of the project are present here. The folder structure is defined as

```
├───css
├───db
├───images
│   └───favicon_io
├───js
│   ├───Data
│   ├───extjs
│   └───js
│       ├───Functions
│       ├───PageComponent
│       ├───Renderers
│       └───Stores
├───META-INF
└───WEB-INF
    └───lib
```

The servelets structure is

```
└───hrc
    ├───dbData
    │       ComboboxRetrieve.java
    │       SakilaModification.java
    │       SakilaPojo.java
    │       SakilaRetrieve.java
    │
    └───tools
            DebugLogger.java
            ExtractDistinct.java
            ReadQuery.java
            SQLConnect.java
```

##### Servlet Mapping

```XML
<servlet>
	<name>Sakila Data Retrieval</name>
	<url>/Java_Training_2021/api/getSakilaData</url>
</servlet>
<servlet>
	<name>Sakila Set Retrieval</name>
	<url>/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=special_features&type=set</url>
</servlet>
<servlet>
	<name>Sakila Enum Retrieval</name>
	<url>/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=rating&type=enum</url>
</servlet>
<servlet>
	<name>Sakila Table Column Retrieval</name>
	<url>/Java_Training_2021/api/getComboboxData?db=sakila&table=language&column=name&type=table</url>
</servlet>
<servlet>
	<name>Sakila Data Insert</name>
	<url>/Java_Training_2021/api/sakilaModification?qType=insert</url>
</servlet>
<servlet>
	<name>Sakila Data Update</name>
	<url>/Java_Training_2021/api/sakilaModification?qType=update</url>
</servlet>
<servlet>
	<name>Sakila Data Delete</name>
	<url>/Java_Training_2021/api/sakilaModification?qType=delete</url>
</servlet>
```