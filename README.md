# MongoDB Persistence 비교

* 주로 사용되는 3가지 방법을 간단하게 구현해서, 어떤 방법이 훨씬 직관적일지 보기 위한 예시. Service를 별도 구현하지 않고, controller에 때려 넣었음. (mongo access하는 코드 비교만
  하면 되니..)
<hr/>

## JDBC Driver

#### module: driver-only

* JDBC driver로만 DB access DB CRUD를 위한 common한 util 직접 구현해야함.<br/>
  eg. _com.kakao.at.ticketdev.appservices.MongoRepoService.class_

insert (_User.class_)

```
// db.userCol.insertOne({"_id": "aaa", "passwd": "aaa", "userType": "Admin"});
JSONObject document = new JSONObject();
document.put("_id", id);
document.put("passwd", passwd);
document.put("userType", userType);

JSONObject queryObject = new JSONObject();
queryObject.put("action", "insert");
queryObject.put("collection", "userCol");
queryObject.put("document", document);

JSONObject result = mongoRepoService.query(queryObject);
```

find (_User.class_)

```
// db.userCol.find({}, {"userType": 1})
// criteria
JSONObject criteria = new JSONObject();
if (!StringUtils.isEmpty(userType)) {
    criteria.put("userType", userType);
}
// projection
JSONObject projection = new JSONObject();
projection.put("userType", 1);

JSONObject queryObject = new JSONObject();
queryObject.put("action", "find");
queryObject.put("collection", "userCol");
queryObject.put("criteria", criteria);
queryObject.put("projection", projection);

JSONObject result = mongoRepoService.query(queryObject);
```

<hr/>

## Spring data mongoDb with simple mongoTemplate

#### module: spring-data-mongo

* _com.kakao.at.ticketdev.config.MongoConfig.class_ 에 Simple mongoTemplate 설정후 사용. DB CRUD는 mongoTemplate의 method를 통해서
  수행함.

insert (_User.class_)

```
// db.userCol.insertOne({"_id": "aaa", "passwd": "aaa", "userType": "Admin"});
JSONObject document = new JSONObject();
document.put("_id", id);
document.put("passwd", passwd);
document.put("userType", userType);

JSONObject result = mongoTemplate.insert(document, "userCol");
```

find (_User.class_)

```
// db.userCol.find({}, {"userType": 1})
Query query = new Query();
// criteria
if (!StringUtils.isEmpty(userType)) {
    query.addCriteria(Criteria.where("userType").is(userType));
}
// projection
query.fields().include("userType");
List<JSONObject> userList = mongoTemplate.find(query, JSONObject.class, "userCol");
```

## Spring data mongodb with Repository

#### module: spring-data-mongo-repository

* _com.kakao.at.ticketdev.config.MongoConfig.class_ 에서 @EnableMongoRepositories annoation추가하고,
  AbstractMongoClientConfiguration 상속함.
* _com.kakao.at.ticketdev.repository.UserRepository_ interface 생성하고, 구현 class는 작성할 필요 없이 메소드정의에 @Query annotation으로
  메소드구현체 정의.
* _com.kakao.at.ticketdev.user.UserEntity.class_ 에 @Document, @Id annotation에 MongoDB collection과 _id field 설정.

insert (_User.class_)

```
// db.userCol.insertOne({"_id": "aaa", "passwd": "aaa", "userType": "Admin"});
UserEntity userEntity = new UserEntity(id, passwd, userType);
UserEntity result = userRepository.insert(userEntity);
```

find (_User.class_)

```
//db.userCol.find({}, {"userType": 1})
if (StringUtils.hasText(userType)) {
    userList = userRepository.findByUserType(userType);
} else {
    userList = userRepository.findAll();
}
```