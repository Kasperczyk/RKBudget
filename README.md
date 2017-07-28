# RKBudget

A budgeting web application that I'm currently developing for personal needs and to improve my programming skills. 
I could just use one of the gazillion available budgeting apps, but doing it myself allows me to learn something and 
build it exactly the way I want (being PO and Dev at the same time sure has its advantages).

## Technology Stack

- Spring Boot Application
- JSF PrimeFaces front end
- Spring Boot Data JPA
- PostgreSQL database (H2 for testing and development)

### Reasoning Q&A

Q: Why Spring Boot?

A: It's great, simple, efficient at what it does.

***

Q: Why would you use JSF (combined with Spring instead of Java EE at that ...)?

A: Because this seems to be what is prevalent in enterprises I happen to work for and I want to become more proficient at
doing my job. At a later stage I might port it to Angular (or React) and implement a REST API.

***

Q: Why Spring Boot Data JPA?

A: Again, it's great, simple, efficient at what it does.

***

Q: Why PostgreSQL and H2?

A: *TODO*

***

## Package structure

I'm going to try to package by feature in this project. It sounds good in theory (having everything related to a feature
in one place, being able to use the package-private modifier). Another reason is curiosity: every project I've worked on
professionally used package by layer and I want to see how package by feature works out and feels.

## Architecture

- JSF Facelets have access to Spring managed Controllers ("managed beans")
    - each Facelet has its own Controller
- Controllers are relatively dumb and rely on Services to perform business logic
    - each Controller can only other Controllers and its own Service
    - if a Controller needs something from another Service, it must use its own Service as a proxy
- Services perform business logic and use Repositories to interact with the database
    - Services can only use the feature specific Repository; if it needs data corresponding to 
    another feature, it must use that one's Service
- everything should be as protected as possible (mostly package protected, public if necessary)

## Code conventions / guidelines

- prefer lambdas over classic loops
- use constructor injection
- one line per attribute in Facelets
- pattern for i18n keys: feature_type_name (type and name with camelCase)
- write unit tests for new logic

## MVP (Minimum Viable Product)

- App starts at Login / Registration page
- User can register for the website and receives an email with a verification link
- Registered user can log into the app and is redirected to a welcome or tutorial page on their first visit
- Users can change their settings in the profile menu
- Users can add their accounts
- Users can set the colors of categories
- Users can create custom tags
- Users can add fixed income and expenses (+ bill reminder)
- Fixed income and expenses are automatically processed via batch jobs
- Users can add variable income and expenses
- Users can share costs with other users (or non users)
- Shared costs are calculated and presented in an end of month report or when requested
- Users can set monthly limits to categories (sub categories) and define a warning threshold -> when reached they get a message
- User can see the balance of each month and year

- Users can send Bugreports/Feature requests
- Impressum / Contact
- Deployed on Heroku(?)

=> search users to test the app!

- dashboard and calculators not included in MVP (probably)

## How to run locally

1. Clone the project to your file system via **git clone https://github.com/Kasperczyk/RKBudget.git**
2. cd into the project
3. Run **mvn clean install** on the command line
4. Start the app with **mvn spring-boot:run -Dspring.profiles.active=dev-h2**
5. Open up your favorite browser and navigate to **localhost:8080**
6. If port 8080 is already used by some other process (that you don't want to stop), 
you can change it by either setting server.port=port in the 
application-dev-h2.properties or adding **-Dserver.port=port** as a VM argument where 
port is whatever port you may want to use instead of 8080.
7. You can use the following predefined users:
    - email: kasperczyk.rene@gmail.com, user name: Vyrwel, password: secret
    - email: vyrwel@gmail.com, user name: Ryana, password: geheim