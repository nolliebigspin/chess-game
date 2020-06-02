# Chess-Game

## [Introduction](./docs/content/text/introduction.md)

## [Manual](./manual.pdf)

## [Storycards](./storycards.pdf)

## [Requirements analysis](./requirements-analysis.pdf )

## [Project plan](./project-plan.pdf)

## Javadoc
To read javadoc please navigate into `./docs/javadoc` and open `index.html` in your favorite browser.

## [Diagrams](./docs/content/diagrams/index.md)

## Checker
To run the Checker, just enter the following commands:

1) `$ mvn clean compile javafx:jlink`
2) Use the command for your current OS:
* Unix: `$ java -jar checker.jar "target/schach/bin/schach --no-gui" > log.txt`
* Windows: `$ java -jar checker.jar "target/schach/bin/schach.bat --no-gui" > log.txt`