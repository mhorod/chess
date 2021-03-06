![logo](logo.png)

# chess

Chess made as an assignment project for OOP course 2021/22

Authors:
- Michał Horodecki
- Michał Pajda
- Dominik Matuszek

## Installation
Project is built with [Maven](https://maven.apache.org/)
Application requires Java 17 to run

1. Clone the repository
2. In the directory containing `pom.xml` run `mvn clean javafx:run`
3. Alternatively you can run `app.Main` class from your IDE
4. You can build the project with `mvn package` or using provided script `./package.sh` - this will create a jar file inside `target` directory

## Changelog

### 5-05-2022 goals [Completed]

- [x] basic UI interactions
- [x] game skeleton
- [x] integrating ui with game core

### 26-06-2022 goals [Completed]
- [x] menus
- [x] color scheme selection
- [x] complete chess logic
- [x] optional chess configuration (for now at compile time)
- [x] api for external players and ai (almost)
- [x] checkers and minesweeper

### 9-06-2022 goals [Completed]
- [x] ai moving after delay
- [x] minor code fixes
- [x] some documentation
- [x] compilation to jar 

# Contribution percentage
Person | Total contribution percentage
--- | --- |
Michał Horodecki | 34%
Dominik Matuszek | 34%
Michał Pajda | 34%
Kacper Topolski | -2%

# Screenshots

![s1](screenshots/s1.png)
![s2](screenshots/s2.png)
![s3](screenshots/s3.png)
![s4](screenshots/s4.png)
![s5](screenshots/s5.png)
![s6](screenshots/s6.png)
![s7](screenshots/s7.png)

# Project architecture

![uml](uml.png)
