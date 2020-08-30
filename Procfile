# 1. Create tables in database (only once)
#web: java -jar -Ddw.server.connector.port=$PORT target/optoviva-1.0.0-SNAPSHOT.jar db migrate config.yml
# 2. Load default data into database (will replace existing data)
#web: java -jar -Ddw.server.connector.port=$PORT target/optoviva-1.0.0-SNAPSHOT.jar load -d src/test/resources/data.sql config.yml
# 3. Run app
web: java -jar -Ddw.server.connector.port=$PORT target/optoviva-1.0.0-SNAPSHOT.jar server config.yml