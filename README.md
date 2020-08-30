# Optoviva

Web app that allows a user to quickly find out which area in a city is the optimal place to live in, based on the user's
preferences.

Tech used:
- Java 8+, JavaScript, HTML, CSS
- Dropwizard 2.x
- Postgres 12.x
- Heroku

# How to build

```
mvn clean package
```

# How to set up

This web app is designed to run on Heroku. You can run it locally using the
[Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) with a local Postgres database (macOS with Homebrew):
- Install Maven, `brew install mvn`
- Install local Postgres, `brew install postgres`
- Create the local database and user with Postgres tools, `createdb optoviva;createuser optoviva -s`
- Install Heroku CLI, `brew tap heroku/brew && brew install heroku`
- Create a file named `.env` in the project root with the following contents:
```
PORT=9090
DATABASE_URL=postgres://optoviva:optoviva@localhost:5432/optoviva
```
- Build Optoviva, `mvn package`
- Set up the database tables by commenting out all lines in Procfile, except line 2, then run `heroku local`
- Load data into the database commenting out all lines in Procfile, except line 4, then run `heroku local`
- Now make sure all the lines are commented out except the last one in Procfile. The app is now ready to run

# How to run

```
heroku local
```
The web app should now be available at [http://localhost:9090](http://localhost:9090)

Test the database using the API:
```
curl localhost:9090/api/location

[
  {
    "id": 2,
    "name": "Barcelona",
    "description": "Capital of Catalonia, Spain"
  },
  {
    "id": 1,
    "name": "Stockholm",
    "description": "Capital of Sweden"
  }
]
```


