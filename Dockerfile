FROM openjdk:21

WORKDIR /usrapp/bin

ENV PORT=6000

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency
COPY target/classes/static /resources/static

CMD ["java","-cp","./classes:./dependency/*","edu.escuelaing.app.DemoApplication"]