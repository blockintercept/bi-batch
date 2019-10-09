# BlockIntercept Batch   

A Simple batch app that loads data from a database and creates a transaction sending the data in raw format into an ethereum instance through JSON_RPC client

##### Requirements

* java 1.8 or more
* mysql (Optional - needed for persistent job store) and also mysql is the data store currently supported.



##### Configuration File

```
config/bi-batch.properties
```
Copy this file to any directory <config.path>

##### Run the bi-scheduler

###### through spring-boot maven plugin

```
	mvn spring:boot:run -Dspring.config.location=file:/<config.path>
```

###### through built java application (from target directory)

```
	java -jar bi-batch-0.0.1.jar --spring.config.location=file:/<config.path>
```

##### configuration parameters

The address to which the transaction goes to, this can be any valid ethereum address

```
	blockintercept.eth.blockchain.to.address = 0xb1650EC34f07a6acdE464C398f931Bc516Cb1BEf
```

Any valid ethereum private key, this needs to have find to pay for the transaction gas 

```
	blockintercept.eth.blockchain.private.key = bda2eaa4e921aab5bb11074ea564a87752618e7cac82a8b33da995cffe2f0aee
```

The JSON RPC node host and port

```
	blockintercept.eth.blockchain.rpc.node = http://localhost:7545
```

the source datastore type - currently supports mysql 

```
	blockintercept.batch.readerType = db_mysql
```

The data store schema, replace <your schema with yours>

```
	blockintercept.batch.readerType.db_mysql.jdbc-url = jdbc:mysql://localhost:3306/<your_schema>
```

MySQL driver, leave it as it is unless the driver class changes 

```
	blockintercept.batch.readerType.db_mysql.driver= com.mysql.cj.jdbc.Drive
```
Credentails for the remote data store

```
	blockintercept.batch.readerType.db_mysql.* 
```

The query your wish to execute, just replace the table name, you can add a where predicate also

```
	blockintercept.batch.readerType.db_mysql.query = select * from <your_table>
```

There are two REST GET calls that drive the batch

```
	http://localhost:8090/startAgent
```

```
	http://localhost:8090/getBlockchainData/<transactionHash>
```

The transaction hash can be captured from the logs in the following statement


```
19:34:17.573 [http-nio-8090-exec-5] INFO  i.b.b.writer.BlockInterceptWriter - sent data to blockchain, transaction 0xaa904b37b37ccd05a553fb98de74ce82cf8ba4cd6dfa81a6939b0225c008a363

```
 