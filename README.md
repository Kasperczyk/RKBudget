#RKBudget
A budgeting web application that I'm currently developing for personal needs and to improve my programming skills. 
I could just use one of the gazillion available budgeting apps, but doing it myself allows me to learn something and 
build it exactly the way I want (being PO and Dev at the same time sure has its advantages).

##Technology Stack
- Spring Boot Application
- JSF PrimeFaces front end
- Spring Boot Data JPA
- PostgreSQL database (H2 for testing and development)

###Reasoning Q&A
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

##Package structure
I'm going to try to package by feature in this project. It sounds good in theory (having everything related to a feature
in one place, being able to use the package-private modifier). Another reason is curiosity: every project I've worked on
professionally used package by layer and I want to see how package by feature works out and feels.

##Architecture
- JSF Facelets have access to Spring managed Controllers ("managed beans")
    - each Facelet has its own Controller
- Controllers are relatively dumb and rely on Services to perform business logic
    - each Controller can access several Services
- Services perform business logic and use Repositories to interact with the database
    - Services can only use the feature specific Repository; if it needs data corresponding to 
    another feature, it must use that one's Service

##Code conventions / guidelines
- prefer lambdas over classic loops
- use constructor injection
- one line per attribute in Facelets
- pattern for i18n keys: feature_type_name (type and name with camelCase)
- write unit tests for new logic