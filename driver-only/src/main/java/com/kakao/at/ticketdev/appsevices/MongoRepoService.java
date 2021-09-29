package com.kakao.at.ticketdev.appsevices;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoRepoService {
	private final MongoDatabase mongoDatabase;

	public MongoRepoService() {
		//String conString = "mongodb://ticketdev:ticketdev@10.202.4.201";
		String conString = "mongodb://127.0.0.1";
		ConnectionString connectionString = new ConnectionString(conString);

		// MongoClient settings
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.retryWrites(true)
				.build();

		MongoClient mongoClient = MongoClients.create(mongoClientSettings);
		this.mongoDatabase = mongoClient.getDatabase("jdbcDriverTestDb");
	}

	public JSONObject query(JSONObject queryObj) {
		String action = queryObj.get("action").toString();
		JSONObject result = null;

		try {
			switch (action) {
				case "insert":
					result = doInsertOne(queryObj);
					return result;

				case "insertMany":
				case "insertmany":
				case "insert_many":
					result = doInsertMany(queryObj);
					return result;

				case "find":
					result = doFind(queryObj);
					return result;

				case "findOne":
				case "fine_one":
				case "fineone":
					//result = doFindOne(queryObj);
					return result;

				case "findAndModify":
				case "find_and_modify":
					//result = doFindAndModify(queryObj);
					return result;

				case "delete":
					//result = doDelete(queryObj);
					return result;

				case "count":
					//result = doCount(queryObj);
					return result;

				case "aggregate":
					//result = doAggregate(queryObj);
					return result;

				case "getCollections":
				case "get_collections":
					//result = getCollections();
					return result;

				case "dropCollection":
				case "drop_collection":
					//result = dropCollection(queryObj);
					return result;

				case "collectionStats":
				case "collection_stats":
					//result = getCollectionStats(queryObj);
					return result;

				case "command":
					//result = runCommand(queryObj);
					return result;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private JSONObject doInsertOne(JSONObject queryObj) {
		String collection = queryObj.get("collection").toString();
		JSONObject queryResult = null;
		if (collection == null) {
			return queryResult;
		}
		JSONObject jsonDoc = (JSONObject) queryObj.get("document");
		Document document = Document.parse(jsonDoc.toString());

		MongoCollection coll = this.mongoDatabase.getCollection(collection);

		try {

			InsertOneResult insertOneResult = coll.insertOne(document);
			queryResult = makeReply();
		} catch (MongoException ex) {
			queryResult = makeErrorReply("fail to insert data");
		}

		return queryResult;
	}

	private JSONObject doInsertMany(JSONObject queryObj) {
		String collection = queryObj.get("collection").toString();
		JSONObject queryResult = null;
		if (collection == null) {
			return queryResult;
		}
		JSONObject jsonDoc = (JSONObject) queryObj.get("documents");
		List<JSONObject> jsonDocs = (List<JSONObject>) queryObj.get("documents");

		List<Document> documents = jsonDocs.stream()
				.map(doc -> Document.parse(doc.toString())).collect(Collectors.toList());

		MongoCollection coll = this.mongoDatabase.getCollection(collection);

		try {

			InsertManyResult insertManyResult = coll.insertMany(documents);
			queryResult = makeReply();
		} catch (MongoException ex) {
			queryResult = makeErrorReply("fail to insert data");
		}

		return queryResult;
	}

	private JSONObject doFind(JSONObject queryObj) {
		String collection = queryObj.get("collection").toString();
		JSONObject queryResult = new JSONObject();
		List<JSONObject> results = new ArrayList<>();

		if (collection == null) {
			return null;
		}

		MongoCollection coll = this.mongoDatabase.getCollection(collection);

		try {
			Bson criteria = Document.parse(queryObj.get("criteria").toString());
			Bson projection = Document.parse(queryObj.get("projection").toString());
			coll.find(criteria).projection(projection).into(results);

			queryResult = makeReply();
			queryResult.put("results", results);
			queryResult.put("size", results.size());

		} catch (MongoException ex) {
			queryResult = makeErrorReply("오류!");
			ex.printStackTrace();
		}

		return queryResult;
	}

	private JSONObject makeReply() {
		return makeReply(null);
	}

	private JSONObject makeReply(JSONObject reply) {
		if (reply == null)
			reply = new JSONObject();
		reply.put("status", 1);
		return reply;
	}

	private JSONObject makeErrorReply(String error) {
		JSONObject reply = new JSONObject();
		reply.put("status", 0);
		reply.put("message", error);
		return reply;
	}
}
