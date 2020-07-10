# simple-object-relation-mapping-framework (SORM)
This is a simple Java framework used to interact with Database system. Firstly, this framework would scan the meta-data of the database and
automatically generate persistent object(PO) class source files. Then, it it can convert records from database to PO class and manipulate
records in databse through objects of PO class as well.

Several things worth notice before use:   
1. A configuration file called "db.properties" is used (the name is fixed and cannot be changed). A sample configuration file can be found under src, all properties
in it are used and must be filled properly. (the first char of the database using should be upper case).
2. Only primary key is supported. In other words, joint key is not supported.
3. The Name of the auto generated PO classes is corresponding to tables.
4. To generate source code of PO classes, just specify the package in configuration file and run the main function.
5. To use this framework, firstly add the jar as a dependency or add all source code to your project. Then add this two line of codes:   
`Query query = QueryFactory.getQuery(); //get query object`         
`TableContext.updateMap(); // init map from PO Class to table info`   
An example is given in test class.
6. It is assumed that package of PO class would be generated under directory of "src".
7. All interactions with database are performed through object of subclass of "Query" class. "Query" class contain some common operations and each subclass contains some
database specific operation like pagination. In this demo, only "MySQLQuery" subclass is provided and no database specific function is implemented. Add other subclass 
and implement needed functions if you want.

Hope this brief introduction helpful and please see comments in source code for more details.
